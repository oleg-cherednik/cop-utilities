package cop.icoman.bitmap;

import cop.icoman.IconImage;
import cop.icoman.exceptions.IconManagerException;

import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.ByteOrder;

/**
 * @author Oleg Cherednik
 * @since 01.09.2013
 */
public abstract class Bitmap {
	/*
	 * @see http://msdn.microsoft.com/library/default.asp?url=/library/en-us/gdi/bitmaps_1rw2.asp
	 */
	private BufferedImage image;
	// protected int _bytesInHeader=0;

	protected final int width;
	protected final int height;
	protected final BitmapInfoHeader header;

	protected Bitmap(ImageInputStream in, int width, int height) throws IOException {
		in.setByteOrder(ByteOrder.LITTLE_ENDIAN);

		this.width = width;
		this.height = height;
		header = new BitmapInfoHeader(in);
	}

	protected final void readImage(ImageInputStream is) throws IOException {
		image = createImage(is);
	}

//    public static Bitmap getImageIoBitmap(IconImage entry) throws IOException {
//        return new BitmapImageIO(entry);
//    }

	public final BufferedImage getImage() {
		return image;
	}

	protected abstract BufferedImage createImage(ImageInputStream is) throws IOException;

	private void append(StringBuffer sb, boolean first, Field[] data)
			throws IllegalAccessException, IllegalArgumentException {

		for (Field f : data) {
			if (f.getName().equals("entry") || f.getName().equals("reader"))
				continue;
			if (!first)
				sb.append(", ");
			else
				first = false;
			sb.append(f.getName()).append("=");
			sb.append(f.get(this));
		}

	}

	// ========== Object ==========

	public String toString() {
		StringBuffer sb = new StringBuffer(100);
		String name = this.getClass().getName();
		int idx = name.lastIndexOf('.');
		sb.append(name.substring(idx + 1));
		sb.append(" [");
		try {
			java.util.LinkedList<Class> classes = new java.util.LinkedList<Class>();
			Class tmpC = this.getClass();
			while (tmpC != Bitmap.class && tmpC != Object.class && tmpC != null) {
				classes.addFirst(tmpC);
				tmpC = tmpC.getSuperclass();
			}
//      classes.addFirst(tmpC);
			append(sb, true, tmpC.getDeclaredFields());
			for (Class c : classes) {
				append(sb, false, c.getDeclaredFields());
			}
		} catch(IllegalAccessException ex) {
		} catch(IllegalArgumentException ex) {
		}
		sb.append("]");
		return sb.toString();
	}

	// ========== static ==========

	public static Bitmap getBitmap(IconImage entry) throws IOException, IconManagerException {
		int bitsPerPixel = entry.getHeader().getBitsPerPixel();
		int width = entry.getHeader().getImageKey().getWidth();
		int height = entry.getHeader().getImageKey().getHeight();

		try (InputStream in = new ByteArrayInputStream(entry.getData())) {
			if (bitsPerPixel == 1 || bitsPerPixel == 2 || bitsPerPixel == 4 || bitsPerPixel == 8)
				return new IndexedBitmap(in, width, height);
			if (bitsPerPixel == 16)
				return new RgbBitmap(in, 2, width, height);
			if (bitsPerPixel == 24)
				return new RgbBitmap(in, 3, width, height);
			if (bitsPerPixel == 32)
				return new RgbBitmap(in, 4, width, height);

			throw new IconManagerException("Unsupported image, bitsPerPixel=" + bitsPerPixel);
		}
	}

	public static Bitmap getBitmap(int bitsPerPixel, int width, int height, ImageInputStream in)
			throws IOException, IconManagerException {
		if (bitsPerPixel == 1 || bitsPerPixel == 2 || bitsPerPixel == 4 || bitsPerPixel == 8)
			return new IndexedBitmap(in, width, height);
		if (bitsPerPixel == 16)
			return new RgbBitmap(in, 2, width, height);
		if (bitsPerPixel == 24)
			return new RgbBitmap(in, 3, width, height);
		if (bitsPerPixel == 32)
			return new RgbBitmap(in, 4, width, height);

		throw new IconManagerException("Unsupported image, bitsPerPixel=" + bitsPerPixel);
	}
}