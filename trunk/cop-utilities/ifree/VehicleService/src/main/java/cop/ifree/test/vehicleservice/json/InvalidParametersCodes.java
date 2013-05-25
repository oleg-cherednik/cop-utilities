package cop.ifree.test.vehicleservice.json;

/** Codes that might be returned as a description of reason invalid parameters for saving domain elements */
public enum InvalidParametersCodes {
	/** At least one of parameters must be specified */
	AT_LEAST_ONE,
	/** Value of required property must be specified */
	REQUIRED,
	/** Both parameters must be specified or not at the same time */
	BOTH
}
