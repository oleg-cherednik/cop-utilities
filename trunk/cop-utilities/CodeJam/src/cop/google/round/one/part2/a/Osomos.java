package cop.google.round.one.part2.a;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Osomos {
	private int total;
	private int A;
	private int N[];

	public static void main(String[] args) {
		try {
			// new Osomos().run("test");
			new Osomos().run("small");
			// new Booleye().run("large");
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
				readCase(in);
				buildSet();
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

	private void readCase(BufferedReader in) throws IOException {
		String[] str = in.readLine().split(" ");

		A = Integer.parseInt(str[0]);
		N = new int[Integer.parseInt(str[1])];

		int i = 0;

		for (String val : in.readLine().split(" "))
			N[i++] = Integer.parseInt(val);
	}

	private void buildSet() throws Exception {
		total = 0;

		Arrays.sort(N);

		int arr[][] = new int[3][N.length + 1];

		arr[1][0] = A;

		for (int i = 1; i < N.length + 1; i++) {
			arr[0][i] = N[i - 1];
			arr[1][i] = arr[0][i] + arr[1][i - 1];
			arr[2][i] = 2 * arr[1][i - 1] - arr[1][i] - 1;
		}

		int curr = 1;

		if (arr[0][1] >= arr[1][0]) {
			for (; curr < N.length + 1; curr++) {
				if (arr[0][curr] < arr[1][0])
					break;

				if (curr < N.length && arr[1][0] > 1) {
					if (arr[1][0] + arr[1][0] - 1 > arr[0][curr + 1]) {
						if (arr[0][curr + 1] >= arr[1][0]) {
							arr[0][curr] = arr[1][0] - 1;
							total += 2;
						}
						break;
					}
				}
			}

		}

		total += curr - 1;

		if (total == N.length)
			return;

		if (curr > 1)
			arr[1][curr - 1] = arr[1][0];

		for (; curr < N.length + 1; curr++) {
			arr[1][curr] = arr[0][curr] + arr[1][curr - 1];
			arr[2][curr] = 2 * arr[1][curr - 1] - arr[1][curr] - 1;
			if (arr[2][curr] < 0)
				total++;
		}
	}

	private static void write(BufferedWriter out, int total, int i) throws IOException {
		if (i > 0)
			out.write("\n");

		String str = "Case #" + (i + 1) + ": " + total;
		out.write(str);

		System.out.println(str);
	}
}
