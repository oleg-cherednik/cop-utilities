package cop.icoman;

import cop.icoman.exceptions.IconManagerException;

import java.util.Arrays;

/**
 * @author Oleg Cherednik
 * @since 03.07.2013
 */
public final class IconImage {
    private final IconImageHeader header;
    private final byte[] data;

    public static IconImage createImage(IconImageHeader header, byte[] data) throws IconManagerException {
        check(header, data);
        return new IconImage(header, data);
    }

    private IconImage(IconImageHeader header, byte[] data) {
        this.header = header;
        this.data = data;
    }

    public IconImageHeader getHeader() {
        return header;
    }

    public byte[] getData() {
        return Arrays.copyOf(data, data.length);
    }

    // ========== Object ==========

    @Override
    public String toString() {
        return header.toString();
    }

    // ========== static ==========

    private static void check(IconImageHeader header, byte[] data) throws IconManagerException {
        if (header == null)
            throw new IconManagerException("header is not set");
        if (data == null || data.length == 0)
            throw new IconManagerException("data is not set");
        if (header.getSize() != data.length)
            throw new IconManagerException("data size is not equals to 'header.size'");
    }
}
