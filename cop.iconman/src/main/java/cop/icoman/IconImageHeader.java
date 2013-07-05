package cop.icoman;

import cop.icoman.exceptions.IconManagerException;

import java.io.DataInput;
import java.io.IOException;

/*
^ The classic BITMAPINFOHEADER bitmap format supports storing images with 32 bits per pixel. When saved as a standalone .BMP file, "the high byte in each [pixel] is not used". However, when this same data is stored inside a ICO or CUR file, Windows XP (the first Windows version to support ICO/CUR files with more than 1 bit of transparency) and above interpret this byte as an alpha value.
^ Although Microsoft's technical documentation states that this value must be zero, the icon encoder built into .NET (System.Drawing.IconFile.Save) sets this value to 255. It appears that the operating system ignores this value altogether.
^ Setting the color planes to 0 or 1 is treated equivalently by the operating system, but if the color planes are set higher than 1, this value should be multiplied by the bits per pixel to determine the final color depth of the image. It is unknown if the various Windows operating system versions are resilient to different color plane values.
^ The bits per pixel might be set to zero, but can be inferred from the other data; specifically, if the bitmap is not PNG compressed, then the bits per pixel can be calculated based on the length of the bitmap data relative to the size of the image. If the bitmap is PNG compressed, the bits per pixel are stored within the PNG data. It is unknown if the various Windows operating system versions contain logic to infer the bit depth for all possibilities if this value is set to zero.
 */
public final class IconImageHeader {
    public static final int SIZE = 16;

    private final int id;
    private final ImageKey key;
    // private int res; // size: 1, offs: 0x3 (0 or 255, ignored)
    private int colorPlanes; // size: 2, offs: 0x4 (...)
    private int bitsPerPixel; // size: 2, offs: 0x6 (...)
    private int size; // size: 4, offs: 0x8 (bitmap data size)
    private int offs; // size: 4, offs: 0xC (bitmap data offset)

    public static IconImageHeader readHeader(int id, DataInput in) throws IconManagerException, IOException {
        ImageKey key = ImageKey.readKey(in);

        skip(id, in);

        int colorPlanes = in.readShort();
        int bitsPerPixel = in.readShort();
        int size = in.readInt();
        int offs = in.readInt();

        check(key, colorPlanes, bitsPerPixel, size, offs);

        return new IconImageHeader(id, key, colorPlanes, bitsPerPixel, size, offs);
    }

    private IconImageHeader(int id, ImageKey key, int colorPlanes, int bitsPerPixel, int size, int offs) {
        this.id = id;
        this.key = key;
        this.colorPlanes = colorPlanes;
        this.bitsPerPixel = bitsPerPixel;
        this.size = size;
        this.offs = offs;
    }

    public int getId() {
        return id;
    }

    public ImageKey getImageKey() {
        return key;
    }

    public int getColorPlanes() {
        return colorPlanes;
    }

    public int getBitsPerPixel() {
        return bitsPerPixel;
    }

    public int getSize() {
        return size;
    }

    public int getOffs() {
        return offs;
    }

    // ========== Object ==========

    @Override
    public String toString() {
        return key + " " + bitsPerPixel + "bits";
    }

    // ========== static ==========

    private static void check(ImageKey key, int colorPlanes, int bitsPerPixel, int size, int offs) {
    }

    private static void skip(int id, DataInput in) throws IOException, IconManagerException {
        int val = in.readUnsignedByte();

        if (val != 0 && val != 255)
            throw new IconManagerException("'header offs:0, size:2' of image no. " + id + " is reserved, should be 0 or 255");
    }
}
