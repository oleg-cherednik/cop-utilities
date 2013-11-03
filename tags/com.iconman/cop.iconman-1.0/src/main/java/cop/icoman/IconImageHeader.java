package cop.icoman;

import cop.icoman.exceptions.IconManagerException;

import java.io.DataInput;
import java.io.IOException;

/**
 * @author Oleg Cherednik
 * @since 01.09.2013
 */
public final class IconImageHeader {
	public static final int SIZE = 16;

	private final int id;
	private final ImageKey key;
	// private int res; // size: 1, offs: 0x3 (0 or 255, ignored)
	private int colorPlanes; // size: 2, offs: 0x4 (...)
	private int bitsPerPixel; // size: 2, offs: 0x6 (...)
	private int size; // size: 4, offs: 0x8 (bitmap data size)
	private int offs; // size: 4, offs: 0xC (bitmap data offset)

	public static IconImageHeader readHeader(int id, DataInput in) throws IconManagerException, IOException {
		ImageKey key = ImageKey.readKey(in);

		skip(id, in);

		int colorPlanes = in.readShort();
		int bitsPerPixel = in.readShort();
		int size = in.readInt();
		int offs = in.readInt();

		check(key, colorPlanes, bitsPerPixel, size, offs);

		return new IconImageHeader(id, key, colorPlanes, bitsPerPixel, size, offs);
	}

	private IconImageHeader(int id, ImageKey key, int colorPlanes, int bitsPerPixel, int size, int offs) {
		this.id = id;
		this.key = key;
		this.colorPlanes = colorPlanes;
		this.bitsPerPixel = bitsPerPixel;
		this.size = size;
		this.offs = offs;
	}

	public int getId() {
		return id;
	}

	public ImageKey getImageKey() {
		return key;
	}

	public int getColorPlanes() {
		return colorPlanes;
	}

	public int getBitsPerPixel() {
		return bitsPerPixel;
	}

	public int getSize() {
		return size;
	}

	public int getOffs() {
		return offs;
	}

	// ========== Object ==========

	@Override
	public String toString() {
		return key + " " + bitsPerPixel + "bits";
	}

	// ========== static ==========

	private static void check(ImageKey key, int colorPlanes, int bitsPerPixel, int size, int offs) {
	}

	private static void skip(int id, DataInput in) throws IOException, IconManagerException {
		int val = in.readUnsignedByte();

		if (val != 0 && val != 255)
			throw new IconManagerException(
					"'header offs:0, size:2' of image no. " + id + " is reserved, should be 0 or 255");
	}
}
