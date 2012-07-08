package cop.i18n.exceptions;

/**
 * @author Oleg Cherednik
 * @since 08.07.2012
 */
public class UnknownKeyException extends LocaleStoreException {
	private static final long serialVersionUID = -2747324505520081101L;

	public UnknownKeyException() {}

	public UnknownKeyException(String message) {
		super(message);
	}

	public UnknownKeyException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownKeyException(Throwable cause) {
		super(cause);
	}
}
