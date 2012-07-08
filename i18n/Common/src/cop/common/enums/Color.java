package cop.common.enums;

import java.util.Locale;

import cop.common.CommonProperty;
import cop.i18n.LocaleStore;
import cop.i18n.Localizable;

public enum Color implements Localizable {
	RED,
	BLUE,
	GREEN,
	WHITE;

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
		LocaleStore.registerBundle(Color.class, CommonProperty.PATH_I18N);
		LocaleStore.registerBundle(Color.class, CommonProperty.PATH_I18N1);
	}
}
