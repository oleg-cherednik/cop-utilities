/*
 * $Id$
 * $HeadURL$
 */
package cop.utils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static cop.utils.StringUtils.isEmpty;
import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;

/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 *
 * @author Oleg
 */
public final class JavaBeanUtils {
	private JavaBeanUtils() {
	}

	/**
	 * Returns property name for giving {@link Field} or {@link Method} according to JavaBean specification.<br>
	 * If <code>obj</code> is {@link Method} object, then method calls {@link #getPropertyNameByMethodName(String)}.<br>
	 * If <code>obj</code> is {@link Field} object, then method just returns field name.
	 *
	 * @param obj {@link Field} or {@link Method} object
	 * @return property name
	 * @throws IllegalArgumentException if <code>obj</code> is <code>null</code> or not {@link Field} or {@link Method}
	 * @see #getPropertyNameByMethodName(String)
	 */
	public static String getPropertyName(AccessibleObject obj) throws IllegalArgumentException {
		if (obj instanceof Field)
			return ((Field)obj).getName();

		if (obj instanceof Method)
			return getPropertyNameByMethodName(((Method)obj).getName());

		throw new IllegalArgumentException("Giving object is not Field or Method");
	}

	/**
	 * Returns setter method name for giving {@link Field} or {@link Method} according to JavaBean specification.<br>
	 * If <code>obj</code> is {@link Field} object, then method calls {@link #getSetterMethodNameByPropertyName(String)}
	 * If <code>obj</code> is {@link Method} object and it's a setter method, then method just returns method's name.<br>
	 * If <code>obj</code> is {@link Method} object and it's a getter method, then method calls
	 * {@link #getSetterMethodNameByGetterMethodName(String)}.
	 *
	 * @param obj setter/getter {@link Method} or {@link Field}
	 * @return setter method names
	 * @see #getSetterMethodNameByPropertyName(String)
	 * @see #getSetterMethodNameByGetterMethodName(String)
	 * @see #isGetterMethod(Method)
	 * @see #isSetterMethod(Method)
	 */
	public static String getSetterMethodName(AccessibleObject obj) {
		if (obj instanceof Field)
			return getSetterMethodNameByPropertyName(((Field)obj).getName());

		if (obj instanceof Method) {
			Method method = (Method)obj;

			if (isSetterMethod(method))
				return method.getName();

			if (isGetterMethod(method))
				return getSetterMethodNameByGetterMethodName(method.getName());
		}

		throw new IllegalArgumentException("Giving obj is not Field or setter/getter Method");
	}

	/**
	 * Checks giving <b>methodName</b> for valid getter method name according to JavaBean specification.
	 *
	 * @param methodName
	 * @return <code>true</code> if <b>methodName</b> is valid getter name (starts from <i>get</i> or <i>is</i>)
	 */
	public static boolean isGetterMethodName(String methodName) {
		if (isEmpty(methodName))
			return false;

		int length = methodName.length();

		return (length > 3 && methodName.startsWith("get")) || (length > 2 && methodName.startsWith("is"));
	}

	/**
	 * Checks giving <b>methodName</b> for valid setter method name according to JavaBean specification.
	 *
	 * @param methodName
	 * @return <code>true</code> if <b>methodName</b> is valid getter name (starts from <i>set</i>)
	 */
	public static boolean isSetterMethodName(String methodName) throws IllegalArgumentException {
		return isEmpty(methodName) ? false : (methodName.length() > 3 && methodName.startsWith("set"));
	}

	/**
	 * Checks giving {@link Method} for valid getter method according to JavaBean specification.
	 *
	 * @param method {@link Method} object
	 * @return <code>true</code> if {@link Method} is valid getter method
	 */
	public static boolean isGetterMethod(Method method) {
		if (!isGetterMethodName(method.getName()))
			return false;

		return method.getReturnType() != void.class;
	}

	/**
	 * Checks giving {@link Method} for valid setter method according to JavaBean specification.
	 *
	 * @param method {@link Method} object
	 * @return <code>true</code> if {@link Method} is valid setter method
	 */

	public static boolean isSetterMethod(Method method) {
		if (!isSetterMethodName(method.getName()))
			return false;

		Class<?>[] parameters = method.getParameterTypes();

		return parameters.length == 1 && parameters[0] != void.class;
	}

