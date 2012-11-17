/**
 * @licence GNU Leser General Public License
 *
 * $Id$
 * $HeadURL$
 */
package cop.common.utils;

import static cop.common.utils.JavaBeanUtils.getSetterMethod;
import static cop.common.utils.CommonUtils.isNotNull;
import static cop.common.utils.CommonUtils.isNull;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ReflectionUtils
{
	private ReflectionUtils()
	{}

	public static String getObjectName(AccessibleObject obj)
	{
		if(isNull(obj))
			return "";

		if(obj instanceof Field)
			return ((Field)obj).getName();

		if(obj instanceof Method)
			return ((Method)obj).getName();

		return "";
	}

	public static <T> Object getFieldValue(T item, Field field) throws Exception
	{
		if(isNull(field))
			return null;

		field.setAccessible(true);

		return field.get(item);
	}

	public static <T> void setFieldValue(T item, Field field, Object value) throws Exception
	{
		if(isNull(field))
			return;

		field.setAccessible(true);
		field.set(item, value);
	}

	public static <T> Object invokeMethod(T item, Method method) throws Exception
	{
		if(isNull(method))
			return null;

		method.setAccessible(true);

		return method.invoke(item);
	}

	public static <T> Object invokeMethod(T item, Method method, Object... args) throws Exception
	{
		if(isNull(method))
			return null;

		method.setAccessible(true);

		return method.invoke(item, args);
	}

	public static <T> Object invoke(T item, AccessibleObject obj) throws Exception
	{
		if(isNull(obj) || isNull(item))
			return null;

		if(obj instanceof Field)
			return getFieldValue(item, (Field)obj);

		if(obj instanceof Method)
			return invokeMethod(item, (Method)obj);

		return null;
	}

	public static <T> Object invoke(T item, AccessibleObject obj, Object... args) throws Exception
	{
		if(isNull(obj) || isNull(item))
			return null;

		if(obj instanceof Field)
		{
			if(args == null || args.length != 1)
				throw new IllegalArgumentException("For fileds must be only one argument");

			setFieldValue(item, (Field)obj, args[0]);
			return null;
		}

		if(obj instanceof Method)
			return invokeMethod(item, getSetterMethod(obj, item.getClass()), args);

		return null;
	}

	public static Class<?> getType(AccessibleObject obj)
	{
		return getType(obj, null);
	}

	public static Class<?> getType(AccessibleObject obj, Class<?> def)
	{
		if(obj instanceof Field)
			return ((Field)obj).getType();
		if(obj instanceof Method)
			return ((Method)obj).getReturnType();

		return def;
	}

	// public static boolean isLocalizable(AccessibleObject obj)
	// {
	// Class<?> type = getType(obj);
	//
	// if(type == null)
	// return false;
	//
	// try
	// {
	// type.asSubclass(Localizable.class);
	// return true;
	// }
	// catch(Exception e)
	// {}
	//
	// return false;
	// }

	public static boolean isString(Class<?> type)
	{
		return isNotNull(type) ? type.isAssignableFrom(String.class) : false;
	}

	public static boolean isBoolean(Class<?> type)
	{
		if(isNull(type))
			return false;

		return type.isAssignableFrom(boolean.class) || type.isAssignableFrom(Boolean.class);
	}

	public static boolean isNotBoolean(Class<?> type)
	{
		return !isBoolean(type);
	}

	public static boolean isInteger(Class<?> type)
	{
		if(isNull(type))
			return false;

		return type.isAssignableFrom(int.class) || isIntegerWrapper(type);
	}

	public static boolean isIntegerWrapper(Class<?> type)
	{
		return isNotNull(type) ? type.isAssignableFrom(Integer.class) : false;
	}

	public static boolean isByte(Class<?> type)
	{
		if(isNull(type))
			return false;

		return type.isAssignableFrom(byte.class) || isByteWrapper(type);
	}

	public static boolean isByteWrapper(Class<?> type)
	{
		return isNotNull(type) ? type.isAssignableFrom(Byte.class) : false;
	}

	public static boolean isShort(Class<?> type)
	{
		if(isNull(type))
			return false;

		return type.isAssignableFrom(short.class) || isShortWrapper(type);
	}

	public static boolean isShortWrapper(Class<?> type)
	{
		return isNotNull(type) ? type.isAssignableFrom(Short.class) : false;
	}

	public static boolean isLong(Class<?> type)
	{
		if(isNull(type))
			return false;

		return type.isAssignableFrom(long.class) || isLongWrapper(type);
	}

	public static boolean isLongWrapper(Class<?> type)
	{
		return isNotNull(type) ? type.isAssignableFrom(Long.class) : false;
	}

	public static boolean isFloat(Class<?> type)
	{
		if(isNull(type))
			return false;

		return type.isAssignableFrom(float.class) || isFloatWrapper(type);
	}

	public static boolean isFloatWrapper(Class<?> type)
	{
		return isNotNull(type) ? type.isAssignableFrom(Float.class) : false;
	}

	public static boolean isDouble(Class<?> type)
	{
		if(isNull(type))
			return false;

		return type.isAssignableFrom(double.class) || isDoubleWrapper(type);
	}

	public static boolean isDoubleWrapper(Class<?> type)
	{
		return isNotNull(type) ? type.isAssignableFrom(Double.class) : false;
	}

	public static boolean isNumeric(Class<?> type)
	{
		if(isNull(type))
			return false;

		return isByte(type) || isShort(type) || isInteger(type) || isLong(type) || isFloat(type) || isDouble(type);
	}

	public static boolean isNumberWrapper(Class<?> type)
	{
		if(isNull(type))
			return false;

		return isByteWrapper(type) || isShortWrapper(type) || isIntegerWrapper(type) || isLongWrapper(type)
		                || isFloatWrapper(type) || isDoubleWrapper(type);
	}

	// public static boolean is

	// public static boolean isIntegerNumber(Class<?> type)
	// {
	// return isNotNull(type) ? isByte(type) || isShort(type) || isIntegerInstance(type) || isLong(type) : false;
	// }

	public static Class<?>[] getByteClasses()
	{
		return new Class<?>[] { byte.class, Byte.class };
	}

	public static Class<?>[] getShortClasses()
	{
		return new Class<?>[] { short.class, Short.class };
	}

	public static Class<?>[] getIntegerClasses()
	{
		return new Class<?>[] { int.class, Integer.class };
	}

	public static Class<?>[] getLongClasses()
	{
		return new Class<?>[] { long.class, Long.class };
	}

	public static Class<?>[] getFloatClasses()
	{
		return new Class<?>[] { float.class, Float.class };
	}

	public static Class<?>[] getDoubleClasses()
	{
		return new Class<?>[] { double.class, Double.class };
	}

	public static Class<?>[] getNumericClasses()
	{
		return new Class<?>[] { byte.class, Byte.class, short.class, Short.class, int.class, Integer.class, long.class,
		                Long.class, float.class, Float.class, double.class, Double.class };
	}

	public static Class<?>[] getBooleanClasses()
	{
		return new Class<?>[] { boolean.class, Boolean.class };
	}

	public static Number getNumberValue(Class<?> type, Number number)
	{
		if(isNull(type) || isNull(number))
			return null;

		if(isByte(type))
			return number.byteValue();
		if(isShort(type))
			return number.shortValue();
		if(isInteger(type))
			return number.intValue();
		if(isLong(type))
			return number.longValue();
		if(isFloat(type))
			return number.floatValue();
		if(isDouble(type))
			return number.doubleValue();

		return null;
	}
}
