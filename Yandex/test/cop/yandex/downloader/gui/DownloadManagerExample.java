package cop.yandex.downloader.gui;

import java.awt.BorderLayout;
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

import cop.yandex.downloader.DownloadManager;
import cop.yandex.downloader.DownloadManagerListener;
import cop.yandex.downloader.Status;
import cop.yandex.downloader.requests.HttpDownloadRequest;

public class DownloadManagerExample extends JFrame implements Observer, ListSelectionListener, WindowListener,
		ActionListener, DownloadManagerListener {
	private static final long serialVersionUID = -1986271889775806286L;

	private static final int BUTTON_PAUSE = 0x1;
	private static final int BUTTON_RESUME = 0x2;
	private static final int BUTTON_CANCEL = 0x4;
	private static final int BUTTON_CLEAR = 0x8;

	private final DownloadManager manager = new DownloadManager(5);
	private final DownloadsTableModel tableModel = new DownloadsTableModel(manager);

	private final JTextField addTextField = new JTextField(30);
	private final JButton pauseButton = new JButton("Pause");
	private final JButton resumeButton = new JButton("Resume");
	private final JButton cancelButton = new JButton("Cancel");
	private final JButton clearButton = new JButton("Clear");

	private JTable table;
	private int currId = -1;

	public DownloadManagerExample() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		setTitle("Download Manager");
		setSize(640, 480);

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		JMenuItem fileExitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
		fileExitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(fileExitMenuItem);
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);

		// Set up add panel.
		JPanel addPanel = new JPanel();

		addPanel.add(addTextField);
		JButton addButton = new JButton("Add Download");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionAdd();
			}
		});
		addPanel.add(addButton);

		// Set up Downloads table.

		table = new JTable(tableModel);

		table.getSelectionModel().addListSelectionListener(this);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		ProgressRenderer renderer = new ProgressRenderer(0, 100);
		renderer.setStringPainted(true); // show progress text
		table.setDefaultRenderer(JProgressBar.class, renderer);

		table.setRowHeight((int)renderer.getPreferredSize().getHeight());

		JPanel downloadsPanel = new JPanel();
		downloadsPanel.setBorder(BorderFactory.createTitledBorder("Downloads"));
		downloadsPanel.setLayout(new BorderLayout());
		downloadsPanel.add(new JScrollPane(table), BorderLayout.CENTER);

		JPanel buttonsPanel = new JPanel();

		pauseButton.setEnabled(false);
		buttonsPanel.add(pauseButton);

		resumeButton.setEnabled(false);
		buttonsPanel.add(resumeButton);

		cancelButton.setEnabled(false);
		buttonsPanel.add(cancelButton);
		clearButton.setEnabled(false);
		buttonsPanel.add(clearButton);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(addPanel, BorderLayout.NORTH);
		getContentPane().add(downloadsPanel, BorderLayout.CENTER);
		getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

		addListeners();
	}

	private void addListeners() {
		addWindowListener(this);

		manager.addListener(this);
		manager.addObserver(this);

		pauseButton.addActionListener(this);
		resumeButton.addActionListener(this);
		cancelButton.addActionListener(this);
		clearButton.addActionListener(this);
	}

	private void actionAdd() {
		URL verifiedUrl = verifyUrl(addTextField.getText());

		if (verifiedUrl != null) {
			manager.addTask(new HttpDownloadRequest(verifiedUrl, new File("d:/tmp"), 1024));
			addTextField.setText(null);
		} else {
			JOptionPane.showMessageDialog(this, "Invalid Download URL", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private static URL verifyUrl(String url) {
		if (!url.toLowerCase().startsWith("http://"))
			return null;

		URL verifiedUrl = null;
		try {
			verifiedUrl = new URL(url);
		} catch (Exception e) {
			return null;
		}

		if (verifiedUrl.getFile().length() < 2)
			return null;

		return verifiedUrl;
	}

	private void updateButtons() {
		DownloadStatusDecorator status = DownloadStatusDecorator.NONE;

		if (currId >= 0)
			status = DownloadStatusDecorator.parseStatus(manager.getTaskStatus(currId));

		pauseButton.setEnabled(isBitSet(status.availableButtons, BUTTON_PAUSE));
		resumeButton.setEnabled(isBitSet(status.availableButtons, BUTTON_RESUME));
		cancelButton.setEnabled(isBitSet(status.availableButtons, BUTTON_CANCEL));
		clearButton.setEnabled(isBitSet(status.availableButtons, BUTTON_CLEAR));
	}

	public void update(Observable o, Object arg) {
		// Update buttons if the selected download has changed.
		// if (selectedDownload != null && selectedDownload.equals(o))
		updateButtons();
	}

	// Run the Download Manager.
	public static void main(String[] args) {
		DownloadManagerExample manager = new DownloadManagerExample();
		manager.setVisible(true);
	}

	// ========== ListSelectionListener ==========

	public void valueChanged(ListSelectionEvent event) {
		if (event.getSource() != table.getSelectionModel())
			return;

		int row = table.getSelectedRow();
		System.out.println(row);
		currId = tableModel.getTaskId(row);
		updateButtons();
	}

	// ========== WindowListener ==========

	@Override
	public void windowOpened(WindowEvent event) {}

	@Override
	public void windowClosing(WindowEvent event) {
		manager.dispose();
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent event) {}

	@Override
	public void windowIconified(WindowEvent event) {}

	@Override
	public void windowDeiconified(WindowEvent eventvent) {}

	@Override
	public void windowActivated(WindowEvent e) {}

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
		else if (event.getSource() == clearButton)
			tableModel.remove(manager.removeNotActiveTasks());
	}

	// ========== DownloadManagerListener ==========

	public void onTaskUpdate(int id) {
		updateButtons();
	}

	// ========== enum ==========

	private enum DownloadStatusDecorator {
		NONE(Status.NONE),
		NEW(Status.NEW),
		DOWNLOADING(Status.DOWNLOADING, BUTTON_PAUSE | BUTTON_CANCEL),
		PAUSED(Status.PAUSED, BUTTON_RESUME | BUTTON_CANCEL),
		COMPLETE(Status.COMPLETE, BUTTON_CLEAR),
		CANCELLED(Status.CANCELLED, BUTTON_CLEAR),
		FERROR(Status.ERROR, BUTTON_CLEAR);

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

	// ========== static ==========

	private static boolean isBitSet(int val, int mask) {
		return (val & mask) != 0;
	}
}
