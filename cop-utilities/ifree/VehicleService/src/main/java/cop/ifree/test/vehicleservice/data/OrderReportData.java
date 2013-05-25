package cop.ifree.test.vehicleservice.data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Oleg Cherednik
 * @since 25.05.2013
 */
public class OrderReportData {
	private int uniqueCustomers;
	private int newOrders;
	private int foundPartsAmount;
	private int deliveredOrders;
	private final Set<Long> notFoundParts = new HashSet<>();


	/*
	Уникальных пользователей: 12
	Новых заказов: 12
	Найденых но но не доставленых заказов: 2
	Выполненых заказов: 4
	Ненайденых деталей (12): 5,7,8,9
	*/
}
