package cop.icoman;

import cop.icoman.exceptions.IconManagerException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.ByteOrder;

public class ExampleMain extends JFrame implements FilenameFilter {
	public ExampleMain() throws IOException, IconManagerException {
		super("Example");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		init("testico");
	}

	private void init(String name) throws IOException, IconManagerException {
		IconManager.register();

		GridBagConstraints gbc = new GridBagConstraints();
		JPanel panel = new JPanel(new GridBagLayout());

		getContentPane().add(new JScrollPane(panel));
		gbc.gridwidth = GridBagConstraints.REMAINDER;
//		panel.setBackground(Color.green);

		IconFile iconFile = read(String.format("/%s.ico", name));

		for (int i = 0, total = iconFile.getImagesAmount(); i < total; i++) {
			IconImage iconImage = iconFile.getImage(i);
			JLabel icon = createLabelIcon(iconImage.getIcon());
			panel.add(createPanel(icon, new JLabel(iconImage.getHeader().getImageKey().toString())), gbc);
		}

		pack();
	}

	// ========== FilenameFilter ==========

	@Override
	public boolean accept(File dir, String name) {
		return name != null && name.endsWith(".ico");
	}

	// ========== static ==========

	public static void main(String[] args) throws IOException, IconManagerException {
		new ExampleMain().setVisible(true);
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

	private static JLabel createLabelIcon(Icon icon) {
		JLabel label = new JLabel(icon);

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
}
