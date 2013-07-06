package cop.cs.shop;

import cop.cs.shop.data.DateRange;
import cop.cs.shop.data.Price;
import cop.cs.shop.data.Product;
import cop.cs.shop.exceptions.PriceNotFoundException;
import cop.cs.shop.exceptions.ProductExistsException;
import cop.cs.shop.exceptions.ProductNotFoundException;
import cop.cs.shop.store.PriceProvider;
import cop.cs.shop.store.PriceStore;
import cop.cs.shop.store.ProductProvider;
import cop.cs.shop.store.ProductStore;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Oleg Cherednik
 * @since 19.04.2013
 */
public final class Shop implements ProductProvider, PriceProvider {
	private final String name;

	private final ProductProvider productProvider = new ProductStore();
	private final PriceProvider priceProvider = new PriceStore();

	public Shop(String name) {
		this.name = name;
	}

	// ========== ProductProvider ==========

	@Override
	public void addProduct(Product product) throws ProductExistsException {
		productProvider.addProduct(product);
	}

	@Override
	public Product getProduct(String productCode) throws ProductNotFoundException {
		return productProvider.getProduct(productCode);
	}

	@Override
	public void removeProduct(String productCode) {
		productProvider.removeProduct(productCode);
	}

	@Override
	public Set<String> getProductCodes() {
		return productProvider.getProductCodes();
	}

	// ========== PriceProvider ==========

	@Override
	public void addPrice(Price price) throws ProductNotFoundException {
		if (price == null || price == Price.NULL)
			return;

		productProvider.getProduct(price.getProductCode());
		priceProvider.addPrice(price);
	}

	@Override
	public long getPrice(String productCode, long date, int department, int number)
			throws ProductNotFoundException, PriceNotFoundException {
		productProvider.getProduct(productCode);
		return priceProvider.getPrice(productCode, date, department, number);
	}

	@Override
	public Map<DateRange, Long> getPriceHistory(String productCode, int department, int number)
			throws ProductNotFoundException {
		if (productCode == null || productCode.isEmpty())
			return Collections.emptyMap();

		productProvider.getProduct(productCode);
		return priceProvider.getPriceHistory(productCode, department, number);
	}
}