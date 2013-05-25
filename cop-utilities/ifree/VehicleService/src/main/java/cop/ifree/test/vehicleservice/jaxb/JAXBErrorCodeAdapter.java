package cop.ifree.test.vehicleservice.jaxb;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.lang.StringUtils;

import cop.ifree.test.vehicleservice.exceptions.ErrorCode;

public class JAXBErrorCodeAdapter extends XmlAdapter<String, ErrorCode> {

	public ErrorCode unmarshal(String str) throws Exception {
		return StringUtils.isNotBlank(str) ? ErrorCode.parseString(str) : null;
	}

	public String marshal(ErrorCode status) throws Exception {
		return status != null ? Long.toString(status.getId()) : "";
	}
}
