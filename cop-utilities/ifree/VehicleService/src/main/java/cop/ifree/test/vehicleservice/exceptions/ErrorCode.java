/**
 * $$Id$
 * $$URL$
 */
package cop.ifree.test.vehicleservice.exceptions;

/**
 * @author Oleg Cherednik
 * @since 24.05.2013
 */
public enum ErrorCode {
	OTHER(4000, "other"),
	CUSTOMER_ID_NOT_SET(4010, "customer id is not set"),
	TOO_MANY_CUSTOMER_IDS(4020, "too many customer ids"),
	UNKNOWN_CUSTOMER_ID(4030, "unknown customer id"),
	EMPTY_FILTER(4040, "empty filter");

	private final int id;
	private final String description;

	ErrorCode(int id, String description) {
		this.id = id;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	// ========== static ==========

	public static ErrorCode parseString(String id) {
		for (ErrorCode status : values()) {
			if (Long.toString(status.id).equals(id)) {
				return status;
			}
		}
		throw new EnumConstantNotPresentException(ErrorCode.class, id);
	}
}
