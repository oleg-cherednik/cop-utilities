package cop.cs.shop.store;

import cop.cs.shop.data.DateRange;
import cop.cs.shop.data.Price;
import cop.cs.shop.exceptions.IllegalDateRangeException;
import cop.cs.shop.exceptions.PriceNotFoundException;
import cop.cs.shop.exceptions.ProductNotFoundException;

import java.util.List;
import java.util.Map;

/**
 * @author Oleg Cherednik
 * @since 19.04.2013
 */
public interface PriceProvider {
	void addPrice(Price price) throws ProductNotFoundException;

	long getPrice(String productCode, long date, int department, int number)
			throws ProductNotFoundException, PriceNotFoundException;

	Map<DateRange, Long> getPriceHistory(String productCode, int department, int number)
			throws ProductNotFoundException;
}
