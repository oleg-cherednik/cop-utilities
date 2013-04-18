package cop.cs.shop.data;

import cop.cs.shop.utils.ProductCodeGenerator;

/**
 * @author Oleg Cherednik
 * @since 18.04.2013
 */
public final class Product {
	private final String code;
	private final String description;

	public Product(String description) {
		this(ProductCodeGenerator.get(), description);
	}

	public Product(String code, String description) {
		this.code = code;
		this.description = description;
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
		return this == obj || obj instanceof Product && code.equals(((Product)obj).code);
	}

	@Override
	public int hashCode() {
		return code.hashCode();
	}

	@Override
	public String toString() {
		return "Product{code='" + code + "', description='" + description + "'}";
	}
}
