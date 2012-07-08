package com.devexperts.tos.ui.admin.controls.nameprovider;

import java.util.Locale;
import java.util.Map;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * @author Oleg Cherednik
 * @param 07.07.2012
 */
class SingleDelegate<T> implements LocalizableNameProvider<T> {
	private final BiMap<T, String> names = HashBiMap.create();

	public SingleDelegate(Map<T, String> data) {
		names.putAll(data);
	}

	/*
	 * DataHolder
	 */

	public String getName(T key) {
		return names.get(key);
	}

	public String getName(T key, String suffix) {
		return getName(key);
	}

	public String getName(T key, Locale locale) {
		return null;
	}

	public String getName(T key, String suffix, Locale locale) {
		return null;
	}
}
