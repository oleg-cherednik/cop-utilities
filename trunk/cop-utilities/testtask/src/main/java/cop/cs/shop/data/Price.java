package cop.cs.shop.data;

/**
 * @author Oleg Cherednik
 * @since 18.04.2013
 */
public final class Price {
	public static final Price NULL = new Builder().createPrice();

	private final long id;
	private final String productCode;
	private final PriceKey key;
	private final DateRange dateRange;
	private final long value;

	public static Builder createBuilder() {
		return new Builder();
	}

	private Price(Builder builder) {
		id = builder.id;
		productCode = builder.productCode;
		key = builder.key;
		dateRange = builder.dateRange;
		value = builder.value;
	}

	public long getId() {
		return id;
	}

	public String getProductCode() {
		return productCode;
	}

	public PriceKey getKey() {
		return key;
	}

	public DateRange getDateRange() {
		return dateRange;
	}

	public long getValue() {
		return value;
	}

	// ========== builder ==========

	public static class Builder {
		private long id;
		private String productCode;
		private PriceKey key = PriceKey.NULL;
		private DateRange dateRange = DateRange.NULL;
		private long value;

		private Builder() {}

		public Price createPrice() {
			if (key == PriceKey.NULL || dateRange == DateRange.NULL || id <= 0)
				return Price.NULL;
			return new Price(this);
		}

		public Builder setId(long id) {
			this.id = id;
			return this;
		}

		public Builder setProductCode(String productCode) {
			this.productCode = productCode;
			return this;
		}

		public Builder setKey(PriceKey key) {
			this.key = key != null ? key : PriceKey.NULL;
			return this;
		}

		public Builder setDateRange(DateRange dateRange) {
			this.dateRange = dateRange != null ? dateRange : DateRange.NULL;
			return this;
		}

		public Builder setValue(long value) {
			this.value = value;
			return this;
		}
	}
}
