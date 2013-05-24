package cop.ifree.test.vehicleservice.data;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Oleg Cherednik
 * @since 12.05.2013
 */
@XmlRootElement(name = "order")
public final class Order {
	private final long id;
	private final long customerId;
	private final long vehiclePartId;
	private final long createTime;
	private final long updateTime;
	private final OrderStatus status;

	public static Builder createBuilder() {
		return new Builder();
	}

	private Order(Builder builder) {
		this.id = builder.id;
		this.customerId = builder.customerId;
		this.vehiclePartId = builder.vehiclePartId;
		this.createTime = builder.createTime;
		this.updateTime = builder.updateTime;
		this.status = builder.status;
	}

	public long getId() {
		return id;
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

		Order order = (Order)obj;

		if (createTime != order.createTime)
			return false;
		if (customerId != order.customerId)
			return false;
		if (id != order.id)
			return false;
		if (updateTime != order.updateTime)
			return false;
		if (vehiclePartId != order.vehiclePartId)
			return false;
		if (status != order.status)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = (int)(id ^ (id >>> 32));
		result = 31 * result + (int)(customerId ^ (customerId >>> 32));
		result = 31 * result + (int)(vehiclePartId ^ (vehiclePartId >>> 32));
		result = 31 * result + (int)(createTime ^ (createTime >>> 32));
		result = 31 * result + (int)(updateTime ^ (updateTime >>> 32));
		result = 31 * result + status.hashCode();
		return result;
	}

	// ========== builder ==========

	public static final class Builder {
		private long id;
		private long customerId;
		private long vehiclePartId;
		private long createTime;
		private long updateTime;
		private OrderStatus status = OrderStatus.NEW;

		public Builder setId(long id) {
			this.id = id;
			return this;
		}

		public Order createOrder() {
			return new Order(this);
		}

		public Builder setCustomerId(long customerId) {
			this.customerId = customerId;
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
