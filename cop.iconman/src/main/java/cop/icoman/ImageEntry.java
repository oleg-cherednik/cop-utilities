package cop.icoman;

/**
 * ICOReader (ImageIO compatible class for reading ico files)
 * Copyright (C) 2005 J.B. van der Burgh
 * contact me at: icoreader (at) vdburgh.tmfweb.nl
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License along
 * with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
import static cop.icoman.IconTypeEnum.parseIconTypeEnum;

import java.io.DataInput;
import java.io.IOException;
import java.text.ParseException;

import javax.imageio.stream.ImageInputStream;

/**
 * <pre>
 * typedef struct
 * {
 *     BYTE        bWidth;          // Width, in pixels, of the image
 *     BYTE        bHeight;         // Height, in pixels, of the image
 *     BYTE        bColorCount;     // Number of colors in image (0 if >=8bpp)
 *     BYTE        bReserved;       // Reserved ( must be 0)
 *     WORD        wPlanes;         // Color Planes
 *     WORD        wBitCount;       // Bits per pixel
 *     DWORD       dwBytesInRes;    // How many bytes in this resource?
 *     DWORD       dwImageOffset;   // Where in the file is this image?
 * } ICONDIRENTRY, *LPICONDIRENTRY;
 * </pre>
 * 
 * @author J.B. van der Burgh
 * @version 1.0
 */
public class ImageEntry
{
	private int width;
	private int height;
	private int colorCount;
	private int reserved;
	private int planes;
	private int bitCount;
	private int bytesInResource;
	private int imageOffset;
	private byte[] imageData;

	public ImageEntry(ImageInputStream in) throws IOException
	{
		read(in);
		//
		// try
		// {
		// int offset = in.getBitOffset();//getOffs();
		// int skip = imageOffset - offset;
		// if(skip < 0)
		// {
		// System.err.println("Error.... unable to figure out how much bytes to skip to get to offset: "
		// + imageOffset + "  current offset: " + offset);
		// // throw new IOException("Error.... unable to figure out how much bytes to skip to get to offset: " +
		// // imageOffset + "  current offset: " + offset);
		// return;
		// }
		// in.skipBytes(skip);
		//
		// imageData = new byte[bytesInResource];
		//
		// in.read(imageData);
		// }
		// finally
		// {
		// in.reset();
		// }

		// if(bitCount == 0 && imageOffset > 0)
		// {
		// // if (DEBUG)
		// // System.out.println("bitCount field is missing (bits per pixel): trying to fetch it from the imagedata");
		// int newbpp = Bitmap.readBitCountFromData(this.imageData);
		// this.bitCount = newbpp;
		// }
	}

	public void read(DataInput in) throws IOException
	{
		width = in.readUnsignedByte();
		height = in.readUnsignedByte();
		colorCount = in.readUnsignedByte();
		reserved = in.readUnsignedByte();
		planes = in.readShort();
		bitCount = in.readShort();
		bytesInResource = in.readInt();
		imageOffset = in.readInt();
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer(100);
		sb.append("IconEntry [");
		sb.append("w=").append(width);
		sb.append(", h=").append(height);
		sb.append(", bitCount(bpp)=").append(bitCount);
		sb.append(", colorCount=").append(colorCount);
		sb.append(", reserved=").append(reserved);
		sb.append(", planes=").append(planes);
		sb.append(", bytesInResource=").append(bytesInResource);
		sb.append(", imageOffset=").append(imageOffset);
		sb.append(", imageData=").append(imageData);
		sb.append(" ]");
		return sb.toString();
	}

	// public Bitmap getImageIoBitmap() throws IOException
	// {
	// return Bitmap.getImageIoBitmap(this);
	// }
	//
	// public Bitmap getBitmap() throws IOException
	// {
	// return Bitmap.getBitmap(this);
	// }
	//
	// public int getHeight()
	// {
	// return height;
	// }
	//
	// public int getWidth()
	// {
	// return width;
	// }
	//
	// public int getBitCount()
	// {
	// return bitCount;
	// }
	//
	// public int getBytesInResource()
	// {
	// return bytesInResource;
	// }
	//
	// public int getColorCount()
	// {
	// return colorCount;
	// }
	//
	// public int getImageOffset()
	// {
	// return imageOffset;
	// }
	//
	// public int getPlanes()
	// {
	// return planes;
	// }
	//
	// public int getReserved()
	// {
	// return reserved;
	// }
	//
	// public byte[] getImageData()
	// {
	// return imageData;
	// }
	//
	// public void setWidth(int width)
	// {
	// this.width = width;
	// }
	//
	// public void setReserved(int reserved)
	// {
	// this.reserved = reserved;
	// }
	//
	// public void setPlanes(int planes)
	// {
	// this.planes = planes;
	// }
	//
	// public void setImageOffset(int imageOffset)
	// {
	// this.imageOffset = imageOffset;
	// }
	//
	// public void setHeight(int height)
	// {
	// this.height = height;
	// }
	//
	// public void setColorCount(int colorCount)
	// {
	// this.colorCount = colorCount;
	// }
	//
	// public void setBytesInResource(int bytesInResource)
	// {
	// this.bytesInResource = bytesInResource;
	// }
	//
	// public void setBitCount(int bitCount)
	// {
	// this.bitCount = bitCount;
	// }
	//
	// public void setImageData(byte[] imageData)
	// {
	// this.imageData = imageData;
	// }

}
