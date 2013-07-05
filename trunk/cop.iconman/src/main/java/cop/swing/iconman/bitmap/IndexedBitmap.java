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

import cop.icoman.exceptions.IconManagerException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteOrder;

public final class IndexedBitmap extends Bitmap {
    public static IndexedBitmap read(byte[] data, int width, int height) throws IOException, IconManagerException {
        try (ImageInputStream is = ImageIO.createImageInputStream(new ByteArrayInputStream(data))) {
            is.setByteOrder(ByteOrder.LITTLE_ENDIAN);
            return new IndexedBitmap(is, width, height);
        }
    }

    /*  protected int XORmaskSize;
      protected int ANDMaskSize;
      protected byte[] RGBQUAD;
      protected byte[] XOR;
      protected byte[] AND;
    */
    private IndexedBitmap(ImageInputStream is, int width, int height) throws IOException, IconManagerException {
        super(is, width, height);
        init(is);
    }

    private void init(ImageInputStream is) throws IOException, IconManagerException {
//    meta.put("XORmaskSize", ( (Integer) meta.get("biWidth")) * ( (Integer) (meta.get("biHeight")) / 2) * ( (Integer) meta.get("biBitCount")) / 8);

        this.XORmaskSize = biWidth * biHeight / 2 * biBitCount / 8;
//    this.XORmaskSize = ( (super.biWidth) * ( (Integer) (meta.get("biHeight")) / 2) * ( (Integer) meta.get("biBitCount")) / 8);
        int tmpL = Math.max(biWidth, 32) * biHeight / 2 / 8; // don't know if max() is the way to round up to long
        this.ANDMaskSize = (int)tmpL;
        {
            int length;
            if ((super.biBitCount <= 8)) {
                length = (int)Math.pow(2, super.biBitCount) * 4;
            } else {
                length = 0;
            }
            if (length > 500000) {
                throw new IconManagerException("RGBQUAD mask to large... " + length);
            }
            this.RGBQUAD = new byte[length];
            is.read(this.RGBQUAD);
        }
        //meta.put("RGBQUAD", CHUNK("RGBQUAD", (long) ( ( (Integer) meta.get("biBitCount") <= 8) ? (Math.pow(2, ( (Integer) meta.get("biBitCount"))) * 4) : 0)));

        if (this.XORmaskSize > 500000) {
            throw new IconManagerException("XOR mask to large... " + this.XORmaskSize);
        }
        this.XOR = new byte[this.XORmaskSize];
        is.read(this.XOR);

        if (this.ANDMaskSize > 500000) {
            throw new IconManagerException("AND mask to large... " + this.ANDMaskSize);
        }

        this.AND = new byte[ANDMaskSize];
        is.read(this.AND);
    }


    /**
     * createImage
     *
     * @return BufferedImage
     * @throws java.io.IOException
     * @todo Implement this nl.ikarus.nxt.priv.imageio.icoreader.obj.Bitmap
     * method
     */
    protected BufferedImage createImage() throws IOException {
        if (height < 1 || height < 1) {
            System.err.println("java.lang.IllegalArgumentException: Width (0) and height (0) cannot be <= 0");
            return null;
        }
        final Color TRANSPARENT = new Color(0, 0, 0, 0);
        BufferedImage image = new BufferedImage(height, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        //g.setBackground(Color.white);

        for (int y = this.biHeight / 2 - 1; y >= 0; y--) {
            for (int x = 0; x < this.biWidth; x++) {
                if (!hasAlpha(x, y)) {
                    g.setColor(getRGB(x, y));
                    //        int[] rgb = getRGB(x, y);
                    //      currentColor = new Color(rgb[0], rgb[1], rgb[2]);
                } else {
                    g.setColor(TRANSPARENT);
                }
                // System.out.println("FillRect: " + x +","+(h - y) + "     y="+y);
                /**
                 * changed: g.fillRect(x, h - y , 1, 1);
                 * to:  g.fillRect(x, h - y - 1, 1, 1);
                 *  because the icons were missing the bottom line of pixels
                 * I'm not sure this is the correct sollution, but it seems to work.
                 */
                g.fillRect(x, height - y - 1, 1, 1);
            }
        }
//System.out.println("--------");
        return image;
    }


    /**
     * returns the rgb value of the color
     *
     * @param xx int
     * @param yy int
     * @return int[]{red, green, blue} or int[]{red, green, blue,alpha}
     */
    private /*int[]*/Color getRGB(int xx, int yy) {
        int bbc = this.biBitCount; // (Integer) meta.get("biBitCount");
        if (bbc > 8) {
            System.err.println("This class can only handle bpp values of < 16.... (<=8 actually) but the current bpp value is " + bbc + "  you may get unexpected results");
        }
        int bbyte = yy * this.biWidth + xx; // en alpha es 32 fijo
        int pixelsPerByte = 8 / this.biBitCount; // can be 1 (biBitCount=8), 2 (biBitCount=4) or 8 (biBitCount=1)
        bbyte = (int)(bbyte / pixelsPerByte); // $n=$xx%8 $n => 0..7  7-$n => 7..0        0..7 0..1 0
        int shift = ((pixelsPerByte - (xx % pixelsPerByte) - 1) * this.biBitCount);
        int colIdx = (ord(((byte[])this.XOR)[bbyte]) >> shift) & ((1 << (this.biBitCount)) - 1);
        // 1 bit   8ppb   0,1,2,3,4,5,6,7   >> 0,1,2,3,4,5,6,7   % 8 = 0,1,2,3,4,5,6,7 * 1
        // 4 bits  2ppb   0,4               >> 0,4               % 2 = 0,1   * 4 = 0,4
        // 8 bits  1ppb   0                 >> 0                 % 1 = 0     * 8 = 0
        int b = ord(this.RGBQUAD[4 * colIdx]);
        int g = ord(this.RGBQUAD[4 * colIdx + 1]);
        int r = ord(this.RGBQUAD[4 * colIdx + 2]);

        return new Color(r, g, b);
    }


    /**
     * make unsigned
     *
     * @param c byte
     * @return int
     */
    private int ord(byte c) {
        return (int)((c < 0) ? c + 256 : c);
    }

    /**
     * ****************************
     * alpha, returns 1 if mask pixel
     * is transparent
     * *****************************
     */
    private boolean hasAlpha(int xx, int yy) {
        int bbyte = yy * 32 + xx; // super.biWidth... hmmm maybe aligned to long? that's it i think... fix this
        bbyte = (int)(bbyte / 8);
        int c = ord(this.AND[bbyte]);
        int res = (c >> (7 - xx % 8)) & 1;
        return (res == 1);
    }

}
