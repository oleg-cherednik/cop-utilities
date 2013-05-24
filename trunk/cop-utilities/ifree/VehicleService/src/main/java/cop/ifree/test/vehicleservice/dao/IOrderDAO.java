package cop.ifree.test.vehicleservice.dao;

import com.test.services.dao.data.parameters.customer.CustomerListParameters;
import com.test.services.entities.Customer;
import cop.ifree.test.vehicleservice.data.Order;

import javax.naming.NamingException;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.List;

public interface IOrderDAO {
	@NotNull
	Order createOrder(long customerId, long vehiclePartId) throws SQLException, NamingException;

	Customer createCustomer(Customer Customer);

	Customer getCustomer(String id);

	Customer updateCustomer(Customer Customer);

	boolean removeCustomer(String id);

	List<Customer> getCustomersList(CustomerListParameters parameters);
}
