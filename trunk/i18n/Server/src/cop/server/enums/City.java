package cop.server.enums;

import java.util.Locale;

import cop.i18n.LocaleStore;
import cop.i18n.Localizable;
import cop.i18n.annotations.i18n;
import cop.server.ServerProperty;

public enum City implements Localizable {
	MOSCOW,
	SAINT_PETERSBURG,
	LONDON,
	BERLIN;

	/*
	 * Localizable
	 */

	@Override
	public String i18n() {
		return LocaleStore._i18n(this, name());
	}

	@Override
	public String i18n(Locale locale) {
		return LocaleStore._i18n(this, name(), locale);
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
		LocaleStore.registerStore(City.class, ServerProperty.PATH_I18N);
	}
}
