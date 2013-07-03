package cop.icoman;

import java.io.DataInput;
import java.io.IOException;

public final class ImageKey implements Comparable<ImageKey> {
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

	/*
     * Comparable
	 */

    public int compareTo(ImageKey obj) {
        if (obj == null)
            return 1;

        if (this.width != obj.width)
            return (this.width > obj.width) ? 1 : -1;
        if (this.height != obj.height)
            return (this.height > obj.height) ? 1 : -1;
        if (this.colors != obj.colors)
            return (this.colors > obj.colors) ? 1 : -1;

        return 0;
    }

	/*
     * Object
	 */

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
        if (colors != other.colors)
            return false;
        if (height != other.height)
            return false;
        if (width != other.width)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return width + "x" + height + " " + ((colors == 256) ? "truecolor" : colors + "colors");
    }

    // ========== static ==========

    private static int fix(int size) {
        return size != 0 ? size : 256;
    }

    private static void check(int width, int height, int colors) {

    }
}
