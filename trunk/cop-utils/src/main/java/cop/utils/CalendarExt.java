/*
 * $Id$
 * $HeadURL$
 */
package cop.utils;

import static cop.utils.CommonExt.isNotNull;
import static cop.utils.CommonExt.isNull;
import static cop.utils.NumericExt.isInRangeMinMax;
import static java.lang.Integer.parseInt;
import static java.text.DateFormat.SHORT;
import static java.util.Arrays.copyOf;
import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.DAY_OF_YEAR;
import static java.util.Calendar.DECEMBER;
import static java.util.Calendar.HOUR;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.JANUARY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.YEAR;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

import cop.utils.enums.TitleTypeEnum;

/**
 * Class provides common methods that can be used within date/time context.<br>
 * This class contains only <i><u>static</u></i> methods. It can't be instantiated or inherited.<br>
 * 
 * @author cop (Cherednik, Oleg)
 * @version 1.0.0 (23.12.2009)
 * @see java.util.Calendar
 * @see org.eclipse.swt.widgets.DateTime
 */
public final class CalendarExt {
	public static final int HOUR12_MIN = 0;
	public static final int HOUR12_MAX = 11;
	public static final int HOUR24_MIN = 0;
	public static final int HOUR24_MAX = 23;
	public static final int MINUTE_MIN = 0;
	public static final int MINUTE_MAX = 59;
	public static final int SECOND_MIN = 0;
	public static final int SECOND_MAX = 59;
	public static final int YEAR_MIN = 0;
	public static final int YEAR_MAX = 9999;

	public static final int DAY_MIN = 1;
	public static final int DAY_MAX = 31;
	public static final int MONTH_MIN = JANUARY;
	public static final int MONTH_MAX = DECEMBER;

	public static final int WEEK_DAYS_NUM = 7;

	private CalendarExt() {}

	/**
	 * If <b>hour</b> is valid hour representation string, then this value will be retunrd. Otherwise <b>defaultHour</b>
	 * will be returnd;
	 * 
	 * @param hour string hour representation
	 * @param defaultHour default hour representation, that will be returnd if smth. wrong with <b>hour</b>
	 * @return <b>hour</b> if it's a vilid hour string, or <b>defaultHour</b>
	 */
	public static String toHour(String hour, String defaultHour) {
		if (StringUtils.isEmpty(hour))
			return defaultHour;

		String trimedHour = hour.trim();

		return isHour24(trimedHour) ? trimedHour : defaultHour;
	}

	/**
	 * If <b>minute</b> is valid minute representation string, then this value will be retunrd. Otherwise
	 * <b>defaultMinute</b> will be returnd;
	 * 
	 * @param minute string minute representation
	 * @param defaultMinute default minute representation, that will be returnd if smth. wrong with <b>minute</b>
	 * @return <b>hour</b> if it's a vilid minute string, or <b>defaultMinute</b>
	 */
	public static String toMinute(String minute, String defaultMinute) {
		if (StringUtils.isEmpty(minute))
			return defaultMinute;

		String trimedMinute = minute.trim();

		return isMinute(trimedMinute) ? trimedMinute : defaultMinute;
	}

	/**
	 * If <b>second</b> is valid second representation string, then this value will be retunrd. Otherwise
	 * <b>defaultSeconds</b> will be returnd;
	 * 
	 * @param second string second representation
	 * @param defaultSeconds default second representation, that will be returnd if smth. wrong with <b>second</b>
	 * @return <b>second</b> if it's a vilid second string, or <b>defaultSeconds</b>
	 */
	public static String toSecond(String second, String defaultSeconds) {
		if (StringUtils.isEmpty(second))
			return defaultSeconds;

		String trimedSecond = second.trim();

		return isSecond(trimedSecond) ? trimedSecond : defaultSeconds;
	}

	public static int toHour24(String hour) {
		return (hour != null && isHour24(hour.trim())) ? parseInt(hour) : -1;
	}

