package cop.google.c;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FairAndSquare {
	private static final Set<Double> FAIR = new HashSet<>();
	private static final Set<Double> FAIR_AND_SQUARE = Collections.synchronizedSet(new TreeSet<Double>());

	private final Set<BigDecimal> SET = Collections.synchronizedSet(new TreeSet<BigDecimal>());

	private final int[] FAS = new int[50];

	private String minStr;
	private String maxStr;
	private int total;

	public static void main(String[] args) {
		try {
			new FairAndSquare().run("test");
			// new FairAndSquare().run("small");
			// new FairAndSquare().run("large");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static int reverse(int val) {
		int reverse = 0;
		int remainder = 0;

		do {
			remainder = val % 10;
			reverse = reverse * 10 + remainder;
			val = val / 10;

		} while (val > 0);

		return reverse;
	}

	private void prepare() {
		char[] arr;

		for (int i = 0; i <= 9; i++) {
			arr = ("" + i).toCharArray();
			double val = i;

			FAIR.add(val);

			if (isSquare(arr, val)) {
				FAIR_AND_SQUARE.add(val);
				FAS[0]++;
			}
		}
	}

	private void foo() throws InterruptedException, ExecutionException {
		ExecutorService pool = Executors.newFixedThreadPool(20);

		final BigDecimal inc = new BigDecimal("10000000000");
		final BigDecimal limit = new BigDecimal("100000000000000000");
		BigDecimal min = BigDecimal.ZERO;
		BigDecimal max = min.add(inc);

		int total = 0;
		long ms = System.currentTimeMillis();

		while (max.compareTo(limit) <= 0) {
			total++;
			pool.submit(new FindFairAndSquareTask(min, max));
			min = max.add(BigDecimal.ONE);
			max = max.add(inc);

			total++;
//			System.out.println("Created " + total + ", founded: " + SET.size());
		};

		int i = 1;

		int a = 0;
		a++;
		
		System.out.println("Time: " + (System.currentTimeMillis() - ms));

		// String str;
		// BigDecimal power;
		//
		// for (int digits = 1; digits <= 50; digits++) {
		// System.out.println("digits: " + digits);
		//
		// min = new BigDecimal(Math.pow(10, digits == 1 ? 0 : (digits / 2 - 1)));
		// max = new BigDecimal(Math.pow(10, digits == 1 ? 1 : (digits / 2)) - 1);
		//
		// if ((digits & 0x1) != 1) {
		// for (BigDecimal i = min; i.compareTo(max) <= 0; i = i.add(BigDecimal.ONE)) {
		// str = getFair(i.toString());
		// power = new BigDecimal(str).pow(2);
		//
		// if (isFair(power.toEngineeringString()))
		// SET.add(power);
		// // System.out.println(power);
		// }
		// } else {
		// if (digits == 1) {
		// for (int i = 0; i <= 9; i++) {
		// int val2 = i * i;
		//
		// if (isFair(Integer.toString(val2)))
		// SET.add(new BigDecimal(val2));
		// // System.out.println(val2);
		// }
		// } else {
		// for (BigDecimal i = min; i.compareTo(max) <= 0; i = i.add(BigDecimal.ONE)) {
		// for (int j = 0; j <= 9; j++) {
		// str = getFair(i.toString(), j);
		// power = new BigDecimal(str).pow(2);
		//
		// if (isFair(power.toEngineeringString()))
		// SET.add(power);
		// // System.out.println(power);
		// }
		// }
		// }
		// }
		// }
	}

	private static String getFair(String str) {
		if (str.length() <= 0)
			return str;
		return str + new StringBuilder(str.length() * 2).append(str).reverse();

	}

	private static String getFair(String str, int mid) {
		if (str.length() <= 0)
			return str;
		return new StringBuilder(str.length() * 2).append(str).reverse().append(mid).append(str).toString();

	}

	private void calc() {
		int minDigit = Math.min(minStr.length(), maxStr.length());
		int maxDigit = Math.max(minStr.length(), maxStr.length());
		double min = Double.parseDouble(minStr);
		double max = Double.parseDouble(maxStr);

		if (minDigit == 1) {
			for (int i = 0; i <= 9; i++) {
				String str = "" + i;
				char[] arr = str.toCharArray();
				double val = Double.parseDouble(str);

				if (Double.compare(val, min) < 0 || Double.compare(val, max) > 0)
					continue;

				if (isSquare(arr, val))
					total++;
			}

			minDigit = maxDigit;
		}

		if (maxDigit == 1)
			return;

		for (int digits = minDigit; digits <= maxDigit; digits++) {
			int max1 = (int)Math.pow(10, digits / 2) - 1;

			if ((digits & 0x1) != 1) {
				for (int i = 1; i <= max1; i++) {
					String str = "" + reverse(i) + i;
					char[] arr = str.toCharArray();
					double val = Double.parseDouble(str);

					if (Double.compare(val, min) < 0 || Double.compare(val, max) > 0)
						continue;

					if (isSquare(arr, val))
						total++;
				}
			} else {
				for (int i = 1; i <= max1; i++) {
					for (int j = 0; j <= 9; j++) {
						String str = "" + reverse(i) + j + i;
						char[] arr = str.toCharArray();
						double val = Double.parseDouble(str);

						if (Double.compare(val, min) < 0 || Double.compare(val, max) > 0)
							continue;

						if (isSquare(arr, val))
							total++;
					}
				}
			}
		}
	}

	private void run(String fileName) throws Exception {
		foo();

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
		minStr = "10000000000000000000";// */str[0];
		maxStr = "100000000000000000000";// */str[1];
	}

	private void find() {
		calc();
	}

	private static boolean isSquare(char[] arr, double val) {
		return true;
		// if (arr[arr.length - 1] == '2')
		// return false;
		// if (arr[arr.length - 1] == '3')
		// return false;
		// if (arr[arr.length - 1] == '7')
		// return false;
		// if (arr[arr.length - 1] == '8')
		// return false;
		//
		// double res1 = Math.sqrt(val);
		// int res2 = (int)res1;
		//
		// if (!isFair(("" + res2).toCharArray()))
		// return false;
		//
		// return Double.compare(res1, res2) == 0;
	}

	private static boolean isFair(char[] arr) {
		if (arr.length == 1)
			return true;

		for (int i = 0, total = arr.length / 2; i < total; i++)
			if (arr[i] != arr[arr.length - 1 - i])
				return false;

		return true;
	}

	private static boolean isFair(String str) {
		if (str.length() == 1)
			return true;

		for (int i = 0, total = str.length() / 2; i < total; i++)
			if (str.charAt(i) != str.charAt(str.length() - 1 - i))
				return false;

		return true;
	}

	private static void write(BufferedWriter out, int total, int i) throws IOException {
		if (i > 0)
			out.write("\n");

		String str = "Case #" + (i + 1) + ": " + total;
		out.write(str);

		System.out.println(str);
	}

	private class FindFairAndSquareTask implements Runnable {
		private final BigDecimal min;
		private final BigDecimal max;

		private FindFairAndSquareTask(BigDecimal min, BigDecimal max) {
			this.min = min;
			this.max = max;
		}

		@Override
		public void run() {
			Set<BigDecimal> res = new HashSet<>();
			String str;
			BigDecimal power;
			int digits = min.toString().length();

			if ((digits & 0x1) != 1) {
				for (BigDecimal i = min; i.compareTo(max) <= 0; i = i.add(BigDecimal.ONE)) {
					str = getFair(i.toString());
					power = new BigDecimal(str).pow(2);

					if (isFair(power.toEngineeringString()))
						res.add(power);
					// System.out.println(power);
				}
			} else {
				if (digits == 1) {
					for (int i = 0; i <= 9; i++) {
						int val2 = i * i;

						if (isFair(Integer.toString(val2)))
							res.add(new BigDecimal(val2));
						// System.out.println(val2);
					}
				} else {
					for (BigDecimal i = min; i.compareTo(max) <= 0; i = i.add(BigDecimal.ONE)) {
						for (int j = 0; j <= 9; j++) {
							str = getFair(i.toString(), j);
							power = new BigDecimal(str).pow(2);

							if (isFair(power.toEngineeringString()))
								res.add(power);
							// System.out.println(power);
						}
					}
				}
			}

			if (!res.isEmpty())
				SET.addAll(res);
		}
	}
}
