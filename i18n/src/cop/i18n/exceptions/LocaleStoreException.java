package cop.i18n.exceptions;

/**
 * @author Oleg Cherednik
 * @since 08.-7.2012
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
