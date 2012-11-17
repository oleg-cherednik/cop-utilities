/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Oleg Cherednik</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.i18n.exceptions;

/**
 * @author Oleg Cherednik
 * @since 08.07.2012
 */
public class LocaleStoreException extends Exception {
	private static final long serialVersionUID = 4102126493678940468L;

	public LocaleStoreException() {}

	public LocaleStoreException(String message) {
		super(message);
	}

	public LocaleStoreException(String message, Throwable cause) {
		super(message, cause);
	}

	public LocaleStoreException(Throwable cause) {
		super(cause);
	}
}
