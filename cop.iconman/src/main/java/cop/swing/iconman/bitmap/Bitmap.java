package cop.swing.iconman.bitmap;
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

import cop.icoman.IconImage;
import cop.icoman.exceptions.IconManagerException;
import nl.ikarus.nxt.priv.imageio.icoreader.obj.*;

import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * <pre>
 *
 * typdef struct
 * {
 * BITMAPINFOHEADER   icHeader;      // DIB header
 * RGBQUAD         icColors[1];   // Color table
 * BYTE            icXOR[1];      // DIB bits for XOR mask
 * BYTE            icAND[1];      // DIB bits for AND mask
 * } ICONIMAGE, *LPICONIMAGE;
 *
 * The icHeader member has the form of a DIB BITMAPINFOHEADER.
 * Only the following members are used: biSize, biWidth, biHeight,
 * biPlanes, biBitCount, biSizeImage. All other members must be 0.
 * The biHeight member specifies the combined height of the XOR and AND masks.
 * The members of icHeader define the contents and sizes of the other
 * elements of the ICONIMAGE structure in the same way that the BITMAPINFOHEADER
 * structure defines a CF_DIB format DIB.
 *
 * The icColors member is an array of RGBQUADs. The number of elements in
 * this array is determined by examining the icHeader member.
 *
 * The icXOR member contains the DIB bits for the XOR mask of the image.
 * The number of bytes in this array is determined by examining the icHeader member.
 * The XOR mask is the color portion of the image and is applied to the destination
 * using the XOR operation after the application of the AND mask.
 *
 * The icAND member contains the bits for the monochrome AND mask. The number of bytes
 * in this array is determined by examining the icHeader member, and assuming 1bpp.
 * The dimensions of this bitmap must be the same as the dimensions of the XOR mask.
 * The AND mask is applied to the destination using the AND operation, to preserve
 * or remove destination pixels before applying the XOR mask.
 *
 * Note: The biHeight member of the icHeader structure represents the combined height
 * of the XOR and AND masks. Remember to divide this number by two before using it to
 * perform calculations for either of the XOR or AND masks. Also remember that the
 * AND mask is a monochrome DIB, with a color depth of 1 bpp.
 *
 * </pre>
 */
public abstract class Bitmap {
    /*
     * @see http://msdn.microsoft.com/library/default.asp?url=/library/en-us/gdi/bitmaps_1rw2.asp
     */
    protected BufferedImage _cachedImage = null;
    // protected int _bytesInHeader=0;

    protected final int width;
    protected final int height;

    protected final int biSize;
    protected final int biWidth;
    protected final int biHeight;
    protected final int biPlanes;
    protected final int biBitCount;
    protected final int biCompression;
    protected final int biSizeImage;
    protected final int biXPelsPerMeter;
    protected final int biYPelsPerMeter;
    protected final int biColorsUsed;
    protected final int biColorsImportant;

    protected int XORmaskSize;
    protected int ANDMaskSize;
    protected byte[] RGBQUAD;
    protected byte[] XOR;
    protected byte[] AND;
//  protected RGBQuad[] icColors; //RGBQUAD
    // protected byte[] icXOR; //XOR mask
    //protected byte[] icAND; //AND mask

//    protected class Compression {
//        public final static int BI_BITFIELDS = 3;
//        public final static int BI_JPEG = 4;
//        public final static int BI_PNG = 5;
//        public final static int BI_RGB = 0; //no compression
//        public final static int BI_RLE4 = 2;
//        public final static int BI_RLE8 = 1;
//        public final static int BI_1632 = 0x32333631;
//
//        public String getCompressionName(int val) {
//            switch (val) {
//                case BI_BITFIELDS:
//                    return "BI_BITFIELDS";
//                case BI_JPEG:
//                    return "BI_JPEG";
//                case BI_PNG:
//                    return "BI_PNG";
//                case BI_RGB:
//                    return "BI_RGB (uncompressed)";
//                case BI_RLE4:
//                    return "BI_RLE4";
//                case BI_RLE8:
//                    return "BI_RLE8";
//                case BI_1632:
//                    return "BI_1632";
//                default:
//                    return "UNKNOWN";
//            }
//        }
//
//    }

    protected Bitmap(ImageInputStream is, int width, int height) throws IOException {
        this.width = width;
        this.height = height;

        biSize = (int)is.readUnsignedInt();
        biWidth = is.readInt();
        biHeight = is.readInt();
        biPlanes = is.readUnsignedShort();
        biBitCount = is.readUnsignedShort();
        biCompression = (int)is.readUnsignedInt();
        biSizeImage = (int)is.readUnsignedInt();
        biXPelsPerMeter = is.readInt();
        biYPelsPerMeter = is.readInt();
        biColorsUsed = (int)is.readUnsignedInt();
        biColorsImportant = (int)is.readUnsignedInt();
    }


//    public static Bitmap getImageIoBitmap(IconImage entry) throws IOException {
//        return new BitmapImageIO(entry);
//    }

    public BufferedImage getImage() throws IOException {
        if (_cachedImage != null)
            return _cachedImage;

        _cachedImage = createImage();
        return _cachedImage;
    }


    protected abstract BufferedImage createImage() throws IOException;

    public String toString() {
        StringBuffer sb = new StringBuffer(100);
        String name = this.getClass().getName();
        int idx = name.lastIndexOf('.');
        sb.append(name.substring(idx + 1));
        sb.append(" [");
        try {
            java.util.LinkedList<Class> classes = new java.util.LinkedList<Class>();
            Class tmpC = this.getClass();
            while (tmpC != Bitmap.class && tmpC != Object.class && tmpC != null) {
                classes.addFirst(tmpC);
                tmpC = tmpC.getSuperclass();
            }
//      classes.addFirst(tmpC);
            append(sb, true, tmpC.getDeclaredFields());
            for (Class c : classes) {
                append(sb, false, c.getDeclaredFields());
            }
        } catch (IllegalAccessException ex) {
        } catch (IllegalArgumentException ex) {
        }
        sb.append("]");
        return sb.toString();
    }

    private void append(StringBuffer sb, boolean first, Field[] data) throws IllegalAccessException, IllegalArgumentException {

        for (Field f : data) {
            if (f.getName().equals("entry") || f.getName().equals("reader"))
                continue;
            if (!first)
                sb.append(", ");
            else
                first = false;
            sb.append(f.getName()).append("=");
            sb.append(f.get(this));
        }

    }

    // ========== static ==========

    public static Bitmap getBitmap(IconImage entry) throws IOException, IconManagerException {
        int bitsPerPixel = entry.getHeader().getBitsPerPixel();
        int width = entry.getHeader().getImageKey().getWidth();
        int height = entry.getHeader().getImageKey().getHeight();

        if (bitsPerPixel == 1 || bitsPerPixel == 2 || bitsPerPixel == 4 || bitsPerPixel == 8)
            return IndexedBitmap.read(entry.getData(), width, height);
        if (bitsPerPixel == 16)
            return RgbBitmap.read(entry.getData(), 2, width, height);
        if (bitsPerPixel == 24)
            return RgbBitmap.read(entry.getData(), 3, width, height);
        if (bitsPerPixel == 32)
            return RgbBitmap.read(entry.getData(), 4, width, height);

        throw new IconManagerException("Unsupported image, bitsPerPixel=" + bitsPerPixel);
    }
}
