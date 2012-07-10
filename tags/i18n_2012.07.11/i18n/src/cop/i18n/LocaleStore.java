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

import cop.i18n.exceptions.DuplicationBundleException;
import cop.i18n.exceptions.LocaleStoreException;
import cop.i18n.exceptions.UnknownKeyException;

/**
 * This class defines a global store for holding localizable strings. It defined as global store, that holds all local
 * stores from many modules, as each local stores of other modules. This implementation gives full methods' set for
 * working with {@link Locale}.<br>
 * E.g. we would like to have access to the localized string through an enum implementation. To do so, the enum must
 * implement {@link Localizable} interface and register itself to the global store. In following example, enum Color
 * register itself in the {@link LocaleStore} and provide them a path to the set of *.properties files, where
 * <tt>name()</tt> is the property key. After that any client code can invoke <tt>i18</tt> methods to get localizable
 * string.
 * <p>
 * <blockquote>
 * 
 * <pre>
 * public enum Color implements Localizable {
 * 	RED,
 * 	BLUE,
 * 	GREEN,
 * 	WHITE;
 * 
 * 	public String i18n() {
 * 		return LocaleStore.i18n(this, name());
 * 	}
 * 
 * 	public String i18n(Locale locale) {
 * 		return LocaleStore.i18n(this, name(), locale);
 * 	}
 * 
 * 	static {
 * 		LocaleStore.registerBundle(Color.class, CommonProperty.PATH_I18N);
 * 	}
 * }
 * </pre>
 * 
 * </blockquote>
 * <p>
 * For this enum property file name should be <tt>Color</tt> (it's a class name of the Color class) and contains set of
 * the pair <tt>key - localizable string</tt>:
 * 
 * <pre>
 * <code>
 * RED=red
 * BLUE=blue
 * GREEN=green
 * WHITE=white
 * </code>
 * </pre>
 * 
 * Invoking of the following methods will give same result:
 * <code>i18n() == i18n({@link LocaleStore#defaultLocale})</code>.<br>
 * Additionally, there's a way to add extra localizable strings to the same key. E.g. to give <i>long color name</i>
 * using same Color enum. To do so client should give extra <tt>suffix</tt> that will be automatically added to the
 * property key. In the following example property file contains extra long color names under <tt>suffix = "LONG"</tt>
 * and client calls {@link LocaleStore#i18n(Object, Object, String)} method too get long localizable string with default
 * system locale for Color.RED key:
 * 
 * <pre>
 * <code>
 * RED=red
 * BLUE=blue
 * GREEN=green
 * WHITE=white
 * 
 * RED_LONG=red (long)
 * BLUE_LONG=blue (long)
 * GREEN_LONG=green (long)
 * WHITE_LONG=white (long)
 * ...
 * LocaleStore.i18n(Color.RED, Color.RED, "LONG") = "red (long)";
 * </code>
 * </pre>
 * 
 * @author Oleg Cherednik
 * @since 04.03.2012
 */
public final class LocaleStore {
	public static final String DEFAULT_SUFFIX = "def";
	public static final String DEFAULT_PATH = "root";
	public static final Locale RUSSIAN = new Locale("ru", "");
	public static final Locale RUSSIA = new Locale("ru", "RU");

	private static final String[] EMPTY_STR_ARR = new String[0];
	// key - class loader's hash code
	private static final Map<Integer, LocaleStore> MAP = new HashMap<Integer, LocaleStore>();

	/**
	 * Default (or system) locale will be used if using methods with no specified locale.<br>
	 */
	private static Locale defaultLocale = Locale.getDefault();

	private final Map<Class<?>, String> paths = new HashMap<Class<?>, String>();

	/**
	 * Register new bundle in the locale store. Bundle can be registered only once.
	 * 
	 * @param cls bundle class
	 * @param path property files path
	 */
	public static synchronized void registerBundle(Class<?> cls, String path) {
		try {
			int hashCode = cls.getClassLoader().hashCode();

			if (!MAP.containsKey(hashCode))
				MAP.put(hashCode, new LocaleStore(cls, path));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private LocaleStore(Class<?> cls, String path) throws LocaleStoreException {
		if (paths.containsKey(cls))
			throw new DuplicationBundleException(cls.getSimpleName());
		paths.put(cls, isEmpty(path) ? "" : path + '.');
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

	private ResourceBundle getBundle(Object obj, Locale locale) throws UnknownKeyException {
		Class<?> cls = getClass(obj);

		if (!paths.containsKey(cls))
			throw new UnknownKeyException(cls.getSimpleName());

		String baseName = paths.get(cls) + cls.getSimpleName();
		ClassLoader loader = obj.getClass().getClassLoader();
		return ResourceBundle.getBundle(baseName, locale, loader);
	}

	/*
	 * static
	 */

	private static Class<?> getClass(Object obj) {
		if (obj instanceof Enum)
			return ((Enum<?>)obj).getDeclaringClass();
		return obj.getClass();
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
		return i18n(obj, key, DEFAULT_SUFFIX, defaultLocale);
	}

	public static <T> String i18n(Object obj, T key, Locale locale) {
		return i18n(obj, key, DEFAULT_SUFFIX, locale);
	}

	public static <T> String i18n(Object obj, T key, String suffix) {
		return i18n(obj, key, suffix, defaultLocale);
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
