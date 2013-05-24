package cop.ifree.test.autoservice.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Oleg Cherednik
 * @since 12.05.2013
 */
@XmlRootElement(name = "order")
public final class Order {
	@XmlElement(name = "id", required = true)
	private final long id;
	@XmlElement(name = "customer_id", required = true)
	private final long customerId;
	@XmlElement(name = "vehicle_part_id", required = true)
	private final long vehiclePartId;
	@XmlElement(name = "time", required = true)
	private final long time = System.currentTimeMillis();

	public Order(long id, long customerId, long vehiclePartId) {
		this.id = id;
		this.customerId = customerId;
		this.vehiclePartId = vehiclePartId;
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

	public long getTime() {
		return time;
	}

	// ========== Object ==========

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Order))
			return false;

		Order that = (Order)obj;

		if (vehiclePartId != that.vehiclePartId)
			return false;
		if (time != that.time)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = (int)(vehiclePartId ^ (vehiclePartId >>> 32));
		result = 31 * result + (int)(time ^ (time >>> 32));
		return result;
	}
}
