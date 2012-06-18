package cop.common.enums;

import cop.common.CommonProperty;
import cop.i18n.LocaleStore;
import cop.i18n.LocalizationExt;

public enum City {
	MOSCOW,
	SAINT_PETERSBURG,
	LONDON,
	BERLIN;

	static {
		LocaleStore.registerStore(City.class, null/*CommonProperty.I18N*/, LocalizationExt.DEFAULT_LOCALE);
	}
}
