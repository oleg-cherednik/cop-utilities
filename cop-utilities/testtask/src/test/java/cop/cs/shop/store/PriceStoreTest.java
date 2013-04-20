package cop.cs.shop.store;

import cop.cs.shop.data.DateRange;
import cop.cs.shop.data.Price;
import cop.cs.shop.data.PriceKey;
import cop.cs.shop.exceptions.IllegalDateRangeException;
import cop.cs.shop.exceptions.PriceNotFoundException;
import cop.cs.shop.utils.ProductCodeGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Oleg Cherednik
 * @since 20.04.2013
 */
public class PriceStoreTest {
	private static final String PRODUCT_A = ProductCodeGenerator.get();
	private static final int DEPARTMENT1 = 1;
	private static final int DEPARTMENT2 = 2;

	private PriceStore store;

	@Before
	public void init() {
		store = new PriceStore();
	}

	@Test
	public void testAddPriceNumber() throws IllegalDateRangeException, PriceNotFoundException {
		final long dateBegin = System.currentTimeMillis();
		final long dateEnd = dateBegin + 10000;
		final long value1 = 111;
		final long value2 = 222;
		final long value3 = 333;
		final Price price1 = createPrice(dateBegin, dateEnd, 1, value1);
		final Price price2 = createPrice(dateBegin, dateEnd, 2, value2);
		final Price price3 = createPrice(dateBegin, dateEnd, 3, value3);

		store.addPrice(price1);
		store.addPrice(price2);
		store.addPrice(price3);

		assertEquals(value1, store.getPrice(PRODUCT_A, dateBegin + 5000, DEPARTMENT1, 1));
		assertEquals(value2, store.getPrice(PRODUCT_A, dateBegin + 5000, DEPARTMENT1, 2));
		assertEquals(value3, store.getPrice(PRODUCT_A, dateBegin + 5000, DEPARTMENT1, 3));
	}

	@Test(expected = PriceNotFoundException.class)
	public void testUnknownDepartmentPrice() throws IllegalDateRangeException, PriceNotFoundException {
		final long dateBegin = System.currentTimeMillis();
		final long dateEnd = dateBegin + 10000;
		final long value1 = 111;
		final long value2 = 222;
		final long value3 = 333;
		final Price price1 = createPrice(dateBegin, dateEnd, 1, value1);
		final Price price2 = createPrice(dateBegin, dateEnd, 2, value2);
		final Price price3 = createPrice(dateBegin, dateEnd, 3, value3);

		store.addPrice(price1);
		store.addPrice(price2);
		store.addPrice(price3);

		store.getPrice(PRODUCT_A, dateBegin + 5000, DEPARTMENT2, 2);
	}

	// ========== static ==========

	private static Price createPrice(long dateBegin, long dateEnd, int number, long value)
			throws IllegalDateRangeException {
		Price.Builder builder = Price.createBuilder();

		builder.setId(1);
		builder.setDateRange(DateRange.createDateRange(dateBegin, dateEnd));
		builder.setKey(PriceKey.createPriceKey(DEPARTMENT1, number));
		builder.setProductCode(PRODUCT_A);
		builder.setValue(value);

		return builder.createPrice();
	}
}
