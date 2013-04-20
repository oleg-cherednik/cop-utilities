package cop.cs.shop.store;

import cop.cs.shop.data.Price;
import cop.cs.shop.exceptions.ProductNotFoundException;

import java.util.List;

/**
 * @author Oleg Cherednik
 * @since 19.04.2013
 */
public interface PriceProvider {
	void addPrice(Price price) throws ProductNotFoundException;

	Price getPrice(String productCode, long date, int department, int number) throws ProductNotFoundException;

	List<Price> getPriceHistory(String productCode, int department) throws ProductNotFoundException;
}
