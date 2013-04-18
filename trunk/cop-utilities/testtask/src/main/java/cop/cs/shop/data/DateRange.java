package cop.cs.shop.data;

import cop.cs.shop.exceptions.IllegalDateRangeException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Oleg Cherednik
 * @since 19.04.2013
 */
public final class DateRange implements Comparable<DateRange> {
	public static final DateRange NULL = new DateRange(0, 0);

	private static final Map<Integer, DateRange> map = new HashMap<>();

	private final long dateBegin;
	private final long dateEnd;

	public static DateRange createDateRange(long date) throws IllegalDateRangeException {
		return createDateRange(date, date);
	}

	public static DateRange createDateRange(long dateBegin, long dateEnd) throws IllegalDateRangeException {
		int hashCode = hashCode(dateBegin, dateEnd);
		DateRange dateRange = map.get(hashCode);

		if (dateRange == null) {
			if (dateBegin > dateEnd || (dateBegin == 0 && dateEnd == 0))
				throw new IllegalDateRangeException(dateBegin, dateEnd);
			dateRange = new DateRange(dateBegin, dateEnd);
		}

		return dateRange;
	}

	private DateRange(long dateBegin, long dateEnd) {
		this.dateBegin = dateBegin;
		this.dateEnd = dateEnd;
		map.put(hashCode(), this);
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
		if (!(obj instanceof DateRange))
			return false;

		DateRange dateRange = (DateRange)obj;

		return this != NULL && dateBegin == dateRange.dateBegin && dateEnd == dateRange.dateEnd;
	}

	@Override
	public int hashCode() {
		return hashCode(dateBegin, dateEnd);
	}

	// ========== static ==========

	private static int hashCode(long dateBegin, long dateEnd) {
		int result = (int)(dateBegin ^ (dateBegin >>> 32));
		result = 31 * result + (int)(dateEnd ^ (dateEnd >>> 32));
		return result;
	}
}
