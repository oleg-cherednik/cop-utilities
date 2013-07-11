package cop.icoman;

import nl.ikarus.nxt.priv.imageio.icoreader.lib.ICOReaderSpi;
import nl.ikarus.nxt.priv.imageio.icoreader.lib.ICOWriterSpi;
import nl.ikarus.nxt.priv.imageio.icoreader.lib.ImageReaderException;
import nl.ikarus.nxt.priv.imageio.icoreader.obj.Bitmap;
import nl.ikarus.nxt.priv.imageio.icoreader.obj.ICOFile;
import nl.ikarus.nxt.priv.imageio.icoreader.obj.IconEntry;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class TestApplication extends JFrame implements FilenameFilter {
	public TestApplication() {
		super("Example");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		List<Image> images = getImages(this);

		JPanel contentPane = new JPanel();
		System.out.println("Fetched: " + images.size() + " images");

		for (Image image : images) {
			Image img = image;
			String label = " ";
			if (image instanceof NxtImageHolder) {
				img = ((NxtImageHolder)image).im;
				label = ((NxtImageHolder)image).label;
			}
			int width = 20;
			int height = (int)((20d / img.getWidth(null)) * img.getHeight(null));
			Image imScaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			JPanel centerPanel = createPanel(createLabelIcon(img), new JLabel(label));
			JPanel eastPanel = createPanel(createLabelIcon(imScaled), new JLabel("20x" + height));

			contentPane.add(createPanel(centerPanel, eastPanel));
		}
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new JScrollPane(contentPane));
//	frame.getContentPane().setBackground(Color.red);
//        contentPane.setBackground(Color.WHITE);
		contentPane.setBackground(Color.LIGHT_GRAY);
		pack();
		Dimension d = getSize();
		d.width += 15;
		d.height += 15;
		d.width = Math.max(d.width, d.height);
		d.height = Math.max(d.width, d.height);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenSize.height -= 100;
		screenSize.width -= 100;
		d.width = Math.min(d.width, screenSize.width);
		d.height = Math.min(d.height, screenSize.height);
		setSize(d);
		setLocation(25, 25);
	}

	private static List<Image> getImages(FilenameFilter filter) {
		List<Image> images = new ArrayList<>();

		for (String name : new File("/").list(filter))
			images.addAll(getImage(new File("/", name)));

		return images.isEmpty() ? Collections.<Image>emptyList() : Collections.unmodifiableList(images);
	}

	private static java.util.List<Image> getImage(File path) {
		System.out.println("Fetch image: " + path.getAbsolutePath());
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			FileInputStream in = new FileInputStream(path);
			byte[] buff = new byte[512];
			int off;
			while ((off = in.read(buff)) != -1) {
				out.write(buff, 0, off);
			}
			BufferedImage im = null;
			java.util.List<BufferedImage> imList = null;
			if (imageio)
				imList = getImageImageIO(out.toByteArray());
			else
				imList = getImageNEW(out.toByteArray());
			if (imList == null || imList.size() == 0) {
				System.err.println("Error fetching image " + TestApplication.class.getName());
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
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	static class NxtImageHolder extends BufferedImage {
		BufferedImage im;
		String label;

		public NxtImageHolder(BufferedImage source, String label) {
			super(1, 1, BufferedImage.TYPE_INT_RGB);
			this.im = source;
			this.label = label;
		}

		public Image getScaledInstance(int i1, int i2, int i3) {
			return im.getScaledInstance(i1, i2, i3);
		}

	}

	private static List<BufferedImage> getImageNEW(byte[] data) throws ImageReaderException {
		java.util.List<BufferedImage> images = new ArrayList<BufferedImage>();
		try {
			ICOFile f = new ICOFile(data);

			Iterator<IconEntry> it = f.getEntryIterator();
			while (it.hasNext()) {
				IconEntry e = it.next();
				System.out.println("Entry: " + e);
				Bitmap b = e.getBitmap();
				System.out.println("Bitmap: " + b);
				if (b != null) {
					BufferedImage bi = b.getImage();
					if (bi != null) {
						NxtImageHolder tmp = new NxtImageHolder(bi,
								e.getWidth() + "x" + e.getHeight() + "x" + e.getBitCount());
						images.add(tmp);
					}
				}
			}
		} catch(IOException ex) {
			throw new ImageReaderException(ex);
		}
		return images;
	}

	private static List<BufferedImage> getImageImageIO(byte[] data) throws ImageReaderException {
		java.util.List<BufferedImage> images = new ArrayList<>();
		try {
			ByteArrayInputStream bIn = new ByteArrayInputStream(data);
			ImageInputStream in = ImageIO.createImageInputStream(bIn);
			Iterator<ImageReader> it2 = ImageIO.getImageReaders(in);

			while (it2.hasNext()) {
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
								NodeList nl = n.getChildNodes();
								for (int childNr = 0; childNr < nl.getLength(); childNr++) {
									IIOMetadataNode child = (IIOMetadataNode)nl.item(childNr);
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

							System.out.println("reading image: " + i + "   w x h  = " + w + "x" + h + "x" + bpp);
							BufferedImage bImg = r.read(i);
							if (bImg != null) {
								NxtImageHolder tmp = new NxtImageHolder(bImg, w + "x" + h + "x" + bpp);
								images.add(tmp);
							}
						}
					}
				} catch(Exception ex) {
					System.err.println(ex.getMessage());
//          bIn.reset();
					bIn = new ByteArrayInputStream(data);
					in = ImageIO.createImageInputStream(bIn);
				}
			}
		} catch(IOException ex) {
			throw new ImageReaderException(ex);
		}
		return images;
	}

	static boolean imageio = false;

	/** @param args String[] - the first parameter should be the path to the ico file */
	public static void main(String[] args) {
//		if (args.length == 0) {
//			System.out.println("specify the path to the ico file");
//			return;
//		}
		Class c = ICOReaderSpi.class;
		ICOReaderSpi
				.registerIcoReader();
		ICOWriterSpi
				.registerIcoWriter();

//		if (args[0].equalsIgnoreCase("-newEngine")) {
//			new TestApplication(args[1]);
//		} else if (args[0].equalsIgnoreCase("-imageio")) {
		imageio = true;
//			new TestApplication(args[1]);
//		} else if (args[0].equalsIgnoreCase("-convert") && args.length > 2) {
//			convert(args[1], args[2]);
//		} else {
		new TestApplication().setVisible(true);
//		}
	}

	private static void convert(String source, String dest) {
		File destF = new File(dest);
		File srcF = new File(source);
		if (destF.exists()) throw new UnsupportedOperationException(
				"destination file already exists.... please point to a non-existing file " + destF.getAbsolutePath());
		if (!srcF.exists()) throw new UnsupportedOperationException(
				"source file doesn't exist.... please point to an existing file " + srcF.getAbsolutePath());
		try {
			ImageInputStream in = ImageIO.createImageInputStream(new FileInputStream(srcF));
			Iterator<ImageReader> it = ImageIO.getImageReaders(in);
			while (it.hasNext()) {
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

							BufferedImage imgNew = new BufferedImage(img.getWidth(), img.getHeight(),
									BufferedImage.TYPE_INT_RGB);
							imgNew.getGraphics().drawImage(img, 0, 0, null);
							img = imgNew;


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
							ImageOutputStream imgOut = ImageIO.createImageOutputStream(new FileOutputStream(tmpDest,
									false));
							w.setOutput(imgOut);
							w.write(img);
						}
					}
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}

		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	// ========== FilenameFilter ==========

	@Override
	public boolean accept(File dir, String name) {
		return name != null && name.endsWith(".ico");
	}

	// ========== static ==========

	private static JLabel createLabelIcon(Image image) {
		JLabel label = new JLabel(new ImageIcon(image));

		label.setBorder(BorderFactory.createEtchedBorder());
		label.setOpaque(false);

		return label;
	}

	private static JPanel createPanel(Component... components) {
		JPanel panel = new JPanel();

		panel.setOpaque(false);

		for (Component comp : components)
			panel.add(comp);

		return panel;
	}

	private static JPanel createPanel(JPanel centerPanel, JPanel eastPanel) {
		JPanel panel = new JPanel(new BorderLayout());

		panel.setOpaque(false);
		panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		panel.add(centerPanel, BorderLayout.CENTER);
		panel.add(eastPanel, BorderLayout.EAST);

		return panel;
	}
}
