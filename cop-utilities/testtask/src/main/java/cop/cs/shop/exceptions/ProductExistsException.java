package cop.cs.shop.exceptions;

/**
 * @author Oleg Cherednik
 * @since 19.04.2013
 */
public class ProductExistsException extends ShopException {
	public ProductExistsException() {
		super();
	}

	public ProductExistsException(String message) {
		super(message);
	}

	public ProductExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductExistsException(Throwable cause) {
		super(cause);
	}
}
