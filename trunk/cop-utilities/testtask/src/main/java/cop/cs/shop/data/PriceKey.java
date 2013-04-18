package cop.cs.shop.data;

/**
 * @author Oleg Cherednik
 * @since 18.04.2013
 */
public final class PriceKey {
	private final int department;
	private final int number;

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
