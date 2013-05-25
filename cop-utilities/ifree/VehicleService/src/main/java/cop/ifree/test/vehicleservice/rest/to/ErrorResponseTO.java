/**
 * $$Id$
 * $$URL$
 */
package cop.ifree.test.vehicleservice.rest.to;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import cop.ifree.test.vehicleservice.exceptions.ErrorCode;
import cop.ifree.test.vehicleservice.jaxb.JAXBErrorCodeAdapter;

/**
 * @author Oleg Cherednik
 * @since 24.05.2013
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "error")
public class ErrorResponseTO {

	@XmlAttribute(name = "code", required = true)
	@XmlJavaTypeAdapter(JAXBErrorCodeAdapter.class)
	private ErrorCode code;
	@XmlElement(name = "codeDescription")
	private String codeDescription;
	@XmlElement(name = "msg")
	private String message;

	public ErrorCode getCode() {
		return code;
	}

	public void setCode(@NotNull ErrorCode code) {
		this.code = code;
		this.codeDescription = code.getDescription();
	}

	public String getCodeDescription() {
		return codeDescription;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
