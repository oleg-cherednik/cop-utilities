package cop.ifree.test.vehicleservice.data;

import cop.ifree.test.vehicleservice.rest.to.OrderFilterTO;

/**
 * @author Oleg Cherednik
 * @since 25.05.2013
 */
public final class OrderFilter {
	private final long customerId;
	private final long vehiclePartId;
	private final long createTimeFrom;
	private final long createTimeTo;
	private final OrderStatus status;

	public static Builder createBuilder() {
		return new Builder();
	}

	private OrderFilter(Builder builder) {
		customerId = builder.customerId;
		vehiclePartId = builder.vehiclePartId;
		createTimeFrom = builder.createTimeFrom;
		createTimeTo = builder.createTimeTo;
		status = builder.status;
	}

	public long getCustomerId() {
		return customerId;
	}

	public long getVehiclePartId() {
		return vehiclePartId;
	}

	public long getCreateTimeFrom() {
		return createTimeFrom;
	}

	public long getCreateTimeTo() {
		return createTimeTo;
	}

	public OrderStatus getStatus() {
		return status;
	}

	// ========== builder ==========

	public static class Builder {
		private long customerId;
		private long vehiclePartId;
		private long createTimeFrom;
		private long createTimeTo;
		private OrderStatus status;

		private Builder() {}

		public OrderFilter createFilter() {
			return new OrderFilter(this);
		}

		public Builder copyFrom(OrderFilterTO filter) {
			customerId = filter.getCustomerId() != null ? filter.getCustomerId() : 0;
			vehiclePartId = filter.getVehiclePartId() != null ? filter.getVehiclePartId() : 0;
			createTimeFrom = filter.getCreateTimeFrom() != null ? filter.getCreateTimeFrom() : 0;
			createTimeTo = filter.getCreateTimeTo() != null ? filter.getCreateTimeTo() : 0;
			status = filter.getStatus();
			return this;
		}

		public Builder setCustomerId(long customerId) {
			this.customerId = customerId;
			return this;
		}

		public Builder setVehiclePartId(long vehiclePartId) {
			this.vehiclePartId = vehiclePartId;
			return this;
		}

		public Builder setCreateTimeFrom(long createTimeFrom) {
			this.createTimeFrom = createTimeFrom;
			return this;
		}

		public Builder setCreateTimeTo(long createTimeTo) {
			this.createTimeTo = createTimeTo;
			return this;
		}

		public Builder setStatus(OrderStatus status) {
			this.status = status;
			return this;
		}
	}
}
