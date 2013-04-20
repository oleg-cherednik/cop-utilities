package cop.cs.shop.store;

import cop.cs.shop.data.Price;
import cop.cs.shop.exceptions.ProductNotFoundException;

/**
 * @author Oleg Cherednik
 * @since 19.04.2013
 */
public interface PriceProvider {
	void addPrice(Price price) throws ProductNotFoundException;
}
