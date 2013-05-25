package cop.ifree.test.vehicleservice.json;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class JSONResponse {

	class MyStyle extends ToStringStyle {

		/***/
		private static final long serialVersionUID = -3744895156437373814L;

		{
			setDefaultFullDetail(false);
		}

		@Override
		public void append(StringBuffer buffer, String fieldName, Object value, Boolean fullDetail) {
			Object output = value;
			if (value == null) {
				output = "null";
			} else if (List.class.isAssignableFrom(value.getClass())) {
				buffer.append("\n\t").append(fieldName).append(" = [");
				List<?> list = (List<?>) value;
				if (!list.isEmpty()) {
					buffer.append(list.get(0));
				}
				if (list.size() > 1) {
					buffer.append(", ").append(list.get(1));
				}
				if (list.size() > 2) {
					buffer.append(", ...");
				}
				buffer.append("]");
				return;
			}
			buffer.append("\n\t").append(fieldName).append(" = ").append(output);
		}
	}

	/** Description string of OK result */
	public static final String OK_MESSAGE = "Operation completed successfully";

	/** Operation result code */
	protected OperationOutputCodes code;

	/** Operation result description */
	protected String description;

	/** current cache version in BO */
	protected Long cacheVersion;

	private List<InvalidParametersInfo> validationInfo = new ArrayList<>();

	/***/
	public JSONResponse() {
	}

	/**
	 * @param code operation result code
	 * @param description operation result description
	 */
	public JSONResponse(OperationOutputCodes code, String description) {
		this.code = code;
		this.description = description;
	}

	/** @return instance for OK result */
	public static JSONResponse createOK() {
		return new JSONResponse(OperationOutputCodes.OK, OK_MESSAGE);
	}

	/** @return the code */
	public OperationOutputCodes getCode() {
		return code;
	}

	/** @return the description */
	public String getDescription() {
		return description;
	}

	/** @return current cache version in BO */
	public Long getCacheVersion() {
		return cacheVersion;
	}

	/** @param code the code to set */
	public void setCode(OperationOutputCodes code) {
		this.code = code;
	}

	/** @param description the description to set */
	public void setDescription(String description) {
		this.description = description;
	}

	/** @param cacheVersion set current cache version in BO in response */
	public void setCacheVersion(Long cacheVersion) {
		this.cacheVersion = cacheVersion;
	}

	/** @return description of reason invalid parameters */
	public List<InvalidParametersInfo> getValidationInfo() {
		return validationInfo;
	}

	/** @param validationInfo description of reason invalid parameters */
	public void setValidationInfo(List<InvalidParametersInfo> validationInfo) {
		this.validationInfo = validationInfo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, new MyStyle());
	}
}
