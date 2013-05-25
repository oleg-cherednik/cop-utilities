package cop.ifree.test.vehicleservice.rest.to;

import cop.ifree.test.vehicleservice.data.OrderStatus;
import cop.ifree.test.vehicleservice.jaxb.JAXBOrderStatusAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Oleg Cherednik
 * @since 25.05.2013
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "order_filter")
public class OrderFilterTO {
	@XmlElement(name = "customer_id")
	private Long customerId;
	@XmlElement(name = "vehicle_part_id")
	private Long vehiclePartId;
	@XmlElement(name = "create_time_from")
	private Long createTimeFrom;
	@XmlElement(name = "create_time_to")
	private Long createTimeTo;
	@XmlElement(name = "status")
	@XmlJavaTypeAdapter(JAXBOrderStatusAdapter.class)
	private OrderStatus status;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getVehiclePartId() {
		return vehiclePartId;
	}

	public void setVehiclePartId(Long vehiclePartId) {
		this.vehiclePartId = vehiclePartId;
	}

	public Long getCreateTimeFrom() {
		return createTimeFrom;
	}

	public void setCreateTimeFrom(Long createTimeFrom) {
		this.createTimeFrom = createTimeFrom;
	}

	public Long getCreateTimeTo() {
		return createTimeTo;
	}

	public void setCreateTimeTo(Long createTimeTo) {
		this.createTimeTo = createTimeTo;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public boolean isEmpty() {
		if (customerId != null && customerId != 0)
			return false;
		if (vehiclePartId != null && vehiclePartId != 0)
			return false;
		if (createTimeFrom != null && createTimeFrom != 0)
			return false;
		if (createTimeTo != null && createTimeTo != 0)
			return false;
		return status != null;
	}
}
