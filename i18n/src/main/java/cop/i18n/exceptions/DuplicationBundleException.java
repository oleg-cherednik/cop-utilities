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
public class DuplicationBundleException extends LocaleStoreException {
	private static final long serialVersionUID = -2747324505520081101L;

	public DuplicationBundleException() {}

	public DuplicationBundleException(String message) {
		super(message);
	}

	public DuplicationBundleException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicationBundleException(Throwable cause) {
		super(cause);
	}
}
