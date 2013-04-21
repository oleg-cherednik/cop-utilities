package cop.cs.shop.store;

import cop.cs.shop.data.DateRange;
import cop.cs.shop.data.Price;
import cop.cs.shop.data.PriceKey;
import cop.cs.shop.data.Product;
import cop.cs.shop.exceptions.IllegalDateRangeException;
import cop.cs.shop.exceptions.PriceNotFoundException;
import cop.cs.shop.exceptions.ShopException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author Oleg Cherednik
 * @since 18.04.2013
 */
public final class PriceStore implements PriceProvider {
	private final Map<String, Map<PriceKey, Map<DateRange, Price>>> map = new HashMap<>();

	/**
	 * Search all existed date ranges that cross given one. Returned list is sorted by {@link DateRange#dateBegin}
	 *
	 * @param price     new price
	 * @param dateRange date range
	 * @return sorted by {@link DateRange#dateBegin} not <t>null</t> list of date ranges
	 */
	private List<DateRange> getCrossDateRange(Price price, DateRange dateRange) {
		if (!map.containsKey(price.getProductCode()))
			return Collections.emptyList();

		List<DateRange> res = new ArrayList<>();

		for (Map.Entry<PriceKey, Map<DateRange, Price>> entry : map.get(price.getProductCode()).entrySet())
			if (entry.getKey().equals(price.getKey()))
				for (DateRange range : entry.getValue().keySet())
					if (range.isCrossing(dateRange))
						res.add(range);

		if (res.isEmpty())
			return Collections.emptyList();

		Collections.sort(res);

		return Collections.unmodifiableList(res);
	}

	private void addPriceInt(Price price) {
		assert price != null && price != Price.NULL;

		Map<PriceKey, Map<DateRange, Price>> byProductCode = map.get(price.getProductCode());

		if (byProductCode == null)
			map.put(price.getProductCode(), byProductCode = new HashMap<>());

		Map<DateRange, Price> byPriceKey = byProductCode.get(price.getKey());

		if (byPriceKey == null)
			byProductCode.put(price.getKey(), byPriceKey = new TreeMap<>());

		byPriceKey.put(price.getDateRange(), price);
	}

	private Price getPriceInt(String productCode, long date, int department, int number) throws PriceNotFoundException {
		Map<PriceKey, Map<DateRange, Price>> byProductCode = map.get(productCode);

		if (byProductCode == null || byProductCode.isEmpty())
			throw new PriceNotFoundException(productCode, date, department, number);

		for (Map.Entry<PriceKey, Map<DateRange, Price>> entry1 : byProductCode.entrySet())
			if (entry1.getKey().equals(department, number))
				for (Map.Entry<DateRange, Price> entry2 : entry1.getValue().entrySet())
					if (entry2.getKey().contains(date))
						return entry2.getValue();

		throw new PriceNotFoundException(productCode, date, department, number);
	}

	private void removeDateRanges(Map<DateRange, Price> byPriceKey, List<DateRange> crossDateRange) {
		for (DateRange dateRange : crossDateRange)
			byPriceKey.remove(dateRange);
	}

	private void addDateRanges(Map<DateRange, Price> byPriceKey, Map<DateRange, Price> dateRanges) {
		for (Map.Entry<DateRange, Price> entry : dateRanges.entrySet())
			if (byPriceKey.put(entry.getKey(), entry.getValue()) != null)
				assert false : "date range exists";
	}

	private Map<DateRange, Price> getNewDateRanges(List<DateRange> crossDateRange, Price price) {
		Map<DateRange, Price> res = new HashMap<>();

		List<DateRange> dateRanges = getCrossLimitOnly(price.getDateRange(), crossDateRange);
		Set<Long> dates = getSignificantPoints(price.getDateRange(), dateRanges);

		for (long date : dates) {
			if (date < price.getDateRange().getDateBegin())
				addDateRangeBefore(res, price, date);
			else if (date > price.getDateRange().getDateEnd())
				addDateRangeAfter(res, price, date);
			else
				assert false : "dates contains illegal values";
		}

		if (res.isEmpty())
			return Collections.emptyMap();
		return Collections.unmodifiableMap(res);
	}

	private void addDateRangeBefore(Map<DateRange, Price> map, Price price, long date) {
		String productCode = price.getProductCode();
		int department = price.getKey().getDepartment();
		int number = price.getKey().getNumber();

		try {
			Price tmp = getPriceInt(productCode, date, department, number);
			map.put(DateRange.createDateRange(date, price.getDateRange().getDateBegin() - 1), tmp);
		} catch (ShopException e) {
			e.printStackTrace();
		}
	}

	private void addDateRangeAfter(Map<DateRange, Price> map, Price price, long date) {
		String productCode = price.getProductCode();
		int department = price.getKey().getDepartment();
		int number = price.getKey().getNumber();

		try {
			Price tmp = getPriceInt(productCode, date, department, number);
			map.put(DateRange.createDateRange(price.getDateRange().getDateEnd() + 1, date), tmp);
		} catch (ShopException e) {
			e.printStackTrace();
		}
	}

