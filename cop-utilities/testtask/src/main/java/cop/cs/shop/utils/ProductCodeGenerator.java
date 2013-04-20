package cop.cs.shop.utils;

import java.util.Random;

/**
 * @author Oleg Cherednik
 * @since 19.04.2013
 */
public final class ProductCodeGenerator {
	private static final ProductCodeGenerator INSTANCE = new ProductCodeGenerator();

	private final char[] alphanumeric = alphanumeric();
	private final Random rand = new Random();

	private ProductCodeGenerator() {}

	private String get(int length) {
		StringBuilder buf = new StringBuilder();

		while (buf.length() < length) {
			buf.append(alphanumeric[Math.abs((rand.nextInt() % alphanumeric.length))]);
		}

		return buf.toString();
	}

	private char[] alphanumeric() {
		StringBuilder buf = new StringBuilder(128);

		for (int i = 48; i <= 57; i++)
			buf.append((char)i);
		for (int i = 65; i <= 90; i++)
			buf.append((char)i);
		for (int i = 97; i <= 122; i++)
			buf.append((char)i);

		return buf.toString().toCharArray();
	}

	// ========== static ==========

	public static String get() {
		return INSTANCE.get(10);
	}
}
