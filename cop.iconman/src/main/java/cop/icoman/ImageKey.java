package cop.icoman;

import java.io.DataInput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents single image in the icon. There're no images with same key (width, height, colors), that's why this class
 * is singleton for each key. So each set of width, height and color returns <b>same</b> class instance.
 *
 * @author Oleg Cherednik
 * @since 14.12.2012
 */
public final class ImageKey {
	private static final Map<String, ImageKey> MAP = new HashMap<>();

	private final int width; // size: 1, offs: 0x0 (0-255, 0=256 pixels)
	private final int height; // size: 1, offs: 0x1 (0-255, 0=256 pixels)
	private final int colors; // size: 1, offs: 0x2 (0=256 - true color)

	public static ImageKey readKey(DataInput in) throws IOException {
		int width = fix(in.readUnsignedByte());
		int height = fix(in.readUnsignedByte());
		int colors = fix(in.readUnsignedByte());

		return createKey(width, height, colors);
	}

	public static ImageKey createKey(int width, int height, int colors) {
		check(width, height, colors);

		ImageKey key = MAP.get(getString(width, height, colors));
		return key != null ? key : new ImageKey(width, height, colors);
	}

	private ImageKey(int width, int height, int colors) {
		this.width = fix(width);
		this.height = fix(height);
		this.colors = fix(colors);

		MAP.put(getString(width, height, colors), this);
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
		return getString(width, height, colors);
	}

	// ========== static ==========

	private static String getString(int width, int height, int colors) {
		return width + "x" + height + ' ' + colors + " colors";
	}

	private static int fix(int size) {
		return size != 0 ? size : 256;
	}

	private static void check(int width, int height, int colors) {
	}
}
