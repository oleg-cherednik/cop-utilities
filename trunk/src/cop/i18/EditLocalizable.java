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
 * Provides some ways to set or changed localized string for the current object
 * 
 * @author Oleg Cherednik
 * @since 16.08.2010
 */
public interface EditLocalizable extends Localizable {
	/**
	 * Set new localized string of the current object for the {@link Locale#getDefault()} locale.<br>
	 * It's equal to invoke {@link #setI18n(String, Locale)} with {@link Locale#getDefault()} locale.
	 * 
	 * @param str localized string
	 */
	void setI18n(String str);

	/**
	 * Set new localized string of the current object for the given locale.<br>
	 * 
	 * @param str localized string
	 * @param locale locale
	 */
	void setI18n(String str, Locale locale);
}
