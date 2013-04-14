package cop.google.c;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class FairAndSquareLarge1 {
	private final Set<BigDecimal> set = new HashSet<>();
	private String strMin;
	private String strMax;
	private BigDecimal min;
	private BigDecimal max;
	private int total;

	public static void main(String[] args) {
		foo();
		try {
			// new FairAndSquareLarge1().run("test");
			new FairAndSquareLarge1().run("small");
			// new FairAndSquareLarge1().run("large");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void foo() {
		// 012345678901234567890
		String str = "111111111152222222222";
		double ii = Double.parseDouble(str.substring(str.length() / 2 + 1, str.length()));
		int jj = Integer.parseInt(str.substring(str.length() / 2, str.length() / 2 + 1));

		int a = 0;
		a++;
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
		strMin = "999999999999999999999";// str[0];
		strMax = "10000000000000000000000000";// str[1];
		min = new BigDecimal(strMin);
		max = new BigDecimal(strMax);
	}

	private static String reverse(BigDecimal val) {
		return new StringBuilder(val.toPlainString()).reverse().toString();
	}

	private void find() {
		// getPeriods();
		BigDecimal val = min;

		for (; val.compareTo(max) <= 0; val = val.add(BigDecimal.ONE)) {
			if (set.contains(val))
				total++;
			else {
				if (!isFair(val))
					continue;

				break;
			}
		}

		// if (isSquare(val)) {
		// set.add(val);
		// total++;
		// }

		int minDigit = Math.min(val.precision(), max.precision());
		int maxDigit = Math.max(val.precision(), max.precision());

		for (int digits = minDigit; digits <= maxDigit; digits++) {
			BigDecimal max1 = new BigDecimal(Math.pow(10, digits / 2) - 1);
			String str = val != null ? val.toString() : null;

			if ((digits & 0x1) != 1) {
				BigDecimal ii = str != null ? new BigDecimal(str.substring(str.length() / 2, str.length())) : BigDecimal.ONE;

				for (BigDecimal i = ii; i.compareTo(max1) <= 0; i = i.add(BigDecimal.ONE)) {
					String str1 = "" + reverse(i) + i;
					val = new BigDecimal(str1);

					if (isSquare(val))
						total++;
				}
			} else {
				BigDecimal ii = str != null ? new BigDecimal(str.substring(str.length() / 2 + 1, str.length())) : BigDecimal.ONE;
				int jj = str != null ? Integer.parseInt(str.substring(str.length() / 2, str.length() / 2 + 1)) : 0;

				for (BigDecimal i = ii; i.compareTo(max1) <= 0; i = i.add(BigDecimal.ONE)) {
					for (int j = jj; j <= 9; j++) {
						String str1 = "" + reverse(i) + j + i;
						val = new BigDecimal(str1);

						if (val.compareTo(max) > 0)
							return;

						if (isSquare(val))
							total++;
					}
				}
			}
			
			val = null;
		}

	}

	// private double[] getPeriods() {
	// double diff = max - min;
	// int minScale = (int)(min / 500);
	// int maxScale = (int)(max / 500);
	//
	// double mainVal = Math.pow(10, strMin.length()) - 1;
	// double maxVal = Math.pow(10, strMax.length()) - 1;
	//
	// return null;
	// }

	private static boolean isFair(BigDecimal val) {
		char[] arr = val.toString().toCharArray();

		for (int i = 0, total = arr.length / 2; i < total; i++)
			if (arr[i] != arr[arr.length - 1 - i])
				return false;

		return true;
	}

	private static boolean isSquare(BigDecimal val) {
		double res1 = Math.sqrt(val.doubleValue());
		BigDecimal res2 = new BigDecimal(res1);
		return !res2.toString().contains(".") && isFair(res2);
	}

	private static void write(BufferedWriter out, int total, int i) throws IOException {
		if (i > 0)
			out.write("\n");

		String str = "Case #" + (i + 1) + ": " + total;
		out.write(str);

		System.out.println(str);
	}
}
