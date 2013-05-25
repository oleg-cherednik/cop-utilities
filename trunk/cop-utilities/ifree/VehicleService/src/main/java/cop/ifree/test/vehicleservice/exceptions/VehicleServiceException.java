/**
 * $$Id$
 * $$URL$
 */
package cop.ifree.test.vehicleservice.exceptions;

import javax.validation.constraints.NotNull;

/**
 * @author Oleg Cherednik
 * @since 24.05.2013
 */
public class VehicleServiceException extends Exception {

	private final ErrorCode code;
	private final String details;

	public VehicleServiceException(@NotNull ErrorCode code) {
		this(code, null);
	}

	public VehicleServiceException(@NotNull ErrorCode code, String details) {
		super(code.getDescription());
		this.code = code;
		this.details = details;

	}

	@NotNull
	public ErrorCode getCode() {
		return code;
	}

	public String getDetails() {
		return details;
	}

}
