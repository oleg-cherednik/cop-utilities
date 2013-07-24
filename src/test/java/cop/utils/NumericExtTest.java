/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 *
 * $Id$
 * $HeadURL$
 */
package cop.utils;

import org.junit.Test;

import static cop.utils.NumericExt.getValueInNewRange;
import static org.junit.Assert.assertEquals;

/** @author <a href="mailto:abba-bestl@mail.ru">Cherednik, Oleg</a> */
public class NumericExtTest {
	@Test
	public void testGetValueInNewRangeInt() {
		assertEquals(getValueInNewRange(50, 0, 100, 0, 20), 10);
		assertEquals(getValueInNewRange(0, -100, 100, -20, 20), 0);
		assertEquals(getValueInNewRange(-75, -100, -50, 0, 20), 10);
	}
}
