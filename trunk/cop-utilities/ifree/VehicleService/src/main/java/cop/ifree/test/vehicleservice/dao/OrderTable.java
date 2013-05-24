package cop.ifree.test.vehicleservice.dao;

import com.test.services.jaxb.adapter.OrderDTO;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleg Cherednik
 * @since 12.05.2013
 */
@XStreamAlias(OrderTable.TITLE)
public final class OrderTable {
	public static final String TITLE = "tbl_orders";

	@XStreamImplicit(itemFieldName = OrderDTO.TITLE)
	private List<OrderDTO> orders;

//	public void addLoop(SheetTag loop) {
//		if (loop == null)
//			loops = new ArrayList<SheetTag>();
//		loops.add(loop);
//	}
}
