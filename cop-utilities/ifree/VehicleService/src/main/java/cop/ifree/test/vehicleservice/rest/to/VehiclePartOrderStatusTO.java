/**
 * $$Id$
 * $$URL$
 */
package cop.ifree.test.vehicleservice.rest.to;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import cop.ifree.test.vehicleservice.data.Order;
import cop.ifree.test.vehicleservice.data.OrderStatus;
import cop.ifree.test.vehicleservice.jaxb.JAXBDateAdapter;
import cop.ifree.test.vehicleservice.jaxb.JAXBOrderStatusAdapter;

/**
 * @author Oleg Cherednik
 * @since 24.05.2013
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "vehiclePartOrder")
public class VehiclePartOrderStatusTO {

	@XmlAttribute(name = "number", required = true)
	private String number;
	@XmlAttribute(name = "customerId", required = true)
	private Long customerId;
	@XmlAttribute(name = "vehiclePartId", required = true)
	private Long vehiclePartId;
	@XmlAttribute(name = "createTime", required = true)
	@XmlJavaTypeAdapter(JAXBDateAdapter.class)
	private Long createTime;
	@XmlAttribute(name = "updateTime", required = true)
	@XmlJavaTypeAdapter(JAXBDateAdapter.class)
	private Long updateTime;
	@XmlAttribute(name = "status", required = true)
	@XmlJavaTypeAdapter(JAXBOrderStatusAdapter.class)
	private OrderStatus status;

	public VehiclePartOrderStatusTO() {
	}

	public VehiclePartOrderStatusTO(Order order) {
		customerId = order.getCustomerId();
		number = order.getNumber();
		vehiclePartId = order.getVehiclePartId();
		createTime = order.getCreateTime();
		updateTime = order.getUpdateTime();
		status = order.getStatus();
	}

	public Long getCustomerId() {
		return customerId;
	}

	public String getNumber() {
		return number;
	}

	public Long getVehiclePartId() {
		return vehiclePartId;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public OrderStatus getStatus() {
		return status;
	}
}
