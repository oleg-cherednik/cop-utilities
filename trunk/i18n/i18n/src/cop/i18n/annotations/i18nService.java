/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Oleg Cherednik</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.i18n.annotations;

import cop.i18n.exceptions.i18nDeclarationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Oleg Cherednik
 * @since 16.08.2010
 */
public final class i18nService {
	private static final String[] NO_STRING = new String[0];
	private static final Method[] NO_METHODS = new Method[0];

	private i18nService() {}

	public static <T> String getTranslation(Class<T> cls, String key) throws i18nDeclarationException {
		return getTranslation(cls, key, null);
	}

	public static <T> String getTranslation(Class<T> cls, String key, Locale locale) throws i18nDeclarationException {
		if (isEmpty(key))
			throw new IllegalArgumentException("key is empty");

		Class<?> parameterType = locale != null ? Locale.class : null;
		Method[] methods = getAnnotatedMethods(cls, i18n.class, String.class, parameterType);

		if (methods.length == 0)
			return "";

		if (methods.length > 1)
			throw new i18nDeclarationException("There're to many methods annotatated with @i18n. Can't choose.");

		if (methods[0].getReturnType() != String.class)
			throw new i18nDeclarationException("Method annotatated with @i18n must return String or String[]");

		try {
			return (String)invokeMethod(null, methods[0], key, locale);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	// TODO write checks
	// for ENUM
	public static String[] getTranslations(Class<?> item, Locale locale) throws i18nDeclarationException {
		if (item == null)
			throw new NullPointerException("item == null");

		if (!item.isEnum())
			return NO_STRING;

		Object[] constants = item.getEnumConstants();

		if (isEmpty(constants))
			return NO_STRING;

		Method[] methods = getAnnotatedMethods(item, i18n.class, Locale.class);

		if (isEmpty(methods))
			return NO_STRING;

		if (methods.length > 1)
			throw new i18nDeclarationException("There're to many methods annotatated with @i18n. Can't choose.");

		// if(methods.length > 2)
		// throw new AnnotationDeclarationException(
		// "Annotation @i18n can be use maximum 2 time per enum. See annotation description.");

		try {
			return (String[])invokeMethod(null, methods[0], locale);
			// Method method = getRightMethod(methods[0], (methods.length == 2) ? methods[1] : null);

			// return null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return NO_STRING;
	}

	// private static Method getRightMethod(Method method1, Method method2) throws AnnotationDeclarationException
	// {
	// Assert.isNotNull(method1);
	//
	// Class<?> returnType1 = method1.getReturnType();
	// Class<?> returnType2 = isNotNull(method2) ? method2.getReturnType() : null;
	//
	// for(Class<?> type : new Class<?>[] { returnType1, returnType2 })
	// if(isNotNull(type) && !type.isAssignableFrom(String.class) && !type.isAssignableFrom(String[].class))
	// throw new AnnotationDeclarationException("Method annotatated with @i18n must return String or String[]");
	//
	// if(returnType1.equals(returnType2))
	// throw new AnnotationDeclarationException("Methods annotatated with @i18n must return String or String[]");
	//
	// if(isNull(returnType2))
	// return method1;
	//
	// return method1.getReturnType().isAssignableFrom(String[].class) ? method1 : method2;
	// }

	// private static String[] getTranslations(Method method, Object[] constants) throws Exception
	// {
	// Assert.isNotNull(method);
	// Assert.isTrue(isNotEmpty(constants));
	//
	// Class<?> returnType = method.getReturnType();
	//
	// if(returnType.isAssignableFrom(String[].class))
	// return (String[])invokeMethod(null, method);
	//
	// if(returnType.isAssignableFrom(String.class))
	// {
	// String[] i18n = getTranslationsSingle(method, constants);
	//
	// if(isEmpty(i18n) || i18n.length != constants.length)
	// throw new AnnotationDeclarationException("Method with @i18n annotation and return type "
	// + "String[] must return exactly the same number of elements"
	// + "as enumeration constant number");
	//
	// return i18n;
	// }
	//
	// Assert.isTrue(false, "Method annotatated with @i18n must return String or String[]");
	//
	// return new String[0];
	// }

	// private static String[] getTranslationsSingle(Method method, Object[] constants) throws Exception
	// {
	// assert method != null;
	// assert !isEmpty(constants);
	//
	// String[] i18n = new String[constants.length];
	//
	// for(int i = 0, size = constants.length; i < size; i++)
	// i18n[i] = invokeMethod(constants[i], method).toString();
	//
	// return i18n;
	// }

	/*
	 * static
	 */

	private static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	private static <T> boolean isEmpty(T... arr) {
		return arr == null || arr.length == 0;
	}

	// private static <T> Object invokeMethod(T item, Method method) throws Exception
	// {
	// if(method == null)
	// return null;
	//
	// method.setAccessible(true);
	//
	// return method.invoke(item);
	// }

	private static <T> Object invokeMethod(T item, Method method, Object... args) throws Exception {
		if (method == null)
			return null;

		method.setAccessible(true);

		return method.invoke(item, args);
	}

	// private static <T extends Annotation> Method[] getAnnotatedMethods(Class<?> cls, Class<T> annotationClass) {
	// return getAnnotatedMethods(cls, annotationClass, (Class<?>)null);
	// }

	private static <T extends Annotation> Method[] getAnnotatedMethods(Class<?> cls, Class<T> annotationClass,
	                Class<?>... parameterTypes) {
		if (cls == null)
			return NO_METHODS;

		List<Method> methods = new ArrayList<Method>();

		for (Method method : getMethods(cls))
			if (isAnnotated(method, annotationClass) && isSameParameters(method, parameterTypes))
				methods.add(method);

		return methods.isEmpty() ? NO_METHODS : methods.toArray(new Method[methods.size()]);
	}

	private static Method[] getMethods(Class<?> cls) {
		if (cls == null)
			return NO_METHODS;

		Set<Method> methods = new HashSet<Method>();

		for (Method method : cls.getDeclaredMethods())
			methods.add(method);

		for (Method method : cls.getMethods())
			methods.add(method);

		return methods.toArray(new Method[methods.size()]);
	}

	private static <T extends Annotation> boolean isAnnotated(AccessibleObject obj, Class<T> annotationClass) {
		if (obj == null)
			return false;
		if (annotationClass != null)
			return obj.getAnnotation(annotationClass) != null;

		return isEmpty(obj.getAnnotations());
	}

	private static boolean isSameParameters(Method method, Class<?>... parameterTypes) {
		if (method == null)
			return false;
		if (isEmpty(parameterTypes))
			return true;

		return Arrays.equals(method.getParameterTypes(), parameterTypes);
	}
}
