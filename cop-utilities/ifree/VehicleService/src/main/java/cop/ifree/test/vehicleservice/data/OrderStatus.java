package cop.ifree.test.vehicleservice.data;

/**
 * @author Oleg Cherednik
 * @since 15.05.2013
 */
public enum OrderStatus {
	NEW("new"),
	NOT_FOUND("not_dound"),
	FOUND("found"),
	DELIVERED("delivered");

	private final String id;

	OrderStatus(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	// ========== static ==========

	public static OrderStatus parseString(String id) {
		for (OrderStatus status : values())
			if (status.id.equals(id))
				return status;
		throw new EnumConstantNotPresentException(OrderStatus.class, id);
	}
}
