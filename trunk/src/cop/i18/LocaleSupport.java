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
 * Provides way to set new locale for the current object
 * 
 * @author Oleg Cherednik
 * @since 16.08.2010
 */
public interface LocaleSupport {
	/**
	 * Changes locale of the current object
	 * 
	 * @param locale new locale
	 */
	void setLocale(Locale locale);
}
