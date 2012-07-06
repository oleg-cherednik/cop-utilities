/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Oleg Cherednik</a>
 *
 * $Id$
 * $HeadURL$
 */
package cop.i18n;

import java.util.*;

/**
 * This class describes a locale store. It's just a simple set of *.property files are holding localizable strings for
 * one given object.
 * 
 * suffix - key suffix, if it's not empty and not {@link #DEFAULT_SUFFIX}, then <code>key = key + '_' + suffix</code>
 * 
 * @author Oleg Cherednik
 * @since 04.03.2012
 */
public final class LocaleStore {
	public static final String DEFAULT_SUFFIX = "def";
	public static final String DEFAULT_PATH = "root";
	public static final Locale RUSSIAN = new Locale("ru", "");
	public static final Locale RU = new Locale("ru", "RU");

	private static final String[] EMPTY_STR_ARR = new String[0];
	// key - class loader's hash code
	private static final Map<Integer, LocaleStore> MAP = new HashMap<Integer, LocaleStore>();

	/**
	 * Default (or system) locale will be used if using methods with no specified locale.<br>
	 * <ul>
	 * Invoking following methods are equal:<br>
	 * <li><code>String i18nInt(String name)</code>
	 * <li><code>String i18nInt(String name, LocaleStore.defaultLocale)</code>
	 * </ul>
	 */
	private static Locale defaultLocale = Locale.getDefault();

	private final String path;

	public static synchronized void registerBundle(Class<?> cls, String path) {
		try {
			int hashCode = cls.getClassLoader().hashCode();

			if (!MAP.containsKey(hashCode))
				MAP.put(hashCode, new LocaleStore(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private LocaleStore(String path) {
		this.path = isEmpty(path) ? "" : path + '.';
	}

	private String _i18n(Object obj, Object key, String suffix, Locale locale) {
		if (obj == null || key == null || locale == null)
			return "unknown";

		ResourceBundle bundle;
		String keyName = getKeyName(key, suffix);

		try {
			if ((bundle = getBundle(obj, locale)) != null)
				return bundle.getString(keyName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return keyName;
	}

	private ResourceBundle getBundle(Object obj, Locale locale) {
		String baseName = path + getClassName(obj);
		ClassLoader loader = obj.getClass().getClassLoader();
		return ResourceBundle.getBundle(baseName, locale, loader);
	}

	/*
	 * static
	 */

	private static String getClassName(Object obj) {
		if (obj instanceof Enum)
			return ((Enum<?>)obj).getDeclaringClass().getSimpleName();
		return obj.getClass().getSimpleName();
	}

	private static String getKeyName(Object obj, String suffix) {
		String name = (obj instanceof Enum) ? ((Enum<?>)obj).name() : obj.toString();
		return name + (isEmpty(suffix) || DEFAULT_SUFFIX.equals(suffix) ? "" : '_' + suffix);
	}

	public static Locale getDefaultLocale() {
		return defaultLocale;
	}

	public static void setDefaultLocale(Locale locale) {
		defaultLocale = locale;
	}

	public static <T> String i18n(Object obj, T key) {
		return i18n(obj, key, DEFAULT_SUFFIX);
	}

	public static <T> String i18n(Object obj, T key, Locale locale) {
		return i18n(obj, key, DEFAULT_SUFFIX, locale);
	}

	public static <T> String i18n(Object obj, T key, String suffix) {
		return MAP.get(obj.getClass().getClassLoader().hashCode())._i18n(obj, key, suffix, defaultLocale);
	}

	public static <T> String i18n(Object obj, T key, String suffix, Locale locale) {
		return MAP.get(obj.getClass().getClassLoader().hashCode())._i18n(obj, key, suffix, locale);
	}

	public static String[] i18n(Localizable[] objs) {
		return i18n(objs, defaultLocale);
	}

	public static String[] i18n(Localizable[] objs, Locale locale) {
		if (isEmpty(objs) || locale == null)
			return EMPTY_STR_ARR;

		String[] res = new String[objs.length];

		for (int i = 0, size = objs.length; i < size; i++)
			res[i] = objs[i].i18n(locale);

		return res;
	}

	private static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	private static <T> boolean isEmpty(T[] arr) {
		return arr == null || arr.length == 0;
	}
}
