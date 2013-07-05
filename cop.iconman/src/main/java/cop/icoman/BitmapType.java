package cop.icoman;

import cop.icoman.exceptions.IconManagerException;

import java.io.DataInput;
import java.io.IOException;

public enum BitmapType {
    NONE(0),
    ICO(1) {
        @Override
        public IconImageHeader createImageHeader(int id, DataInput in) throws IOException, IconManagerException {
            return IconImageHeader.readHeader(id, in);
        }
    },
    CUR(2);

    private final int code;

    BitmapType(int code) {
        this.code = code;
    }

    public IconImageHeader createImageHeader(int id, DataInput in) throws IOException, IconManagerException {
        throw new IconManagerException("'header.type' " + name() + " if image no. " + id + " + is not supported");
    }

    public static BitmapType parseCode(int code) {
        for (BitmapType type : values())
            if (type.code == code)
                return type;

        return BitmapType.NONE;
    }
}