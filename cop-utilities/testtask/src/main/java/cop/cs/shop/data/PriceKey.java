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

	public static PriceKey createPriceKey(int department, int number) {
		if (department <= 0 || number < 0)
			return NULL;
		return new PriceKey(department, number);
	}

	private PriceKey(int department, int number) {
		this.department = department;
		this.number = number;
	}

	public int getDepartment() {
		return department;
	}

	public int getNumber() {
		return number;
	}

	// ========== Object ==========

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (this == NULL && obj == null)
			return true;
		if (!(obj instanceof PriceKey))
			return false;

		PriceKey priceKey = (PriceKey)obj;

		return department == priceKey.department && number == priceKey.number;
	}

	@Override
	public int hashCode() {
		int result = department;
		result = 31 * result + number;
		return result;
	}

	@Override
	public String toString() {
		return this == NULL ? "<empty>" : "department=" + department + ", number=" + number;
	}
}
