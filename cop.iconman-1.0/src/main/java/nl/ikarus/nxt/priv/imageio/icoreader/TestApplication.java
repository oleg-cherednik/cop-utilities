package nl.ikarus.nxt.priv.imageio.icoreader;
/**
 * ICOReader (ImageIO compatible class for reading ico files)
 * Copyright (C) 2005 J.B. van der Burgh
 * contact me at: icoreader (at) vdburgh.tmfweb.nl
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License along
 * with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
//import nl.ikarus.nxt.priv.imageio.icoreader.lib.tools.*;
import nl.ikarus.nxt.priv.imageio.icoreader.obj.*;
import java.util.*;
//import com.borland.jbcl.layout.*;
import nl.ikarus.nxt.priv.imageio.icoreader.lib.*;
import javax.imageio.*;
import javax.imageio.stream.*;
import javax.imageio.metadata.*;
import org.w3c.dom.NodeList;

/**
 * This test application parses the specified ico and displays it at (zoomed in 4x) in a frame with a red background so you may be able to figure out which pixels are transparent
 * @author J.B. van der Burgh
 * @version 1.0
 */
public class TestApplication {
  public TestApplication(String path) {
    super();

    java.util.List<Image> imageList = new ArrayList<Image>();
    File pathF = new File(path);
    if (pathF.isDirectory()) {
      File[] t = pathF.listFiles();
      for(File f : t) {
	//        if (!f.getName().equals("nxtfav.ico")) continue;
	//if (!f.getName().equals("IE PimpeD-32bit.ico")) continue;
//        if (!f.getName().equals("Word XP2-24bit.ico")) continue;

	if (f.getName().toLowerCase().endsWith(".ico")) {
	  java.util.List<Image> lst=getImage(f);
	  if (lst != null) imageList.addAll(lst);
	}
      }
    } else {
//      Image im=getImage(pathF);
      java.util.List<Image> lst=getImage(pathF);
      if (lst != null) imageList.addAll(lst);
    }
	JFrame f = new JFrame("TEST");
	//      f.setBackground(Color.yellow);
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	JScrollPane x=new JScrollPane();
	//VerticalFlowLayout lay=new VerticalFlowLayout();
	FlowLayout lay=new FlowLayout();
//	lay.setHorizontalFill(false);
//	lay.setVerticalFill(false);
	JPanel contentPane = new JPanel();
	contentPane.setLayout(lay);
	System.out.println("Fetched: " + imageList.size() + " images");
	for(Image i : imageList) {
	  Image img = i;
	  String label = " ";
	  if (i instanceof NxtImageHolder) {
	    img = ((NxtImageHolder)i).im;
	    label = ((NxtImageHolder)i).label;
	  }
	  int width = 20;
	  int height = (int)((20d / img.getWidth(null)) * img.getHeight(null));
	  Image imScaled = img.getScaledInstance(width,height,Image.SCALE_SMOOTH);
	  JPanel p = new JPanel(new BorderLayout());
	  p.setOpaque(false);
	  p.setBorder(BorderFactory.createLineBorder(Color.black,1));
	  //JPanel pCenter = new JPanel(new VerticalFlowLayout(false,false));
	  JPanel pCenter = new JPanel(new FlowLayout());
	  pCenter.setOpaque(false);
	  //JPanel pEast = new JPanel(new VerticalFlowLayout(false,false));
	  JPanel pEast = new JPanel(new FlowLayout());
	  pEast.setOpaque(false);
	  p.add(pCenter,BorderLayout.CENTER);
	  p.add(pEast,BorderLayout.EAST);
	  JLabel scaledLbl = new JLabel(new ImageIcon(imScaled));
	  pEast.add(scaledLbl);
	  pEast.add(new JLabel("20x"+height));

	  JLabel lbl = new JLabel(new ImageIcon(img));
          lbl.setBorder(BorderFactory.createLineBorder(Color.red,1));
	  lbl.setOpaque(false);

    //      p.add(lbl,BorderLayout.CENTER);
	  JLabel lbl2 = new JLabel(label);
  //        lbl2.setBorder(BorderFactory.createLineBorder(Color.black,1));
  //        p.add(lbl2,BorderLayout.EAST);
//	  contentPane.add(p);
	  pCenter.add(lbl);
	  pCenter.add(lbl2);
	  contentPane.add(p);
	}
	x.getViewport().add(contentPane);
	f.getContentPane().setLayout(new BorderLayout());
	f.getContentPane().add(x);
//	f.getContentPane().setBackground(Color.red);
//        contentPane.setBackground(Color.WHITE);
        contentPane.setBackground(Color.LIGHT_GRAY);
	f.pack();
	Dimension d = f.getSize();
	d.width += 15;
	d.height += 15;
	d.width = Math.max(d.width, d.height);
	d.height = Math.max(d.width, d.height);
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	screenSize.height -= 100;
	screenSize.width  -= 100;
	d.width = Math.min(d.width, screenSize.width);
	d.height = Math.min(d.height,screenSize.height);
	f.setSize(d);
	f.setLocation(25, 25);
	f.setVisible(true);
  }

