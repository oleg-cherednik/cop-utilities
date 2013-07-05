package cop.swing.iconman;

import cop.icoman.IconFile;
import nl.ikarus.nxt.priv.imageio.icoreader.obj.Bitmap;

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

    public Icon getIcon(int id) {
        if (icons[id] == null)
            icons[id] = new javax.swing.ImageIcon(iconFile.getImage(id).getData());

        return icons[id];
    }

    private Bitmap getBitmap() throws IOException {
        return Bitmap.getBitmap(this);
    }
}
