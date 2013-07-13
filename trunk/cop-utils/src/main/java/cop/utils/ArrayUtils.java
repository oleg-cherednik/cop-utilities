package cop.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.TreeSet;

import static cop.utils.CollectionUtils.toIntArray;
import static cop.utils.CommonExt.isNotNull;
import static cop.utils.CommonExt.isNull;
import static cop.utils.NumericExt.isInRangeMinMax;

public final class ArrayUtils {
	public static final int[] EMPTY_INT_ARR = new int[0];
	public static final char[] EMPTY_CHAR_ARR = new char[0];
	public static final String[] EMPTY_STR_ARR = new String[0];
	public static final String[] ONE_ITEM_STR_ARR = new String[1];

	private ArrayUtils() {
	}

	public static int[][] invertArrayVertically(int[][] arr) {
		if (arr == null)
			return null;

		int[][] res = new int[arr.length][];

		for (int i = 0; i < arr.length; i++) {
			res[i] = new int[arr[i].length];

			for (int j = 0, len = arr[i].length; j < len; j++)
				res[i][len - 1 - j] = arr[i][j];
		}

		return res;
	}

	public static int[][] invertArrayHorizontally(int[][] arr) {
		if (arr == null)
			return null;

		int[][] res = new int[arr.length][];

		for (int i = arr.length - 1, j = 0; i >= 0; i--, j++) {
			res[i] = new int[arr[i].length];
			System.arraycopy(arr[j], 0, res[i], 0, arr[j].length);
		}

		return res;
	}

	public static <T> T[] invertArray(T... arr) {
		if (isEmpty(arr))
			return null;

		T[] res = arr.clone();
		int i = res.length - 1;

		for (T val : arr)
			res[i--] = val;

		return res;
	}

	public static char[] invertArray(char... arr) {
		if (isEmpty(arr))
			return null;

		if (arr.length == 0)
			return EMPTY_CHAR_ARR;

		char[] res = new char[arr.length];
		int i = res.length - 1;

		for (char ch : arr)
			res[i--] = ch;

		return res;
	}

	public static boolean isEmpty(double... arr) {
		return arr == null || arr.length == 0;
	}

	public static boolean isEmpty(boolean... arr) {
		return arr == null || arr.length == 0;
	}

	public static boolean isEmpty(int... arr) {
		return arr == null || arr.length == 0;
	}

	public static boolean isEmpty(long... arr) {
		return arr == null || arr.length == 0;
	}

	public static <T> boolean isEmpty(T... arr) {
		return arr == null || arr.length == 0;
	}

	public static boolean isEmpty(char... arr) {
		return arr == null || arr.length == 0;
	}

	public static boolean isNotEmpty(double... arr) {
		return !isEmpty(arr);
	}

	public static boolean isNotEmpty(boolean... arr) {
		return !isEmpty(arr);
	}

	public static boolean isNotEmpty(int... arr) {
		return !isEmpty(arr);
	}

	public static boolean isNotEmpty(long... arr) {
		return !isEmpty(arr);
	}

	public static <T> boolean isNotEmpty(T... arr) {
		return !isEmpty(arr);
	}

	public static boolean isNotEmpty(char... arr) {
		return !isEmpty(arr);
	}

	public static <T> T[] copyOfRange(T[] arr, int from) {
		return copyOfRange(arr, from, -1);
	}

	public static <T> T[] copyOfRange(T[] arr, int from, int to) {
		if (isEmpty(arr) || from < 0)
			return arr;

		if (from > arr.length - 1)
			return null;

		if (to < 0) {
			int _from = from;
			int _to = arr.length;
			return Arrays.copyOfRange(arr, _from, _to);
		}

		return (from < to) ? arr : Arrays.copyOfRange(arr, from, to);
	}

	public static <T> T getLastItem(T... arr) throws IllegalArgumentException {
		if (isEmpty(arr))
			throw new IllegalArgumentException("array is empty or null");

		return arr[arr.length - 1];
	}

