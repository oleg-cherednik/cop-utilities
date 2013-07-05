package cop.swing.iconman;

import cop.icoman.IconFile;
import cop.icoman.exceptions.IconManagerException;

import javax.swing.Icon;
import java.awt.*;
import java.io.IOException;

/**
 * @author Oleg Cherednik
 * @since 05.07.2013
 */
public final class ImageIcon implements Icon {
    private final String filename;
    private final String description;
    private final IconFile icon;

    public ImageIcon(String filename) throws IOException, IconManagerException {
        this(filename, filename);
    }

    public ImageIcon(String filename, String description) throws IOException, IconManagerException {
        this.filename = filename;
        this.description = description;
        icon = IconFile.read(filename);


//        ImageIO.read();


//        Image img = ImageIO.read(getClass().getResource("resources/water.bmp"));


//        loadImage(image);
    }

    // ========== IconFile ==========

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
    }

    @Override
    public int getIconWidth() {
        return 1;
    }

    @Override
    public int getIconHeight() {
        return 2;
    }
}
