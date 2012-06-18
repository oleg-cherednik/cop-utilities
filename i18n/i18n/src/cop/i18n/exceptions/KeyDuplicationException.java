package cop.i18n.exceptions;

public class KeyDuplicationException extends Exception {
	private static final long serialVersionUID = -6799655442267743311L;

	public KeyDuplicationException() {}

	public KeyDuplicationException(String message) {
		super(message);
	}

	public KeyDuplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public KeyDuplicationException(Throwable cause) {
		super(cause);
	}
}
