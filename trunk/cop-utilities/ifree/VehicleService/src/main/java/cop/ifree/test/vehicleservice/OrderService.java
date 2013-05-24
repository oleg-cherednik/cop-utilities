package cop.ifree.test.vehicleservice;

import com.test.services.customers.rest.exceptions.Error;
import com.test.services.entities.Customer;
import com.test.services.rest.response.ResponseCreator;
import cop.ifree.test.vehicleservice.dao.IOrderDAO;
import cop.ifree.test.vehicleservice.data.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleg Cherednik
 * @since 12.05.2013
 */
@Service("orderService")
public class OrderService implements IOrderService {
	@Resource(name = "orderDAO")
	private IOrderDAO daoOrder;

	// for retrieving request headers from context
	// an injectable interface that provides access to HTTP header information.
	@Context
	private HttpHeaders requestHeaders;

	private String getHeaderVersion() {
		return "aa";//requestHeaders.getRequestHeader("version").get(0);
	}

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

	@GET
	@Path("orderVehiclePart")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response orderVehiclePart(@QueryParam("customerId") long customerId,
			@QueryParam("vehiclePartId") long vehiclePartId) {

		try {
			Order order = daoOrder.createOrder(customerId, vehiclePartId);
			ResponseCreator.success(getHeaderVersion(), order);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//		CustomerListParameters parameters = new CustomerListParameters();
		//		parameters.setKeyword(keyword);
		//		parameters.setPageNum(pageNum);
		//		parameters.setPageSize(pageSize);
		//		parameters.setOrderBy(orderBy);
		//		parameters.setOrder(OrderOld.fromString(order));
		//		List<Customer> listCust = customersDAO.getCustomersList(parameters);

		List<Customer> listCust = new ArrayList<Customer>();
		Customer customer = new Customer();
		customer.setFirst_name("Oleg");
		customer.setLast_name("Cherednik");
		customer.setId("id");
		listCust.add(customer);
		if (listCust != null) {
			GenericEntity<List<Customer>> entity = new GenericEntity<List<Customer>>(listCust) {};
			return ResponseCreator.success(getHeaderVersion(), entity);
		} else {
			return ResponseCreator.error(404, Error.NOT_FOUND.getCode(), getHeaderVersion());
		}
	}
}