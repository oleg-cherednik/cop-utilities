package cop.google;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

public class Booleye {
	private static final BigDecimal TWO = new BigDecimal(2);
	private static final BigDecimal FOUR = new BigDecimal(4);
	private static final BigDecimal EIGHT = new BigDecimal(8);
	private static final BigDecimal SIXTEEN = new BigDecimal(16);

	private BigDecimal r;
	private BigDecimal t;
	private long total;

	public static void main(String[] args) {
		try {
			// new Booleye().run("test");
			// new Booleye().run("small");
			new Booleye().run("large");
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

		r = new BigDecimal(str[0]);
		t = new BigDecimal(str[1]);
	}

	private void buildSet() throws Exception {
		BigDecimal a1 = r.add(BigDecimal.ONE).pow(2).subtract(r.pow(2)); // a1
		BigDecimal b = TWO.multiply(a1).subtract(FOUR).negate(); // b
		BigDecimal c = TWO.multiply(t).negate(); // c

		BigDecimal bb = b.multiply(b); // b * b
		BigDecimal ac = SIXTEEN.multiply(c);
		BigDecimal sqrt = new BigDecimal(Math.sqrt(bb.subtract(ac).doubleValue()));

		BigDecimal x1 = b.add(sqrt).divide(EIGHT);
		BigDecimal x2 = b.subtract(sqrt).divide(EIGHT);

		long xx1 = x1.longValue();
		long xx2 = x2.longValue();

		if (xx1 <= 0 && xx2 <= 0)
			throw new Exception();

		total = Math.max(xx1, xx2);
	}

	private static void write(BufferedWriter out, long total, int i) throws IOException {
		if (i > 0)
			out.write("\n");

		String str = "Case #" + (i + 1) + ": " + total;
		out.write(str);

		System.out.println(str);
	}
}
