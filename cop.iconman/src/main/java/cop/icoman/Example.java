package cop.icoman;

import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

public class Example
{
	public static void main(String[] arg)
	{
		try
		{

			final String path = "d:\\testico.ico";
			final File file = new File(path);
			FileInputStream fis = new FileInputStream(path);
			final ImageInputStream iis = ImageIO.createImageInputStream(fis);
			
			IconHeader header = new IconHeader();


			// ICOFile ico = new ICOFile(iis);

			int a = 0;
			a++;

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
