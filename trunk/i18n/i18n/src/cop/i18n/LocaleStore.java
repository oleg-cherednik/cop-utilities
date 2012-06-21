/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Oleg Cherednik</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.i18n;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Oleg Cherednik
 * @since 04.03.2012
 */
public final class LocaleStore {
	public static final String DEFAULT_KEY = "default";
	public static final Locale RUSSIAN = new Locale("ru", "");
	public static final Locale RU = new Locale("ru", "RU");

	private static final String[] EMPTY_STR_ARR = new String[0];
	private static final Map<Integer, LocalStoreDecorator> MAP = new HashMap<Integer, LocalStoreDecorator>();

	private static Locale defaultLocale = Locale.getDefault();

	private final String root;

	public static synchronized void registerStore(Class<?> cls) {
		registerStore(cls, DEFAULT_KEY, null, true);
	}

	public static synchronized void registerStore(Class<?> cls, String root) {
		registerStore(cls, DEFAULT_KEY, root, true);
	}

	public static synchronized void registerStore(Class<?> cls, String key, String storeRoot) {
		registerStore(cls, key, storeRoot, false);
	}

	public static synchronized void registerStore(Class<?> cls, String key, String root, boolean def) {
		try {
			int hashCode = cls.getClassLoader().hashCode();
			LocalStoreDecorator unit = MAP.get(hashCode);

			if(unit == null)
				MAP.put(hashCode, unit = new LocalStoreDecorator());
			else if(!unit.containsKey(key))
				return;

			unit.addLocalStore(key, new LocaleStore(root), def);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private LocaleStore(String root) {
		this.root = isEmpty(root) ? "" : root + ".";
	}

	public String i18n(Object obj, String key) {
		return i18n(obj, key, defaultLocale);
	}

	public String i18n(Object obj, String key, Locale locale) {
		if(obj == null || isEmpty(key) || locale == null)
			return "unknown";

		try {
			ResourceBundle bundle = getBundle(obj, locale);

			if(bundle != null)
				return bundle.getString(key);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return key;
	}

	private ResourceBundle getBundle(Object obj, Locale locale) {
		String baseName = root + getClassName(obj);
		ClassLoader loader = obj.getClass().getClassLoader();

		return ResourceBundle.getBundle(baseName, locale, loader);
	}

	/*
	 * class
	 */

	private static class LocalStoreDecorator {
		private final Map<String, LocaleStore> map = new HashMap<String, LocaleStore>();
		private String defKey;

		public boolean containsKey(String key) {
			return map.containsKey(key);
		}

		public void addLocalStore(String key, LocaleStore store, boolean def) {
			map.put(key, store);

			if(def || map.size() == 1)
				defKey = key;
		}

		public LocaleStore getStore() {
			return getStore(defKey);
		}

		public LocaleStore getStore(String key) {
			return map.get(key);
		}
	}

	/*
	 * static
	 */

	private static String getClassName(Object obj) {
		if(obj instanceof Enum)
			return ((Enum<?>)obj).getDeclaringClass().getSimpleName();
		return obj.getClass().getSimpleName();
	}

	public static Locale getDefaultLocale() {
		return defaultLocale;
	}

	public static void setDefaultLocale(Locale locale) {
		LocaleStore.defaultLocale = locale;
	}

	public static String _i18n(Object obj, String key) {
		return MAP.get(obj.getClass().getClassLoader().hashCode()).getStore().i18n(obj, key);
	}

	public static String _i18n(Object obj, String key, Locale locale) {
		return MAP.get(obj.getClass().getClassLoader().hashCode()).getStore().i18n(obj, key, locale);
	}

	public static String[] i18n(Localizable[] objs) {
		return i18n(objs, defaultLocale);
	}

	public static String[] i18n(Localizable[] objs, Locale locale) {
		if(isEmpty(objs) || locale == null)
			return EMPTY_STR_ARR;

		String[] res = new String[objs.length];

		for(int i = 0, size = objs.length; i < size; i++)
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
