/**
 * @licence GNU Leser General Public License
 *
 * $Id$
 * $HeadURL$
 */
package cop.common.utils;

import static cop.common.utils.ArrayUtils.isEmpty;
import static cop.common.utils.ArrayUtils.isNotEmpty;
import static cop.common.utils.CollectionUtils.isArraysEqual;
import static cop.common.utils.CommonUtils.isNotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class AnnotationUtils {
	private AnnotationUtils() {}

	private static Method[] getMethods(Class<?> cls) {
		if (cls == null)
			return new Method[0];

		Set<Method> res = new HashSet<Method>();

		for (Method[] methods : new Method[][] { cls.getDeclaredMethods(), cls.getMethods() })
			for (Method method : methods)
				res.add(method);

		return res.toArray(new Method[0]);
	}

	public static Method[] getAnnotatedMethods(Class<?> cls) {
		return getAnnotatedMethods(cls, null, (Class<?>)null);
	}

	public static <T extends Annotation> Method[] getAnnotatedMethods(Class<?> cls, Class<T> annotationClass) {
		return getAnnotatedMethods(cls, annotationClass, (Class<?>)null);
	}

	public static <T extends Annotation> Method[] getAnnotatedMethods(Class<?> cls, Class<T> annotationClass,
			Class<?>... parameterTypes) {
		if (cls == null)
			return null;

		List<Method> methods = new ArrayList<Method>();

		for (Method method : getMethods(cls))
			if (isAnnotated(method, annotationClass) && isSameParameters(method, parameterTypes))
				methods.add(method);

		return methods.toArray(new Method[0]);
	}

	public static Field[] getAnnotatedFields(Class<?> cls) {
		return getAnnotatedFields(cls, null);
	}

	public static <T extends Annotation> Field[] getAnnotatedFields(Class<?> cls, Class<T> annotationClass) {
		if (cls == null)
			return new Field[0];

		List<Field> fields = new ArrayList<Field>();

		for (Field field : cls.getDeclaredFields())
			if (isAnnotated(field, annotationClass))
				fields.add(field);

		return fields.toArray(new Field[0]);
	}

	private static <T extends Annotation> boolean isAnnotated(AccessibleObject obj, Class<T> annotationClass) {
		if (obj == null)
			return false;

		if (isNotNull(annotationClass))
			return isNotNull(obj.getAnnotation(annotationClass));

		return isEmpty(obj.getAnnotations());
	}

	private static boolean isSameParameters(Method method, Class<?>... parameterTypes) {
		if (method == null)
			return false;

		return isNotEmpty(parameterTypes) ? isArraysEqual(method.getParameterTypes(), parameterTypes) : true;
	}
}
