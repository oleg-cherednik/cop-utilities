package cop.server.enums;

import java.util.Locale;

import cop.i18n.LocaleStore;
import cop.i18n.Localizable;
import cop.server.ServerProperty;

public enum City implements Localizable {
	MOSCOW,
	SAINT_PETERSBURG,
	LONDON,
	BERLIN;

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
		LocaleStore.registerBundle(City.class, ServerProperty.PATH_I18N);
	}
}
