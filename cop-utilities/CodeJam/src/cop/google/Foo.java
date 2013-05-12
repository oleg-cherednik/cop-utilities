package cop.google;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Foo {
	private int r;
	private int n;
	private int m;
	private int[] kk;
	private int[] kkk;
	private int max;
	private String res;

	public static void main(String[] args) {
		try {
			// new Foo().run("test");
			new Foo().run("small");
			// new Foo().run("large");
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

			Integer.parseInt(in.readLine().trim());
			String[] str = in.readLine().split(" ");

			r = Integer.parseInt(str[0]);
			n = Integer.parseInt(str[1]);
			m = Integer.parseInt(str[2]);
			tmp = new int[20];
			kk = new int[Integer.parseInt(str[3])];
			kkk = new int[kk.length];

			out.write("Case #1:");

			for (int i = 0; i < r; i++) {
				readCase(in);
				buildSet();
				write(out, res);
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

	private static boolean contains(int[] arr, int value) {
		for (int val : arr)
			if (val == value)
				return true;
		return false;
	}

	private static boolean div(int[] arr, int value) {
		for (int val : arr)
			if (val % value != 0)
				return false;
		return true;
	}

	private void readCase(BufferedReader in) throws IOException {
		String[] str = in.readLine().split(" ");

		max = 0;

		for (int i = 0, j = 0; i < str.length; i++) {
			kk[i] = Integer.parseInt(str[i]);

			if (!contains(kkk, kk[i]))
				kkk[j++] = kk[i];

			max = Math.max(max, kk[i]);
		}

		Arrays.sort(kk);
		Arrays.sort(kkk);
	}

	private int min(int val) {
		for (int i = val; i > 1; i--)
			if (i <= m && val % i == 0)
				return i;
		return val;
	}

	private int[] tmp;

	private int[] split(int val) {
		Arrays.fill(tmp, 0);

		for (int i = 2, j = 0; i <= val;) {
			if (val % i == 0) {
				tmp[j++] = i;
				val /= i;
			} else
				i++;
		}

		return tmp;
	}

	private void buildSet() throws Exception {
		int j = 0;
		int[] arr = new int[n];

		for (int i = 0; i < kk.length; i++) {
			int min = min(kk[i]);
			if (min != 1 && !contains(arr, min))
				arr[j++] = min;
		}

		if (j < 3) {
			int[] tt = split(max);
			
			for (int i = 0; i < kk.length; i++) {
				if (kk[i] == 1 || contains(arr, kk[i]) || div(arr, kk[i]))
					continue;

				

				int a = 0;
				a++;
				// int d =

				int min = min(kk[i]);
				if (min != 1 && !contains(arr, min))
					arr[j++] = min;
			}
		}

		int v1 = arr[0];
		int v2 = arr[1];
		int v3 = arr[2];

		if (v1 == 0 || v2 == 0 || v3 == 0) {
			for (int k : kk) {
				if (k == 1)
					continue;
				int i = 0;
				if (v1 != 0 && (i = k % v1) == 0)
					continue;
				if (v2 != 0 && (i = k % v2) == 0)
					continue;
				if (v3 != 0 && (i = k % v3) == 0)
					continue;

				v3 = i;
				break;
			}
		}

		v3 = v3 == 0 ? v1 : v3;

		if (v1 == 0 && v2 == 0 && v3 == 0)
			v1 = v2 = v3 = 2;

		test(v1, v2, v3);

		res = Integer.toString(v1) + Integer.toString(v2) + Integer.toString(v3);
	}

	private static int nod(int val1, int val2) {
		int n = val1 % val2;

		val1 = val2;
		val1 = n;

		return (n > 0) ? nod(val1, val2) : val1;
	}

	private void test(int v1, int v2, int v3) throws Exception {
		test(v1);
		test(v2);
		test(v3);

		for (int v : kk) {
			if (v == v1 || v == v2 || v == v3)
				continue;
			if (v == v1 * v2 || v == v1 * v3 || v == v1 * v2 * v3 || v == v2 * v3)
				continue;
			throw new Exception(Arrays.deepToString(Arrays.asList(kk).toArray()));
		}
	}

	private void test(int val) throws Exception {
		if (val < 2 || val > m)
			throw new Exception();
	}

	private static void write(BufferedWriter out, String str) throws IOException {
		out.write("\n");
		out.write(str);
		System.out.println(str);
	}
}
