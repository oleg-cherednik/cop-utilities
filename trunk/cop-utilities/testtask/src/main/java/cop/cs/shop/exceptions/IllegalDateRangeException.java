package cop.cs.shop.exceptions;

/**
 * @author Oleg Cherednik
 * @since 19.04.2013
 */
public class IllegalDateRangeException extends ShopException {
	public IllegalDateRangeException(long dateBegin, long dateEnd) {
		super("dateBegin: " + dateBegin + ", dateEnd: " + dateEnd);
	}
}
