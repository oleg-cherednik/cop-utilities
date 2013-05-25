package cop.ifree.test.vehicleservice.data;

import javax.validation.constraints.NotNull;

/**
 * @author Oleg Cherednik
 * @since 12.05.2013
 */
public final class Order {
	private final long orderId;
	private final String number;
	private final long customerId;
	private final long vehiclePartId;
	private final long createTime;
	private final long updateTime;
	private final OrderStatus status;

	public static Builder createBuilder() {
		return new Builder();
	}

	private Order(Builder builder) {
		this.orderId = builder.id;
		this.number = builder.number;
		this.customerId = builder.customerId;
		this.vehiclePartId = builder.vehiclePartId;
		this.createTime = builder.createTime;
		this.updateTime = builder.updateTime;
		this.status = builder.status;
	}

	public long getOrderId() {
		return orderId;
	}

	@NotNull
	public String getNumber() {
		return number;
	}

	public long getCustomerId() {
		return customerId;
	}

	public long getVehiclePartId() {
		return vehiclePartId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	@NotNull
	public OrderStatus getStatus() {
		return status;
	}

	// ========== Object ==========

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Order))
			return false;

		return orderId == ((Order)obj).orderId;
	}

	@Override
	public int hashCode() {
		return (int)(orderId ^ (orderId >>> 32));
	}

	// ========== builder ==========

	public static final class Builder {
		private long id;
		private String number;
		private long customerId;
		private long vehiclePartId;
		private long createTime;
		private long updateTime;
		private OrderStatus status = OrderStatus.NEW;

		public Order createOrder() {
			return new Order(this);
		}

		public Builder setId(long id) {
			this.id = id;
			return this;
		}

		public Builder setCustomerId(long customerId) {
			this.customerId = customerId;
			return this;
		}

		public Builder setNumber(String number) {
			this.number = number;
			return this;
		}

		public Builder setVehiclePartId(long vehiclePartId) {
			this.vehiclePartId = vehiclePartId;
			return this;
		}

		public Builder setCreateTime(long createTime) {
			this.createTime = createTime;
			return this;
		}

		public Builder setUpdateTime(long updateTime) {
			this.updateTime = updateTime;
			return this;
		}

		public Builder setStatus(OrderStatus status) {
			this.status = status != null ? status : OrderStatus.NEW;
			return this;
		}
	}
}
