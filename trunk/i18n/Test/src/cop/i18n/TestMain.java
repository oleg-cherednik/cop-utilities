package cop.i18n;

import java.util.Locale;

import cop.common.enums.Color;

public class TestMain {
	private static final Locale[] locales = { LocalizationExt.RU, Locale.ENGLISH, Locale.GERMAN };

	public static void main(String[] args) {
		setSystemDefaultLocale(LocalizationExt.RU);
		printColors();
	}

	private static void setSystemDefaultLocale(Locale locale) {
		System.out.println("system locale: " + locale);
		Locale.setDefault(locale);
	}

	private static void printColors() {
		printColor(Color.BLUE);
		
		for(Locale locale : locales)
			printColor(Color.BLUE, locale);
			
	}

	private static void printColor(Color color, Locale locale) {
		System.out.println("locale: " + locale + ", " + color.i18n(locale));
	}

	private static void printColor(Color color) {
		System.out.println(color.i18n());
	}
}
