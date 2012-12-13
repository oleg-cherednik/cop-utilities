package cop.icoman;

import static cop.icoman.IconTypeEnum.ICO;

import java.io.DataInput;
import java.io.IOException;

public class IconImage extends BitmapImage
{
	public IconImage(DataInput in) throws IOException
	{
		read(in);
	}

	public int getColsorPlanes()
	{
		return getPlanesHor();
	}

	public int getBitsPerPixel()
	{
		return getBitCountVer();
	}

	/*
	 * Object
	 */

	@Override
	public String toString()
	{
		return ICO + ":" + super.toString() + " " + getBitsPerPixel() + "bits";
	}
}
