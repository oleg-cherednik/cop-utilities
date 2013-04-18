package cop.cs.shop.store;

import cop.cs.shop.data.Product;
import cop.cs.shop.exceptions.ProductExistsException;
import cop.cs.shop.exceptions.ProductNotFoundException;

/**
 * @author Oleg Cherednik
 * @since 19.04.2013
 */
interface ProductProvider {
	void addProduct(Product product) throws ProductExistsException;

	Product getProduct(String code) throws ProductNotFoundException;

	void removeProduct(String code);
}
