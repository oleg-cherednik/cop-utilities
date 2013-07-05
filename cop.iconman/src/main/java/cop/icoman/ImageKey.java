package cop.icoman;

import java.io.DataInput;
import java.io.IOException;

/**
 * @author Oleg Cherednik
 * @since 14.12.2012
 */
public final class ImageKey {
    private final int width; // size: 1, offs: 0x0 (0-255, 0=256 pixels)
    private final int height; // size: 1, offs: 0x1 (0-255, 0=256 pixels)
    private final int colors; // size: 1, offs: 0x2 (0=256 - truecolor)

    public static ImageKey readKey(DataInput in) throws IOException {
        int width = fix(in.readUnsignedByte());
        int height = fix(in.readUnsignedByte());
        int colors = fix(in.readUnsignedByte());

        check(width, height, colors);

        return new ImageKey(width, height, colors);
    }

    public static ImageKey createKey(int width, int height, int colors) {
        check(width, height, colors);
        return new ImageKey(width, height, colors);
    }

    private ImageKey(int width, int height, int colors) {
        this.width = fix(width);
        this.height = fix(height);
        this.colors = fix(colors);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getColors() {
        return colors;
    }

    // ========== Object ==========

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + colors;
        result = prime * result + height;
        result = prime * result + width;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        ImageKey other = (ImageKey)obj;

        return colors == other.colors && height == other.height && width == other.width;
    }

    @Override
    public String toString() {
        return width + "x" + height + " " + (colors == 256 ? "truecolor" : colors + "colors");
    }

    // ========== static ==========

    private static int fix(int size) {
        return size != 0 ? size : 256;
    }

    private static void check(int width, int height, int colors) {

    }
}