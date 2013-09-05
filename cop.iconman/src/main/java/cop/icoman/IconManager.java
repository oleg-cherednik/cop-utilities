package cop.icoman;

import cop.icoman.exceptions.DuplicateException;
import cop.icoman.exceptions.IconManagerException;
import cop.icoman.exceptions.IconNotFoundException;
import cop.icoman.imageio.bmp.IconBitmapReaderSpi;
import cop.icoman.imageio.ico.IconReaderSpi;
import cop.utils.StringUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.Icon;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Oleg Cherednik
 * @since 01.09.2013
 */
public final class IconManager {
	private static final IconManager INSTANCE = new IconManager();

	private final Map<String, IconFile> icons = new HashMap<>();

	public static IconManager getInstance() {
		return INSTANCE;
	}

	private IconManager() {
	}

	@NotNull
	public IconFile addIcon(String id, String filename) throws IconManagerException, IOException {
		IconFile icon = read(filename);
		addIcon(id, icon);
		return icon;
	}

	public void addIcon(String id, IconFile icon) throws IconManagerException {
		if (StringUtils.isBlank(id) || icon == null)
			throw new IconManagerException("id/icon is not set");
		if (icons.put(id, icon) != null)
			throw new DuplicateException(id);
	}

	public void removeIcon(String id) {
		icons.remove(id);
	}

	@NotNull
	public IconFile getIconFile(String id) throws IconNotFoundException {
		IconFile icon = icons.get(id);

		if (icon == null)
			throw new IconNotFoundException(id);

		return icon;
	}

	@NotNull
	public Icon getIcon(String id, ImageKey key) throws IconManagerException, IOException {
		return getIconFile(id).getImage(key).getIcon();
	}

	// ========== static ==========

	public static void register() {
		IconReaderSpi.register();
		IconBitmapReaderSpi.register();
	}

	private static IconFile read(String filename) throws IOException, IconManagerException {
		try (ImageInputStream in = ImageIO.createImageInputStream(IconFile.class.getResourceAsStream(filename))) {
			in.setByteOrder(ByteOrder.LITTLE_ENDIAN);

			IconFile icon = IconFile.read(in);

			if (in.read() != -1)
				throw new IconManagerException("End of the stream is not reached");

			return icon;
		}
	}
}
