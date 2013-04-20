package cop.cs.shop.exceptions;

/**
 * @author Oleg Cherednik
 * @since 21.04.2013
 */
public class PriceNotFoundException extends ShopException {
	public PriceNotFoundException() {
		super();
	}

	public PriceNotFoundException(String productCode, long date, int department, int number) {
		super("productCode: " + productCode + ", date: " + date + ", department: " + department + ", number: " + number);
	}

	public PriceNotFoundException(String message) {
		super(message);
	}
}
