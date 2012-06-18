package cop.i18n;

import java.util.Locale;

import cop.common.enums.Color;

public class TestMain {
	public static void main(String[] args) {
		setSystemDefaultLocale(LocalizationExt.RU);
		printColors();
	}

	private static void setSystemDefaultLocale(Locale locale) {
		System.out.println("system locale: " + locale);
		Locale.setDefault(locale);
	}

	private static void printColors() {
		Color color = Color.BLUE;
		System.out.println(color.i18n());
		System.out.println(color.i18n(Locale.ENGLISH));
	}
}
