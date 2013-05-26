package cop.ifree.test.vehicleservice.dao;

import cop.ifree.test.vehicleservice.data.Order;
import cop.ifree.test.vehicleservice.data.OrderFilter;
import cop.ifree.test.vehicleservice.data.OrderHistory;
import cop.ifree.test.vehicleservice.data.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Repository
public class OrderDAO {
	private static final String SQL_CREATE_ORDER = "insert into orders (number, customerId, vehiclePartId, createTime, updateTime, status) values (?,?,?,?,?,?)";

	private SimpleJdbcInsert insertCustomer;
	private JdbcTemplate templCustomer;
	@Autowired
	private DataSource ds;

	public void setDataSource(DataSource dataSource) {
		this.templCustomer = new JdbcTemplate(dataSource);
		this.insertCustomer = new SimpleJdbcInsert(dataSource).withTableName("customer");
	}

	private PreparedStatement createPS(String sql) throws SQLException {
		Connection con = ds.getConnection();

		return con.prepareStatement(sql);
	}

	@Transactional
	public Order createOrder(long customerId, long vehiclePartId) throws SQLException, NamingException {
		Order.Builder builder = Order.createBuilder();

		builder.setId(666);
		builder.setNumber(Long.toString(666));
		builder.setCustomerId(customerId);
		builder.setVehiclePartId(vehiclePartId);
		builder.setCreateTime(System.currentTimeMillis());
		builder.setUpdateTime(System.currentTimeMillis());
		builder.setStatus(OrderStatus.NEW);

		return builder.createOrder();

		//		Context initCtx = new InitialContext();
		//		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		//		DataSource ds = (DataSource) envCtx.lookup("jdbc/EmployeeDB");
		//		Connection con = ds.getConnection();
		//
		//		PreparedStatement pp = con.prepareStatement(SQL_CREATE_ORDER);
		//
		//		try (PreparedStatement ps = createPS(SQL_CREATE_ORDER)) {
		//			int i = 1;
		//			long time = System.currentTimeMillis();
		//
		//			ps.setLong(i++, customerId);
		//			ps.setLong(i++, vehiclePartId);
		//			ps.setTimestamp(i++, new Timestamp(time));
		//			ps.setTimestamp(i++, new Timestamp(time));
		//			ps.setString(i, OrderStatus.NEW.getOrderId());
		//
		//			return createOrder(ps.executeQuery());
		//		}
	}

	@Transactional
	public List<OrderHistory> getOrderHistory(long orderId, long timeFrom, long timeTo) {
		List<OrderHistory> history = new ArrayList<>();
		OrderHistory.Builder builder = OrderHistory.createBuilder();

		builder.setHistoryId(1);
		builder.setOrderId(orderId);

		builder.setUpdateTime(System.currentTimeMillis());
		builder.setStatus(OrderStatus.NEW);
		history.add(builder.createOrderHistory());

		builder.setUpdateTime(System.currentTimeMillis());
		builder.setStatus(OrderStatus.FOUND);
		history.add(builder.createOrderHistory());

		builder.setUpdateTime(System.currentTimeMillis());
		builder.setStatus(OrderStatus.DELIVERED);
		history.add(builder.createOrderHistory());

		return Collections.unmodifiableList(history);
	}

	@Transactional
	public Order updateOrderStatus(long customerId, long orderId, OrderStatus status) {
		// add orderHistory record
		// update order record

		Order.Builder builder = Order.createBuilder();

		builder.setNumber(Long.toString(666));
		builder.setCustomerId(11);
		builder.setVehiclePartId(22);
		builder.setCreateTime(System.currentTimeMillis());
		builder.setUpdateTime(System.currentTimeMillis());
		builder.setStatus(OrderStatus.NEW);

		return builder.createOrder();
	}

	@Transactional
	public Set<Order> getOrders(OrderFilter orderFIlter) throws SQLException, NamingException {
		Set<Order> orders = new TreeSet<>();
		Order.Builder builder = Order.createBuilder();

		builder.setNumber(Long.toString(666));
		builder.setCustomerId(11);
		builder.setVehiclePartId(22);
		builder.setCreateTime(System.currentTimeMillis());
		builder.setUpdateTime(System.currentTimeMillis());
		builder.setStatus(OrderStatus.NEW);

		builder.setId(666);
		builder.setNumber(Long.toString(666));
		orders.add(builder.createOrder());
		builder.setId(667);
		builder.setNumber(Long.toString(667));
		orders.add(builder.createOrder());
		builder.setId(668);
		builder.setNumber(Long.toString(668));
		orders.add(builder.createOrder());

		return Collections.unmodifiableSet(orders);
	}

	@Transactional
	public Set<Order> getOrders(long updateTimeFrom, long updateTimeTo) throws SQLException, NamingException {
		Set<Order> orders = new HashSet<>();
		Order.Builder builder = Order.createBuilder();

		builder.setNumber(Long.toString(666));
		builder.setCustomerId(11);
		builder.setVehiclePartId(22);
		builder.setCreateTime(System.currentTimeMillis());
		builder.setUpdateTime(System.currentTimeMillis());
		builder.setStatus(OrderStatus.NEW);

		builder.setId(666);
		builder.setNumber(Long.toString(666));
		orders.add(builder.createOrder());
		builder.setId(667);
		builder.setNumber(Long.toString(667));
		orders.add(builder.createOrder());
		builder.setId(668);
		builder.setNumber(Long.toString(668));
		orders.add(builder.createOrder());

		return Collections.unmodifiableSet(orders);
	}

