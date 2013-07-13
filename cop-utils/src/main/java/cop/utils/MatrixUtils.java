/*
 * $Id$
 * $HeadURL$
 */
package cop.utils;

import static java.lang.Math.max;

/** @author <a href="mailto:abba-bestl@mail.ru">Cherednik, Oleg</a> */
public final class MatrixUtils {
	private MatrixUtils() {
	}

	/**
	 * Create a new matrix that is transpose of the giving one.
	 *
	 * @param matrix matrix
	 * @return a new matrix that is transpose of the giving <b>matrix</b>
	 */
	public static int[][] transposeMatrix(int[][] matrix) {
		if (matrix == null)
			return null;

		int columns = 0;
		int rows = matrix.length;

		for (int[] row : matrix)
			columns = max(columns, row.length);

		int[][] arr = new int[columns][rows];

		for (int i = 0; i < columns; i++)
			for (int j = 0; j < rows; j++)
				arr[i][j] = matrix[j][i];

		return arr;
	}
}
