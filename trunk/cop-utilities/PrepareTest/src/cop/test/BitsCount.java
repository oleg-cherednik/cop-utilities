package cop.test;

public class BitsCount {
	public static void main(String... args) {
		int value = 5;
		System.out.println(value + " - " + count(value));
	}

	private static int count(int value) {
		int total = 0;

		for (int mask = 0x80000000; mask != 0; mask >>>= 1)
			if ((value & mask) != 0)
				total++;

		return total;
	}
}