	//	public Customer getCustomer(String id) {
	//		if ((templCustomer.queryForInt("Select count(1) FROM customer WHERE id = '" + id + "'")) > 0) {
	//			Customer customer = (Customer)templCustomer
	//					.queryForObject("SELECT * FROM customer WHERE id = '" + id + "'", new RowMapper<Customer>() {
	//						public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
	//							Customer Customer = new Customer();
	//							Customer.setFirstName(rs.getString("first_name"));
	//							Customer.setLastName(rs.getString("last_name"));
	//							Customer.setPhone(rs.getString("phone"));
	//							Customer.setMail(rs.getString("mail"));
	//							Customer.setAddress(rs.getString("adress"));
	//							Customer.setContractId(rs.getString("contract_id"));
	//							if (rs.getString("contract_expire_date") != "" && rs
	//									.getString("contract_expire_date") != null)
	//								Customer.setContractExpireDate(Date.valueOf(rs.getString("contract_expire_date")));
	//
	//							return Customer;
	//						}
	//					});
	//
	//			return customer;
	//		} else {
	//			return null;
	//		}
	//	}

	//	public Customer createCustomer(Customer customer) {
	//		if (customer != null) {
	//			Map<String, Object> parameters = new HashMap<String, Object>(3);
	//			String uuid = UUID.randomUUID().toString();
	//			customer.setId(uuid);
	//			parameters.put("id", uuid);
	//			if (customer.getFirstName() != null)
	//				parameters.put("first_name", customer.getFirstName());
	//			if (customer.getLastName() != null)
	//				parameters.put("last_name", customer.getLastName());
	//			if (customer.getPhone() != null)
	//				parameters.put("phone", customer.getPhone());
	//			if (customer.getMail() != null)
	//				parameters.put("mail", customer.getMail());
	//			parameters.put("adress", customer.getAddress());
	//			parameters.put("contract_id", customer.getContractId());
	//			parameters.put("contract_expire_date", customer.getContractExpireDate());
	//			insertCustomer.execute(parameters);
	//
	//			return customer;
	//		} else {
	//			return null;
	//		}
	//	}

	//	public Customer updateCustomer(Customer customer) {
	//		if (customer != null && customer.getId() != null) {
	//			Customer oldCustomer = getCustomer(customer.getId());
	//			String sqlUpdate = String
	//					.format("UPDATE customer SET first_name = %s, last_name = %s, phone = %s, mail = %s, adress = %s, contract_id = %s, contract_expire_date = %s WHERE id = %s",
	//							"'" + customer.getFirstName() + "'", "'" + customer.getLastName() + "'",
	//							"'" + customer.getPhone() + "'", "'" + customer.getMail() + "'",
	//							"'" + customer.getAddress() + "'", "'" + customer.getContractId() + "'",
	//							((customer.getContractExpireDate() != null) ? "'" + customer
	//									.getContractExpireDate() + "'" : "null"), "'" + customer.getId() + "'");
	//			System.out.println(sqlUpdate);
	//			templCustomer.update(sqlUpdate);
	//			return oldCustomer;
	//		} else {
	//			return null;
	//		}
	//	}

//	public boolean removeCustomer(String id) {
//		if (templCustomer.update("DELETE FROM customer WHERE id = '" + id + "'") > 0) {
//			return true;
//		} else {
//			return false;
//		}
//	}

	//	public List<Customer> getCustomersList(CustomerListParameters parameters) {
	//		List<Customer> CustomerList = (List<Customer>)templCustomer
	//				.query("SELECT * FROM customers.customer;", new RowMapper<Customer>() {
	//					public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
	//						Customer customer = new Customer();
	//						customer.setId(rs.getString("id"));
	//						customer.setFirstName(rs.getString("first_name"));
	//						customer.setLastName(rs.getString("last_name"));
	//						customer.setPhone(rs.getString("phone"));
	//						customer.setMail(rs.getString("mail"));
	//						customer.setAddress(rs.getString("adress"));
	//						customer.setContractId(rs.getString("contract_id"));
	//						if (rs.getString("contract_expire_date") != "" && rs.getString("contract_expire_date") != null)
	//							customer.setContractExpireDate(Date.valueOf(rs.getString("contract_expire_date")));
	//						return customer;
	//					}
	//				});
	//
	//		return CustomerList;
	//	}

	// ========== static ==========

	private static Order createOrder(ResultSet rs) throws SQLException {
		Order.Builder builder = Order.createBuilder();

		builder.setId(rs.getInt("id"));
		builder.setNumber(rs.getString("number"));
		builder.setCustomerId(rs.getLong("customerId"));
		builder.setVehiclePartId(rs.getLong("vehiclePartId"));
		builder.setCreateTime(rs.getTimestamp("createTime").getTime());
		builder.setUpdateTime(rs.getTimestamp("updateTime").getTime());
		builder.setStatus(OrderStatus.parseString(rs.getString("status")));

		return builder.createOrder();
	}
}
