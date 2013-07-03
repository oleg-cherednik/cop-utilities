package cop.icoman;

import nl.ikarus.nxt.priv.imageio.icoreader.obj.ICOFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.InputStream;
import java.nio.ByteOrder;

public class Example {
	public static void main(String[] arg) {
		try  {
			InputStream is = Example.class.getResourceAsStream("/testico.ico");
			final ImageInputStream iis = ImageIO.createImageInputStream(is);
			iis.setByteOrder(ByteOrder.LITTLE_ENDIAN);
			IconHeader header = IconHeader.readHeader(iis);

			BitmapImage image = header.getType().createBitmapImage(iis);


			ICOFile ico = new ICOFile(iis);

			int a = 0;
			a++;

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
