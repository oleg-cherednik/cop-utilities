package cop.cs.shop.data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Oleg Cherednik
 * @since 18.04.2013
 */
public final class PriceKey {
	public static final PriceKey NULL = new PriceKey(-1, -1);

	private final int department;
	private final int number;

//	public static PriceKey createPriceKey(int department, int number) {
//		if(department == NULL.g)
//
//	}

	public PriceKey(int department, int number) {
		this.department = department;
		this.number = number;
	}

	public int getDepartment() {
		return department;
	}

	public int getNumber() {
		return number;
	}


}
