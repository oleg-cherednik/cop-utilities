package nl.ikarus.nxt.priv.imageio.icoreader;

import static nl.ikarus.nxt.priv.imageio.icoreader.lib.ICOReaderSpi.registerIcoReader;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import nl.ikarus.nxt.priv.imageio.icoreader.obj.ICOFile;

public class Test {
  private final static Color BG_COLOR_1 = Color.white;
  private final static Color BG_COLOR_2 = Color.red;
  public Test() {
    super();
  }
  int x = 0, y = 25;
  private JFrame getNewFrame(String title) {
    JFrame frame = new JFrame(title);
    frame.getContentPane().setLayout(new BorderLayout());
    frame.setSize(300,200);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setLocation(x,y);

    x += Math.min(300,(int)( Toolkit.getDefaultToolkit().getScreenSize().width / 3));
    if (x > (Toolkit.getDefaultToolkit().getScreenSize().width - 100)) {
      x = 0;
      y += 100;
    }
    return frame;
  }
  private JPanel setMainPanel(JFrame frame, Color bgColor) {
    JPanel contentPane = new JPanel(new FlowLayout());
    contentPane.setBackground(bgColor);
    frame.getContentPane().add(new JScrollPane(contentPane));
    return contentPane;

  }
  public void showIcon(String path, Color bgColor) throws IOException {
    JFrame frame =  getNewFrame("All icons");
    JPanel contentPane=setMainPanel(frame,bgColor);
    addImages(contentPane,new File(path),false);
    frame.setVisible(true);

    String oldVal = System.setProperty("nl.ikarus.nxt.priv.imageio.icoreader.autoselect.icon","true");
    frame = getNewFrame("largest icon");
    contentPane = setMainPanel(frame,bgColor);
    addImages(contentPane, new File(path),true);
    frame.setVisible(true);
    System.setProperty("nl.ikarus.nxt.priv.imageio.icoreader.autoselect.icon",(oldVal!=null)?oldVal:"");


    frame = getNewFrame("Alternative way to get all icons");
   contentPane = setMainPanel(frame,bgColor);
   addImagesAlternative(contentPane, new File(path));
   frame.setVisible(true);



  }

  private void addImagesAlternative(JPanel contentPane , File file) throws IOException {
    ImageInputStream in = ImageIO.createImageInputStream(new FileInputStream(file));
    ICOFile f;
    f = new  nl.ikarus.nxt.priv.imageio.icoreader.obj.ICOFile(in);
    Iterator it = f.getEntryIterator();
    while(it.hasNext()) {
      nl.ikarus.nxt.priv.imageio.icoreader.obj.IconEntry ie = (nl.ikarus.nxt.priv.imageio.icoreader.obj.IconEntry) it.next();
      JButton b = new JButton();
      BufferedImage img = ie.getBitmap().getImage();
      b.setIcon(new ImageIcon(img));
      b.setText(ie.getWidth() + "x" + ie.getHeight() + "x" + ie.getBitCount() + "bit");
      b.setFocusPainted(false);
      b.setOpaque(false);
      b.setBackground(Color.white);

      contentPane.add(b);
    }

  }

  private void addImages(JPanel contentPane , File file, boolean onlyAddTheFirst) throws IOException {
    ImageInputStream in = ImageIO.createImageInputStream(new FileInputStream(file));
    Iterator it = ImageIO.getImageReaders(in);
    while(it.hasNext()) {
      try {
        ImageReader r = (ImageReader) it.next();
        r.setInput(in);
        if (onlyAddTheFirst) {
          BufferedImage im = r.read(0);
           JButton b = new JButton(new ImageIcon(im));
           b.setText(im.getWidth() + "x" + im.getHeight());
           b.setOpaque(false);
           b.setBackground(Color.white);
           b.setFocusPainted(false);

           contentPane.add(b);

        } else {
          int nr = r.getNumImages(true);
          for (int i = 0; i < nr; i++) {
            BufferedImage im = r.read(i);
            JButton b = new JButton(new ImageIcon(im));
            b.setText(im.getWidth() + "x" + im.getHeight());
            b.setOpaque(false);
            b.setBackground(Color.white);
            b.setFocusPainted(false);

            contentPane.add(b);
          }
        }
        //reading the image was successfull, so break the loop
        break;
      } catch (Exception ex) {
        in = ImageIO.createImageInputStream(new FileInputStream(file));
      }
    }
  }

  public static void main(String[] args) {
    try {
      registerIcoReader();
      if (args.length == 1 && new File(args[0]).exists() && args[0].toLowerCase().endsWith(".ico")) {
        Test app = new Test();
        app.showIcon(args[0], Test.BG_COLOR_1);
        app.x = 0;
        app.y = 225;
        app.showIcon(args[0], Test.BG_COLOR_2);
      } else {
        System.out.println("Please specify an ico file");
      }
    } catch (IOException ex) {
      ex.printStackTrace();
    }

  }
}