	public static double getLastItem(double... arr) throws IllegalArgumentException {
		if (isEmpty(arr))
			throw new IllegalArgumentException("array is empty or null");

		return arr[arr.length - 1];
	}

	public static boolean getLastItem(boolean... arr) throws IllegalArgumentException {
		if (isEmpty(arr))
			throw new IllegalArgumentException("array is empty or null");

		return arr[arr.length - 1];
	}

	public static int getLastItem(int... arr) throws IllegalArgumentException {
		if (isEmpty(arr))
			throw new IllegalArgumentException("array is empty or null");

		return arr[arr.length - 1];
	}

	public static long getLastItem(long... arr) {
		if (isEmpty(arr))
			throw new IllegalArgumentException("array is empty or null");

		return arr[arr.length - 1];
	}

//	public static <T> String[] convertToStringArray(T[] arr) {
//		return convertToStringArray(arr, -1);
//	}
//
//	public static <T> String[] convertToStringArray(T[] arr, int length) {
//		if (isEmpty(arr))
//			return new String[0];
//
//		int newLength = isInRangeMinMax(length, 1, arr.length) ? length : arr.length;
//		String[] res = new String[newLength];
//
//		for (int i = 0, size = newLength; i < size; i++)
//			res[i] = "" + arr[i];
//
//		return res;
//	}

	public static int[] convertToIntArray(Integer[] values) {
		if (isEmpty(values))
			return new int[0];

		int[] arr = new int[values.length];
		int i = 0;

		for (Integer value : values)
			arr[i++] = isNotNull(value) ? value.intValue() : 0;

		return arr;
	}

	public static Integer[] convertToIntegerArray(int[] values) {
		if (isEmpty(values))
			return new Integer[0];

		Integer[] arr = new Integer[values.length];
		int i = 0;

		for (Integer value : values)
			arr[i++] = Integer.valueOf(isNotNull(value) ? value : 0);

		return arr;
	}

	public static int[] removeDublicates(int[] arr) {
		return removeDublicates(arr, null, null);
	}

	public static int[] removeDublicates(int[] arr, Integer minimum, Integer maximum) {
		try {
			return toIntArray(toCollection(arr, LinkedHashSet.class, minimum, maximum));
		} catch(Exception e) {
			return new int[0];
		}
	}

	public static int[] removeDuplicatesAndSort(int[] arr) {
		return removeDuplicatesAndSort(arr, null, null);
	}

	public static int[] removeDuplicatesAndSort(int[] arr, Integer minimum, Integer maximum) {
		try {
			return toIntArray(toCollection(arr, TreeSet.class, minimum, maximum));
		} catch(Exception e) {
			return new int[0];
		}
	}

	public static void moveArrayX(int[] arr, int offs) {
		if (isNull(arr))
			return;

		for (int i = 0; i < arr.length; i += 2)
			arr[i] += offs;
	}

	public static void moveArrayY(int[] arr, int offs) {
		if (isNull(arr))
			return;

		for (int i = 1; i < arr.length; i += 2)
			arr[i] += offs;
	}

	@SuppressWarnings("rawtypes")
	public static Collection<Integer> toCollection(final int[] arr, Class<? extends Collection> cls) {
		try {
			return toCollection(arr, cls, null, null);
		} catch(Exception ignored) {
			return null;
		}
	}

	public static <T> Collection<T> toCollection(final T[] arr, Class<? extends Collection<T>> cls) {
		try {
			Collection<T> dest = cls.getConstructor().newInstance();

			if (isNotEmpty(arr))
				Collections.addAll(dest, arr);

			return dest;
		} catch(Exception ignored) {
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Collection<Integer> toCollection(final int[] arr, Class<? extends Collection> cls, Integer min,
			Integer max) throws Exception {
		Collection<Integer> obj = cls.getConstructor().newInstance();

		if (isEmpty(arr))
			return obj;

		for (Integer val : arr)
			if (isInRangeMinMax(val, min, max))
				obj.add(val);

		return obj;
	}

}
