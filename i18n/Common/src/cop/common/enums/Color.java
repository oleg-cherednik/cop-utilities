package cop.common.enums;

import java.util.Locale;

import cop.common.CommonProperty;
import cop.i18n.LocaleStore;
import cop.i18n.LocalizationExt;

public enum Color {
	RED,
	BLUE,
	GREEN,
	WHITE;

	/*
	 * Localizable
	 */

//	@Override
	public String i18n() {
		return LocaleStore._i18n(this, name());
	}

//	@Override
	public String i18n(Locale locale) {
		return LocaleStore._i18n(this, name(), locale);
	}

	/*
	 * statis
	 */

	static {
		LocaleStore.registerStore(Color.class, null /*CommonProperty.I18N*/, LocalizationExt.DEFAULT_LOCALE);
	}
}
