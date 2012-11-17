/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Oleg Cherednik</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.i18n;

import java.util.Locale;

/**
 * @author Oleg Cherednik
 * @since 11.07.2012
 */
public enum Count implements Localizable {
	ONE,
	TWO,
	THREE,
	FOUR,
	FIVE,
	SIX,
	SEVEN,
	EIGHT,
	NINE,
	TEN;

	// ========== Localizable ==========

	public String i18n() {
		return LocaleStore.i18n(this, name());
	}

	public String i18n(Locale locale) {
		return LocaleStore.i18n(this, name(), locale);
	}

	// ========== static ==========

	static {
		LocaleStore.registerBundle(Count.class, LocaleStoreTest.PATH_I18N);
	}
}
