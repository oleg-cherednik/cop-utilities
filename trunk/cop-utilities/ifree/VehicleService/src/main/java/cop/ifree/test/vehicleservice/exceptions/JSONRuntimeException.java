/**
 * $$Id$
 * $$URL$
 */
package cop.ifree.test.vehicleservice.exceptions;

import javax.ws.rs.core.Response;

import cop.ifree.test.vehicleservice.json.JSONResponse;
import cop.ifree.test.vehicleservice.json.OperationOutputCodes;

/**
 * @author Oleg Cherednik
 * @since 24.05.2013
 */
public class JSONRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 5989433547756355589L;
	private final JSONResponse response;
	private final Response.Status status;

	/**
	 * @param response
	 * @param status
	 */
	public JSONRuntimeException(JSONResponse response, Response.Status status) {
		this(response, status, null);
	}

	/** @param response */
	public JSONRuntimeException(JSONResponse response) {
		this(response, Response.Status.OK);
	}

	/**
	 * @param code
	 * @param description
	 */
	public JSONRuntimeException(OperationOutputCodes code, String description) {
		this(new JSONResponse(code, description));
	}

	/**
	 * @param code
	 * @param description
	 * @param status Статус HTTP response при данном исключении
	 */
	public JSONRuntimeException(OperationOutputCodes code, String description, Response.Status status) {
		this(new JSONResponse(code, description), status);
	}

	/**
	 * @param response
	 * @param status
	 * @param cause
	 */
	public JSONRuntimeException(JSONResponse response, Response.Status status, Throwable cause) {
		super(response.getDescription(), cause);
		this.response = response;
		this.status = status;
	}

	/**
	 * @param response
	 * @param cause
	 */
	public JSONRuntimeException(JSONResponse response, Throwable cause) {
		this(response, Response.Status.OK, cause);
	}

	/**
	 * @param code
	 * @param description
	 * @param cause
	 */
	public JSONRuntimeException(OperationOutputCodes code, String description, Throwable cause) {
		this(new JSONResponse(code, description), cause);
	}

	/**
	 * @param code
	 * @param description
	 * @param status
	 * @param cause
	 */
	public JSONRuntimeException(OperationOutputCodes code, String description, Response.Status status, Throwable cause) {
		this(new JSONResponse(code, description), status, cause);
	}

	/** @return Статус HTTP response при данном исключении */
	public Response.Status getStatus() {
		return status;
	}

	/** @return JSONResponse */
	public JSONResponse getJSONResponse() {
		return response;
	}
}
