package cop.icoman;

import cop.icoman.imageio.bmp.IconBitmapReaderSpi;
import cop.icoman.imageio.ico.IconReaderSpi;

/**
 * @author Oleg Cherednik
 * @since 01.09.2013
 */
public final class IconManager {
	private IconManager() {
	}

	// ========== static ==========

	public static void register() {
		IconReaderSpi.register();
		IconBitmapReaderSpi.register();
	}
}
