package com.devexperts.tos.ui.admin.controls.nameprovider;

import java.util.Locale;

import cop.i18n.LocaleStore;
import cop.i18n.Localizable;

/**
 * @author Oleg Cherednik
 * @param 07.07.2012
 */
public class LocalizableEnumNameProvider<T extends Localizable> implements LocalizableNameProvider<T> {
	protected LocalizableEnumNameProvider() {}

	/*
	 * NameProvider
	 */

	public final String getName(T key) {
		return key.i18n();
	}

	public final String getName(T key, String suffix) {
		return LocaleStore.i18n(key, key, suffix);
	}

	/*
	 * LocalizableNameProvider
	 */

	public final String getName(T key, Locale locale) {
		return key.i18n(locale);
	}

	public final String getName(T key, String suffix, Locale locale) {
		return LocaleStore.i18n(key, key, suffix, locale);
	}
}
