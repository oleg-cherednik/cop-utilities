package cop.icoman;

import java.io.DataInput;

import static cop.icoman.IconTypeEnum.parseCode;

public final class IconHeader {
	public static final IconHeader NULL = new IconHeader(IconTypeEnum.NONE, 0);

	private final IconTypeEnum type; // size: 2, offs: 0x2
	private final int imageCount; // size: 2, offs: 0x4

	public static IconHeader readHeader(DataInput in) throws Exception {
		in.skipBytes(2);
		return new IconHeader(parseCode(in.readUnsignedShort()), in.readUnsignedShort());
	}

	private IconHeader(IconTypeEnum type, int imageCount) {
		this.type = type != null ? type : IconTypeEnum.NONE;
		this.imageCount = imageCount >= 0 ? imageCount : 0;
	}

	public IconTypeEnum getType() {
		return type;
	}

	public int getImageCount() {
		return imageCount;
	}

	// ========== Object ==========

	@Override
	public String toString() {
		return type + ":" + imageCount;
	}
}
