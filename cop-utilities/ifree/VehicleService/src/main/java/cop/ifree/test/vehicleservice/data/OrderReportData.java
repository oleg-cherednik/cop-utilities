package cop.ifree.test.vehicleservice.data;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Oleg Cherednik
 * @since 25.05.2013
 */
public final class OrderReportData {
	public static final OrderReportData NULL = new Builder().createReportData();

	private final long timeFrom;
	private final long timeTo;
	private final int uniqueCustomers;
	private final int newOrders;
	private final int foundOrders;
	private final int deliveredOrders;
	private final Set<Long> notFoundParts;

	public static Builder createBuilder() {
		return new Builder();
	}

	private OrderReportData(Builder builder) {
		timeFrom = builder.timeFrom;
		timeTo = builder.timeTo;
		uniqueCustomers = builder.uniqueCustomers;
		newOrders = builder.newOrders;
		foundOrders = builder.foundOrders;
		deliveredOrders = builder.deliveredOrders;
		notFoundParts = builder.getNotFoundParts();
	}

	public int getUniqueCustomers() {
		return uniqueCustomers;
	}

	public int getNewOrders() {
		return newOrders;
	}

	public int getFoundOrders() {
		return foundOrders;
	}

	public int getDeliveredOrders() {
		return deliveredOrders;
	}

	public Set<Long> getNotFoundParts() {
		return notFoundParts;
	}

	public long getTimeFrom() {
		return timeFrom;
	}

	public long getTimeTo() {
		return timeTo;
	}

	// ========== builder ==========

	public static class Builder {
		private long timeFrom;
		private long timeTo;
		private int uniqueCustomers;
		private int newOrders;
		private int foundOrders;
		private int deliveredOrders;
		private final Set<Long> notFoundParts = new HashSet<>();

		private Builder() {}

		public OrderReportData createReportData() {
			return new OrderReportData(this);
		}

		public Builder setTimeFrom(long timeFrom) {
			this.timeFrom = timeFrom;
			return this;
		}

		public Builder setTimeTo(long timeTo) {
			this.timeTo = timeTo;
			return this;
		}

		public Builder setUniqueCustomers(int uniqueCustomers) {
			this.uniqueCustomers = uniqueCustomers;
			return this;
		}

		public Builder incNewOrders() {
			newOrders++;
			return this;
		}

		public Builder incFoundOrders() {
			foundOrders++;
			return this;
		}

		public Builder incDeliveredOrders() {
			deliveredOrders++;
			return this;
		}

		public Builder addNotFoundPart(long vehiclePartId) {
			notFoundParts.add(vehiclePartId);
			return this;
		}

		private Set<Long> getNotFoundParts() {
			return notFoundParts.isEmpty() ? Collections.<Long>emptySet() : Collections.unmodifiableSet(notFoundParts);
		}
	}

}
