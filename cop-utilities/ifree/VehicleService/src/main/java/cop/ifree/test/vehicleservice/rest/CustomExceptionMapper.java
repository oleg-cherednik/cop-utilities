package cop.ifree.test.vehicleservice.rest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.springframework.stereotype.Service;

import cop.ifree.test.vehicleservice.exceptions.ErrorCode;
import cop.ifree.test.vehicleservice.rest.to.ErrorResponseTO;

@Service("customExceptionMapper")
public class CustomExceptionMapper implements ExceptionMapper<Exception> {

	public Response toResponse(Exception ex) {
		System.out.println(ex.getMessage() + ex.getCause());

		ErrorResponseTO entity = new ErrorResponseTO();

		entity.setCode(ErrorCode.OTHER);
		entity.setMessage(ex.getMessage());

		Response.ResponseBuilder response = Response.status(entity.getCode().getId());
//		response.header("version", version);
//		response.header("errorcode", errorCode);
		response.entity(entity);
		return response.build();
//        return ResponseCreator.error(500, Error.SERVER_ERROR.getCode(), getHeaderVersion());
	}
}