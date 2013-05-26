package cop.ifree.test.vehicleservice.rest.to;

import cop.ifree.test.vehicleservice.jaxb.JAXBDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Oleg Cherednik
 * @since 25.05.2013
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "report_filter")
public class ReportFilterTO {
	@XmlElement(name = "time_from")
	@XmlJavaTypeAdapter(JAXBDateAdapter.class)
	private Long timeFrom;
	@XmlElement(name = "time_to")
	@XmlJavaTypeAdapter(JAXBDateAdapter.class)
	private Long timeTo;

	public Long getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(Long timeFrom) {
		this.timeFrom = timeFrom;
	}

	public Long getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(Long timeTo) {
		this.timeTo = timeTo;
	}

	public boolean isEmpty() {
		return timeFrom == null || timeFrom == 0 || timeTo == null || timeTo == 0;
	}
}
