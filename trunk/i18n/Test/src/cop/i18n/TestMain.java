package cop.i18n;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import cop.common.enums.Color;

public class TestMain {
	private static final Locale[] locales = { LocalizationExt.RU, Locale.ENGLISH, Locale.GERMAN };

	public static void main(String[] args) {
		try {
			System.setOut(new PrintStream(System.out, true, "UTF8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		setSystemDefaultLocale(LocalizationExt.RU);
		printColors();
	}

	private static void setSystemDefaultLocale(Locale locale) {
		System.out.println("system locale: " + locale);
		Locale.setDefault(locale);
	}

	private static void printColors() {
		for (Color color : Color.values()) {
			printColor(color);

			for (Locale locale : locales)
				printColor(color, locale);
		}

	}

	private static void printColor(Color color, Locale locale) {
		System.out.println("locale: " + locale + ", " + color.i18n(locale));
	}

	private static void printColor(Color color) {
		System.out.println(color.i18n());
	}
}
