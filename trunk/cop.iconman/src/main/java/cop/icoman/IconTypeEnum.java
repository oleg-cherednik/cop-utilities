package cop.icoman;

import java.io.DataInput;
import java.io.IOException;
import java.text.ParseException;

public enum IconTypeEnum {
	NONE(0) {
		@Override
		public IconImage createBitmapImage(DataInput in) throws IOException {
			return null;
		}
	},
	ICO(1) {
		@Override
		public IconImage createBitmapImage(DataInput in) throws IOException {
			return new IconImage(in);
		}
	},
	CUR(2) {
		@Override
		public CursorImage createBitmapImage(DataInput in) throws IOException {
			return new CursorImage(in);
		}
	};

	private final int code;

	IconTypeEnum(int code) {
		this.code = code;
	}

	public abstract BitmapImage createBitmapImage(DataInput in) throws IOException;

	public static IconTypeEnum parseCode(int code) throws ParseException {
		for (IconTypeEnum type : values())
			if (type.code == code)
				return type;

		throw new ParseException("Unknown code: " + code, code);
	}
}
