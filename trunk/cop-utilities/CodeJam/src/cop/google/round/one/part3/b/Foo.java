package cop.google.round.one.part3.b;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Foo {
	private int r;
	private int t;
	private int total;

	public static void main(String[] args) {
		try {
			new Foo().run("test");
			// new Booleye().run("small");
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

		r = Integer.parseInt(str[0]);
		t = Integer.parseInt(str[1]);
	}

	private void buildSet() throws Exception {
		total = 0;
		int sum = 0;
		int i = 1;

		while (true) {
			int curr = (r + 1) * (r + 1) - r * r;

			i++;
			sum += curr;
			r += 2;

			if (sum < t)
				continue;
			total = (sum == t) ? (i - 1) : (i - 2);
			return;
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
