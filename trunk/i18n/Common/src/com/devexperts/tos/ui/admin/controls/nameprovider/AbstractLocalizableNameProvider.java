package com.devexperts.tos.ui.admin.controls.nameprovider;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cop.i18n.LocaleStore;
import cop.i18n.Localizable;

/**
 * @author Oleg Cherednik
 * @since 29.06.2012
 */
public abstract class AbstractLocalizableNameProvider<T> implements LocalizableNameProvider<T> {
	protected final Map<T, Localizable> names = new HashMap<T, Localizable>();

	protected AbstractLocalizableNameProvider() {
		names.putAll(getKeyToNameMap());
	}

	@SuppressWarnings("static-method")
	protected Map<T, Localizable> getKeyToNameMap() {
		return Collections.emptyMap();
	}

	/*
	 * NameProvider
	 */

	public final String getName(T key) {
		return names.get(key).i18n();
	}

	public final String getName(T key, String suffix) {
		return LocaleStore.i18n(names.get(key), key, suffix);
	}

	/*
	 * LocalizableNameProvider
	 */

	public final String getName(T key, Locale locale) {
		return names.get(key).i18n(locale);
	}

	public final String getName(T key, String suffix, Locale locale) {
		return LocaleStore.i18n(names.get(key), key, suffix, locale);
	}
}
