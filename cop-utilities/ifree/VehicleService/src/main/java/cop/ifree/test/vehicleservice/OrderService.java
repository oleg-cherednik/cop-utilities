package cop.ifree.test.vehicleservice;

import cop.ifree.test.vehicleservice.dao.OrderDAO;
import cop.ifree.test.vehicleservice.data.Order;
import cop.ifree.test.vehicleservice.rest.to.VehiclePartOrderStatusTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
}