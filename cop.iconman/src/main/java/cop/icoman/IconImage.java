package cop.icoman;

/**
 * @author Oleg Cherednik
 * @since 03.07.2013
 */
public final class IconImage {
    private IconImageHeader header;
    private byte[] data;

    public IconImage(IconImageHeader header, byte[] data) {
        this.header = header;
        this.data = data;
    }
}
