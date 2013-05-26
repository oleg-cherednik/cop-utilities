package cop.ifree.test.vehicleservice;

import cop.ifree.test.vehicleservice.dao.CustomerDAO;
import cop.ifree.test.vehicleservice.dao.OrderDAO;
import cop.ifree.test.vehicleservice.data.Order;
import cop.ifree.test.vehicleservice.data.OrderFilter;
import cop.ifree.test.vehicleservice.data.OrderHistory;
import cop.ifree.test.vehicleservice.data.OrderReportData;
import cop.ifree.test.vehicleservice.exceptions.ErrorCode;
import cop.ifree.test.vehicleservice.exceptions.VehicleServiceException;
import cop.ifree.test.vehicleservice.rest.to.OrderFilterTO;
import cop.ifree.test.vehicleservice.rest.to.OrderListTO;
import cop.ifree.test.vehicleservice.rest.to.ReportFilterTO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
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

	@POST
	@Path("/createOrderReport")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createOrderReport(ReportFilterTO filter) throws Exception {
		check(filter);

		Set<Order> orders = daoOrder.getOrders(filter.getTimeFrom(), filter.getTimeTo());
		OrderReportData reportData = createReportData(orders, filter);
		File file = createReportFile(reportData);

		return Response.ok(new OrderListTO(OrderService.convertOrders(orders))).build();
	}

	private File createReportFile(OrderReportData data) throws IOException {
		if (data == null || data == OrderReportData.NULL)
			return null;

		File file = File.createTempFile("report", "txt");

		try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
			Set<Long> notFoundParts = data.getNotFoundParts();
			Long[] parts = notFoundParts.toArray(new Long[notFoundParts.size()]);

			out.write(String.format("Report (%1$td.%1$tm.%1$tY - %2$td.%2$tm.%2$tY)\n", data.getTimeFrom(),
					data.getTimeTo()));
			out.write(String.format("Unique customers: %d\n", data.getUniqueCustomers()));
			out.write(String.format("New orders: %d\n", data.getNewOrders()));
			out.write(String.format("Found vehicle parts: %d\n", data.getFoundOrders()));
			out.write(String.format("Delivered parts: %d\n", data.getDeliveredOrders()));
			out.write(String.format("Not found parts: %s", Arrays.deepToString(parts)));
		}

		return file;
	}

	@NotNull
	private OrderReportData createReportData(Set<Order> orders, ReportFilterTO filter) {
		if (CollectionUtils.isEmpty(orders) || filter.isEmpty())
			return OrderReportData.NULL;

		final long updateTimeFrom = filter.getTimeFrom();
		final long updateTimeTo = filter.getTimeTo();

		OrderReportData.Builder builder = OrderReportData.createBuilder();
		Set<Long> customers = new HashSet<>();

		for (Order order : orders) {
			for (OrderHistory history : daoOrder.getOrderHistory(order.getOrderId(), updateTimeFrom, updateTimeTo)) {
				customers.add(order.getCustomerId());

				switch (history.getStatus()) {
				case NEW:
					builder.incNewOrders();
					break;
				case NOT_FOUND:
					builder.addNotFoundPart(order.getVehiclePartId());
					break;
				case FOUND:
					builder.incFoundOrders();
					break;
				case DELIVERED:
					builder.incDeliveredOrders();
					break;
				default:
					assert false : "OrderStatus not implemented";
				}
			}
		}

		builder.setTimeFrom(filter.getTimeFrom());
		builder.setTimeTo(filter.getTimeTo());
		builder.setUniqueCustomers(customers.size());
		return builder.createReportData();
	}

	// ========== static ==========

	private static void check(ReportFilterTO filter) throws Exception {
		if (filter == null || filter.isEmpty())
			throw new VehicleServiceException(ErrorCode.EMPTY_FILTER);
	}
}