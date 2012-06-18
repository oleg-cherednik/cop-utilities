/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Oleg Cherednik</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.i18;

import java.util.Locale;

/**
 * Provides some ways to get localized string for the current object
 * 
 * @author Oleg Cherednik
 * @since 16.08.2010
 */
public interface Localizable {
	/**
	 * Returns localized string of the current object for the {@link Locale#getDefault()} locale.<br>
	 * It's equal to invoke {@link Localizable#i18n(Locale)} with {@link Locale#getDefault()} locale.
	 * 
	 * @return localized string
	 */
	String i18n();

	/**
	 * Returns localized string of the current object for the given locale.
	 * 
	 * @param locale locale
	 * @return localized string
	 */

	String i18n(Locale locale);
}
