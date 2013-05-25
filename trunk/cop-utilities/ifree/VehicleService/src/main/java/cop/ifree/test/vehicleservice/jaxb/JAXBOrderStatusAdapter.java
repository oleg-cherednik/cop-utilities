package cop.ifree.test.vehicleservice.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.lang.StringUtils;

import cop.ifree.test.vehicleservice.data.OrderStatus;

public class JAXBOrderStatusAdapter extends XmlAdapter<String, OrderStatus> {

	public OrderStatus unmarshal(String str) throws Exception {
		return StringUtils.isNotBlank(str) ? OrderStatus.parseString(str) : null;
	}

	public String marshal(OrderStatus status) throws Exception {
		return status != null ? status.getId() : "";
	}
}
