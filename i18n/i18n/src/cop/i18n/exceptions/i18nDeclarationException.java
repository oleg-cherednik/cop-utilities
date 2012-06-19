/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Oleg Cherednik</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.i18n.exceptions;

/**
 * Exception declares any error while using self-defined annotations.
 * 
 * @author Oleg Cherednik
 * @since 16.08.2010
 */
public class i18nDeclarationException extends Exception {
	private static final long serialVersionUID = 1907237273650425166L;

	public i18nDeclarationException() {}

	public i18nDeclarationException(String message) {
		super(message);
	}

	public i18nDeclarationException(String message, Throwable cause) {
		super(message, cause);
	}

	public i18nDeclarationException(Throwable cause) {
		super(cause);
	}
}
