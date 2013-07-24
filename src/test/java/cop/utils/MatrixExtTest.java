/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.utils;

import static cop.utils.MatrixUtils.transposeMatrix;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

/**
 * @author <a href="mailto:abba-bestl@mail.ru">Cherednik, Oleg</a>
 */
public class MatrixExtTest {
	@Test
	public void testTransposeMatrix() {
		assertArrayEquals(transposeMatrix(null), null);
		assertArrayEquals(transposeMatrix(new int[0][0]), new int[0][0]);
		assertArrayEquals(transposeMatrix(createSquareMatrix()), createSquareTransposeMatrix());
		assertArrayEquals(transposeMatrix(createHRectangularMatrix()), createHRectangularTransposeMatrix());
		assertArrayEquals(transposeMatrix(createVRectangularMatrix()), createVRectangularTransposeMatrix());
	}

	private static int[][] createSquareMatrix() {
		int[][] matrix = new int[5][5];

		matrix[0] = new int[] { 0, 1, 2, 3, 4 };
		matrix[1] = new int[] { 5, 6, 7, 8, 9 };
		matrix[2] = new int[] { 10, 11, 12, 13, 14 };
		matrix[3] = new int[] { 15, 16, 17, 18, 19 };
		matrix[4] = new int[] { 20, 21, 22, 23, 24 };

		return matrix;
	}

	private static int[][] createSquareTransposeMatrix() {
		int[][] matrix = new int[5][5];

		matrix[0] = new int[] { 0, 5, 10, 15, 20 };
		matrix[1] = new int[] { 1, 6, 11, 16, 21 };
		matrix[2] = new int[] { 2, 7, 12, 17, 22 };
		matrix[3] = new int[] { 3, 8, 13, 18, 23 };
		matrix[4] = new int[] { 4, 9, 14, 19, 24 };

		return matrix;
	}

	private static int[][] createHRectangularMatrix() {
		int[][] matrix = new int[2][3];

		matrix[0] = new int[] { 0, 1, 2 };
		matrix[1] = new int[] { 3, 4, 5 };

		return matrix;
	}

	private static int[][] createHRectangularTransposeMatrix() {
		int[][] matrix = new int[3][2];

		matrix[0] = new int[] { 0, 3 };
		matrix[1] = new int[] { 1, 4 };
		matrix[2] = new int[] { 2, 5 };

		return matrix;
	}

	private static int[][] createVRectangularMatrix() {
		int[][] matrix = new int[4][3];

		matrix[0] = new int[] { 0, 1, 2 };
		matrix[1] = new int[] { 3, 4, 5 };
		matrix[2] = new int[] { 6, 7, 8 };
		matrix[3] = new int[] { 9, 10, 11 };

		return matrix;
	}

	private static int[][] createVRectangularTransposeMatrix() {
		int[][] matrix = new int[3][4];

		matrix[0] = new int[] { 0, 3, 6, 9 };
		matrix[1] = new int[] { 1, 4, 7, 10 };
		matrix[2] = new int[] { 2, 5, 8, 11 };

		return matrix;
	}
}
