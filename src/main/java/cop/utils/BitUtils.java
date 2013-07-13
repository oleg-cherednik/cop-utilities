/*
 * $Id$
 * $HeadURL$
 */
package cop.utils;

/**
 * Class provides different methods to work with bits.
 *
 * @author Oleg Cherednik, Oleg
 * @since 16.08.2010
 */
public final class BitUtils {
	public static final int BIT0 = 0x1;
	public static final int BIT1 = 0x2;
	public static final int BIT2 = 0x4;
	public static final int BIT3 = 0x8;
	public static final int BIT4 = 0x10;
	public static final int BIT5 = 0x20;
	public static final int BIT6 = 0x40;
	public static final int BIT7 = 0x80;

	public static final int BIT8 = 0x100;
	public static final int BIT9 = 0x200;
	public static final int BIT10 = 0x400;
	public static final int BIT11 = 0x800;
	public static final int BIT12 = 0x1000;
	public static final int BIT13 = 0x2000;
	public static final int BIT14 = 0x4000;
	public static final int BIT15 = 0x8000;

	private static final int[] TMP_LOWEST = { 0xFFFF, 0x00FF, 0x000F, 0x0003, 0x0001 };
	private static final int[] TMP_HIGHEST = { 0xFFFF0000, 0x0000FF00, 0x000000F0, 0x0000000C, 0x00000002 };

	private static final int G31 = 0x49249249; // 0100_1001_0010_0100_1001_0010_0100_1001
	private static final int G32 = 0x381c0e07; // 0011_1000_0001_1100_0000_1110_0000_0111

	private BitUtils() {
	}

	/**
	 * Checks if all bits of giving bit set are set or not
	 *
	 * @param val  checked val
	 * @param bits checked bit or bit set
	 * @return <code>true</code> if all selected bit(s) are set
	 */
	public static boolean isBitSet(int val, int bits) {
		return (val & bits) == bits;
	}

	/**
	 * Checks if any bits of giving bit set are set or not
	 *
	 * @param val  checked val
	 * @param bits checked bit or bit set
	 * @return <code>true</code> if any of giving bit(s) are set
	 */
	public static boolean isAnyBitSet(int val, int bits) {
		return (val & bits) != 0;
	}

	/**
	 * Checks if all bits of giving bit set are clear or not
	 *
	 * @param val  checked val
	 * @param bits checked bit or bit set
	 * @return <code>true</code> if all selected bit(s) are clear
	 */
	public static boolean isBitClear(int val, int bits) {
		return (val & bits) == 0;
	}

	/**
	 * Checks if any bits of giving bit set are clear or not
	 *
	 * @param val  checked val
	 * @param bits checked bit or bit set
	 * @return <code>true</code> if any selected bit(s) are clear
	 */
	public static boolean isAnyBitClear(int val, int bits) {
		return (~val & bits) != 0;
	}

	/**
	 * Set selected bit(s) in giving val
	 *
	 * @param val  val
	 * @param bits bit or bit set to set in the val
	 * @return <code>val</code> with set selected bits
	 */
	public static int setBits(int val, int bits) {
		return val | bits;
	}

	/**
	 * Clear selected bit(s) in giving val
	 *
	 * @param val  val
	 * @param bits bit or bit set to clear in the val
	 * @return <code>val</code> with cleared selected bits
	 */
	public static int clearBits(int val, int bits) {
		return val & ~bits;
	}

	/**
	 * Get lowest set bit number of the given val
	 *
	 * @param val val
	 * @return number of the lowest set bit
	 */
	public static int getLowestSetBitNumber(int val) {
		int k = 0;

		for (int i = 0, j = 16; i < 5; i++, j /= 2) {
			if ((val & TMP_LOWEST[i]) != 0)
				continue;

			k += j;
			val >>= j;
		}

		return k;
	}

	/**
	 * Get highest set bit number of the given val
	 *
	 * @param val val
	 * @return number of the highest set bit
	 */
	public static int getHighestSetBitNumber(int val) {
		int k = 0;

		for (int i = 0, j = 16; i < 5; i++, j /= 2) {
			if ((val & TMP_HIGHEST[i]) == 0)
				continue;

			k += j;
			val >>= j;
		}

		return k;
	}

	/**
	 * Get amount of set bits of the giving val.<br>
	 * Performance: log2(val)
	 *
	 * @param val val
	 * @return amount of the set bits
	 */
	public static int getSetBitAmount(int val) {
		val = (val & G31) + ((val >> 1) & G31) + ((val >> 2) & G31);
		val = ((val + (val >> 3)) & G32) + ((val >> 6) & G32);

		return (val + (val >> 9) + (val >> 18) + (val >> 27)) & 0x3f;
	}

	/**
	 * Returns lowest set bit of the giving value
	 *
	 * @param value value
	 * @return lowest set bit
	 */
	public static int getLowestSetBit(int value) {
		return ~(value - 1) & value;
	}

	/**
	 * Clears lowest set bit in giving val
	 *
	 * @param val val
	 * @return <code>val</code> where lowest set bit is cleared
	 */
	public static int clearLowestSetBit(int val) {
		return (val - 1) & val;
	}

	/**
	 * Checks if giving value is power of 2 or not
	 *
	 * @param val value
	 * @return <code>true</code> if giving value is power of 2
	 */
	public static boolean isPowerOfTwo(int val) {
		return ((val - 1) & val) == 0;
	}

	public static long reverse(long val) {
		val = ((val & 0xaaaaaaaa) >> 1) | ((val & 0x55555555) << 1);
		val = ((val & 0xcccccccc) >> 2) | ((val & 0x33333333) << 2);
		val = ((val & 0xf0f0f0f0) >> 4) | ((val & 0x0f0f0f0f) << 4);
		val = ((val & 0xff00ff00) >> 8) | ((val & 0x00ff00ff) << 8);
		return (val >> 16) | (val << 16);
	}

	public boolean isSimple(long val) {
		for (int i = 2, sqrt = (int)Math.sqrt(val); i <= sqrt; i++)
			if (val % i == 0)
				return false;

		return true;
	}
}
