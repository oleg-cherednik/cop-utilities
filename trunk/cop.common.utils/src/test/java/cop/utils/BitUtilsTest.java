package cop.utils;

import static cop.utils.BitUtils.BIT1;
import static cop.utils.BitUtils.BIT2;
import static cop.utils.BitUtils.BIT3;
import static cop.utils.BitUtils.BIT31;
import static cop.utils.BitUtils.BIT4;
import static cop.utils.BitUtils.BIT5;
import static cop.utils.BitUtils.clearBits;
import static cop.utils.BitUtils.clearLowestSetBit;
import static cop.utils.BitUtils.getHighestSetBitPosition;
import static cop.utils.BitUtils.getLowestSetBit;
import static cop.utils.BitUtils.getLowestSetBitPosition;
import static cop.utils.BitUtils.getSetBitsAmount;
import static cop.utils.BitUtils.isAnyBitsClear;
import static cop.utils.BitUtils.isAnyBitsSet;
import static cop.utils.BitUtils.isBitsClear;
import static cop.utils.BitUtils.isBitsSet;
import static cop.utils.BitUtils.isPower2;
import static cop.utils.BitUtils.setBits;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Class provides different methods to work with bits.
 * 
 * @author <a href="mailto:abba-best@mail.ru">Oleg Cherednik</a>
 * @since 17.11.2012
 */
public class BitUtilsTest {
	@Test
	@SuppressWarnings("static-method")
	public void testIsBitsSet() {
		assertTrue(isBitsSet(BIT3 | BIT5, BIT3));
		assertTrue(isBitsSet(BIT3 | BIT5, BIT3 | BIT5));
		assertFalse(isBitsSet(BIT3 | BIT5, BIT2));
		assertFalse(isBitsSet(BIT3 | BIT5, BIT3 | BIT4));
	}

	@Test
	@SuppressWarnings("static-method")
	public void testIsAnyBitsSet() {
		assertTrue(isAnyBitsSet(BIT3 | BIT5, BIT3));
		assertTrue(isAnyBitsSet(BIT3 | BIT5, BIT3 | BIT4));
		assertFalse(isAnyBitsSet(BIT3 | BIT5, BIT2));
		assertFalse(isAnyBitsSet(BIT3 | BIT5, BIT2 | BIT4));
	}

	@Test
	@SuppressWarnings("static-method")
	public void testIsBitsClear() {
		assertTrue(isBitsClear(BIT3 | BIT5, BIT2));
		assertTrue(isBitsClear(BIT3 | BIT5, BIT2 | BIT4));
		assertFalse(isBitsClear(BIT3 | BIT5, BIT3));
		assertFalse(isBitsClear(BIT3 | BIT5, BIT2 | BIT5));
	}

	@Test
	@SuppressWarnings("static-method")
	public void testIsAnyBitsClear() {
		assertTrue(isAnyBitsClear(BIT3 | BIT5, BIT2));
		assertTrue(isAnyBitsClear(BIT3 | BIT5, BIT2 | BIT3));
		assertFalse(isAnyBitsClear(BIT3 | BIT5, BIT3));
		assertFalse(isAnyBitsClear(BIT3 | BIT5, BIT3 | BIT5));
	}

	@Test
	@SuppressWarnings("static-method")
	public void testSetBits() {
		assertEquals(BIT3, setBits(BIT3, BIT3));
		assertEquals(BIT3 | BIT4, setBits(BIT3, BIT4));
	}

	@Test
	@SuppressWarnings("static-method")
	public void testClearBits() {
		assertEquals(0, clearBits(BIT3, BIT3));
		assertEquals(BIT3, clearBits(BIT3 | BIT4, BIT4));
	}

	@Test
	@SuppressWarnings("static-method")
	public void testGetLowestSetBitPosition() {
		assertEquals(-1, getLowestSetBitPosition(0));
		assertEquals(1, getLowestSetBitPosition(BIT1 | BIT3));
		assertEquals(3, getLowestSetBitPosition(BIT3 | BIT5));
		assertEquals(31, getLowestSetBitPosition(BIT31));
	}

	@Test
	@SuppressWarnings("static-method")
	public void testGetHighestSetBitPosition() {
		assertEquals(-1, getHighestSetBitPosition(0));
		assertEquals(3, getHighestSetBitPosition(BIT1 | BIT3));
		assertEquals(5, getHighestSetBitPosition(BIT3 | BIT5));
		assertEquals(31, getLowestSetBitPosition(BIT31));
	}

	@Test
	@SuppressWarnings("static-method")
	@Ignore
	public void testGetSetBitsAmount() {
		assertEquals(0, getSetBitsAmount(0));
		assertEquals(1, getSetBitsAmount(BIT3));
		assertEquals(2, getSetBitsAmount(BIT1 | BIT3));
		assertEquals(3, getSetBitsAmount(BIT1 | BIT3 | BIT5));
	}

	@Test
	@SuppressWarnings("static-method")
	public void testGetLowestSetBit() {
		assertEquals(0, getLowestSetBit(0));
		assertEquals(BIT1, getLowestSetBit(BIT1 | BIT3));
		assertEquals(BIT3, getLowestSetBit(BIT3 | BIT5));
	}

	@Test
	@SuppressWarnings("static-method")
	public void testClearLowestSetBit() {
		assertEquals(0, clearLowestSetBit(BIT3));
		assertEquals(BIT3, clearLowestSetBit(BIT1 | BIT3));
		assertEquals(BIT3 | BIT5, clearLowestSetBit(BIT1 | BIT3 | BIT5));
	}

	@Test
	@SuppressWarnings("static-method")
	public void testIsPower2() {
		assertTrue(isPower2(0));
		assertTrue(isPower2(1));
		assertTrue(isPower2(2));
		assertTrue(isPower2(4));
		assertTrue(isPower2(8));

		assertFalse(isPower2(7));
		assertFalse(isPower2(11));
	}
}
