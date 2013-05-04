package cop.test;

/**
 * Return higher nearest power of 2
 * 
 * @author Oleg Cherednik
 * @since 18.02.2013
 */
public class NearestPow2 {
	public static void main(String... args) {
		for (int i = 0; i < 20; i++)
			System.out.println(i + " - " + foo(i));
	}

	private static int foo(int val) {
		if (val == 0)
			return 0x1;

		int mask = 0x80000000;

		for (; mask != 0; mask >>>= 1)
			if ((val & mask) != 0)
				break;

		return mask >= val ? mask : mask << 1;
	}
}
