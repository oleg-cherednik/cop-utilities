package cop.swing.icoman.bitmap;

import cop.icoman.IconImage;
import cop.icoman.exceptions.IconManagerException;

import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Field;

public abstract class Bitmap {
    /*
     * @see http://msdn.microsoft.com/library/default.asp?url=/library/en-us/gdi/bitmaps_1rw2.asp
     */
    private BufferedImage _cachedImage;
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

    protected final void readImage(ImageInputStream is) throws IOException {
        _cachedImage = createImage(is);
    }

//    public static Bitmap getImageIoBitmap(IconImage entry) throws IOException {
//        return new BitmapImageIO(entry);
//    }

    public final BufferedImage getImage() throws IOException {
        return _cachedImage;
    }

    protected abstract BufferedImage createImage(ImageInputStream is) throws IOException;

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

    // ========== Object ==========

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
