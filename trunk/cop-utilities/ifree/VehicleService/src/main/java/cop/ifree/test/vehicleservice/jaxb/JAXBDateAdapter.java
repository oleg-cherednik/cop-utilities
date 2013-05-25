package cop.ifree.test.vehicleservice.jaxb;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.lang.StringUtils;

public class JAXBDateAdapter extends XmlAdapter<String, Long> {

	private final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

	public Long unmarshal(String str) throws Exception {
		return StringUtils.isNotBlank(str) ? DF.parse(str).getTime() : 0L;
	}

	public String marshal(Long date) throws Exception {
		return date != null ? DF.format(new Date(date)) : "";
	}
}
