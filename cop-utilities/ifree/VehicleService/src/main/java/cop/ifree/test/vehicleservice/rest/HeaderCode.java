/**
 * $$Id$
 * $$URL$
 */
package cop.ifree.test.vehicleservice.rest;

/**
 * @author Oleg Cherednik
 * @since 24.05.2013
 */
public enum HeaderCode {
	CUSTOMER_ID("customer_id");

	private final String id;

	HeaderCode(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
}
