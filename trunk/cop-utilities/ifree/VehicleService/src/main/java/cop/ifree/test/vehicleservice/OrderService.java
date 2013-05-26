package cop.ifree.test.vehicleservice;

import cop.ifree.test.vehicleservice.dao.OrderDAO;
import cop.ifree.test.vehicleservice.data.Order;
import cop.ifree.test.vehicleservice.data.OrderFilter;
import cop.ifree.test.vehicleservice.data.OrderStatus;
import cop.ifree.test.vehicleservice.exceptions.ErrorCode;
import cop.ifree.test.vehicleservice.exceptions.VehicleServiceException;
import cop.ifree.test.vehicleservice.rest.to.OrderFilterTO;
import cop.ifree.test.vehicleservice.rest.to.OrderListTO;
import cop.ifree.test.vehicleservice.rest.to.OrderTO;
import cop.ifree.test.vehicleservice.rest.to.VehiclePartOrderStatusTO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Oleg Cherednik
 * @since 12.05.2013
 */
@Service("orderService")
@Path("/order")
public class OrderService {
	@Resource(name = "orderDAO")
	private OrderDAO daoOrder;

	@GET
	@Path("/orderVehiclePart")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response orderVehiclePart(@HeaderParam("customer_id") long customerId,
			@QueryParam("vehiclePartId") long vehiclePartId) throws Exception {
		Order order = daoOrder.createOrder(customerId, vehiclePartId);
		return Response.ok(new VehiclePartOrderStatusTO(order)).build();
	}

	@POST
	@Path("/getFilteredOrders")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFilteredOrders(OrderFilterTO filter) throws Exception {
		check(filter);
		Set<Order> orders = daoOrder.getOrders(OrderFilter.createBuilder().copyFrom(filter).createFilter());
		return Response.ok(new OrderListTO(convertOrders(orders))).build();
	}

	@GET
	@Path("/updateOrderStatus")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateOrderStatus(@HeaderParam("customer_id") long customerId, @QueryParam("orderId") long orderId,
			@QueryParam("status") OrderStatus status) throws Exception {
		Order order = daoOrder.updateOrderStatus(customerId, orderId, status);
		return Response.ok(new VehiclePartOrderStatusTO(order)).build();
	}

	// ========== static ==========

	private static void check(OrderFilterTO filter) throws Exception {
		if (filter == null || filter.isEmpty())
			throw new VehicleServiceException(ErrorCode.EMPTY_FILTER);
	}

	public static List<OrderTO> convertOrders(Collection<Order> orders) {
		if (CollectionUtils.isEmpty(orders))
			return Collections.emptyList();

		List<OrderTO> res = new ArrayList<>(orders.size());

		for (Order order : orders)
			res.add(new OrderTO(order));

		return res;
	}
}