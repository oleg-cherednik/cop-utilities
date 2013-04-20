package cop.cs.shop.data;

import cop.cs.shop.exceptions.IllegalDateRangeException;

/**
 * @author Oleg Cherednik
 * @since 19.04.2013
 */
public final class DateRange implements Comparable<DateRange> {
	public static final DateRange NULL = new DateRange(0, 0);

	private final long dateBegin;
	private final long dateEnd;

	public static DateRange createDateRange(long date) throws IllegalDateRangeException {
		return createDateRange(date, date);
	}

	public static DateRange createDateRange(long dateBegin, long dateEnd) throws IllegalDateRangeException {
		if (dateBegin == NULL.getDateBegin() && dateEnd == NULL.dateEnd)
			return NULL;
		if (dateBegin > dateEnd || dateBegin <= 0 || dateEnd <= 0)
			throw new IllegalDateRangeException("dateBegin > dateEnd || dateBegin <= 0 || dateEnd <= 0");

		return new DateRange(dateBegin, dateEnd);
	}

	private DateRange(long dateBegin, long dateEnd) {
		this.dateBegin = dateBegin;
		this.dateEnd = dateEnd;
	}

	public long getDateBegin() {
		return dateBegin;
	}

	public long getDateEnd() {
		return dateEnd;
	}

	public boolean isCrossing(DateRange dateRange) {
		if (dateRange == null || dateRange == DateRange.NULL || this == DateRange.NULL)
			return false;
		if (dateBegin <= dateRange.dateBegin)
			return dateEnd >= dateRange.dateBegin;
		return dateBegin <= dateRange.dateEnd;
	}

	public boolean contains(long date) {
		return dateBegin <= date && date <= dateEnd;
	}

	// ========== Comparable ==========

	@Override
	public int compareTo(DateRange dateRange) {
		if (dateRange == null)
			return 1;

		if (dateBegin == dateRange.dateBegin) {
			if (dateEnd == dateRange.dateEnd)
				return 0;
			return dateEnd < dateRange.dateEnd ? -1 : 1;
		}

		return dateBegin < dateRange.dateBegin ? -1 : 1;
	}

	// ========== Object ==========

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (this == NULL && obj == null)
			return true;
		if (!(obj instanceof DateRange))
			return false;

		DateRange dateRange = (DateRange)obj;

		return this != NULL && dateBegin == dateRange.dateBegin && dateEnd == dateRange.dateEnd;
	}

	@Override
	public int hashCode() {
		return ("" + dateBegin + '_' + dateEnd).hashCode();
	}

	@Override
	public String toString() {
		if (this == NULL)
			return "<empty>";
		if (dateBegin == dateEnd)
			return "dateBegin = dateEnd = " + dateBegin;
		return "dateBegin=" + dateBegin + ", dateEnd=" + dateEnd;
	}
}
