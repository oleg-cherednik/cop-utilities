package cop.cs.shop.store;

import cop.cs.shop.data.DateRange;
import cop.cs.shop.data.Price;
import cop.cs.shop.data.PriceKey;
import cop.cs.shop.data.Product;
import cop.cs.shop.exceptions.ProductNotFoundException;

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

		Map<DateRange, Map<PriceKey, Price>> byProductCode = map.get(price.getProductCode());

		if (byProductCode == null)
			map.put(price.getProductCode(), byProductCode = new HashMap<>());

		Map<PriceKey, Price> byDateRange = byProductCode.get(price.getDateRange());

		if (byDateRange == null)
			byProductCode.put(price.getDateRange(), byDateRange = new HashMap<>());

		byDateRange.put(price.getKey(), price);
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
	public Price getPrice(String productCode, long date, int department, int number) {
		Map<DateRange, Map<PriceKey, Price>> byProductCode = map.get(productCode);

		if (byProductCode == null || byProductCode.isEmpty())
			return Price.NULL;

		for (Map.Entry<DateRange, Map<PriceKey, Price>> entry1 : byProductCode.entrySet())
			if (entry1.getKey().contains(date))
				for (Map.Entry<PriceKey, Price> entry2 : entry1.getValue().entrySet())
					if (entry2.getKey().getDepartment() == department && entry2.getKey().getNumber() == number)
						return entry2.getValue();

		return Price.NULL;
	}
}
