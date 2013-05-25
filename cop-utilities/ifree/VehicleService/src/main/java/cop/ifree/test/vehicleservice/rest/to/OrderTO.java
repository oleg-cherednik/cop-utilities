/**
 * $$Id$
 * $$URL$
 */
package cop.ifree.test.vehicleservice.rest.to;

import cop.ifree.test.vehicleservice.data.Order;
import cop.ifree.test.vehicleservice.data.OrderStatus;
import cop.ifree.test.vehicleservice.jaxb.JAXBDateAdapter;
import cop.ifree.test.vehicleservice.jaxb.JAXBOrderStatusAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Oleg Cherednik
 * @since 24.05.2013
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "order")
public class OrderTO {
	@XmlAttribute(name = "id", required = true)
	private Long id;
	@XmlAttribute(name = "vehicle_part_id", required = true)
	private Long vehiclePartId;
	@XmlAttribute(name = "create_time", required = true)
	@XmlJavaTypeAdapter(JAXBDateAdapter.class)
	private Long createTime;
	@XmlAttribute(name = "update_time", required = true)
	@XmlJavaTypeAdapter(JAXBDateAdapter.class)
	private Long updateTime;
	@XmlAttribute(name = "status", required = true)
	@XmlJavaTypeAdapter(JAXBOrderStatusAdapter.class)
	private OrderStatus status;

	public OrderTO() {
	}

	public OrderTO(Order order) {
		vehiclePartId = order.getVehiclePartId();
		createTime = order.getCreateTime();
		updateTime = order.getUpdateTime();
		status = order.getStatus();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVehiclePartId() {
		return vehiclePartId;
	}

	public void setVehiclePartId(Long vehiclePartId) {
		this.vehiclePartId = vehiclePartId;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}
}
