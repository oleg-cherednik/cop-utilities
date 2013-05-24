package cop.ifree.test.autoservice;

/**
 * @author Oleg Cherednik
 * @since 12.05.2013
 */

import com.sun.corba.se.pept.transport.ContactInfo;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path(value = "/contacts")
public class ContactsResource {
	@GET
	@Path(value = "/{emailAddress:.+@.+\\.[a-z]+}")
	@Produces(value = {"text/xml", "application/json"})
	public ContactInfo getByEmailAddress(@PathParam(value = "emailAddress") String emailAddress) {
		return null;
	}

	@GET
	@Path(value = "/{lastName}")
	@Produces(value = "text/xml")
	public ContactInfo getByLastName(@PathParam(value = "lastName") String lastName) {
		return null;
	}

	@POST
	@Consumes(value = {"text/xml", "application/json"})
	public void addContactInfo(ContactInfo contactInfo) {
		int a = 0;
		a++;
	}
}
