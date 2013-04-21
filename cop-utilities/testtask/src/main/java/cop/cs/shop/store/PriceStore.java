package cop.cs.shop.store;

import cop.cs.shop.data.DateRange;
import cop.cs.shop.data.Price;
import cop.cs.shop.data.PriceKey;
import cop.cs.shop.data.Product;
import cop.cs.shop.exceptions.IllegalDateRangeException;
import cop.cs.shop.exceptions.PriceNotFoundException;
import cop.cs.shop.exceptions.ProductNotFoundException;

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
	private final Map<String, Map<DateRange, Map<PriceKey, Price>>> map = new HashMap<>();

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

		for (Map.Entry<DateRange, Map<PriceKey, Price>> entry : map.get(price.getProductCode()).entrySet())
			if (entry.getKey().isCrossing(dateRange) && entry.getValue().containsKey(price.getKey()))
				res.add(entry.getKey());

		if (res.isEmpty())
			return Collections.emptyList();

		Collections.sort(res);

		return Collections.unmodifiableList(res);
	}

	private void addPriceInt(Price price) {
		assert price != null && price != Price.NULL;

		Map<DateRange, Map<PriceKey, Price>> byProductCode = map.get(price.getProductCode());

		if (byProductCode == null)
			map.put(price.getProductCode(), byProductCode = new HashMap<>());

		Map<PriceKey, Price> byDateRange = byProductCode.get(price.getDateRange());

		if (byDateRange == null)
			byProductCode.put(price.getDateRange(), byDateRange = new HashMap<>());

		byDateRange.put(price.getKey(), price);
	}

	private Price getPriceInt(String productCode, long date, int department, int number) throws PriceNotFoundException {
		Map<DateRange, Map<PriceKey, Price>> byProductCode = map.get(productCode);

		if (byProductCode == null || byProductCode.isEmpty())
			throw new PriceNotFoundException(productCode, date, department, number);

		for (Map.Entry<DateRange, Map<PriceKey, Price>> entry1 : byProductCode.entrySet())
			if (entry1.getKey().contains(date))
				for (Map.Entry<PriceKey, Price> entry2 : entry1.getValue().entrySet())
					if (entry2.getKey().getDepartment() == department && entry2.getKey().getNumber() == number)
						return entry2.getValue();

		throw new PriceNotFoundException(productCode, date, department, number);
	}

	// ========== PriceProvider ==========

	public void addPrice(Price price) {
		if (price == null || price == Price.NULL)
			return;

		final DateRange priceDateRange = price.getDateRange();
		List<DateRange> crossDateRange = getCrossDateRange(price, priceDateRange);

		if (!crossDateRange.isEmpty()) {
			Map<DateRange, Price> res = new HashMap<>();

			//			res.put(price.getDateRange(), price);

			List<DateRange> dateRanges = getCrossLimitOnly(priceDateRange, crossDateRange);
			Set<Long> dates = getSignificantPoints(priceDateRange, dateRanges);

			for (long date : dates) {
				if (date < priceDateRange.getDateBegin()) {
					String productCode = price.getProductCode();
					int department = price.getKey().getDepartment();
					int number = price.getKey().getNumber();

					try {
						Price tmp = getPriceInt(productCode, date, department, number);
						res.put(DateRange.createDateRange(date, priceDateRange.getDateBegin() - 1), tmp);
					} catch (PriceNotFoundException e) {
						e.printStackTrace();
					} catch (IllegalDateRangeException e) {
						e.printStackTrace();
					}
				} else if (date > priceDateRange.getDateEnd()) {
					String productCode = price.getProductCode();
					int department = price.getKey().getDepartment();
					int number = price.getKey().getNumber();

					try {
						Price tmp = getPriceInt(productCode, date, department, number);
						res.put(DateRange.createDateRange(priceDateRange.getDateEnd() + 1, date), tmp);
					} catch (PriceNotFoundException e) {
						e.printStackTrace();
					} catch (IllegalDateRangeException e) {
						e.printStackTrace();
					}
				} else {
					int a = 0;
					a++;

				}
			}

			Map<DateRange, Map<PriceKey, Price>> byProductCode = map.get(price.getProductCode());

			for (DateRange dateRange : crossDateRange)
				byProductCode.remove(dateRange);

			for (Map.Entry<DateRange, Price> entry : res.entrySet()) {
				Map<PriceKey, Price> byDateRange = byProductCode.get(entry.getKey());

				if (byDateRange == null)
					byProductCode.put(entry.getKey(), byDateRange = new HashMap<>());

				byDateRange.put(price.getKey(), entry.getValue());
			}
		}

		addPriceInt(price);
	}

	public List<Price> getPriceHistory(String productCode, int department) {
		if (!Product.isCodeValid(productCode))
			return Collections.emptyList();

		Map<DateRange, Price> res = new TreeMap<>();
		//		Map<DateRange, Map<PriceKey, Price>> byProductCode = map.get(productCode);
		//
		//		if(byProductCode == null || byProductCode.isEmpty())
		//			return

		//		for(Map.Entry<DateRange, Map<PriceKey, Long>> entry : map.get(productCode).entrySet()) {
		//			DateRange dateRange = entry.getKey();
		//
		//			for(PriceKey key : entry.getValue().keySet()) {
		//				if(key.getDepartment() != department)
		//					continue;
		//
		//				Price.Builder builder = Price.createBuilder();
		//				builder.setId()
		//					res.put(entry.getKey(), new Price())
		//
		//
		//			}
		//		}

		return null;
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
