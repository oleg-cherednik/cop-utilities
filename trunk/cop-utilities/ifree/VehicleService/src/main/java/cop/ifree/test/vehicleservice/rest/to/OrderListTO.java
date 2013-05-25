package cop.ifree.test.vehicleservice.rest.to;

import org.apache.commons.collections.CollectionUtils;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Oleg Cherednik
 * @since 25.05.2013
 */
@XmlRootElement(name = "order_list")
public class OrderListTO {
	@XmlElement(name = "orders")
	private final List<OrderTO> orders = new ArrayList<>();

	public OrderListTO() {
	}

	public OrderListTO(List<OrderTO> orders) {
		setOrders(orders);
	}

	public void setOrders(List<OrderTO> orders) {
		this.orders.clear();

		if (CollectionUtils.isNotEmpty(orders))
			this.orders.addAll(orders);
	}

	public List<OrderTO> getOrders() {
		if (CollectionUtils.isEmpty(orders))
			return Collections.emptyList();
		return Collections.unmodifiableList(orders);
	}
}
