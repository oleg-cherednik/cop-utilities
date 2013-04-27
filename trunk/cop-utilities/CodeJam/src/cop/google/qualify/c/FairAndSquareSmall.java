package cop.google.qualify.c;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FairAndSquareSmall {
	private int min;
	private int max;
	private int total;

	public static void main(String[] args) {
		try {
			// new FairAndSquare().run("test");
			new FairAndSquareSmall().run("small");
			// new FairAndSquare().run("large");
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
				total = 0;

				readCase(in);
				find();
				write(out, total, i);
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

	private void readCase(BufferedReader in) throws Exception {
		String[] str = in.readLine().trim().split(" ");
		min = Integer.parseInt(str[0]);
		max = Integer.parseInt(str[1]);

		if (min > max || min < 0 || max < 0)
			throw new Exception("Illegal numbers: min=" + min + ", max=" + max);
	}

	private void find() {
		for (int i = min; i <= max; i++) {
			boolean fair = isFair(i);
			boolean square = isSquare(i);

			if (fair && square) {
				total++;
				// System.out.println(i);
			}
		}
	}

	private static boolean isFair(int val) {
		char[] arr = Integer.toString(val).toCharArray();

		for (int i = 0, total = arr.length / 2; i < total; i++)
			if (arr[i] != arr[arr.length - 1 - i])
				return false;

		return true;
	}

	private static boolean isSquare(int val) {
		double res1 = Math.sqrt(val);
		int res2 = (int)res1;
		return isFair(res2) && Double.compare(res1, res2) == 0;
	}

	private static void write(BufferedWriter out, int total, int i) throws IOException {
		if (i > 0)
			out.write("\n");

		String str = "Case #" + (i + 1) + ": " + total;
		out.write(str);

		System.out.println(str);
	}
}
