package cop.common.enums;

import com.devexperts.tos.ui.admin.controls.nameprovider.LocalizableEnumNameProvider;

public class ColorNameProvider extends LocalizableEnumNameProvider<Color> {
	private static final ColorNameProvider INSTANCE = new ColorNameProvider();

	public static final String LONG_NAME = "LONG";

	public static ColorNameProvider create() {
		return INSTANCE;
	}

	private ColorNameProvider() {}
}
