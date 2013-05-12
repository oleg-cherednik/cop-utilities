package cop.google.foo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Foo {
	private final char[][] board = new char[4][4];
	private final int[] dataX = new int[10];
	private final int[] dataO = new int[10];

	private boolean usedT;
	private boolean complete;

	public static void main(String[] args) {
		try {
			new Foo().run("test");
//			new Foo().run("small");
//			new Foo().run("large");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void run(String fileName) throws Exception {
		BufferedReader in = null;
		BufferedWriter out = null;

		try {
			in = new BufferedReader(new FileReader(new File(fileName + ".in")));
			out = new BufferedWriter(new FileWriter(new File(fileName + ".out")));

			int T = Integer.parseInt(in.readLine().trim());

			for (int i = 0; i < T; i++) {
				if (i != 0) {
					in.read();
					in.read();
				}

				usedT = false;
				complete = true;

				readCase(in);
				buildSet();

				boolean winX = isWin(dataX);
				boolean winO = isWin(dataO);
				Status status = Status.parseStatus(winX, winO, complete);
				write(out, status, i);
			}
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (Exception e) {}

			try {
				if (out != null)
					out.close();
			} catch (Exception e) {}
		}
	}

	private void readCase(BufferedReader in) throws IOException {
		for (int i = 0; i < 4; i++) {
			in.read(board[i]);
			in.read();
			in.read();
		}
	}

	private void buildSet() throws Exception {
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				char ch = board[row][col];

				if (ch == 'T') {
					if (usedT)
						throw new Exception("T used more than once");

					set(dataX, row, col);
					set(dataO, row, col);
					usedT = true;
				} else if (ch == 'X') {
					set(dataX, row, col);
					clear(dataO, row, col);
				} else if (ch == 'O') {
					set(dataO, row, col);
					clear(dataX, row, col);
				} else if (ch == '.') {
					clear(dataX, row, col);
					clear(dataO, row, col);
					complete = false;
				} else
					throw new Exception("Unknown symbol: " + ch);
			}
		}
	}

	private static void set(int[] data, int row, int col) {
		data[row] |= 1 << col;
		data[4 + col] |= 1 << row;

		if (row == col)
			data[8] |= 1 << col;
		else if (Math.abs(col - 3) == row)
			data[9] |= 1 << row;
	}

	private static void clear(int[] data, int row, int col) {
		data[row] &= ~(1 << col);
		data[4 + col] &= ~(1 << row);

		if (row == col)
			data[8] &= ~(1 << col);
		else if (Math.abs(col - 3) == row)
			data[9] &= ~(1 << row);
	}

	private static boolean isWin(int[] data) {
		for (int val : data)
			if (val == 15)
				return true;
		return false;
	}

	private static void write(BufferedWriter out, Status status, int i) throws IOException {
		if (i > 0)
			out.write("\n");

		String str = "Case #" + (i + 1) + ": " + status.str;
		out.write(str);

		System.out.println(str);
	}
}

enum Status {
	WON_X("X won"),
	WON_O("O won"),
	DRAW("Draw"),
	NOT_COMPLETED("Game has not completed");

	final String str;

	Status(String str) {
		this.str = str;
	}

	public static Status parseStatus(boolean winX, boolean winO, boolean complete) {
		if (winX) {
			if (winO)
				return NOT_COMPLETED;
			return WON_X;
		}
		if (winO)
			return WON_O;
		if (complete)
			return DRAW;
		return NOT_COMPLETED;
	}
}
