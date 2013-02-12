package cop.test;

/**
 * Create queue using 2 stacks
 * 
 * @author Oleg Cherednik
 * @since 12.02.2013
 */
public class CycleArrayShift {
	public static void main(String[] args) {
		final int[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		print(arr);
		shift(arr, 3);
		print(arr);
	}

	private static void print(int[] arr) {
		for (int val : arr)
			System.out.print(" " + val);

		System.out.println();
	}

	private static void shift(int[] arr, int shift) {
		reverce(arr, 0, arr.length - 1);
		reverce(arr, 0, arr.length - shift - 1);
		reverce(arr, arr.length - shift, arr.length - 1);
	}

	private static void reverce(int[] arr, int i, int j) {
		int tmp;

		for (; i < j; i++, j--) {
			tmp = arr[i];
			arr[i] = arr[j];
			arr[j] = tmp;
		}
	}

}
