/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Oleg Cherednik</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.i18;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import cop.extensions.StringExt;
import cop.i18.exceptions.KeyDuplicationException;

/**
 * @author Oleg Cherednik
 * @since 04.03.2012
 */
public final class LocaleStore {
	public static final String DEFAULT_KEY = "default";

	private static final Map<Integer, LocalStoreDecorator> MAP = new HashMap<Integer, LocalStoreDecorator>();

	private final String root;
	private final Locale defLocale;

	public static synchronized void registerStore(Class<?> cls, String root, Locale defLocale)
	                throws KeyDuplicationException {
		registerStore(cls, DEFAULT_KEY, root, defLocale, true);
	}

	public static synchronized void registerStore(Class<?> cls, String key, String storeRoot, Locale defLocale)
	                throws KeyDuplicationException {
		registerStore(cls, key, storeRoot, defLocale, false);
	}

	public static synchronized void registerStore(Class<?> cls, String key, String root, Locale defLocale, boolean def)
	                throws KeyDuplicationException {
		int hashCode = cls.getClassLoader().hashCode();
		LocalStoreDecorator unit = MAP.get(hashCode);

		if (unit == null)
			MAP.put(hashCode, unit = new LocalStoreDecorator());
		else if (unit.containsKey(key))
			throw new KeyDuplicationException(key);

		unit.addLocalStore(key, new LocaleStore(root, defLocale), def);
	}

	private LocaleStore(String root, Locale defLocale) {
		this.root = root + ".";
		this.defLocale = defLocale;
	}

	public String i18n(Object obj, String key) {
		return i18n(obj, key, defLocale);
	}

	public String i18n(Object obj, String key, Locale locale) {
		if (obj == null || StringExt.isEmpty(key) || locale == null)
			return "unknown";

		try {
			ResourceBundle bundle = getBundle(obj, locale);

			if (bundle != null)
				return bundle.getString(key);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return key;
	}

	private ResourceBundle getBundle(Object obj, Locale locale) {
		String baseName = root + obj.getClass().getSimpleName();
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

			if (def || map.size() == 1)
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

	public static String _i18n(Object obj, String key) {
		return MAP.get(obj.getClass().getClassLoader().hashCode()).getStore().i18n(obj, key);
	}

	public static String _i18n(Object obj, String key, Locale locale) {
		return MAP.get(obj.getClass().getClassLoader().hashCode()).getStore().i18n(obj, key, locale);
	}
}
