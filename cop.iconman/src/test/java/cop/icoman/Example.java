package cop.icoman;

import cop.swing.icoman.IconFileDecorator;

import javax.swing.*;
import java.awt.*;
//import cop.swing.iconman.ImageIcon;

public class Example {
    public static void main(String[] arg) {
        try {
            String path = "/testico.ico";
            IconFile icon = IconFile.read(path);

            JDialog dialog = new JDialog();
            dialog.setTitle("Test");
            dialog.setModal(true);
            dialog.setSize(200, 500);

            Container parent = dialog.getContentPane();
            parent.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            gbc.gridwidth = GridBagConstraints.REMAINDER;
            JButton button0 = new JButton("btn0");
            JButton button1 = new JButton("btn1");
            JButton button2 = new JButton("btn2");
            JButton button3 = new JButton("btn3");
            JButton button4 = new JButton("btn4");
            JButton button5 = new JButton("btn5");
            JButton button6 = new JButton("btn6");
            JButton button7 = new JButton("btn7");
            JButton button8 = new JButton("btn8");
            JButton button9 = new JButton("btn9");

            button0.setPreferredSize(new Dimension(100, 50));
            button1.setPreferredSize(new Dimension(100, 50));
            button2.setPreferredSize(new Dimension(100, 50));
            button3.setPreferredSize(new Dimension(100, 50));
            button4.setPreferredSize(new Dimension(100, 50));
            button5.setPreferredSize(new Dimension(100, 50));
            button6.setPreferredSize(new Dimension(100, 50));
            button7.setPreferredSize(new Dimension(100, 50));
            button8.setPreferredSize(new Dimension(100, 50));
            button9.setPreferredSize(new Dimension(100, 50));

            ImageIcon imageIcon = new ImageIcon(Example.class.getResource(String.format("/%s.png", "db")));
            IconFileDecorator dec = new IconFileDecorator(IconFile.read(String.format("/%s.ico", "testico")));

            button0.setIcon(imageIcon);
            button1.setIcon(dec.getIcon(0));
            button2.setIcon(dec.getIcon(1));
            button2.setIcon(dec.getIcon(2));
            button2.setIcon(dec.getIcon(3));
            button2.setIcon(dec.getIcon(4));
//            button2.setIcon(dec.getIcon(5));
//            button2.setIcon(dec.getIcon(6));
//            button2.setIcon(dec.getIcon(7));
//            button2.setIcon(dec.getIcon(8));


//            button1.setIcon(new cop.swing.iconman.ImageIcon(path));
//            button2.setIcon(new javax.swing.ImageIcon("/db.png"));


            parent.add(button0, gbc);
            parent.add(button1, gbc);
            parent.add(button2, gbc);
            parent.add(button3, gbc);
            parent.add(button4, gbc);
//            parent.add(button5, gbc);
//            parent.add(button6, gbc);
//            parent.add(button7, gbc);
//            parent.add(button8, gbc);
//            parent.add(button9, gbc);


            dialog.setVisible(true);

            int a = 0;
            a++;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
