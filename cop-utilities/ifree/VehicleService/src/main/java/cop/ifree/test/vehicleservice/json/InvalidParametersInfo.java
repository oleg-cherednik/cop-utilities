package cop.ifree.test.vehicleservice.json;

import java.util.ArrayList;
import java.util.List;

/** Description of group invalid parameters */
public class InvalidParametersInfo {

	private InvalidParametersCodes code;

	private List<String> names = new ArrayList<>();

	/** @param code code of reason invalid parameters */
	public InvalidParametersInfo(InvalidParametersCodes code) {
		this.code = code;
	}

	/** @return code of reason invalid parameters */
	public InvalidParametersCodes getCode() {
		return code;
	}

	/** @return names of invalid parameters */
	public List<String> getNames() {
		return names;
	}

	/** @param names names of invalid parameters */
	public void setNames(List<String> names) {
		this.names = names;
	}

	/** @param name the name of invalid parameter */
	public void add(String name) {
		if (!names.contains(name)) {
			names.add(name);
		}
	}
}
