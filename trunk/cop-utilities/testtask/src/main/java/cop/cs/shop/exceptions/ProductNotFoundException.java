package cop.cs.shop.exceptions;

/**
 * @author Oleg Cherednik
 * @since 19.04.2013
 */
public class ProductNotFoundException extends ShopException {
	public ProductNotFoundException() {
		super();
	}

	public ProductNotFoundException(String message) {
		super(message);
	}

	public ProductNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductNotFoundException(Throwable cause) {
		super(cause);
	}
}
