package cop.ifree.test.vehicleservice.rest;

import cop.ifree.test.vehicleservice.exceptions.ErrorCode;
import cop.ifree.test.vehicleservice.exceptions.VehicleServiceException;
import org.apache.commons.collections.CollectionUtils;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.HttpHeaders;
import java.util.Arrays;
import java.util.List;

/**
 * @author Oleg Cherednik
 * @since 24.05.2013
 */
public final class HeaderUtils {
	private HeaderUtils() {}

	public static long getCustomerId(@NotNull HttpHeaders requestHeaders) throws VehicleServiceException {
		List<String> ids = requestHeaders.getRequestHeader(HeaderCode.CUSTOMER_ID.getId());

		if (CollectionUtils.isEmpty(ids)) {
			throw new VehicleServiceException(ErrorCode.CUSTOMER_ID_NOT_SET);
		}
		if (ids.size() > 1) {
			throw new VehicleServiceException(ErrorCode.TOO_MANY_CUSTOMER_IDS,
					Arrays.deepToString(ids.toArray(new String[ids.size()])));
		}

		try {
			return Long.parseLong(ids.get(0));
		} catch (Exception e) {
			throw new VehicleServiceException(ErrorCode.UNKNOWN_CUSTOMER_ID, ids.get(0));
		}
	}
}
