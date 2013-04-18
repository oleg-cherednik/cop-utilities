package cop.cs.shop.store;

import cop.cs.shop.data.DateRange;
import cop.cs.shop.data.Price;
import cop.cs.shop.data.PriceKey;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Oleg Cherednik
 * @since 18.04.2013
 */
final class PriceStore implements PriceProvider {
	private final Map<String, Map<DateRange, Map<PriceKey, Long>>> map = new HashMap<>();

	private Set<DateRange> getCrossDateRange(String productCode, DateRange dateRange) {
		if (!map.containsKey(productCode))
			return Collections.emptySet();

		Set<DateRange> res = new TreeSet<>();

		for (DateRange range : map.get(productCode).keySet())
			if (range.isCrossing(dateRange))
				res.add(range);

		return res.isEmpty() ? Collections.<DateRange>emptySet() : Collections.unmodifiableSet(res);
	}

	private void addPriceInt(Price price) {
		assert price != null && price != Price.NULL;

		Map<DateRange, Map<PriceKey, Long>> byProductCode = map.get(price.getProductCode());

		if (byProductCode == null)
			map.put(price.getProductCode(), byProductCode = new HashMap<>());

		Map<PriceKey, Long> byDateRange = byProductCode.get(price.getDateRange());

		if (byDateRange == null)
			byProductCode.put(price.getDateRange(), byDateRange = new HashMap<>());

		byDateRange.put(price.getKey(), price.getValue());
	}

	// ========== PriceProvider ==========

	public void addPrice(Price price) {
		if (price == null || price == Price.NULL)
			return;

		Set<DateRange> crossDateRange = getCrossDateRange(price.getProductCode(), price.getDateRange());

		if (!crossDateRange.isEmpty()) {

		}
			addPriceInt(price);
	}
}
