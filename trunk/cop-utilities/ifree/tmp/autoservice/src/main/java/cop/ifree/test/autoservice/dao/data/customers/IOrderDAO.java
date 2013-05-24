package cop.ifree.test.autoservice.dao.data.customers;

import cop.ifree.test.autoservice.dao.data.parameters.customer.CustomerListParameters;
import cop.ifree.test.autoservice.entities.Customer;
import cop.ifree.test.autoservice.entities.Order;

import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.List;

public interface IOrderDAO {
	@NotNull
	Order addOrder(long customerId, long vehiclePartId) throws SQLException;

	Customer createCustomer(Customer Customer);

	Customer getCustomer(String id);

	Customer updateCustomer(Customer Customer);

	boolean removeCustomer(String id);

	List<Customer> getCustomersList(CustomerListParameters parameters);
}
