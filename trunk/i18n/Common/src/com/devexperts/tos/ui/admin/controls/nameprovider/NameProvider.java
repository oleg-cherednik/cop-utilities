package com.devexperts.tos.ui.admin.controls.nameprovider;

/**
 * Provides a string representation for the given object.
 *
 * @author Oleg Cherednik
 * @since 29.03.2011
 */
public interface NameProvider<T> {
    /**
     * Returns default ({@link NameType#DEFAULT}) string representation for the given <tt>key</tt>. Results is identical
     * with invoking {@link #getName(Object, NameType)} with {@link NameType#DEFAULT}.
     *
     * @param key object key
     *
     * @return default ({@link NameType#DEFAULT}) string representation
     */
    String getName(T key);

    /**
     * Returns string representation for given <tt>key</tt> and <tt>type</tt>. If <tt>type</tt> is {@link
     * NameType#DEFAULT}
     * then invoking this method is identical with {@link #getName(Object)}.
     *
     * @param key  object key
     * @param type string representation type
     *
     * @return string representations for given <tt>key</tt> and <tt>type</tt>
     */
    String getName(T key, String suffix);
}
