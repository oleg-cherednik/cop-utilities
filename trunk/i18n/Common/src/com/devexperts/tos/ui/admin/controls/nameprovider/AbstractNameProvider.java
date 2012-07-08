package com.devexperts.tos.ui.admin.controls.nameprovider;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.*;

/**
 * @author Oleg Cherednik
 * @since 04.04.2011
 */
public abstract class AbstractNameProvider<T> implements NameProvider<T> {
	protected static final String DEFAULT_SUFFIX = MultipleDelegate.DEFAULT_SUFFIX;

	private final NameProvider<T> delegate;

	protected AbstractNameProvider(String... suffixes) {
		Set<String> suffixSet = check(suffixes);

		if (suffixSet.size() == 1)
			delegate = new SingleDelegate<T>(getKeyToNameMap());
		else {
			BiMap<T, String> map;
			Map<String, BiMap<T, String>> names = new HashMap<String, BiMap<T, String>>();

			for (String suffix : suffixSet) {
				names.put(suffix, map = HashBiMap.create());
				map.putAll(DEFAULT_SUFFIX.equals(suffix) ? getKeyToNameMap() : getKeyToNameMap(suffix));
			}

			delegate = new MultipleDelegate<T>(names);
		}
	}

	protected Map<T, String> getKeyToNameMap() {
		return getKeyToNameMap(DEFAULT_SUFFIX);
	}

	@SuppressWarnings({ "static-method", "unused" })
	protected Map<T, String> getKeyToNameMap(String type) {
		return Collections.emptyMap();
	}

	/*
	 * NameProvider
	 */

	public final String getName(T key) {
		return getName(key, DEFAULT_SUFFIX);
	}

	public final String getName(T key, String suffix) {
		return delegate.getName(key, suffix);
	}

	/*
	 * static
	 */

	private static Set<String> check(String... suffixes) {
		Set<String> suffixSet = new HashSet<String>();

		suffixSet.add(DEFAULT_SUFFIX);

		if (suffixes != null && suffixes.length != 0)
			suffixSet.addAll(Arrays.asList(suffixes));

		return Collections.unmodifiableSet(suffixSet);
	}
}