	// ========== PriceProvider ==========

	public void addPrice(Price price) {
		if (price == null || price == Price.NULL)
			return;

		final DateRange priceDateRange = price.getDateRange();
		final List<DateRange> crossDateRange = getCrossDateRange(price, priceDateRange);

		if (!crossDateRange.isEmpty()) {
			Map<DateRange, Price> newDateRanges = getNewDateRanges(crossDateRange, price);
			Map<PriceKey, Map<DateRange, Price>> byProductCode = map.get(price.getProductCode());
			Map<DateRange, Price> byPriceKey = byProductCode.get(price.getKey());

			removeDateRanges(byPriceKey, crossDateRange);
			addDateRanges(byPriceKey, newDateRanges);
		}

		addPriceInt(price);
		mergeDateRanges(price);
	}

	private void mergeDateRanges(Price price) {
		assert price != null && price != Price.NULL;

		Map<PriceKey, Map<DateRange, Price>> byProductCode = map.get(price.getProductCode());

		if (byProductCode == null)
			return;

		Map<DateRange, Price> byPriceKey = byProductCode.get(price.getKey());

		if (byPriceKey == null || byPriceKey.size() <= 1)
			return;

		DateRange prvDateRange = null;
		Price prvPrice = null;

		Map<DateRange, Price> res = new HashMap<>();

		for (Map.Entry<DateRange, Price> entry : byPriceKey.entrySet()) {
			DateRange dateRange = entry.getKey();

			if (prvDateRange == null) {
				prvDateRange = dateRange;
				prvPrice = entry.getValue();
			} else {
				if (prvPrice.getValue() != entry.getValue().getValue()) {
					res.put(prvDateRange, prvPrice);
					prvDateRange = dateRange;
					prvPrice = entry.getValue();
				} else {
					try {
						prvDateRange = DateRange.createDateRange(prvDateRange.getDateBegin(), dateRange.getDateEnd());
					} catch (IllegalDateRangeException e) {
						assert false : e.getMessage();
					}
				}
			}
		}

		if (prvDateRange != null)
			res.put(prvDateRange, prvPrice);

		byPriceKey.clear();
		byPriceKey.putAll(res);
	}

	public Map<DateRange, Long> getPriceHistory(String productCode, int department, int number) {
		if (!Product.isCodeValid(productCode))
			return Collections.emptyMap();

		Map<PriceKey, Map<DateRange, Price>> byProductCode = map.get(productCode);

		if (byProductCode == null || byProductCode.isEmpty())
			return Collections.emptyMap();

		Map<DateRange, Price> byPriceKey = byProductCode.get(PriceKey.createPriceKey(department, number));

		if (byPriceKey == null || byPriceKey.isEmpty())
			return Collections.emptyMap();

		Map<DateRange, Long> res = new TreeMap<>();

		for (Map.Entry<DateRange, Price> entry : byPriceKey.entrySet())
			res.put(entry.getKey(), entry.getValue().getValue());

		return Collections.unmodifiableMap(res);
	}

	@Override
	public long getPrice(String productCode, long date, int department, int number) throws PriceNotFoundException {
		return getPriceInt(productCode, date, department, number).getValue();
	}

	// ========== static ==========

	private static Set<Long> getSignificantPoints(DateRange priceDateRange, List<DateRange> dateRanges) {
		assert priceDateRange != null && priceDateRange != DateRange.NULL;
		assert dateRanges != null;

		if (dateRanges.isEmpty())
			return Collections.emptySet();

		Set<Long> dates = new TreeSet<>();

		for (DateRange dateRange : dateRanges) {
			if (!priceDateRange.contains(dateRange.getDateBegin()))
				dates.add(dateRange.getDateBegin());
			if (!priceDateRange.contains(dateRange.getDateEnd()))
				dates.add(dateRange.getDateEnd());
		}

		assert dates.size() <= 2;

		return dates.isEmpty() ? Collections.<Long>emptySet() : Collections.unmodifiableSet(dates);
	}

	/**
	 * Returns date ranges that cross exactly <t>dateBegin</t> or <t>dateEnd</t>
	 *
	 * @param priceDateRange date range of the new price
	 * @param dateRanges     date ranges that crossing price date range
	 * @return not <t>null</t> list of date ranges
	 */
	private static List<DateRange> getCrossLimitOnly(DateRange priceDateRange, List<DateRange> dateRanges) {
		assert priceDateRange != null;
		assert dateRanges != null && !dateRanges.isEmpty();

		List<DateRange> res = new ArrayList<>(2);

		for (DateRange range : dateRanges)
			if (range.contains(priceDateRange.getDateBegin()) || range.contains(priceDateRange.getDateEnd()))
				res.add(range);

		assert res.size() <= 2;

		return res.isEmpty() ? Collections.<DateRange>emptyList() : Collections.unmodifiableList(res);
	}
}