	/**
	 * Returns setter method for giving <code>cls</code> object by method/field name according JavaBean specification.<br>
	 * If <code>obj</code> is {@link Field} object, then method calls {@link #getSetterMethod(Field, Class)} method.<br>
	 * If <code>obj</code> is {@link Method} object, then method calls {@link #getSetterMethod(Method, Class)} method.<br>
	 *
	 * @param obj {@link Field} or {@link Method} object
	 * @param cls class to get method
	 * @return setter method object or <code>null</code> if no method found
	 * @throws IllegalArgumentException if <code>obj</code> is <code>null</code> or not {@link Field} or {@link Method}
	 * @throws NullPointerException     if <code>cls</code> is <code>null</code>
	 * @see #getSetterMethod(Field, Class)
	 * @see #getSetterMethod(Field, Class)
	 */
	public static Method getSetterMethod(AccessibleObject obj, Class<?> cls) throws IllegalArgumentException,
			NullPointerException {
		if (obj instanceof Field)
			return getSetterMethod((Field)obj, cls);

		if (obj instanceof Method)
			return getSetterMethod((Method)obj, cls);

		throw new IllegalArgumentException("Giving object is not Field or Method");
	}

	/**
	 * Returns setter method for giving <code>cls</code> object by field's name according JavaBean specification.<br>
	 * To get setter method name for property name method calls
	 * {@link JavaBeanUtils#getSetterMethodNameByPropertyName(String)} method.
	 *
	 * @param field field object
	 * @param cls   class to get method
	 * @return setter method object or <code>null</code> if no method found
	 * @see #getSetterMethodNameByPropertyName(String)
	 */
	public static Method getSetterMethod(Field field, Class<?> cls) {
		try {
			return cls.getDeclaredMethod(getSetterMethodNameByPropertyName(field.getName()), field.getType());
		} catch(NoSuchMethodException e) {
		}

		return null;
	}

	/**
	 * Returns setter method for giving <code>cls</code> object by method's name according JavaBean specification.<br>
	 * If <code>method</code> is setter method, method just returns <code>method</code> object itself.<br>
	 * If <code>method</code> is getter method, then method calls {@link #getSetterMethodNameByGetterMethodName(String)}
	 * method to get getter method name and then returns geeter method object.
	 *
	 * @param method field object
	 * @param cls    class to get method
	 * @return setter method object or <code>null</code> if no method found
	 * @see #getSetterMethodNameByGetterMethodName(String)
	 * @see #isGetterMethod(Method)
	 * @see #isSetterMethod(Method)
	 */
	public static Method getSetterMethod(Method method, Class<?> cls) {
		if (isSetterMethod(method))
			return method;

		if (isGetterMethod(method)) {
			try {
				String methodName = getSetterMethodNameByGetterMethodName(method.getName());

				return cls.getDeclaredMethod(methodName, method.getReturnType());
			} catch(NoSuchMethodException e) {
			}
		}

		return null;
	}

	/**
	 * Returns property name by getter/setter method name according JavaBean specification
	 *
	 * @param methodName setter/getter method name
	 * @return property name
	 * @throws IllegalArgumentException if <code>methodName</code> is <code>null</code> or empty
	 */
	public static String getPropertyNameByMethodName(String methodName) throws IllegalArgumentException {
		if (isEmpty(methodName))
			throw new IllegalArgumentException("Method name can't be null or empty");

		int offs = 0;

		if (methodName.startsWith("get") || methodName.startsWith("set"))
			offs = 3;
		else if (methodName.startsWith("is"))
			offs = 2;
		else
			throw new IllegalArgumentException("'" + methodName + "' is not a JavaBean method");

		if (methodName.length() < offs + 1)
			throw new IllegalArgumentException("'" + methodName + "' is not a JavaBean method");

		char letter = toLowerCase(methodName.charAt(offs));

		if (methodName.length() < offs + 2)
			return "" + letter;

		return letter + methodName.substring(offs + 1);
	}

	/**
	 * Returns setter method name by getter method name according to JavaBean specification.
	 *
	 * @param getMethodName
	 * @return setter method name
	 * @throws IllegalArgumentException if <code>getMethodName</code> is <code>null</code>, empty or not valid JavaBean
	 *                                  get method name
	 */
	public static String getSetterMethodNameByGetterMethodName(String getMethodName) throws IllegalArgumentException {
		if (isEmpty(getMethodName))
			throw new IllegalArgumentException("Get method name can't be null or empty");

		int offs = 0;

		if (getMethodName.startsWith("get"))
			offs = 3;
		else if (getMethodName.startsWith("is"))
			offs = 2;
		else
			throw new IllegalArgumentException("'" + getMethodName + "' is not a JavaBean get method");

		if (getMethodName.length() < offs + 1)
			throw new IllegalArgumentException("'" + getMethodName + "' is not a JavaBean get method");

		return "set" + getMethodName.substring(offs);
	}

	/**
	 * Returns setter method name by property name according to JavaBean specification.
	 *
	 * @param propertyName
	 * @return setter method name
	 * @throws IllegalArgumentException if <code>propertyName</code> is <code>null</code> or empty
	 */
	public static String getSetterMethodNameByPropertyName(String propertyName) throws IllegalArgumentException {
		if (isEmpty(propertyName))
			throw new IllegalArgumentException("Property name can't be null or empty");

		return "set" + toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
	}
}
