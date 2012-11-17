/**
 * @licence GNU Leser General Public License
 *
 * $Id$
 * $HeadURL$
 */
package cop.common.utils;

import static cop.common.utils.CommonUtils.isNotNull;
import static cop.common.utils.CommonUtils.isNull;
import static cop.common.utils.NumericUtils.isInRangeMinMax;
import static java.lang.Double.parseDouble;
import static java.util.Arrays.fill;

public final class StringUtils {
	public static final char DEFAULT_CHAR = '_';

	private StringUtils() {}

	public static boolean isEqual(String str1, String str2) {
		return isEmpty(str1) ? isEmpty(str2) : str1.equalsIgnoreCase(str2);
	}

	public static <T> String getText(T res) {
		return getText(res, "");
	}

	public static <T> String getText(T res, String def) {
		return isNotNull(res) ? res.toString() : def;
	}

	public static String getNotEmptyText(String str, String def) {
		return isNotEmpty(str) ? str : def;
	}

	public static int getStringLength(String str) {
		return isNull(str) ? 0 : str.length();
	}

	public static String resizeString(String str, int length, char ch, boolean reverse) {
		return reverse ? resizeStringReverse(str, length, ch) : resizeString(str, length, ch);
	}

	public static String resizeString(String str, int length, char ch) {
		if (isEmpty(str) || length < 0)
			return str;

		if (length == 0)
			return "";

		if (length <= str.length())
			return str.substring(0, length);

		return str + createFilledString(length - str.length(), ch);
	}

	public static String resizeStringReverse(String str, int length, char ch) {
		if (isEmpty(str) || length < 0)
			return str;

		if (length == 0)
			return "";

		if (length <= str.length())
			return str.substring(str.length() - length);

		return createFilledString(length - str.length(), ch) + str;
	}

	public static String createFilledString(int length, char ch) {
		if (length <= 0)
			return "";

		char[] ext = new char[length];

		fill(ext, ch);

		return new String(ext);
	}

	// TODO Refactoring
	public static String replace(String str, String replace, int start, int end) {
		if (start > end || start < 0)
			return null;

		if (isEmpty(str))
			return replace;

		if (end == 0)
			return replace + str;

		if (start == str.length())
			return str + replace;

		if (start == end)
			return "" + new StringBuilder(str).insert(start, replace);

		int _end = (start == end) ? (end + 1) : end;

		return "" + new StringBuilder(str).replace(start, _end, replace);
	}

	public static boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static boolean isNumber(char ch) {
		return isInRangeMinMax(new Integer(ch), 48, 57);
	}

	public static boolean isNumeric(String str) {
		try {
			parseDouble(str);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public static String createString(int length) {
		return createString(length, DEFAULT_CHAR);
	}

	public static String createString(int length, char ch) {
		if (length <= 0)
			return "";

		StringBuilder buf = new StringBuilder();

		for (int i = 0; i < length; i++)
			buf.append(ch);

		return "" + buf;
	}

	public static String replaceAll(String str, String search, String replace) {
		StringBuilder buf = new StringBuilder(str);

		replaceAll(buf, search, replace);

		return "" + buf;
	}

	public static void replaceAll(StringBuilder buf, String search, String replace) {
		if (isEmpty("" + buf) || isEmpty(search) || isEmpty(replace))
			return;

		int fromIndex = 0;
		int len = search.length();

		while (true) {
			int i = buf.indexOf(search, fromIndex);

			if (i < 0)
				break;

			buf.replace(i, i + len, replace);
			fromIndex = i + len;
		}
	}
}
