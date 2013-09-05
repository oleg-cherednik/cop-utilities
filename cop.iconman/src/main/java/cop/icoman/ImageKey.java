package cop.icoman;

import cop.icoman.exceptions.UnsupportedImageException;

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
	private static final int HIGH_COLOR = -1;    // 16bit
	private static final int TRUE_COLOR = -2;    // 24bit
	private static final int XP = -3;            // 32bit

	private static final Map<String, ImageKey> MAP = new HashMap<>();

	private final int width; // size: 1, offs: 0x0 (0-255, 0=256 pixels)
	private final int height; // size: 1, offs: 0x1 (0-255, 0=256 pixels)
	private final int colors; // size: 1, offs: 0x2 (0=256 - high/true color)

	public static ImageKey createKey(int width, int height, int colors) {
		check(width, height, colors);

		ImageKey key = MAP.get(getString(width, height, colors));
		return key != null ? key : new ImageKey(width, height, colors);
	}

	private ImageKey(int width, int height, int colors) {
		this.width = width;
		this.height = height;
		this.colors = colors;

		MAP.put(getString(width, height, colors), this);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getColors() {
		if (colors == HIGH_COLOR)
			return 65536;
		if (colors == TRUE_COLOR || colors == XP)
			return 16777216;
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
		StringBuilder buf = new StringBuilder();

		buf.append(width).append('x').append(height).append(' ');

		if (colors == HIGH_COLOR)
			buf.append(" High Color");
		else if (colors == TRUE_COLOR)
			buf.append(" True Color");
		else if (colors == XP)
			buf.append(" XP");
		else
			buf.append(' ').append(colors).append(" colors");

		return buf.toString();
	}

	private static void check(int width, int height, int colors) {
	}

	static int getColors(int bitsPerPixel) throws UnsupportedImageException {
		if (bitsPerPixel == 1)
			return 2;
		if (bitsPerPixel == 4)
			return 16;
		if (bitsPerPixel == 8)
			return 256;
		if (bitsPerPixel == 16)
			return HIGH_COLOR;
		if (bitsPerPixel == 24)
			return TRUE_COLOR;
		if (bitsPerPixel == 32)
			return XP;

		throw new UnsupportedImageException("bitPerPixel = " + bitsPerPixel);
	}
}
