package cop.yandex.downloader.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;

import cop.yandex.downloader.DownloadManager;
import cop.yandex.downloader.DownloadManagerListener;
import cop.yandex.downloader.Status;
import cop.yandex.downloader.requests.HttpDownloadRequest;
import cop.yandex.downloader.requests.LanDownloadRequest;

/**
 * @author Oleg Cherednik
 * @since 16.02.2013
 */
public class DownloadManagerExample extends JFrame implements Observer, ListSelectionListener, WindowListener,
		ActionListener, DownloadManagerListener {
	private static final long serialVersionUID = -1986271889775806286L;

	private static final int BUTTON_PAUSE = 0x1;
	private static final int BUTTON_RESUME = 0x2;
	private static final int BUTTON_CANCEL = 0x4;
	private static final int BUTTON_CLEAR = 0x8;

	private final DownloadManager manager = new DownloadManager(10);
	private final DownloadsTableModel tableModel = new DownloadsTableModel(manager);
	private final JTable table = new JTable(tableModel);

	private final JTextField srcField = new JTextField(30);
	private final JTextField destField = new JTextField(30);
	private final JButton pauseButton = new JButton("Pause");
	private final JButton resumeButton = new JButton("Resume");
	private final JButton cancelButton = new JButton("Cancel");
	private final JButton clearButton = new JButton("Clear");
	private final JButton addButton = new JButton("Add");

	private final JMenuItem fileExitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);

	private int currId = -1;

	public DownloadManagerExample() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		setTitle("Download Manager");
		setSize(640, 480);

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileMenu.add(fileExitMenuItem);
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultRenderer(JProgressBar.class, createProgressRenderer());

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(createAddPanel(), BorderLayout.NORTH);
		getContentPane().add(createDownloadsPanel(), BorderLayout.CENTER);
		getContentPane().add(createButtonPanel(), BorderLayout.SOUTH);

		addListeners();
		updateButtons();
	}

	private JPanel createAddPanel() {
		JPanel panel = new JPanel(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.EAST;

		panel.add(new JLabel("Source:"), gbc);
		gbc.weightx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(srcField, gbc);

		gbc.gridwidth = 1;
		gbc.weightx = 0;
		panel.add(new JLabel("Destination path:"), gbc);
		gbc.weightx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(destField, gbc);

		panel.add(addButton, gbc);

		return panel;
	}

	private JPanel createDownloadsPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		panel.setBorder(BorderFactory.createTitledBorder("Downloads"));
		panel.add(new JScrollPane(table), BorderLayout.CENTER);

		return panel;
	}

	private JPanel createButtonPanel() {
		JPanel panel = new JPanel();

		panel.add(pauseButton);
		panel.add(resumeButton);
		panel.add(cancelButton);
		panel.add(clearButton);

		return panel;
	}

	private void addListeners() {
		addWindowListener(this);

		manager.addListener(this);
		table.getSelectionModel().addListSelectionListener(this);
		fileExitMenuItem.addActionListener(this);

		pauseButton.addActionListener(this);
		resumeButton.addActionListener(this);
		cancelButton.addActionListener(this);
		clearButton.addActionListener(this);
		addButton.addActionListener(this);
	}

	private void onAddButton() {
		String src = srcField.getText().trim();
		String dest = destField.getText().trim();

		if (src.isEmpty())
			JOptionPane.showMessageDialog(this, "Source is empty", "Error", JOptionPane.ERROR_MESSAGE);
		else if (dest.isEmpty())
			JOptionPane.showMessageDialog(this, "Destination is empty", "Error", JOptionPane.ERROR_MESSAGE);
		else {
			if (src.startsWith("http://")) {
				try {
					manager.addTask(new HttpDownloadRequest(new URL(src), new File(dest), 1024));
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				File file = new File(src);

				if (file.isFile())
					manager.addTask(new LanDownloadRequest(file, new File(dest), 1024));
				else
					JOptionPane.showMessageDialog(this, "Can't recognize source file", "Error",
							JOptionPane.ERROR_MESSAGE);
			}

			srcField.setText(null);
			destField.setText(null);
		}
	}

	private void updateButtons() {
		DownloadStatusDecorator status = DownloadStatusDecorator.NONE;

		if (currId >= 0)
			status = DownloadStatusDecorator.parseStatus(manager.getTaskStatus(currId).getStatus());

		pauseButton.setEnabled(isBitSet(status.availableButtons, BUTTON_PAUSE));
		resumeButton.setEnabled(isBitSet(status.availableButtons, BUTTON_RESUME));
		cancelButton.setEnabled(isBitSet(status.availableButtons, BUTTON_CANCEL));
		clearButton.setEnabled(isBitSet(status.availableButtons, BUTTON_CLEAR));
	}

	private void onExitMenu() {
		manager.dispose();
		System.exit(0);
	}

	// ========== Observer ==========

	public void update(Observable o, Object arg) {
		updateButtons();
	}

	// ========== ListSelectionListener ==========

	public void valueChanged(ListSelectionEvent event) {
		if (event.getSource() != table.getSelectionModel())
			return;

		currId = tableModel.getTaskId(table.getSelectedRow());
		updateButtons();
	}

	// ========== WindowListener ==========

	@Override
	public void windowOpened(WindowEvent event) {}

	@Override
	public void windowClosing(WindowEvent event) {
		onExitMenu();
	}

	@Override
	public void windowClosed(WindowEvent event) {}

	@Override
	public void windowIconified(WindowEvent event) {}

	@Override
	public void windowDeiconified(WindowEvent event) {}

	@Override
	public void windowActivated(WindowEvent event) {}

	@Override
	public void windowDeactivated(WindowEvent event) {}

	// ========== ActionListener ==========

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == pauseButton)
			manager.pauseTask(currId);
		else if (event.getSource() == resumeButton)
			manager.resumeTask(currId);
		else if (event.getSource() == cancelButton)
			manager.cancelTask(currId);
		else if (event.getSource() == addButton)
			onAddButton();
		else if (event.getSource() == clearButton)
			tableModel.remove(manager.removeNotActiveTasks());
		else if (event.getSource() == fileExitMenuItem)
			onExitMenu();
	}

	// ========== DownloadManagerListener ==========

	public void onTaskUpdate(int id) {
		updateButtons();
	}

	// ========== static ==========

	private static boolean isBitSet(int val, int mask) {
		return (val & mask) != 0;
	}

	private static TableCellRenderer createProgressRenderer() {
		ProgressRenderer renderer = new ProgressRenderer(0, 100);
		renderer.setStringPainted(true);
		return renderer;
	}

	public static void main(String[] args) {
		new DownloadManagerExample().setVisible(true);
	}

	// ========== enum ==========

	private enum DownloadStatusDecorator {
		NONE(Status.NONE),
		NEW(Status.NEW),
		DOWNLOADING(Status.DOWNLOADING, BUTTON_PAUSE | BUTTON_CANCEL),
		PAUSED(Status.PAUSED, BUTTON_RESUME | BUTTON_CANCEL),
		COMPLETE(Status.COMPLETE, BUTTON_CLEAR),
		CANCELLED(Status.CANCELLED, BUTTON_CLEAR | BUTTON_RESUME),
		ERROR(Status.ERROR, BUTTON_CLEAR | BUTTON_RESUME);

		private final Status status;
		private final int availableButtons;

		private DownloadStatusDecorator(Status status) {
			this(status, 0x0);
		}

		private DownloadStatusDecorator(Status status, int availableButtons) {
			this.status = status;
			this.availableButtons = availableButtons;
		}

		// ========== static ==========

		public static DownloadStatusDecorator parseStatus(Status status) {
			for (DownloadStatusDecorator decorator : values())
				if (decorator.status == status)
					return decorator;
			return NONE;
		}
	}
}
