package cop.cs.shop;

import cop.cs.shop.data.DateRange;
import cop.cs.shop.data.Price;
import cop.cs.shop.data.PriceKey;
import cop.cs.shop.exceptions.IllegalDateRangeException;
import cop.cs.shop.exceptions.PriceNotFoundException;
import cop.cs.shop.exceptions.ProductNotFoundException;
import cop.cs.shop.utils.ProductCodeGenerator;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Oleg Cherednik
 * @since 21.04.2013
 */
public class ShopTest {
	private static final String PRODUCT_A = ProductCodeGenerator.get();
	private static final int DEPARTMENT1 = 1;
	private static int id = 1;

	private Shop shop;

	@Before
	public void init() {
		shop = new Shop("Metro");
	}

	@Test(expected = ProductNotFoundException.class)
	public void testAddUnknownProduct() throws ProductNotFoundException, IllegalDateRangeException {
		final long date = 100;

		shop.addPrice(createPrice(date, date + 100, 1, 111));
	}

	@Test(expected = ProductNotFoundException.class)
	public void testGetUnknownProductPrice() throws ProductNotFoundException, PriceNotFoundException {
		shop.getPrice(PRODUCT_A, 100, DEPARTMENT1, 1);
	}

	@Test(expected = ProductNotFoundException.class)
	public void testGetUnknownProductPriceHistory() throws ProductNotFoundException {
		shop.getPriceHistory(PRODUCT_A, DEPARTMENT1, 1);
	}

	// ========== static ==========

	private static Price createPrice(long dateBegin, long dateEnd, int number, long value)
			throws IllegalDateRangeException {
		Price.Builder builder = Price.createBuilder();

		builder.setId(id++);
		builder.setDateRange(DateRange.createDateRange(dateBegin, dateEnd));
		builder.setKey(PriceKey.createPriceKey(DEPARTMENT1, number));
		builder.setProductCode(PRODUCT_A);
		builder.setValue(value);

		return builder.createPrice();
	}
}
