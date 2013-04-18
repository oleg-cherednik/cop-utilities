package cop.cs.shop.store;

import cop.cs.shop.data.Price;
import cop.cs.shop.data.Product;
import cop.cs.shop.exceptions.ProductExistsException;
import cop.cs.shop.exceptions.ProductNotFoundException;

import java.util.Collections;
import java.util.List;

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

	public List<Price> getProductPriceHistory(String productCode) {
		if (productCode == null || productCode.isEmpty())
			return Collections.emptyList();

		return null;
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

	// ========== PriceProvider ==========

	@Override
	public void addPrice(Price price) {
		priceProvider.addPrice(price);
	}
}
