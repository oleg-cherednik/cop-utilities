/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 *
 * $Id$
 * $HeadURL$
 */
package cop.utils;

import org.junit.Test;

import static cop.utils.ArrayUtils.invertArrayHorizontally;
import static cop.utils.ArrayUtils.invertArrayVertically;
import static org.junit.Assert.assertArrayEquals;

public class ArrayUtilsTest {
	private int[][] arr = createArray();

	@Test
	public void testInvertArrayVertically() {
		assertArrayEquals(invertArrayVertically(null), null);
		assertArrayEquals(invertArrayVertically(new int[0][0]), new int[0][0]);
		assertArrayEquals(invertArrayVertically(arr), createVerticalInvertArray());
	}

	@Test
	public void testInvertArrayHorizontally() {
		assertArrayEquals(invertArrayHorizontally(null), null);
		assertArrayEquals(invertArrayHorizontally(new int[0][0]), new int[0][0]);
		assertArrayEquals(invertArrayHorizontally(arr), createHorizontalInvertArray());
	}

	private static int[][] createArray() {
		int[][] matrix = new int[5][5];

		matrix[0] = new int[] { 0, 1, 2, 3, 4 };
		matrix[1] = new int[] { 5, 6, 7, 8, 9 };
		matrix[2] = new int[] { 10, 11, 12, 13, 14 };
		matrix[3] = new int[] { 15, 16, 17, 18, 19 };
		matrix[4] = new int[] { 20, 21, 22, 23, 24 };

		return matrix;
	}

	private static int[][] createVerticalInvertArray() {
		int[][] matrix = new int[5][5];

		matrix[0] = new int[] { 4, 3, 2, 1, 0 };
		matrix[1] = new int[] { 9, 8, 7, 6, 5 };
		matrix[2] = new int[] { 14, 13, 12, 11, 10 };
		matrix[3] = new int[] { 19, 18, 17, 16, 15 };
		matrix[4] = new int[] { 24, 23, 22, 21, 20 };

		return matrix;
	}

	private static int[][] createHorizontalInvertArray() {
		int[][] matrix = new int[5][5];

		matrix[0] = new int[] { 20, 21, 22, 23, 24 };
		matrix[1] = new int[] { 15, 16, 17, 18, 19 };
		matrix[2] = new int[] { 10, 11, 12, 13, 14 };
		matrix[3] = new int[] { 5, 6, 7, 8, 9 };
		matrix[4] = new int[] { 0, 1, 2, 3, 4 };

		return matrix;
	}
}
