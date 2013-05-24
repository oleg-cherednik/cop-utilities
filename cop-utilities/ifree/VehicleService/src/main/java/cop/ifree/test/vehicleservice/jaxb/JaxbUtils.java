package cop.ifree.test.vehicleservice.jaxb;

import cop.ifree.test.vehicleservice.data.Order;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.Collection;

/**
 * @author Oleg Cherednik
 * @since 12.05.2013
 */
public final class JaxbUtils {
	public static void save(File file, Collection<Order> orders) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Order.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			//			jaxbMarshaller.marshal(orders, file);
			// Writing to console
			jaxbMarshaller.marshal(orders, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
