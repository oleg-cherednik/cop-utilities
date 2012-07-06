package cop.common.enums;

import java.util.Locale;

import cop.common.CommonProperty;
import cop.i18n.LocaleStore;
import cop.i18n.Localizable;
import cop.i18n.annotations.i18n;

public enum Count implements Localizable {
	ONE,
	TWO,
	THREE,
	FOUR,
	FIVE,
	SIX,
	SEVEN,
	EIGHT,
	NINE,
	TEN;

	/*
	 * Localizable
	 */

	public String i18n() {
		return LocaleStore.i18n(this, name());
	}

	public String i18n(Locale locale) {
		return LocaleStore.i18n(this, name(), locale);
	}

	/*
	 * static
	 */

	@i18n
	public static String[] getLocalizedName() {
		return LocaleStore.i18n(values());
	}

	@i18n
	public static String[] getLocalizedName(Locale locale) {
		return LocaleStore.i18n(values(), locale);
	}

	static {
		LocaleStore.registerBundle(Count.class, CommonProperty.PATH_I18N);
	}
}
