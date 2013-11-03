package cop.icoman;

import cop.icoman.exceptions.IconManagerException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * @author Oleg Cherednik
 * @since 03.07.2013
 */
public final class IconImage {
	private final IconImageHeader header;
	private final byte[] data;
	private ImageIcon icon;

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

	public ImageIcon getIcon() throws IOException {
		if (icon != null)
			return icon;

		InputStream in = new ByteArrayInputStream(data);
		BufferedImage image = ImageIO.read(in);



//		BufferedImage image;

//		try {
//
//			if (data[0] == 40)
//				image = Bitmap.getBitmap(this).getImage();
//			else
//				image = ImageIO.read(new ByteArrayInputStream(data));

//		ImageInputStream in = ImageIO.createImageInputStream(new ByteArrayInputStream(data));
//		in.setByteOrder(ByteOrder.LITTLE_ENDIAN);

//		BufferedImage image = ImageIO.read(new ByteArrayInputStream(data));

			return icon = new ImageIcon(image);
//		} catch(Exception e) {
//			throw new IOException(e);
//		}
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
