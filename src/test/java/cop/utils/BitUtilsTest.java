/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.utils;

import static cop.utils.BitUtils.BIT0;
import static cop.utils.BitUtils.BIT1;
import static cop.utils.BitUtils.BIT2;
import static cop.utils.BitUtils.BIT3;
import static cop.utils.BitUtils.BIT4;
import static cop.utils.BitUtils.clearBits;
import static cop.utils.BitUtils.clearLowestSetBit;
import static cop.utils.BitUtils.getHighestSetBitNumber;
import static cop.utils.BitUtils.getLowestSetBit;
import static cop.utils.BitUtils.getLowestSetBitNumber;
import static cop.utils.BitUtils.getSetBitAmount;
import static cop.utils.BitUtils.isAnyBitClear;
import static cop.utils.BitUtils.isAnyBitSet;
import static cop.utils.BitUtils.isBitClear;
import static cop.utils.BitUtils.isBitSet;
import static cop.utils.BitUtils.isPowerOfTwo;
import static cop.utils.BitUtils.setBits;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author <a href="mailto:abba-bestl@mail.ru">Cherednik, Oleg</a>
 */
public class BitUtilsTest {
	@Test
	public void testIsBitSet() {
		assertTrue(isBitSet(BIT1, BIT1));
		assertTrue(isBitSet(BIT3, BIT3));
		assertTrue(isBitSet(BIT1 | BIT3, BIT1 | BIT3));

		assertFalse(isBitSet(BIT1 | BIT3, BIT0));
		assertFalse(isBitSet(BIT1 | BIT3, BIT2));
		assertFalse(isBitSet(BIT1 | BIT3, BIT2 | BIT4));
		assertFalse(isBitSet(BIT1 | BIT3, BIT1 | BIT2));
	}

	@Test
	public void testIsAnyBitSet() {
		assertTrue(isAnyBitSet(BIT1, BIT1));
		assertTrue(isAnyBitSet(BIT3, BIT3));
		assertTrue(isAnyBitSet(BIT1 | BIT3, BIT1 | BIT3));
		assertTrue(isAnyBitSet(BIT1 | BIT3, BIT1 | BIT2));

		assertFalse(isAnyBitSet(BIT1 | BIT3, BIT0));
		assertFalse(isAnyBitSet(BIT1 | BIT3, BIT2));
		assertFalse(isAnyBitSet(BIT1 | BIT3, BIT0 | BIT2));
	}

	@Test
	public void testIsBitClear() {
		assertTrue(isBitClear(BIT1, BIT0));
		assertTrue(isBitClear(BIT1 | BIT3, BIT2));
		assertTrue(isBitClear(BIT1 | BIT3, BIT0 | BIT2));

		assertFalse(isBitClear(BIT1 | BIT3, BIT1));
		assertFalse(isBitClear(BIT1 | BIT3, BIT1 | BIT3));
		assertFalse(isBitClear(BIT1 | BIT3, BIT1 | BIT2));
	}

	@Test
	public void testIsAnyBitClear() {
		assertTrue(isAnyBitClear(BIT1, BIT0));
		assertTrue(isAnyBitClear(BIT1 | BIT3, BIT2));
		assertTrue(isAnyBitClear(BIT1 | BIT3, BIT0 | BIT2));
		assertTrue(isAnyBitClear(BIT1 | BIT3, BIT0 | BIT1));

		assertFalse(isAnyBitClear(BIT1 | BIT3, BIT1));
		assertFalse(isAnyBitClear(BIT1 | BIT3, BIT1 | BIT3));
	}

	@Test
	public void testSetBits() {
		assertEquals(32 | BIT1, setBits(32, BIT1));
		assertEquals(32 | BIT1 | BIT2, setBits(32, BIT1 | BIT2));
	}

	@Test
	public void testClearBits() {
		assertEquals(45, clearBits(45, BIT1));
		assertEquals(44, clearBits(45, BIT0));
		assertEquals(33, clearBits(45, BIT2 | BIT3));
	}

	@Test
	public void testGetLowestSetBitNumber() {
		assertEquals(0, getLowestSetBitNumber(725));
		assertEquals(1, getLowestSetBitNumber(298));
		assertEquals(2, getLowestSetBitNumber(116));
		assertEquals(3, getLowestSetBitNumber(296));
	}

	@Test
	public void testGetHighestSetBitNumber() {
		assertEquals(9, getHighestSetBitNumber(725));
		assertEquals(8, getHighestSetBitNumber(298));
		assertEquals(6, getHighestSetBitNumber(116));
		assertEquals(8, getHighestSetBitNumber(296));
	}

	@Test
	public void testGetSetBitAmount() {
		assertEquals(6, getSetBitAmount(725));
		assertEquals(4, getSetBitAmount(298));
		assertEquals(4, getSetBitAmount(116));
		assertEquals(3, getSetBitAmount(296));
	}

	@Test
	public void testGetLowestSetBit() {
		assertEquals(BIT0, getLowestSetBit(725));
		assertEquals(BIT1, getLowestSetBit(298));
		assertEquals(BIT2, getLowestSetBit(116));
		assertEquals(BIT3, getLowestSetBit(296));
	}

	@Test
	public void testClearLowestSetBit() {
		assertEquals(clearBits(725, getLowestSetBit(725)), clearLowestSetBit(725));
		assertEquals(clearBits(298, getLowestSetBit(298)), clearLowestSetBit(298));
		assertEquals(clearBits(116, getLowestSetBit(116)), clearLowestSetBit(116));
		assertEquals(clearBits(296, getLowestSetBit(296)), clearLowestSetBit(296));
	}

	@Test
	public void testIsPowerOf2() {
		assertTrue(isPowerOfTwo(2));
		assertTrue(isPowerOfTwo(4));
		assertTrue(isPowerOfTwo(8));
		assertTrue(isPowerOfTwo(16));
		assertTrue(isPowerOfTwo(32));

		assertFalse(isPowerOfTwo(725));
		assertFalse(isPowerOfTwo(100));
		assertFalse(isPowerOfTwo(60));
	}
}
