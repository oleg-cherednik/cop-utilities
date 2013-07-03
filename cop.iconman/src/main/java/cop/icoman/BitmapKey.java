package cop.icoman;

public class BitmapKey {
    private int width;
    private int height;
    private int colors;
    private int planesHor; // size: 2, offs: 0x4 (...)
    private int bitCountVer; // size: 2, offs: 0x6 (...)
    private int size; // size: 4, offs: 0x8 (bitmap data size)
    private int offs; // size: 4, offs: 0xC (bitmap data offset)

}
