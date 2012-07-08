package com.devexperts.tos.ui.admin.controls.nameprovider;

import java.util.Locale;
import java.util.Map;

import com.google.common.collect.BiMap;

/**
 * @author Oleg Cherednik
 * @param 07.07.2012
 */
class MultipleDelegate<T> implements LocalizableNameProvider<T> {
	static final String DEFAULT_SUFFIX = "def";

	private final Map<String, BiMap<T, String>> names;

	public MultipleDelegate(Map<String, BiMap<T, String>> names) {
		this.names = names;
	}

	private BiMap<T, String> getMap(String suffix) {
		if (suffix == null)
			suffix = DEFAULT_SUFFIX;

		BiMap<T, String> map = names.get(suffix);
		return map == null || map.isEmpty() ? names.get(DEFAULT_SUFFIX) : map;
	}

	/*
	 * DataHolder
	 */

	public String getName(T key) {
		return getName(key, DEFAULT_SUFFIX);
	}

	public String getName(T key, String suffix) {
		return getMap(suffix).get(key);
	}

	public String getName(T key, Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName(T key, String suffix, Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}
}
