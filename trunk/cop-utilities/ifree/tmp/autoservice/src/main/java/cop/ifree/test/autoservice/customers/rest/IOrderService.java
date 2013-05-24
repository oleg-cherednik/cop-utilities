package cop.ifree.test.autoservice.customers.rest;

import javax.ws.rs.core.Response;

import cop.ifree.test.autoservice.entities.Customer;

public interface IOrderService {
//	Response getCustomer(String id);
//
//	Response removeCustomer(String id);
//
//	Response createCustomer(Customer str);
//
//	Response updateCustomer(Customer str);
//
//	Response getCustomers(long customerId, String keyword, String orderBy, String order, Integer pageNum, Integer pageSize);

	Response orderVehiclePart(long customerId, long vehiclePartId);
}
