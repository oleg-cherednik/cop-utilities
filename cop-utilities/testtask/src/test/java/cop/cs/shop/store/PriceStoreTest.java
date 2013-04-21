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
	private static int id = 1;

	private PriceStore store;

	@Before
	public void init() {
		store = new PriceStore();
	}

	@Test
	public void testAddPriceNumber() throws IllegalDateRangeException, PriceNotFoundException {
		final long dateBegin = 100;
		final long dateEnd = dateBegin + 100;
		final long value1 = 111;
		final long value2 = 222;
		final long value3 = 333;
		final Price price1 = createPrice(dateBegin, dateEnd, 1, value1);
		final Price price2 = createPrice(dateBegin, dateEnd, 2, value2);
		final Price price3 = createPrice(dateBegin, dateEnd, 3, value3);

		store.addPrice(price1);
		store.addPrice(price2);
		store.addPrice(price3);

		assertEquals(value1, store.getPrice(PRODUCT_A, dateBegin + 50, DEPARTMENT1, 1));
		assertEquals(value2, store.getPrice(PRODUCT_A, dateBegin + 50, DEPARTMENT1, 2));
		assertEquals(value3, store.getPrice(PRODUCT_A, dateBegin + 50, DEPARTMENT1, 3));
	}

	@Test(expected = PriceNotFoundException.class)
	public void testUnknownDepartmentPrice() throws IllegalDateRangeException, PriceNotFoundException {
		final long dateBegin = 100;
		final long dateEnd = dateBegin + 100;
		final long value1 = 111;
		final long value2 = 222;
		final long value3 = 333;
		final Price price1 = createPrice(dateBegin, dateEnd, 1, value1);
		final Price price2 = createPrice(dateBegin, dateEnd, 2, value2);
		final Price price3 = createPrice(dateBegin, dateEnd, 3, value3);

		store.addPrice(price1);
		store.addPrice(price2);
		store.addPrice(price3);

		store.getPrice(PRODUCT_A, dateBegin + 50, DEPARTMENT2, 2);
	}

	@Test
	public void testInnerPrice1() throws IllegalDateRangeException, PriceNotFoundException {
		final long dateBegin = System.currentTimeMillis();
		final long dateEnd = dateBegin + 10000;
		final long value1 = 111;
		final long value2 = 222;
		final Price price1 = createPrice(dateBegin, dateEnd, 1, value1);
		final Price price2 = createPrice(dateBegin + 2000, dateEnd - 2000, 1, value2);

		store.addPrice(price1);
		store.addPrice(price2);

		assertEquals(value1, store.getPrice(PRODUCT_A, dateBegin, DEPARTMENT1, 1));
		assertEquals(value1, store.getPrice(PRODUCT_A, dateBegin + 1000, DEPARTMENT1, 1));
		assertEquals(value2, store.getPrice(PRODUCT_A, dateBegin + 2000, DEPARTMENT1, 1));
		assertEquals(value2, store.getPrice(PRODUCT_A, dateBegin + 3000, DEPARTMENT1, 1));
		assertEquals(value2, store.getPrice(PRODUCT_A, dateEnd - 2000, DEPARTMENT1, 1));
		assertEquals(value2, store.getPrice(PRODUCT_A, dateBegin + 5000, DEPARTMENT1, 1));
		assertEquals(value2, store.getPrice(PRODUCT_A, dateBegin + 5000, DEPARTMENT1, 1));
	}

	@Test
	public void testInnerPrice2() throws IllegalDateRangeException, PriceNotFoundException {
		final long date = 100;
		final Price price1 = createPrice(date, date + 70, 1, 100);
		final Price price2 = createPrice(date + 70, date + 100, 1, 120);
		final Price price3 = createPrice(date + 50, date + 80, 1, 110);

		store.addPrice(price1);
		assertEquals(100, store.getPrice(PRODUCT_A, date, DEPARTMENT1, 1));
		assertEquals(100, store.getPrice(PRODUCT_A, date + 50, DEPARTMENT1, 1));
		assertEquals(100, store.getPrice(PRODUCT_A, date + 70, DEPARTMENT1, 1));

		store.addPrice(price2);
		assertEquals(100, store.getPrice(PRODUCT_A, date, DEPARTMENT1, 1));
		assertEquals(100, store.getPrice(PRODUCT_A, date + 69, DEPARTMENT1, 1));
		assertEquals(120, store.getPrice(PRODUCT_A, date + 70, DEPARTMENT1, 1));
		assertEquals(120, store.getPrice(PRODUCT_A, date + 71, DEPARTMENT1, 1));
		assertEquals(120, store.getPrice(PRODUCT_A, date + 100, DEPARTMENT1, 1));

		store.addPrice(price3);
		assertEquals(100, store.getPrice(PRODUCT_A, date + 20, DEPARTMENT1, 1));
		assertEquals(100, store.getPrice(PRODUCT_A, date + 49, DEPARTMENT1, 1));
		assertEquals(110, store.getPrice(PRODUCT_A, date + 50, DEPARTMENT1, 1));
		assertEquals(110, store.getPrice(PRODUCT_A, date + 69, DEPARTMENT1, 1));
		assertEquals(110, store.getPrice(PRODUCT_A, date + 70, DEPARTMENT1, 1));
		assertEquals(110, store.getPrice(PRODUCT_A, date + 71, DEPARTMENT1, 1));
		assertEquals(110, store.getPrice(PRODUCT_A, date + 79, DEPARTMENT1, 1));
		assertEquals(110, store.getPrice(PRODUCT_A, date + 80, DEPARTMENT1, 1));
		assertEquals(120, store.getPrice(PRODUCT_A, date + 81, DEPARTMENT1, 1));
		assertEquals(120, store.getPrice(PRODUCT_A, date + 100, DEPARTMENT1, 1));
	}

	@Test
	public void testBigNewPrice() throws IllegalDateRangeException, PriceNotFoundException {
		final long date = 100;
		final Price price1 = createPrice(date + 20, date + 40, 1, 100);
		final Price price2 = createPrice(date + 60, date + 80, 1, 110);
		final Price price3 = createPrice(date, date + 100, 1, 120);

		store.addPrice(price1);
		store.addPrice(price2);
		store.addPrice(price3);

		assertEquals(120, store.getPrice(PRODUCT_A, date, DEPARTMENT1, 1));
		assertEquals(120, store.getPrice(PRODUCT_A, date + 19, DEPARTMENT1, 1));
		assertEquals(120, store.getPrice(PRODUCT_A, date + 20, DEPARTMENT1, 1));
		assertEquals(120, store.getPrice(PRODUCT_A, date + 21, DEPARTMENT1, 1));
		assertEquals(120, store.getPrice(PRODUCT_A, date + 39, DEPARTMENT1, 1));
		assertEquals(120, store.getPrice(PRODUCT_A, date + 40, DEPARTMENT1, 1));
		assertEquals(120, store.getPrice(PRODUCT_A, date + 41, DEPARTMENT1, 1));
		assertEquals(120, store.getPrice(PRODUCT_A, date + 59, DEPARTMENT1, 1));
		assertEquals(120, store.getPrice(PRODUCT_A, date + 60, DEPARTMENT1, 1));
		assertEquals(120, store.getPrice(PRODUCT_A, date + 61, DEPARTMENT1, 1));
		assertEquals(120, store.getPrice(PRODUCT_A, date + 79, DEPARTMENT1, 1));
		assertEquals(120, store.getPrice(PRODUCT_A, date + 80, DEPARTMENT1, 1));
		assertEquals(120, store.getPrice(PRODUCT_A, date + 81, DEPARTMENT1, 1));
		assertEquals(120, store.getPrice(PRODUCT_A, date + 100, DEPARTMENT1, 1));
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
