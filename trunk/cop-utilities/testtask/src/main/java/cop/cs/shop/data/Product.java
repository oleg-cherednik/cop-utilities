package cop.cs.shop.data;

import cop.cs.shop.exceptions.IllegalProductException;
import cop.cs.shop.utils.ProductCodeGenerator;

/**
 * @author Oleg Cherednik
 * @since 18.04.2013
 */
public final class Product {
	public static final Product NULL = new Product("", null);

	private final String code;
	private final String description;

	public static Product createProduct(Product product) throws IllegalProductException {
		if (product == null || product == NULL)
			return NULL;
		return createProduct(product.code, product.description);
	}

	public static Product createProduct(String description) throws IllegalProductException {
		return createProduct(ProductCodeGenerator.get(), description);
	}

	public static Product createProduct(String code, String description) throws IllegalProductException {
		if (!isCodeValid(code))
			throw new IllegalProductException("Product code is not valid: " + code);
		return new Product(code, description);
	}

	private Product(String code, String description) {
		this.code = code;
		this.description = isEmpty(description) ? null : description.trim();
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	// ========== Object ==========

	@Override
	public boolean equals(Object obj) {
		return this == NULL && obj == null || obj instanceof Product && code.equals(((Product)obj).code);
	}

	@Override
	public int hashCode() {
		return code.hashCode();
	}

	@Override
	public String toString() {
		return "code='" + code + "', description='" + description + '\'';
	}

	// ========== static ==========

	public static boolean isCodeValid(String code) {
		return code != null && !code.trim().isEmpty();
	}

	private static boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}
}
