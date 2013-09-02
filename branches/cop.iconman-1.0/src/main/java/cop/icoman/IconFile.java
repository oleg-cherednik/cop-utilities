package cop.icoman;

import cop.icoman.exceptions.IconManagerException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Oleg Cherednik
 * @since 03.07.2013
 */
public final class IconFile implements Iterable<IconImage> {
	private final IconHeader header;
	private final List<IconImage> images;

	public static IconFile read(String filename) throws IOException, IconManagerException {
		try (ImageInputStream in = ImageIO.createImageInputStream(IconFile.class.getResourceAsStream(filename))) {
			in.setByteOrder(ByteOrder.LITTLE_ENDIAN);

			IconFile icon = read(in);

			if (in.read() != -1)
				throw new IconManagerException("End of the stream is not reached");

			return icon;
		}
	}

	public static IconFile read(ImageInputStream in) throws IOException, IconManagerException {
		IconHeader header = IconHeader.readHeader(in);
		List<IconImage> images = readImages(header, in);
		return new IconFile(header, images);
	}

	private IconFile(IconHeader header, List<IconImage> images) {
		assert header != null && header != IconHeader.NULL;
		assert images != null && !images.isEmpty();

		this.header = header;
		this.images = images;
	}

	public IconHeader getHeader() {
		return header;
	}

	public IconImage getImage(int id) {
		return images.get(id);
	}

	public int getImagesAmount() {
		return images.size();
	}

	// ========== static ==========

	private static List<IconImageHeader> readImageHeaders(int total, ImageInputStream in)
			throws IOException, IconManagerException {
		assert total > 0;
		assert in != null;

		List<IconImageHeader> headers = new ArrayList<>(total);

		for (int i = 0; i < total; i++)
			headers.add(BitmapType.ICO.createImageHeader(i, in));

		return Collections.unmodifiableList(headers);
	}

	private static List<IconImage> readImages(IconHeader header, ImageInputStream in)
			throws IOException, IconManagerException {
		List<IconImageHeader> imageHeaders = readImageHeaders(header.getImageCount(), in);
		List<IconImage> images = new ArrayList<>(imageHeaders.size());
		int offs = IconHeader.SIZE + imageHeaders.size() * IconImageHeader.SIZE;

		for (IconImageHeader imageHeader : imageHeaders) {
			checkOffs(offs, imageHeader);
			images.add(IconImage.createImage(imageHeader, readData(imageHeader.getSize(), in)));
			offs += imageHeader.getSize();
		}

		return Collections.unmodifiableList(images);
	}

	private static byte[] readData(int size, ImageInputStream in) throws IOException {
		byte[] data = new byte[size];
		in.readFully(data);
		return data;
	}

	private static void checkOffs(int expected, IconImageHeader imageHeader) throws IconManagerException {
		if (expected != imageHeader.getOffs())
			throw new IconManagerException("offs image no. " + imageHeader.getId() + " incorrect. actual=" +
					imageHeader.getOffs() + ", expected=" + expected);
	}

	// ========== Iterable ==========

	@Override
	public Iterator<IconImage> iterator() {
		return images.iterator();
	}
}