	public static int toMinute(String minute) {
		return (minute != null && isMinute(minute.trim())) ? parseInt(minute) : -1;
	}

	public static int toSecond(String second) {
		return (second != null && isSecond(second.trim())) ? parseInt(second) : -1;
	}

	public static boolean isHour24(String hour) {
		try {
			return isHour24(parseInt(hour));
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isHour12(String hour) {
		try {
			return isHour12(parseInt(hour));
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isMinute(String minute) {
		try {
			return isMinute(parseInt(minute));
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isSecond(String second) {
		try {
			return isSecond(parseInt(second));
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isDay(String day) {
		try {
			return isDay(parseInt(day));
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isYear(String year) {
		try {
			return isYear(parseInt(year));
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isHour24(int hour) {
		return isInRangeMinMax(hour, HOUR24_MIN, HOUR24_MAX);
	}

	public static boolean isHour12(int hour) {
		return isInRangeMinMax(hour, HOUR12_MIN, HOUR12_MAX);
	}

	public static boolean isMinute(int minute) {
		return isInRangeMinMax(minute, MINUTE_MIN, MINUTE_MAX);
	}

	public static boolean isSecond(int seconds) {
		return isInRangeMinMax(seconds, SECOND_MIN, SECOND_MAX);
	}

	public static boolean isYear(int year) {
		return isInRangeMinMax(year, YEAR_MIN, YEAR_MAX);
	}

	public static boolean isDay(int day) {
		return isInRangeMinMax(day, DAY_MIN, DAY_MAX);
	}

	public static boolean isMonth(int day) {
		return isInRangeMinMax(day, MONTH_MIN, MONTH_MAX);
	}

	public static Calendar createTimeCalendar(int hour, int minute, int second) {
		boolean isHour = isHour24(hour);
		boolean isMinute = isMinute(minute);
		boolean isSecond = isSecond(second);

		if (!isHour && !isMinute && !isSecond)
			return null;

		Calendar date = Calendar.getInstance();

		date.clear();
		date.set(HOUR_OF_DAY, hour);
		date.set(MINUTE, hour);
		date.set(SECOND, second);

		return date;
	}

	public static Calendar createCalendar(Timestamp date) {
		if (date == null)
			return null;

		Calendar day = Calendar.getInstance();

		day.setTime(date);

		return day;
	}

	public static int convertHour24To12(int hour24) {
		if (hour24 <= HOUR12_MAX || hour24 > HOUR24_MAX)
			return hour24;

		return hour24 - HOUR12_MAX - 1;
	}

	// public static int getYearLong(int year2Digits, int min4Digit)
	// {
	// if(!isInRange(year2Digits, 0, 99))
	// return -1;
	//
	// if(!isInRange(min4Digit, 1000, 9999))
	// return -1;
	//
	// String str = "" + min4Digit;
	// int minHi = parseInt(str.substring(0, 2));
	// int minLo = parseInt(str.substring(2));
	// String val = expandToLimitWithChar(year2Digits, 2, '0');
	//
	// if(year2Digits >= minLo)
	// return parseInt("" + minHi + val);
	// else
	// return parseInt((minHi + 1) + val);
	// }

	public static String[] getMonths(Locale locale) {
		return copyOf(new DateFormatSymbols(locale).getMonths(), 12);
	}

	public static String[] getShortMonths(Locale locale) {
		return copyOf(new DateFormatSymbols(locale).getShortMonths(), 12);
	}

	public static String[] getWeekdays(Locale locale) {
		return new DateFormatSymbols(locale).getWeekdays();
	}

	public static String[] getShortWeekdays(Locale locale) {
		return new DateFormatSymbols(locale).getShortWeekdays();
	}

	public static String getDefaultDate(Calendar date) {
		if (date == null)
			return "";

		DateFormat df = DateFormat.getDateInstance(SHORT);

		return df.format(date.getTime());
	}

	public static String getDefaultTime(Calendar date) {
		if (date == null)
			return "";

		DateFormat df = DateFormat.getTimeInstance(SHORT);

		return df.format(date.getTime());
	}

	public static boolean isEmpty(Calendar date) {
		return isDateEmpty(date) && isTimeEmpty(date);
	}

	public static boolean isDateEmpty(Calendar date) {
		return isNull(date) ? true : !date.isSet(DATE) && !date.isSet(MONTH) && !date.isSet(YEAR);
	}

	public static boolean isTimeEmpty(Calendar date) {
		return isNull(date) ? true : !date.isSet(HOUR) && !date.isSet(MINUTE) && !date.isSet(SECOND);
	}

	public static boolean setDate(Calendar day, Calendar date) {
		if (isDateEmpty(date) || isNull(day))
			return false;

		day.set(DATE, date.get(DATE));
		day.set(MONTH, date.get(MONTH));
		day.set(YEAR, date.get(YEAR));

		return true;
	}

	public static boolean setTime(Calendar day, Calendar time) {
		if (isTimeEmpty(time))
			return false;

		day.set(HOUR_OF_DAY, time.get(HOUR_OF_DAY));
		day.set(MINUTE, time.get(MINUTE));
		day.set(SECOND, time.get(SECOND));

		return true;
	}

	public static Calendar createCalendar(Calendar date, Locale locale) {
		if (isNull(date) || isNull(locale))
			return date;

		Calendar day = Calendar.getInstance(locale);

		setDate(day, date);
		setTime(day, date);

		return day;
	}

	public static Calendar createCalendar(Calendar date, Calendar time) {
		Calendar day = Calendar.getInstance();
		boolean changed = false;

		day.clear();

		changed |= setDate(day, date);
		changed |= setTime(day, time);

		return changed ? day : null;
	}

	public static String[] getWeekdaysTitles(TitleTypeEnum type, Locale locale) {
		switch(type)
		{
		case ONE_CHAR:
			return getShortWeekdays(1, locale);
		case TWO_CHARS:
			return getShortWeekdays(2, locale);
		case BRIEF:
			return getShortWeekdays(locale);
		default:
			return getWeekdays(locale);
		}
	}

	private static String[] getShortWeekdays(int length, Locale locale) {
		String[] weekdays = getShortWeekdays(locale);

		for (int i = 0, size = weekdays.length; i < size; i++)
			if (weekdays[i].length() > length)
				weekdays[i] = weekdays[i].substring(0, length);

		return weekdays;
	}

	public static boolean isMonthsSame(Calendar date1, Calendar date2) {
		if (isNotNull(date1) && isNotNull(date2))
			return _isMonthSame(date1, date2) && _isYearSame(date1, date2);

		return false;
	}

	public static boolean isDaySame(Calendar date1, Calendar date2) {
		if (isNotNull(date1) && isNotNull(date2))
			return _isDaySame(date1, date2) && _isYearSame(date1, date2);

		return false;
	}

	public static boolean isDateSame(Calendar date1, Calendar date2) {
		return isMonthsSame(date1, date2) && _isDaySame(date1, date2);
	}

	private static boolean _isYearSame(Calendar date1, Calendar date2) {
		return date1.get(YEAR) == date2.get(YEAR);
	}

	private static boolean _isMonthSame(Calendar date1, Calendar date2) {
		return date1.get(MONTH) == date2.get(MONTH);
	}

	private static boolean _isDaySame(Calendar date1, Calendar date2) {
		return date1.get(DAY_OF_YEAR) == date2.get(DAY_OF_YEAR);
	}

	public static Calendar getFirstCalendarDate(Calendar date) {
		Calendar firstDay = createCalendar(date, Locale.getDefault());

		firstDay.set(DAY_OF_MONTH, 1);
		firstDay.add(DAY_OF_MONTH, getFirstWeekdayOffs(firstDay));

		return firstDay;
	}

	private static int getFirstWeekdayOffs(Calendar date) {
		int offs = date.getFirstDayOfWeek() - date.get(DAY_OF_WEEK);

		return (offs > 0) ? (offs - 7) : offs;
	}
}
