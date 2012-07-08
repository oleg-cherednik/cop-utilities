package com.devexperts.tos.ui.admin.controls.nameprovider;

import java.util.Locale;

/**
 * @author Oleg Cherednik
 * @since 29.06.2012
 */
public interface LocalizableNameProvider<T> extends NameProvider<T> {
	String getName(T key, Locale locale);

	String getName(T key, String suffix, Locale locale);
}
