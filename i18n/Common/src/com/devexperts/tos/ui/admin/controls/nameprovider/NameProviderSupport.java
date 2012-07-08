package com.devexperts.tos.ui.admin.controls.nameprovider;

/**
 * @author Oleg Cherednik
 * @since 14.04.2011
 */
public interface NameProviderSupport<T> {
	void setNameProvider(NameProvider<T> nameProvider);

	NameProvider<T> getNameProvider();
}
