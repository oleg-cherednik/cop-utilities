/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Oleg Cherednik</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.i18;

import java.lang.reflect.AccessibleObject;
import java.util.Locale;
import java.util.Map;

import cop.extensions.ReflectionExt;

/**
 * @author Oleg Cherednik
 * @since 16.08.2010
 */
public final class LocalizationExt {
	public static final String[] EMPTY_STR_ARR = new String[0];

	public static final Locale RUSSIAN = new Locale("ru", "");
	public static final Locale RU = new Locale("ru", "RU");
	public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

	private LocalizationExt() {}

	public static Locale getDefaultApplicationLocale() {
		return Locale.getDefault();
	}

	public static String i18n(Map<Locale, String> map, String def) {
		return i18n(map, getDefaultApplicationLocale(), def);
	}

	public static String i18n(Map<Locale, String> map, Locale locale, String def) {
		if (isEmpty(map))
			return def;

		if (locale == null)
			return getNotEmptyText(map.get(DEFAULT_LOCALE), def);

		return getNotEmptyText(map.get(locale), getNotEmptyText(map.get(DEFAULT_LOCALE), def));
	}

	public static String[] i18n(Localizable[] objs) {
		return i18n(objs, getDefaultApplicationLocale());
	}

	public static String[] i18n(Localizable[] objs, Locale locale) {
		if (isEmpty(objs) || locale == null)
			return EMPTY_STR_ARR;

		String[] res = new String[objs.length];

		for (int i = 0, size = objs.length; i < size; i++)
			res[i] = objs[i].i18n(locale);

		return res;
	}

	public static boolean isLocalizable(AccessibleObject obj) {
		Class<?> type = ReflectionExt.getType(obj);

		if (type == null)
			return false;

		try {
			type.asSubclass(Localizable.class);
			return true;
		} catch (Exception e) {}

		return false;
	}

	private static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	private static boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}

	private static <T> boolean isEmpty(T[] arr) {
		return arr == null || arr.length == 0;
	}

	private static String getNotEmptyText(String str, String def) {
		return isEmpty(str) ? def : str;
	}
}
