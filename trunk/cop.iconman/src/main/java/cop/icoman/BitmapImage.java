package cop.icoman;

import java.io.DataInput;
import java.io.IOException;

public abstract class BitmapImage
{
	private final ImageKey key = new ImageKey();
	// private int res; // size: 1, offs: 0x3 (0 or 255, ignored)
	private int planesHor; // size: 2, offs: 0x4 (...)
	private int bitCountVer; // size: 2, offs: 0x6 (...)
	private int size; // size: 4, offs: 0x8 (bitmap data size)
	private int offs; // size: 4, offs: 0xC (bitmap data offset)

	private byte[] imageData;

	public ImageKey getImageKey()
	{
		return key;
	}

	protected int getPlanesHor()
	{
		return planesHor;
	}

	protected int getBitCountVer()
	{
		return bitCountVer;
	}

	public int getSize()
	{
		return size;
	}

	public int getOffs()
	{
		return offs;
	}

	public void read(DataInput in) throws IOException
	{
		key.read(in);
		in.skipBytes(1);
		planesHor = in.readShort();
		bitCountVer = in.readShort();
		size = in.readInt();
		offs = in.readInt();
	}
}
