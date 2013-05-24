package cop.ifree.test.autoservice.rest;

import cop.ifree.test.autoservice.customers.rest.exceptions.Error;
import cop.ifree.test.autoservice.rest.response.ResponseCreator;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

@Service("customExceptionMapper")
public class CustomExceptionMapper implements ExceptionMapper<Exception> {
	@Context
	private HttpHeaders requestHeaders;

	private String getHeaderVersion() {
		return "a";//requestHeaders.getRequestHeader("version").get(0);
	}

	public Response toResponse(Exception e) {
		System.out.println(e.getMessage());
		return ResponseCreator.error(500, Error.SERVER_ERROR.getCode(), getHeaderVersion());
	}
}