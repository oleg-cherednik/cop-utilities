package cop.ifree.test.vehicleservice;

import cop.ifree.test.vehicleservice.dao.CustomerDAO;
import cop.ifree.test.vehicleservice.dao.OrderDAO;
import cop.ifree.test.vehicleservice.data.Order;
import cop.ifree.test.vehicleservice.data.OrderFilter;
import cop.ifree.test.vehicleservice.data.OrderHistory;
import cop.ifree.test.vehicleservice.data.OrderReportData;
import cop.ifree.test.vehicleservice.data.OrderStatus;
import cop.ifree.test.vehicleservice.exceptions.ErrorCode;
import cop.ifree.test.vehicleservice.exceptions.VehicleServiceException;
import cop.ifree.test.vehicleservice.rest.to.OrderFilterTO;
import cop.ifree.test.vehicleservice.rest.to.OrderListTO;
import cop.ifree.test.vehicleservice.rest.to.OrderTO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Oleg Cherednik
 * @since 12.05.2013
 */
@Service("reportService")
@Path("/admin")
public class ReportService {

	@Resource(name = "orderDAO")
	private OrderDAO daoOrder;

	@Resource(name = "customerDAO")
	private CustomerDAO daoCustomer;

	@Context
	private HttpHeaders requestHeaders;

	// get by id service
	//	@GET
	//	//	@Path("/{id1}")
	//	public Response getCustomer(@PathParam("id") String id) {
	//		Customer customer = customersDAO.getCustomer(id);
	//		if (customer != null) {
	//			return ResponseCreator.success(getHeaderVersion(), customer);
	//		} else {
	//			return ResponseCreator.error(404, Error.NOT_FOUND.getCode(), getHeaderVersion());
	//		}
	//	}

	// remove row from the customers table according with passed id and returned
	// status message in body
	//	@DELETE
	//	//	@Path("/{id}")
	//	public Response removeCustomer(@PathParam("id") String id) {
	//		if (customersDAO.removeCustomer(id)) {
	//			return ResponseCreator.success(getHeaderVersion(), "removed");
	//		} else {
	//			return ResponseCreator.success(getHeaderVersion(), "no such id");
	//		}
	//	}

	// create row representing customer and returns created customer as
	// object->JSON structure
	//	@POST
	//	@Consumes(MediaType.APPLICATION_JSON)
	//	public Response createCustomer(Customer customer) {
	//		System.out.println("POST");
	//		Customer creCustomer = customersDAO.createCustomer(customer);
	//		if (creCustomer != null) {
	//			return ResponseCreator.success(getHeaderVersion(), creCustomer);
	//		} else {
	//			return ResponseCreator.error(500, Error.SERVER_ERROR.getCode(), getHeaderVersion());
	//		}
	//	}

	// update row and return previous version of row representing customer as
	// object->JSON structure
	//	@PUT
	//	@Consumes(MediaType.APPLICATION_JSON)
	//	public Response updateCustomer(Customer customer) {
	//		Customer updCustomer = customersDAO.updateCustomer(customer);
	//		if (updCustomer != null) {
	//			return ResponseCreator.success(getHeaderVersion(), updCustomer);
	//		} else {
	//			return ResponseCreator.error(500, Error.SERVER_ERROR.getCode(), getHeaderVersion());
	//		}
	//	}

	// returns list of customers meeting query params
	//	@GET
	//	@Produces(MediaType.APPLICATION_JSON)
	//	public Response getCustomers(@QueryParam("customer") long customerId, @QueryParam("keyword") String keyword,
	//			@QueryParam("orderby") String orderBy, @QueryParam("order") String order,
	//			@QueryParam("pagenum") Integer pageNum, @QueryParam("pagesize") Integer pageSize) {
	//		//		CustomerListParameters parameters = new CustomerListParameters();
	//		//		parameters.setKeyword(keyword);
	//		//		parameters.setPageNum(pageNum);
	//		//		parameters.setPageSize(pageSize);
	//		//		parameters.setOrderBy(orderBy);
	//		//		parameters.setOrder(OrderOld.fromString(order));
	//		//		List<Customer> listCust = customersDAO.getCustomersList(parameters);
	//
	//		List<Customer> listCust = new ArrayList<Customer>();
	//		Customer customer = new Customer();
	//		customer.setFirst_name("Oleg");
	//		customer.setLast_name("Cherednik");
	//		customer.setId("id");
	//		listCust.add(customer);
	//		if (listCust != null) {
	//			GenericEntity<List<Customer>> entity = new GenericEntity<List<Customer>>(listCust) {};
	//			return ResponseCreator.success(getHeaderVersion(), entity);
	//		} else {
	//			return ResponseCreator.error(404, Error.NOT_FOUND.getCode(), getHeaderVersion());
	//		}
	//	}

