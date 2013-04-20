package cop.cs.shop.data;

import cop.cs.shop.exceptions.IllegalDateRangeException;
import cop.cs.shop.exceptions.IllegalProductException;
import cop.cs.shop.utils.ProductCodeGenerator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Oleg Cherednik
 * @since 20.04.2013
 */
public class ProductTest {
	@Test(expected = IllegalProductException.class)
	public void testInvalidProduct1() throws IllegalProductException {
		Product.createProduct("", "description");
	}

	@Test(expected = IllegalProductException.class)
	public void testInvalidProduct2() throws IllegalProductException {
		Product.createProduct("  ", "description");
	}

	@Test
	public void testValidProduct() throws IllegalProductException {
		final String productCode = ProductCodeGenerator.get();
		final String description = "test description";
		Product product1 = Product.createProduct(productCode, description);
		Product product2 = Product.createProduct(description);

		assertEquals(productCode, product1.getCode());
		assertEquals(description, product1.getDescription());
		assertTrue(Product.isCodeValid(product2.getCode()));
		assertEquals(description, product2.getDescription());
	}

	@Test
	public void testSameProductInstance() throws IllegalProductException {
		final String description = "test description";
		Product product1 = Product.createProduct(description);
		Product product2 = Product.createProduct(description);

		assertFalse("product1 == product2", product1 == product2);
	}

	@Test
	public void testEquals() throws IllegalProductException {
		final Product nullObj = null;
		final String description = "test description";

		Product product1 = Product.createProduct(description);
		Product product2 = Product.createProduct(product1);
		Product product3 = Product.createProduct(product1);

		assertTrue(product1.equals(product1));
		assertTrue(product1.equals(product2));
		assertTrue(product2.equals(product1));
		assertTrue(product2.equals(product3));
		assertTrue(product3.equals(product3));
		assertFalse(product1.equals(nullObj));
		assertFalse(product1.equals(Product.NULL));
		assertTrue(Product.NULL.equals(Product.NULL));
		assertTrue(Product.NULL.equals(nullObj));
	}
}
