package cop.cs.shop.exceptions;

/**
 * @author Oleg Cherednik
 * @since 19.04.2013
 */
public class ShopException extends Exception {
	public ShopException() {
		super();
	}

	public ShopException(String message) {
		super(message);
	}

	public ShopException(String message, Throwable cause) {
		super(message, cause);
	}

	public ShopException(Throwable cause) {
		super(cause);
	}
}
