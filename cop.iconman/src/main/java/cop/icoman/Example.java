package cop.icoman;

import cop.icoman.exceptions.IconManagerException;
import cop.swing.icon.IconFileDecorator;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.ByteOrder;
//import cop.swing.icon.ImageIcon;

public class Example {
    public static void main(String[] arg) {
        try {
            String path = "/testico.ico";
            IconFile icon = getIcon(path);

            JDialog dialog = new JDialog();
            dialog.setTitle("Test");
            dialog.setModal(true);
            dialog.setSize(500, 100);

            Container parent = dialog.getContentPane();
            parent.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            JButton button1 = new JButton("btn1");
            JButton button2 = new JButton("btn2");

            button1.setPreferredSize(new Dimension(100, 20));
            button2.setPreferredSize(new Dimension(100, 20));

            ImageIcon imageIcon = new ImageIcon(Example.class.getResource(String.format("/%s.png", "db")));
            IconFileDecorator dec = new IconFileDecorator(IconFile.read(String.format("/%s.ico", "testico")));

            button1.setIcon(imageIcon);
            button2.setIcon(dec.getIcon(0));


//            button1.setIcon(new cop.swing.icon.ImageIcon(path));
//            button2.setIcon(new javax.swing.ImageIcon("/db.png"));

            parent.add(button1, gbc);
            parent.add(button2, gbc);


            dialog.setVisible(true);

            int a = 0;
            a++;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static IconFile getIcon(String path) throws IOException, IconManagerException {
        try (ImageInputStream is = ImageIO.createImageInputStream(Example.class.getResourceAsStream(path))) {
            is.setByteOrder(ByteOrder.LITTLE_ENDIAN);

            IconFile icon = IconFile.read(is);

            if (is.read() != -1)
                throw new IconManagerException("End of the stream is not reached");

            return icon;
        }
    }
}
