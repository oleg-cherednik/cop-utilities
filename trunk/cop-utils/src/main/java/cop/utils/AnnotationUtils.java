/*
 * $Id$
 * $HeadURL$
 */
package cop.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static cop.utils.ArrayUtils.isEmpty;

public final class AnnotationUtils {
	private static final Method[] NO_METHODS = new Method[0];
	private static final Field[] NO_FIELDS = new Field[0];

	private AnnotationUtils() {
	}

	private static Method[] getMethods(Class<?> cls) {
		if (cls == null)
			return NO_METHODS;

		Set<Method> res = new HashSet<Method>();

		for (Method[] methods : new Method[][] { cls.getDeclaredMethods(), cls.getMethods() })
			Collections.addAll(res, methods);

		return res.isEmpty() ? NO_METHODS : res.toArray(new Method[res.size()]);
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

		return methods.isEmpty() ? NO_METHODS : methods.toArray(new Method[methods.size()]);
	}

	public static Field[] getAnnotatedFields(Class<?> cls) {
		return getAnnotatedFields(cls, null);
	}

	public static <T extends Annotation> Field[] getAnnotatedFields(Class<?> cls, Class<T> annotationClass) {
		if (cls == null)
			return NO_FIELDS;

		List<Field> fields = new ArrayList<Field>();

		for (Field field : cls.getDeclaredFields())
			if (isAnnotated(field, annotationClass))
				fields.add(field);

		return fields.isEmpty() ? NO_FIELDS : fields.toArray(new Field[fields.size()]);
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
