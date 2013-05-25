package cop.ifree.test.vehicleservice.data;

import javax.validation.constraints.NotNull;

/**
 * @author Oleg Cherednik
 * @since 25.05.2013
 */
public final class OrderHistory {
	private final long historyId;
	private final long orderId;
	private final long updateTime;
	private final OrderStatus status;

	public static Builder createBuilder() {
		return new Builder();
	}

	private OrderHistory(Builder builder) {
		this.historyId = builder.historyId;
		this.orderId = builder.orderId;
		this.updateTime = builder.updateTime;
		this.status = builder.status;
	}

	public long getHistoryId() {
		return historyId;
	}

	public long getOrderId() {
		return orderId;
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
		if (!(obj instanceof OrderHistory))
			return false;

		return historyId == ((OrderHistory)obj).historyId;
	}

	@Override
	public int hashCode() {
		return (int)(historyId ^ (historyId >>> 32));
	}

	// ========== builder ==========

	public static final class Builder {
		private long historyId;
		private long orderId;
		private long updateTime;
		private OrderStatus status = OrderStatus.NEW;

		public OrderHistory createOrderHistory() {
			return new OrderHistory(this);
		}

		public Builder setHistoryId(long historyId) {
			this.historyId = historyId;
			return this;
		}

		public Builder setOrderId(long orderId) {
			this.orderId = orderId;
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
