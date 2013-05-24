package com.test.services.jaxb.adapter;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Oleg Cherednik
 * @since 12.05.2013
 */
//@XStreamAlias(DatabaseTag.TITLE)
public final class OrderDTO {
	public static final String TITLE = "order";

	@XmlElement(name = "id", required = true)
	private long id;
	@XmlElement(name = "customer_id", required = true)
	private long customerId;
	@XmlElement(name = "vehicle_part_id", required = true)
	private long vehiclePartId;
	@XmlElement(name = "time", required = true)
	private long time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getVehiclePartId() {
		return vehiclePartId;
	}

	public void setVehiclePartId(long vehiclePartId) {
		this.vehiclePartId = vehiclePartId;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}
