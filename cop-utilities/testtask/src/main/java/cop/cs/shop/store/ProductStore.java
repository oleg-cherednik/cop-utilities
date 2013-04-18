package cop.cs.shop.store;

import cop.cs.shop.data.Product;
import cop.cs.shop.exceptions.ProductExistsException;
import cop.cs.shop.exceptions.ProductNotFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Oleg Cherednik
 * @since 18.04.2013
 */
final class ProductStore implements ProductProvider {
	private final Map<String, Product> map = new HashMap<>();

	// ========== ProductProvider ==========

	public void addProduct(Product product) throws ProductExistsException {
		if (product == null)
			return;
		if (map.containsKey(product.getCode()))
			throw new ProductExistsException(product.getCode());
		map.put(product.getCode(), product);
	}

	public Product getProduct(String code) throws ProductNotFoundException {
		if (map.containsKey(code))
			return map.get(code);
		throw new ProductNotFoundException(code);
	}

	public void removeProduct(String code) {
		map.remove(code);
	}
}
