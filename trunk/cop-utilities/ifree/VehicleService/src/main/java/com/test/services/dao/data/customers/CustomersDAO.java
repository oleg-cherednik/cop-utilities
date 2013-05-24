package com.test.services.dao.data.customers;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.*;

import com.test.services.dao.data.parameters.customer.CustomerListParameters;
import com.test.services.entities.Customer;

public class CustomersDAO implements ICustomersDAO {

	Map<String, Customer> profsMap = new HashMap<String, Customer>();

	DataSource datasource;

	private SimpleJdbcInsert insertCustomer;
	private JdbcTemplate templCustomer;

	public void setDataSource(DataSource dataSource) {
		this.templCustomer = new JdbcTemplate(dataSource);
		this.insertCustomer = new SimpleJdbcInsert(dataSource)
				.withTableName("customer");
	}

	public Customer getCustomer(String id) {
		if ((templCustomer
				.queryForInt("Select count(1) FROM customer WHERE id = '" + id
						+ "'")) > 0) {
			Customer customer = (Customer) templCustomer.queryForObject(
					"SELECT * FROM customer WHERE id = '" + id + "'",
					new RowMapper<Customer>() {
						public Customer mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							Customer Customer = new Customer();
							Customer.setFirstName(rs.getString("first_name"));
							Customer.setLastName(rs.getString("last_name"));
							Customer.setPhone(rs.getString("phone"));
							Customer.setMail(rs.getString("mail"));
							Customer.setAddress(rs.getString("adress"));
							Customer.setContractId(rs.getString("contract_id"));
							if (rs.getString("contract_expire_date") != ""
									&& rs.getString("contract_expire_date") != null)
								Customer.setContractExpireDate(Date.valueOf(rs.getString("contract_expire_date")));

							return Customer;
						}
					});

			return customer;
		} else {
			return null;
		}
	}

	public Customer createCustomer(Customer customer) {
		if (customer != null) {
			Map<String, Object> parameters = new HashMap<String, Object>(3);
			String uuid = UUID.randomUUID().toString();
			customer.setId(uuid);
			parameters.put("id", uuid);
			if (customer.getFirstName() != null)
				parameters.put("first_name", customer.getFirstName());
			if (customer.getLastName() != null)
				parameters.put("last_name", customer.getLastName());
			if (customer.getPhone() != null)
				parameters.put("phone", customer.getPhone());
			if (customer.getMail() != null)
				parameters.put("mail", customer.getMail());
			parameters.put("adress", customer.getAddress());
			parameters.put("contract_id", customer.getContractId());
			parameters.put("contract_expire_date",
					customer.getContractExpireDate());
			insertCustomer.execute(parameters);

			return customer;
		} else {
			return null;
		}
	}

	public Customer updateCustomer(Customer customer) {
		if (customer != null && customer.getId() != null) {
			Customer oldCustomer = getCustomer(customer.getId());
			String sqlUpdate = String
					.format("UPDATE customer SET first_name = %s, last_name = %s, phone = %s, mail = %s, adress = %s, contract_id = %s, contract_expire_date = %s WHERE id = %s",
							"'" + customer.getFirstName() + "'", "'" +  customer.getLastName()+ "'",
							"'" + customer.getPhone()+ "'", "'" + customer.getMail()+ "'",
							"'" + customer.getAddress()+ "'", "'" + customer.getContractId()+ "'",
							((customer.getContractExpireDate()!= null) ? "'" + customer.getContractExpireDate()+ "'" : "null"),
							"'" + customer.getId() + "'");
			System.out.println(sqlUpdate);
			templCustomer.update(sqlUpdate);
			return oldCustomer;
		} else {
			return null;
		}
	}

	public boolean removeCustomer(String id) {
		if (templCustomer
				.update("DELETE FROM customer WHERE id = '" + id + "'") > 0) {
			return true;
		} else {
			return false;
		}
	}

	public List<Customer> getCustomersList(CustomerListParameters parameters) {
		List<Customer> CustomerList = (List<Customer>) templCustomer.query(
				"SELECT * FROM customers.customer;", new RowMapper<Customer>() {
					public Customer mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Customer customer = new Customer();
						customer.setId(rs.getString("id"));
						customer.setFirstName(rs.getString("first_name"));
						customer.setLastName(rs.getString("last_name"));
						customer.setPhone(rs.getString("phone"));
						customer.setMail(rs.getString("mail"));
						customer.setAddress(rs.getString("adress"));
						customer.setContractId(rs.getString("contract_id"));
						if (rs.getString("contract_expire_date") != ""
								&& rs.getString("contract_expire_date") != null)
							customer.setContractExpireDate(Date.valueOf(rs.getString("contract_expire_date")));
						return customer;
					}
				});

		return CustomerList;
	}
}