  private java.util.List<Image> getImage(File path) {
    System.out.println("Fetch image: " + path.getAbsolutePath());
    try {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      FileInputStream in = new FileInputStream(path);
      byte[] buff = new byte[512];
      int off;
      while ( (off = in.read(buff)) != -1) {
	out.write(buff, 0, off);
      }
      BufferedImage im=null;
      java.util.List<BufferedImage> imList = null;
      if (imageio)
        imList = getImageImageIO(out.toByteArray());
      else
        imList = getImageNEW(out.toByteArray());
      if (imList == null || imList.size() == 0) {
	System.err.println("Error fetching image " + this.getClass().getName());
	return null;
      }
      java.util.List<Image> res = new ArrayList<Image>();
      /*
      final int MULITPLIER = 4;
      java.util.List<Image> res = new ArrayList<Image>();
      for(BufferedImage bim : imList) {
	Image im2 = bim.getScaledInstance(bim.getWidth(null) * MULITPLIER, bim.getHeight(null) * MULITPLIER, Image.SCALE_SMOOTH);
	res.add(im2);
      }*/
      res.addAll(imList);
      return res;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }
  class NxtImageHolder extends BufferedImage {
    BufferedImage im;
    String label;
    public NxtImageHolder(BufferedImage source, String label) {
      super(1,1,BufferedImage.TYPE_INT_RGB);
      this.im=source;
      this.label=label;
    }
    public Image getScaledInstance(int i1, int i2, int i3) {
      return im.getScaledInstance(i1,i2,i3);
    }

  }

  private java.util.List<BufferedImage> getImageNEW(byte[] data) throws ImageReaderException {
    java.util.List<BufferedImage> images = new java.util.ArrayList<BufferedImage>();
    try {
      ICOFile f = new ICOFile(data);

      Iterator<IconEntry> it= f.getEntryIterator();
      while(it.hasNext()) {
	IconEntry e = it.next();
	System.out.println("Entry: " + e);
	Bitmap b = e.getBitmap();
	System.out.println("Bitmap: " + b);
	if (b != null) {
	  BufferedImage bi = b.getImage();
	  if (bi != null) {
	    NxtImageHolder tmp = new NxtImageHolder(bi,e.getWidth() + "x" + e.getHeight() + "x" + e.getBitCount());
	    images.add(tmp);
	  }
	}
      }
    } catch (IOException ex) {
      throw new ImageReaderException(ex);
    }
    return images;
  }

  private java.util.List<BufferedImage> getImageImageIO(byte[] data) throws ImageReaderException {
    java.util.List<BufferedImage> images = new java.util.ArrayList<BufferedImage>();
    try {
      ByteArrayInputStream bIn = new ByteArrayInputStream(data);
      ImageInputStream in = ImageIO.createImageInputStream(bIn);
      Iterator<ImageReader> it2 = ImageIO.getImageReaders(in);

      while(it2.hasNext()) {
        ImageReader r = it2.next();
        System.err.println("Using: " + r.getClass().getName());
        try {
          r.setInput(in);
          int nr = r.getNumImages(true);
          if (nr == -1)
            System.err.println("Unknown amount of images");
          else {
            for (int i = 0; i < nr; i++) {
                int w = r.getWidth(i);
                int h = r.getHeight(i);
                String bpp = "?";
                IIOMetadata meta = r.getImageMetadata(i);
                IIOMetadataNode n = (IIOMetadataNode)meta.getAsTree(meta.getNativeMetadataFormatName());

                if (n.hasChildNodes()) {
                  org.w3c.dom.NodeList nl = n.getChildNodes();
                  for (int childNr=0;childNr<nl.getLength();childNr++) {
                    IIOMetadataNode child =(IIOMetadataNode) nl.item(childNr);
                    String key =
                        child.getAttribute("keyword");
                    System.out.println("Found keyword: " + key);
                    if (key != null && key.equals("bpp")) {
                      bpp = child.getAttribute("value");
                      System.out.println("Value: " + bpp);
                      break;
                    }
                  }
                }

                System.out.println("reading image: " + i + "   w x h  = "+w+"x"+h+ "x" + bpp);
                BufferedImage bImg = r.read(i);
                if (bImg != null) {
                  NxtImageHolder tmp = new NxtImageHolder(bImg, w + "x" + h + "x" + bpp);
                  images.add(tmp);
                }
            }
          }
        }catch(Exception ex) {System.err.println(ex.getMessage());
//          bIn.reset();
          bIn = new ByteArrayInputStream(data);
          in = ImageIO.createImageInputStream(bIn);
        }
      }
    } catch (IOException ex) {
      throw new ImageReaderException(ex);
    }
    return images;
  }
  static boolean imageio = false;

  /**
   *
   * @param args String[] - the first parameter should be the path to the ico file
   */
  public static void main(String[] args) {
    if (args.length ==0) {
      System.out.println("specify the path to the ico file");
      return;
    }
    Class c = ICOReaderSpi.class;
    ICOReaderSpi
        .registerIcoReader();
    ICOWriterSpi
        .registerIcoWriter();

    if (args[0].equalsIgnoreCase("-newEngine")) {
      new TestApplication(args[1]);
    }else if (args[0].equalsIgnoreCase("-imageio")) {
      imageio = true;
      new TestApplication(args[1]);
    } else if (args[0].equalsIgnoreCase("-convert") && args.length > 2) {
      convert(args[1],args[2]);
    } else {
      new TestApplication(args[0]);
    }
  }
  private static void convert(String source, String dest) {
    File destF = new File(dest);
    File srcF = new File(source);
    if (destF.exists()) throw new UnsupportedOperationException("destination file already exists.... please point to a non-existing file "+destF.getAbsolutePath());
    if (!srcF.exists()) throw new UnsupportedOperationException("source file doesn't exist.... please point to an existing file "+srcF.getAbsolutePath());
    try {
      ImageInputStream in = ImageIO.createImageInputStream(new FileInputStream(srcF));
      Iterator<ImageReader> it = ImageIO.getImageReaders(in);
      while(it.hasNext()) {
        ImageReader r = it.next();
        try {
          System.out.println("Trying reader " + r.getClass().getName());
          ImageInputStream in2 = ImageIO.createImageInputStream(new FileInputStream(srcF));
          r.setInput(in2);
          int nr = r.getNumImages(true);
          if (nr == -1) {
            System.err.println("Unknown number of image");
          } else {
            for (int i = 0; i < nr; i++) {
              BufferedImage img = r.read(i);

              BufferedImage imgNew = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_RGB);
              imgNew.getGraphics().drawImage(img,0,0,null);
              img=imgNew;


              //ImageWriter w = ImageIO.getImageWriter(r);
//              ICOWriter w = ICOWriter;
              ICOWriterSpi.registerIcoWriter();
              ImageWriter w = ImageIO.getImageWritersBySuffix("ico").next();

              if (w == null) {
                System.out.println("No writer for reader " + r.getClass().getName());
                break;
              }
              System.out.println(".  Trying writer " + w.getClass().getName());
              ImageInputStream imgIn = ImageIO.createImageInputStream(img);
              File tmpDest = new File(destF.getAbsolutePath() + "-" + i + ".ico");
              ImageOutputStream imgOut = ImageIO.createImageOutputStream(new FileOutputStream(tmpDest,false));
              w.setOutput(imgOut);
              w.write(img);
            }
          }
        }catch(Exception ex) {
          ex.printStackTrace();
        }
      }

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