	/*
	{"order_filter": {
   "@status": "found",
   "@create_time_to": "1369474545900",
   "@create_time_from": "1369474545900",
   "@vehicle_part_id": "22",
   "@customer_id": "11"
}}
	 */

	@POST
	@Path("/getFilteredOrders")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFilteredOrders(OrderFilterTO filter) throws Exception {
		check(filter);
		List<Order> orders = daoOrder.getOrders(OrderFilter.createBuilder().copyFrom(filter).createFilter());
		return Response.ok(new OrderListTO(convertOrders(orders))).build();
	}

	@POST
	@Path("/createOrderReport")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createOrderReport(OrderFilterTO filter) throws Exception {
		check(filter);

		List<Order> orders = daoOrder.getOrders(OrderFilter.createBuilder().copyFrom(filter).createFilter());

		return Response.ok(new OrderListTO(convertOrders(orders))).build();
	}

	private void foo(Set<Order> orders) {
		if (CollectionUtils.isEmpty(orders))
			return;

		int uniqueCustomers = 0;
		int newOrders = 0;
		int notFoundOrders = 0;
		int foundOrders = 0;
		int deliveredOrders = 0;
		Set<Long> notFoundParts = new HashSet<>();
		Map<Long, List<Order>> map = getOrderMap(orders);

		OrderReportData data = new OrderReportData();
		Set<Long> customers = new HashSet<>();

		for (Order order : orders) {
			for (OrderHistory history : daoOrder.getOrderHistory(order.getOrderId(), 0, 0)) {
				switch (history.getStatus()) {
				case NEW:
					newOrders++;
					break;
				case NOT_FOUND:
					notFoundOrders++;
					notFoundParts.add(order.getVehiclePartId());
					break;
				case FOUND:
					foundOrders++;
					break;
				case DELIVERED:
					deliveredOrders++;
					break;
				default:
					assert false : "OrderStatus not implemented";
				}
			}
		}

//		for (Map.Entry<Long, List<Order>> entry : getOrderMap(orders).entrySet()) {
//			List<Order> history = entry.getValue();
//			OrderStatus status = history.get(history.size() - 1).getStatus();
//
//			data.incOrderStatus(order.getStatus());
//			data.incPartOrder(order.getVehiclePartId());
//			customers.add(order.getCustomerId());
//		}
//
//		data.setTotalCustomers(customers.size());
	}

	/**
	 * @param orders orders ordered by update time
	 * @return
	 */
	private static Map<Long, List<Order>> getOrderMap(List<Order> orders) {
		assert CollectionUtils.isNotEmpty(orders);

		Map<Long, List<Order>> map = new HashMap<>();

		for (Order order : orders) {
			List<Order> tmp = map.get(order.getOrderId());

			if (tmp == null)
				map.put(order.getOrderId(), tmp = new ArrayList<>());

			tmp.add(order);
		}

		return Collections.unmodifiableMap(map);
	}

	// ========== static ==========

	private static void check(OrderFilterTO filter) throws Exception {
		if (filter == null || filter.isEmpty())
			throw new VehicleServiceException(ErrorCode.EMPTY_FILTER);
	}

	private static List<OrderTO> convertOrders(List<Order> orders) {
		if (CollectionUtils.isEmpty(orders))
			return Collections.emptyList();

		List<OrderTO> res = new ArrayList<>(orders.size());

		for (Order order : orders)
			res.add(new OrderTO(order));

		return res;
	}
}