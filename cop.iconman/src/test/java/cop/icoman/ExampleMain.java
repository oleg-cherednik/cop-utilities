package cop.icoman;

import cop.icoman.exceptions.IconManagerException;

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

		IconManager iconManager = IconManager.getInstance();
		IconFile iconFile = iconManager.addIcon(name, String.format("/%s.ico", name));

		for (ImageKey key : iconFile.getKeys()) {
			IconImage iconImage = iconManager.getIconFile(name).getImage(key);
			JLabel icon = createLabelIcon(iconManager.getIcon(name, key));
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
