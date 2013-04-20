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

		assertEquals("dateRange1.getDateBegin()", dateBegin, dateRange1.getDateBegin());
		assertEquals("dateRange1.getDateEnd()", dateEnd, dateRange1.getDateEnd());
		assertEquals("dateRange2.getDateBegin()", dateBegin, dateRange2.getDateBegin());
		assertEquals("dateRange2.getDateEnd()", dateBegin, dateRange2.getDateEnd());
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

		assertTrue("dateRange1 == dateRange2", dateRange1 == dateRange2);
		assertTrue("dateRange3 == dateRange4", dateRange3 == dateRange4);
		assertTrue("DateRange.NULL == dateRange5", DateRange.NULL == dateRange5);
	}

	@Test
	public void testEquals() throws IllegalDateRangeException {
		final DateRange nullObj = null;
		final long dateBegin = System.currentTimeMillis();
		final long dateEnd = dateBegin + 10000;

		DateRange dateRange1 = DateRange.createDateRange(dateBegin, dateEnd);
		DateRange dateRange2 = DateRange.createDateRange(dateBegin, dateEnd);
		DateRange dateRange3 = DateRange.createDateRange(dateBegin, dateEnd);

		assertTrue("dateRange1.equals(dateRange1)", dateRange1.equals(dateRange1));
		assertTrue("dateRange1.equals(dateRange2)", dateRange1.equals(dateRange2));
		assertTrue("dateRange2.equals(dateRange1)", dateRange2.equals(dateRange1));
		assertTrue("dateRange2.equals(dateRange3)", dateRange2.equals(dateRange3));
		assertTrue("dateRange3.equals(dateRange3)", dateRange3.equals(dateRange3));
		assertFalse("dateRange1.equals(null)", dateRange1.equals(nullObj));
		assertFalse("dateRange1.equals(DateRange.NULL)", dateRange1.equals(DateRange.NULL));
		assertTrue("DateRange.NULL.equals(DateRange.NULL)", DateRange.NULL.equals(DateRange.NULL));
		assertTrue("DateRange.NULL.equals(null)", DateRange.NULL.equals(nullObj));
	}
}
