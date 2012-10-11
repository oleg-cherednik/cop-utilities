/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Oleg Cherednik</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.i18n;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Oleg Cherednik
 * @since 11.07.2012
 */
public class LocaleStoreTest {
	public static final String PATH_I18N = "i18n";

	private static final String PROPERTY_COUNT = Count.class.getSimpleName() + ".properties";
	private static final String PROPERTY_COUNT_RU = Count.class.getSimpleName() + "_ru.properties";
	private static final String PROPERTY_COUNT_DE = Count.class.getSimpleName() + "_de.properties";

	private static final Properties PROP = new Properties();
	private static final Properties PROP_RU = new Properties();
	private static final Properties PROP_DE = new Properties();

	@BeforeClass
	public static void init() {
		readFile(PROP, PROPERTY_COUNT);
		readFile(PROP_RU, PROPERTY_COUNT_RU);
		readFile(PROP_DE, PROPERTY_COUNT_DE);
	}

	@Test
	@SuppressWarnings("static-method")
	public void testDefaultEnglishLocale() {
		LocaleStore.setAppLocale(Locale.ENGLISH);

		for (Count count : Count.values()) {
			assertEquals(PROP.getProperty(count.name()), count.i18n());
			assertEquals(PROP.getProperty(count.name()), count.i18n(Locale.ITALY));

			assertEquals(PROP_RU.getProperty(count.name()), count.i18n(LocaleStore.RUSSIAN));
			assertEquals(PROP_RU.getProperty(count.name()), count.i18n(LocaleStore.RUSSIA));

			assertEquals(PROP_DE.getProperty(count.name()), count.i18n(Locale.GERMAN));
			assertEquals(PROP_DE.getProperty(count.name()), count.i18n(Locale.GERMANY));
		}
	}

	@Test
	@SuppressWarnings("static-method")
	public void testDefaultGermanLocale() {
		LocaleStore.setAppLocale(Locale.GERMAN);

		for (Count count : Count.values()) {
			assertEquals(PROP.getProperty(count.name()), count.i18n(Locale.ENGLISH));
			assertEquals(PROP.getProperty(count.name()), count.i18n(Locale.ITALY));

			assertEquals(PROP_RU.getProperty(count.name()), count.i18n(LocaleStore.RUSSIAN));
			assertEquals(PROP_RU.getProperty(count.name()), count.i18n(LocaleStore.RUSSIA));

			assertEquals(PROP_DE.getProperty(count.name()), count.i18n());
			assertEquals(PROP_DE.getProperty(count.name()), count.i18n(Locale.GERMANY));
		}
	}

	/*
	 * static
	 */

	private static void readFile(Properties properties, String fileName) {
		InputStream in = null;

		try {
			properties.load(in = LocaleStoreTest.class.getResourceAsStream('/' + PATH_I18N + '/' + fileName));
		} catch (IOException e) {
			assertTrue(e.getMessage(), false);
		} finally {
			close(in);
		}
	}

	private static void close(Closeable obj) {
		if (obj == null)
			return;

		try {
			obj.close();
		} catch (Exception e) {}
	}
}
