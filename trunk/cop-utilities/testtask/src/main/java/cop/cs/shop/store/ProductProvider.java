package cop.cs.shop.store;

import cop.cs.shop.data.Product;
import cop.cs.shop.exceptions.ProductExistsException;
import cop.cs.shop.exceptions.ProductNotFoundException;

import java.util.Set;

/**
 * @author Oleg Cherednik
 * @since 19.04.2013
 */
public interface ProductProvider {
	void addProduct(Product product) throws ProductExistsException;

	Product getProduct(String code) throws ProductNotFoundException;

	void removeProduct(String code);

	Set<String> getProductCodes();
}
