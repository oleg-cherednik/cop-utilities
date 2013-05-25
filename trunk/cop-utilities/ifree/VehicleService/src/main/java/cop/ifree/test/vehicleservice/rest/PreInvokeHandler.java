package cop.ifree.test.vehicleservice.rest;

import cop.ifree.test.vehicleservice.dao.CustomerDAO;
import cop.ifree.test.vehicleservice.exceptions.ErrorCode;
import cop.ifree.test.vehicleservice.exceptions.VehicleServiceException;
import cop.ifree.test.vehicleservice.rest.response.ResponseCreator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.message.Message;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Oleg Cherednik
 * @since 25.05.2013
 */
@Service("preInvokeHandler")
public class PreInvokeHandler implements RequestHandler {
	@Resource(name = "customerDAO")
	private CustomerDAO daoCustomer;

	public Response handleRequest(Message message, ClassResourceInfo arg1) {
		try {
			Map<String, List<String>> headers = CastUtils.cast((Map<?, ?>)message.get(Message.PROTOCOL_HEADERS));
			checkCustomerId(headers.get(HeaderCode.CUSTOMER_ID.getId()));
			return null;
		} catch (VehicleServiceException e) {
			return ResponseCreator.error(e);
		}
	}

	private void checkCustomerId(@NotNull List<String> ids) throws VehicleServiceException {
		if (CollectionUtils.isEmpty(ids)) {
			throw new VehicleServiceException(ErrorCode.CUSTOMER_ID_NOT_SET);
		}
		if (ids.size() > 1) {
			throw new VehicleServiceException(ErrorCode.TOO_MANY_CUSTOMER_IDS,
					Arrays.deepToString(ids.toArray(new String[ids.size()])));
		}

		try {
			daoCustomer.checkCustomer(Long.parseLong(ids.get(0)));
		} catch (Exception e) {
			throw new VehicleServiceException(ErrorCode.UNKNOWN_CUSTOMER_ID, ids.get(0));
		}
	}
}
