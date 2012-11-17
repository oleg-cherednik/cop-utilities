/**
 * @licence GNU Leser General Public License
 *
 * $Id$
 * $HeadURL$
 */
package cop.common.utils;

import static cop.common.utils.CommonUtils.isNotNull;
import static cop.common.utils.CommonUtils.isNull;
import static cop.common.utils.NumericUtils.isInRangeMin;
import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public final class CollectionUtils {
	public static final List<Object> EMPTY_LIST = new ArrayList<Object>(0);
	public static final List<String[]> EMPTY_STR_ARR_LIST = new ArrayList<String[]>(0);

	private CollectionUtils() {}

	public static boolean isArraysEqual(int[] arr1, int[] arr2) {
		if (arr1 == arr2)
			return true;

		if (isNull(arr1) || isNull(arr2) || arr1.length != arr2.length)
			return false;

		for (int i = 0, size = arr1.length; i < size; i++)
			if (arr1[i] != arr2[i])
				return false;

		return true;
	}

	public static boolean isArraysEqual(Class<?>[] arr1, Class<?>[] arr2) {
		if (arr1 == arr2)
			return true;

		if (isNull(arr1) || isNull(arr2) || arr1.length != arr2.length)
			return false;

		for (int i = 0, size = arr1.length; i < size; i++)
			if (arr1[i] != arr2[i])
				return false;

		return true;
	}

	public static <T> T[] removeNull(T[] arr) {
		List<T> res = new LinkedList<T>();

		for (T val : arr) {
			if (val != null)
				res.add(val);
		}

		return res.toArray(arr);
	}

	// public static <T> T[] replaceAll(T[] arr, T oldVal, T newVal)
	// {
	// for(int i = 0; i < arr.length; i++)
	// if(arr[i].equals(oldVal))
	// arr[i] = newVal;
	//
	// return arr;
	// }

	public static char[] replaceAll(char[] arr, char oldVal, char newVal) {
		for (int i = 0; i < arr.length; i++)
			if (arr[i] == oldVal)
				arr[i] = newVal;

		return arr;
	}

	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}

	public static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}

	public static <T> int getMaximumLength(T[] arr) {
		if (arr == null)
			return -1;

		if (arr.length == 0)
			return 0;

		int res = 0;

		for (T obj : arr)
			res = max(res, obj.toString().length());

		return res;
	}

	public static <T> int getMinimumLength(T[] arr) {
		if (arr == null)
			return -1;

		if (arr.length == 0)
			return 0;

		int res = Integer.MAX_VALUE;

		for (T obj : arr)
			res = max(res, obj.toString().length());

		return res;
	}

	public static <T> void addNotNull(Collection<T> dest, Collection<T> src) {
		if (dest == null || isEmpty(src) || dest == src)
			return;

		for (T obj : src)
			if (obj != null)
				dest.add(obj);
	}

	public static <T> boolean swap(List<T> list, int index1, int index2) {
		if (isEmpty(list))
			return false;

		int size = list.size();

		if (!isInRangeMin(index1, 0, size) || !isInRangeMin(index2, 0, size))
			return false;

		T tmp = list.get(index1);

		list.set(index1, list.get(index2));
		list.set(index2, tmp);

		return true;
	}

	public static <T> void convertToAnotherColletion(Collection<T> dest, Collection<T> src) {
		if (isNull(dest) || isEmpty(src))
			return;

		if (!dest.isEmpty())
			dest.clear();

		for (T obj : src)
			dest.add(obj);
	}

	public static int[] convertToIntArray(Collection<Integer> values) {
		if (isEmpty(values))
			return new int[0];

		int[] arr = new int[values.size()];
		int i = 0;

		for (Integer value : values)
			arr[i++] = isNotNull(value) ? value.intValue() : 0;

		return arr;
	}

	public static <T> void removeDublicates(Collection<T> arr) {
		try {
			if (isEmpty(arr))
				return;

			filterCollection(LinkedHashSet.class, arr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static <T> void removeDuplicatesAndSort(Collection<T> arr) {
		try {
			if (isEmpty(arr))
				return;

			filterCollection(TreeSet.class, arr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// public static <T> Collection<T>invertCollection(Collection<T> arr)
	// {
	//
	// }

	public static <T extends Number> int[] toIntArray(Collection<T> arr) {
		int i = 0;
		int[] res = new int[arr.size()];

		for (T val : arr)
			res[i++] = val.intValue();

		return res;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static <T> void filterCollection(Class<? extends Collection> cls, Collection<T> arr)
			throws InstantiationException, IllegalAccessException {
		if (isEmpty(arr))
			return;

		Collection<T> tmp = cls.newInstance();

		convertToAnotherColletion(tmp, arr);
		convertToAnotherColletion(arr, tmp);
	}

}
