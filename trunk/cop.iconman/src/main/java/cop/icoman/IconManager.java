package cop.icoman;

import cop.icoman.imageio.bmp.IconBitmapReaderSpi;
import cop.icoman.imageio.ico.IconReaderSpi;

public final class IconManager {
	public static IconImageHeader createBitmaImage(String path) {
		return null;
	}

	private IconManager() {
	}

	public static void register() {
		IconReaderSpi.register();
		IconBitmapReaderSpi.register();
	}
}
