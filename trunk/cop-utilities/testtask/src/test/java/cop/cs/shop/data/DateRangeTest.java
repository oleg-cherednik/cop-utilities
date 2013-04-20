package cop.cs.shop.data;

import cop.cs.shop.exceptions.IllegalDateRangeException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Oleg Cherednik
 * @since 19.04.2013
 */
public class DateRangeTest {
	@Test(expected = IllegalDateRangeException.class)
	public void testInvalidDate1() throws IllegalDateRangeException {
		DateRange.createDateRange(10, 9);
	}

	@Test(expected = IllegalDateRangeException.class)
	public void testInvalidDate2() throws IllegalDateRangeException {
		DateRange.createDateRange(-1, 0);
	}

	@Test(expected = IllegalDateRangeException.class)
	public void testInvalidDate3() throws IllegalDateRangeException {
		DateRange.createDateRange(0, -1);
	}

	@Test(expected = IllegalDateRangeException.class)
	public void testInvalidDate4() throws IllegalDateRangeException {
		DateRange.createDateRange(-1, -1);
	}

	@Test
	public void testValidDate() throws IllegalDateRangeException {
		final long dateBegin = System.currentTimeMillis();
		final long dateEnd = dateBegin + 10000;

		DateRange dateRange1 = DateRange.createDateRange(dateBegin, dateEnd);
		DateRange dateRange2 = DateRange.createDateRange(dateBegin);

		assertEquals(dateBegin, dateRange1.getDateBegin());
		assertEquals(dateEnd, dateRange1.getDateEnd());
		assertEquals(dateBegin, dateRange2.getDateBegin());
		assertEquals(dateBegin, dateRange2.getDateEnd());
	}

	@Test
	public void testSameDateInstance() throws IllegalDateRangeException {
		final long dateBegin = System.currentTimeMillis();
		final long dateEnd = dateBegin + 10000;

		DateRange dateRange1 = DateRange.createDateRange(dateBegin, dateEnd);
		DateRange dateRange2 = DateRange.createDateRange(dateBegin, dateEnd);
		DateRange dateRange3 = DateRange.createDateRange(dateBegin);
		DateRange dateRange4 = DateRange.createDateRange(dateBegin, dateBegin);
		DateRange dateRange5 = DateRange.createDateRange(0, 0);

		assertEquals(dateRange1, dateRange2);
		assertEquals(dateRange3, dateRange4);
		assertEquals(DateRange.NULL, dateRange5);
	}

	@Test
	public void testEquals() throws IllegalDateRangeException {
		final DateRange nullObj = null;
		final long dateBegin = System.currentTimeMillis();
		final long dateEnd = dateBegin + 10000;

		DateRange dateRange1 = DateRange.createDateRange(dateBegin, dateEnd);
		DateRange dateRange2 = DateRange.createDateRange(dateBegin, dateEnd);
		DateRange dateRange3 = DateRange.createDateRange(dateBegin, dateEnd);

		assertTrue(dateRange1.equals(dateRange1));
		assertTrue(dateRange1.equals(dateRange2));
		assertTrue(dateRange2.equals(dateRange1));
		assertTrue(dateRange2.equals(dateRange3));
		assertTrue(dateRange3.equals(dateRange3));
		assertFalse(dateRange1.equals(nullObj));
		assertFalse(dateRange1.equals(DateRange.NULL));
		assertTrue(DateRange.NULL.equals(DateRange.NULL));
		assertTrue(DateRange.NULL.equals(nullObj));
	}

	@Test
	public void testContains1() throws IllegalDateRangeException {
		final long dateBegin = System.currentTimeMillis();
		final long dateEnd = dateBegin + 10000;
		DateRange dateRange = DateRange.createDateRange(dateBegin, dateEnd);

		assertTrue(dateRange.contains(dateBegin));
		assertTrue(dateRange.contains(dateBegin + 1));
		assertTrue(dateRange.contains(dateEnd));
		assertFalse(dateRange.contains(dateBegin - 1));
		assertFalse(dateRange.contains(dateEnd + 1));
	}

	@Test
	public void testContains2() throws IllegalDateRangeException {
		final long date = System.currentTimeMillis();
		DateRange dateRange = DateRange.createDateRange(date);

		assertTrue(dateRange.contains(date));
		assertFalse(dateRange.contains(date - 1));
		assertFalse(dateRange.contains(date + 1));
	}

	@Test
	public void testCrossing1() throws IllegalDateRangeException {
		final long dateBegin = System.currentTimeMillis();
		final long dateEnd = dateBegin + 10000;
		final DateRange dateRange = DateRange.createDateRange(dateBegin, dateEnd);

		assertTrue(dateRange.isCrossing(DateRange.createDateRange(dateBegin - 1, dateEnd + 1)));
		assertTrue(dateRange.isCrossing(DateRange.createDateRange(dateBegin - 1, dateEnd)));
		assertTrue(dateRange.isCrossing(DateRange.createDateRange(dateBegin - 1, dateBegin + 1)));
		assertTrue(dateRange.isCrossing(DateRange.createDateRange(dateBegin - 1, dateBegin)));
		assertTrue(dateRange.isCrossing(DateRange.createDateRange(dateBegin, dateEnd + 1)));
		assertTrue(dateRange.isCrossing(DateRange.createDateRange(dateBegin + 1, dateEnd + 1)));
		assertTrue(dateRange.isCrossing(DateRange.createDateRange(dateEnd - 1, dateEnd + 1)));
		assertTrue(dateRange.isCrossing(DateRange.createDateRange(dateEnd, dateEnd + 1)));
		assertTrue(dateRange.isCrossing(DateRange.createDateRange(dateBegin, dateEnd)));
		assertTrue(dateRange.isCrossing(DateRange.createDateRange(dateBegin + 1, dateEnd)));
		assertTrue(dateRange.isCrossing(DateRange.createDateRange(dateBegin, dateEnd - 1)));
		assertTrue(dateRange.isCrossing(DateRange.createDateRange(dateBegin + 1, dateEnd - 1)));
		assertTrue(dateRange.isCrossing(DateRange.createDateRange(dateBegin + 1)));
		assertFalse(dateRange.isCrossing(DateRange.createDateRange(dateBegin - 3, dateBegin - 1)));
		assertFalse(dateRange.isCrossing(DateRange.createDateRange(dateEnd + 1, dateEnd + 3)));
		assertFalse(dateRange.isCrossing(DateRange.createDateRange(dateBegin - 1)));
		assertFalse(dateRange.isCrossing(DateRange.createDateRange(dateEnd + 1)));
	}

	@Test
	public void testCrossing2() throws IllegalDateRangeException {
		final long date = System.currentTimeMillis();
		final DateRange dateRange = DateRange.createDateRange(date);

		assertTrue(dateRange.isCrossing(DateRange.createDateRange(date - 1, date + 1)));
		assertTrue(dateRange.isCrossing(DateRange.createDateRange(date - 1, date)));
		assertTrue(dateRange.isCrossing(DateRange.createDateRange(date, date + 1)));
		assertTrue(dateRange.isCrossing(DateRange.createDateRange(date)));
		assertFalse(dateRange.isCrossing(DateRange.createDateRange(date - 1)));
		assertFalse(dateRange.isCrossing(DateRange.createDateRange(date + 1)));
		assertFalse(dateRange.isCrossing(DateRange.createDateRange(date - 3, date - 1)));
		assertFalse(dateRange.isCrossing(DateRange.createDateRange(date + 1, date + 3)));
	}

}
