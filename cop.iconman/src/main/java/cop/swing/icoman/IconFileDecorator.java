package cop.swing.icoman;

import cop.icoman.IconFile;
import cop.icoman.IconImage;
import cop.icoman.exceptions.IconManagerException;
import cop.swing.icoman.bitmap.Bitmap;

import javax.swing.*;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author Oleg Cherednik
 * @since 05.07.2013
 */
public final class IconFileDecorator {
	private final IconFile iconFile;
	private final Icon[] icons;

	public IconFileDecorator(IconFile iconFile) {
		this.iconFile = iconFile;
		icons = new Icon[iconFile.getHeader().getImageCount()];
	}

	public Icon getIcon(int id) throws IOException, IconManagerException {
		return icons[id] == null ? icons[id] = new ImageIcon(getBitmap(iconFile.getImage(id)).getImage()) : icons[id];
	}

	public IconFile getIconFile() {
		return iconFile;
	}

	public int size() {
		return icons.length;
	}

	// ========== static ==========

	private static Bitmap getBitmap(IconImage iconImage) throws IOException, IconManagerException {
		return Bitmap.getBitmap(iconImage);
	}
}
