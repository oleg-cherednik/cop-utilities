package cop.swing.iconman;

import cop.icoman.IconFile;
import cop.icoman.IconImage;
import cop.icoman.exceptions.IconManagerException;
import cop.swing.iconman.bitmap.Bitmap;

import javax.swing.*;
import java.io.IOException;

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
        if (icons[id] == null)
            icons[id] = new ImageIcon(getBitmap(iconFile.getImage(id)).getImage());

        return icons[id];
    }

    private Bitmap getBitmap(IconImage iconImage) throws IOException, IconManagerException {
        return Bitmap.getBitmap(iconImage);
    }
}
