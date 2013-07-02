package cop.icoman;

import static cop.icoman.IconTypeEnum.parseIconTypeEnum;

import java.io.DataInput;
import java.io.IOException;
import java.text.ParseException;

public class IconHeader
{
	private IconTypeEnum type; // size: 2, offs: 0x2
	private int imageCount; // size: 2, offs: 0x4

	public IconHeader()
	{}

	public IconHeader(DataInput in) throws IOException
	{
		read(in);
	}

	public IconTypeEnum getType()
	{
		return type;
	}

	public int getImageCount()
	{
		return imageCount;
	}

	public void read(DataInput in) throws IOException
	{
		try
		{
			int val = in.readUnsignedShort();
			val = in.readUnsignedShort();
			in.skipBytes(2);
			type = parseIconTypeEnum(in.readUnsignedShort());
			imageCount = in.readUnsignedShort();
		}
		catch(ParseException e)
		{
			throw new IOException(e);
		}
	}

	/*
	 * Object
	 */

	@Override
	public String toString()
	{
		return type + ":" + imageCount;
	}
}
