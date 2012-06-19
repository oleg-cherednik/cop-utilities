package cop.i18n;

import java.util.Locale;

import cop.common.enums.Color;
import cop.common.enums.Count;
import cop.server.enums.City;

public class TestMain {
	private static final Locale[] locales = { LocaleStore.RUSSIAN, Locale.ENGLISH, Locale.GERMAN, Locale.FRENCH };

	public static void main(String[] args) {
		setSystemDefaultLocale(LocaleStore.RU);
		// printCounts();
		// printColors();
		printCities();
	}

	private static void setSystemDefaultLocale(Locale locale) {
		System.out.println("system locale: " + locale);
		Locale.setDefault(locale);
	}

	private static void printColors() {
		for(Color color : Color.values()) {
			printColor(color);

			for(Locale locale : locales)
				printColor(color, locale);
		}
	}

	private static void printCounts() {
		for(Count count : Count.values()) {
			printCount(count);

			for(Locale locale : locales)
				printCount(count, locale);
		}
	}

	private static void printCities() {
		for(City city : City.values()) {
			printCity(city);

			for(Locale locale : locales)
				printCity(city, locale);
		}
	}

	private static void printColor(Color color, Locale locale) {
		String str = color.i18n(locale);
		System.out.println("locale: " + locale + ", " + str);
	}

	private static void printColor(Color color) {
		System.out.println(color.i18n());
	}

	private static void printCount(Count count, Locale locale) {
		String str = count.i18n(locale);
		System.out.println("locale: " + locale + ", " + str);
	}

	private static void printCount(Count count) {
		System.out.println(count.i18n());
	}

	private static void printCity(City city, Locale locale) {
		String str = city.i18n(locale);
		System.out.println("locale: " + locale + ", " + str);
	}

	private static void printCity(City city) {
		System.out.println(city.i18n());
	}
}
