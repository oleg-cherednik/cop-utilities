package cop.common.enums;

import java.util.Locale;

import cop.common.CommonProperty;
import cop.i18n.LocaleStore;
import cop.i18n.Localizable;

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

	static {
		LocaleStore.registerBundle(Count.class, CommonProperty.PATH_I18N);
	}
}
