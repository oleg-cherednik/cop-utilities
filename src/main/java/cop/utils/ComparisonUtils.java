package cop.utils;

import java.text.Collator;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static cop.utils.CommonExt.isNull;
import static cop.utils.ReflectionUtils.isBoolean;
import static cop.utils.ReflectionUtils.isByte;
import static cop.utils.ReflectionUtils.isDouble;
import static cop.utils.ReflectionUtils.isFloat;
import static cop.utils.ReflectionUtils.isInteger;
import static cop.utils.ReflectionUtils.isLong;
import static cop.utils.ReflectionUtils.isShort;

public final class ComparisonUtils {
	private static final Map<Class<?>, Comparator<?>> MAP = new HashMap<Class<?>, Comparator<?>>();

	private ComparisonUtils() {
	}

	public static <T> int compareObjects(Comparable<T> obj1, T obj2) {
		if (obj1 == obj2)
			return 0;
		if (isNull(obj1) ^ isNull(obj2))
			return isNull(obj2) ? 1 : -1;

		return obj1.compareTo(obj2);
	}

	public static <T> int compareStrings(String str1, String str2, Collator collator) {
		if (collator == null)
			return compareObjects(str1, str2);
		if (str1 == str2)
			return 0;
		if ((str1 == null) ^ (str2 == null))
			return (str2 == null) ? 1 : -1;

		return collator.compare(str1, str2);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Comparable<T>> Comparator<T> createComparator(Class<T> obj) {
		Comparator<?> comparator = MAP.get(obj);

		if (comparator == null) {
			MAP.put(obj, comparator = new Comparator<T>() {
				public int compare(T obj1, T obj2) {
					return compareObjects(obj1, obj2);
				}
			});
		}

		return (Comparator<T>)comparator;
	}

	public static int compareNumbers(Class<?> type, Object obj1, Object obj2) {
		if (isBoolean(type))
			return compareObjects((Boolean)obj1, (Boolean)obj2);
		if (isByte(type))
			return compareObjects((Byte)obj1, (Byte)obj2);
		if (isShort(type))
			return compareObjects((Short)obj1, (Short)obj2);
		if (isInteger(type))
			return compareObjects((Integer)obj1, (Integer)obj2);
		if (isLong(type))
			return compareObjects((Long)obj1, (Long)obj2);
		if (isFloat(type))
			return compareObjects((Float)obj1, (Float)obj2);
		if (isDouble(type))
			return compareObjects((Double)obj1, (Double)obj2);

		throw new IllegalArgumentException(type + " is not a Number");
	}
}
