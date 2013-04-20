package cop.cs.shop.store;

import cop.cs.shop.data.Product;
import cop.cs.shop.exceptions.IllegalProductException;
import cop.cs.shop.exceptions.ProductExistsException;
import cop.cs.shop.exceptions.ProductNotFoundException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Oleg Cherednik
 * @since 20.04.2013
 */
public class ProductStoreTest {
	private ProductStore store;

	@Before
	public void init() {
		store = new ProductStore();
	}

	@Test
	public void testAddNullProduct() throws ProductExistsException {
		assertEquals(0, store.getProductCodes().size());

		store.addProduct(Product.NULL);
		assertEquals(0, store.getProductCodes().size());

		store.addProduct(null);
		assertEquals(0, store.getProductCodes().size());
	}

	@Test(expected = ProductExistsException.class)
	public void testAddExistedProduct() throws ProductExistsException, IllegalProductException {
		Product product = Product.createProduct("Wheel");

		assertEquals(0, store.getProductCodes().size());
		store.addProduct(product);

		assertEquals(1, store.getProductCodes().size());
		store.addProduct(product);
	}

	@Test
	public void testAddProduct() throws ProductExistsException, IllegalProductException, ProductNotFoundException {
		final Product nullObj = null;
		final String productCode1 = "AA";
		final String productCode2 = "BB";
		final String description1 = "description1";
		final String description2 = "description2";
		Product product1 = Product.createProduct(productCode1, description1);
		Product product2 = Product.createProduct(productCode2, description2);

		assertEquals(0, store.getProductCodes().size());

		store.addProduct(Product.NULL);
		assertEquals(0, store.getProductCodes().size());

		store.addProduct(nullObj);
		assertEquals(0, store.getProductCodes().size());

		store.addProduct(product1);
		assertEquals("1 == store.getProductCodes().size()", 1, store.getProductCodes().size());
		assertTrue("product1 == store.getProduct(productCode1)", product1 == store.getProduct(productCode1));

		store.addProduct(product2);
		assertEquals("2 == store.getProductCodes().size()", 2, store.getProductCodes().size());
		assertTrue("product1 == store.getProduct(productCode1)", product1 == store.getProduct(productCode1));
		assertTrue("product2 == store.getProduct(productCode2)", product2 == store.getProduct(productCode2));
	}

	@Test(expected = ProductNotFoundException.class)
	public void testNotFoundProduct() throws ProductExistsException, ProductNotFoundException, IllegalProductException {
		final String productCode = "AA";
		Product product = Product.createProduct(productCode, "description");

		store.addProduct(product);
		assertTrue("product == store.getProduct(productCode)", product == store.getProduct(productCode));
		store.getProduct("BB");
	}
}
