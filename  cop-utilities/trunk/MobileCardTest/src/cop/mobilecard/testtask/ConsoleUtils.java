package cop.mobilecard.testtask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

/**
 * This class contains some methods to read smth. from console.
 * 
 * @author Oleg Cherednik
 * @since 27.01.2013
 */
final class ConsoleUtils {
	private static final ConsoleUtils INSTANCE = new ConsoleUtils();

	public static ConsoleUtils getInstance() {
		return INSTANCE;
	}

	private ConsoleUtils() {}

	/**
	 * Read one line from console.
	 * 
	 * @param emptyAllowed if <tt>true</tt> then empty line is allowed
	 * @return not <tt>null</tt> string
	 */
	public String readLine(boolean emptyAllowed) {
		String str = "";
		BufferedReader in = null;

		try {
			in = new BufferedReader(new InputStreamReader(System.in));

			while ((str = in.readLine()).isEmpty() && !emptyAllowed) {}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return str;
	}

	/**
	 * Read one integer value from console.
	 * 
	 * @param values not <tt>null</tt> list of available value.
	 * @return one of available value
	 */
	public int readInt(Collection<Integer> values) {
		while (true) {
			try {
				int val = Integer.parseInt(readLine(true));

				if (values.isEmpty() || values.contains(val))
					return val;
			} catch (Exception ignored) {
				if (values.isEmpty())
					return -1;
			}
		}
	}

	/**
	 * Read one integer value that equals or greater than given <tt>min</tt> value.
	 * 
	 * @param min minimum available value
	 * @return integer value from console that equals or greater than given <tt>min</tt> value
	 */
	public int readInt(int min) {
		while (true) {
			try {
				String str = readLine(true);

				if (str.isEmpty())
					return -1;

				int val = Integer.parseInt(readLine(true));

				if (val >= min)
					return val;
			} catch (Exception ignored) {}
		}
	}
}
