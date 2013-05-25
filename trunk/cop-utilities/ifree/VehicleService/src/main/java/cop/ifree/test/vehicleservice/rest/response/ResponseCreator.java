package cop.ifree.test.vehicleservice.rest.response;

import cop.ifree.test.vehicleservice.exceptions.VehicleServiceException;
import cop.ifree.test.vehicleservice.rest.to.ErrorResponseTO;

import javax.ws.rs.core.Response;

public class ResponseCreator {

	public static Response error(int status, int errorCode, String version) {
		Response.ResponseBuilder response = Response.status(status);
		response.header("version", version);
		response.header("errorcode", errorCode);
		response.entity("none");
		return response.build();
	}

	public static Response error(VehicleServiceException exception) {
		ErrorResponseTO entity = new ErrorResponseTO();
		entity.setCode(exception.getCode());
		entity.setMessage(exception.getDetails());

		return Response.status(entity.getCode().getId()).entity(entity).build();
	}

	public static Response success(String version, Object object) {
		Response.ResponseBuilder response = Response.ok();
		response.header("version", version);
		if (object != null) {
			response.entity(object);
		} else {
			response.entity("none");
		}
		return response.build();
	}
}
