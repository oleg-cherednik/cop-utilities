package cop.icoman;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Example {
	public static void main(String[] arg) {
		try  {
			InputStream is = Example.class.getResourceAsStream("/testico.ico");
//			final ImageInputStream iis = ImageIO.createImageInputStream(fis);

			IconHeader header = new IconHeader();
			header.read(new DataInputStream(is));


//			ICOFile ico = new ICOFile(iis);

			int a = 0;
			a++;

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
