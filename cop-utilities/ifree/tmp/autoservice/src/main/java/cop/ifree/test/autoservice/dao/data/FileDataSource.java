package cop.ifree.test.autoservice.dao.data;

import cop.ifree.test.autoservice.entities.Order;
import cop.ifree.test.autoservice.jaxb.adapter.JaxbUtils;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Oleg Cherednik
 * @since 12.05.2013
 */
public final class FileDataSource {
	private static final String TABLE_ORDERS = "orders";
	private static final String TMPDIR = System.getProperty("java.io.tmpdir", "c:/autoservice");
	private static final FileDataSource INSTANCE = new FileDataSource();

	private static final File FILE_TABLE_ORDERS = new File(TMPDIR, TABLE_ORDERS + ".tbl");

	private long ORDER_ID = 1;

	private final Map<Long, Order> orders = new HashMap<>();

	public static FileDataSource getInstance() {
		return INSTANCE;
	}

	private FileDataSource() {

	}

	private void readOrderTable() {

	}

	private void saveOrderTable() {
		JaxbUtils.save(FILE_TABLE_ORDERS, orders.values());
	}

	public Order addOrder(long customerId, long vehiclePartId) throws SQLException {
		final Order order = new Order(ORDER_ID++, customerId, vehiclePartId);

		orders.put(order.getId(), order);
		saveOrderTable();

		return order;
	}
}
