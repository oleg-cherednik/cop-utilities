package cop.icoman;

import static cop.icoman.IconTypeEnum.CUR;

import java.io.DataInput;
import java.io.IOException;

public class CursorImage extends BitmapImage
{
	public CursorImage(DataInput in) throws IOException
	{
		read(in);
	}

	public int getHorizontalCoordinate()
	{
		return getPlanesHor();
	}

	public int getVerticalCoordinate()
	{
		return getBitCountVer();
	}

	/*
	 * Object
	 */

	@Override
	public String toString()
	{
		return CUR + ":" + super.toString();
	}
}
