package cop.ifree.test.vehicleservice;

import javax.ws.rs.core.Response;

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
