package cop.yandex.downloader.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import cop.yandex.downloader.DownloadStatus;
import cop.yandex.downloader.requests.Download;

public class DownloadManagerExample extends JFrame implements Observer {
	private static final int BUTTON_PAUSE = 0x1;
	private static final int BUTTON_RESUME = 0x2;
	private static final int BUTTON_CANCEL = 0x4;
	private static final int BUTTON_CLEAR = 0x8;

	private JTextField addTextField = new JTextField(30);

	private DownloadsTableModel tableModel = new DownloadsTableModel();

	private JTable table;

	private JButton pauseButton = new JButton("Pause");
	private JButton resumeButton = new JButton("Resume");
	private JButton cancelButton, clearButton;
	private Download selectedDownload;

	private boolean clearing;

	public DownloadManagerExample() {
		setTitle("Download Manager");
		setSize(640, 480);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
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
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				tableSelectionChanged();
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		ProgressRenderer renderer = new ProgressRenderer(0, 100);
		renderer.setStringPainted(true); // show progress text
		table.setDefaultRenderer(JProgressBar.class, renderer);

		table.setRowHeight((int) renderer.getPreferredSize().getHeight());

		JPanel downloadsPanel = new JPanel();
		downloadsPanel.setBorder(BorderFactory.createTitledBorder("Downloads"));
		downloadsPanel.setLayout(new BorderLayout());
		downloadsPanel.add(new JScrollPane(table), BorderLayout.CENTER);

		JPanel buttonsPanel = new JPanel();

		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPause();
			}
		});
		pauseButton.setEnabled(false);
		buttonsPanel.add(pauseButton);

		resumeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionResume();
			}
		});
		resumeButton.setEnabled(false);
		buttonsPanel.add(resumeButton);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionCancel();
			}
		});
		cancelButton.setEnabled(false);
		buttonsPanel.add(cancelButton);
		clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionClear();
			}
		});
		clearButton.setEnabled(false);
		buttonsPanel.add(clearButton);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(addPanel, BorderLayout.NORTH);
		getContentPane().add(downloadsPanel, BorderLayout.CENTER);
		getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
	}

	private void actionAdd() {
		URL verifiedUrl = verifyUrl(addTextField.getText());
		if (verifiedUrl != null) {
			tableModel.addDownload(new Download(verifiedUrl));
			addTextField.setText(null);
		} else {
			JOptionPane.showMessageDialog(this, "Invalid Download URL", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private URL verifyUrl(String url) {
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

	private void tableSelectionChanged() {
		if (selectedDownload != null)
			selectedDownload.deleteObserver(DownloadManagerExample.this);

		if (!clearing && table.getSelectedRow() > -1) {
			selectedDownload = tableModel.getDownload(table.getSelectedRow());
			selectedDownload.addObserver(DownloadManagerExample.this);
			updateButtons();
		}
	}

	private void actionPause() {
		selectedDownload.pause();
		updateButtons();
	}

	private void actionResume() {
		selectedDownload.resume();
		updateButtons();
	}

	private void actionCancel() {
		selectedDownload.cancel();
		updateButtons();
	}

	private void actionClear() {
		clearing = true;
		tableModel.clearDownload(table.getSelectedRow());
		clearing = false;
		selectedDownload = null;
		updateButtons();
	}

	private void updateButtons() {
		DownloadStatusDecorator status = DownloadStatusDecorator.NONE;
		
		if(selectedDownload != null)
			status = DownloadStatusDecorator.parseStatus(selectedDownload.getStatus());
		
		pauseButton.setEnabled(isBitSet(status.availableButtons, BUTTON_PAUSE));
		resumeButton.setEnabled(isBitSet(status.availableButtons, BUTTON_PAUSE));
		cancelButton.setEnabled(isBitSet(status.availableButtons, BUTTON_PAUSE));
		clearButton.setEnabled(isBitSet(status.availableButtons, BUTTON_PAUSE));
	}

	public void update(Observable o, Object arg) {
		// Update buttons if the selected download has changed.
		if (selectedDownload != null && selectedDownload.equals(o))
			updateButtons();
	}

	// Run the Download Manager.
	public static void main(String[] args) {
		DownloadManagerExample manager = new DownloadManagerExample();
		manager.setVisible(true);
	}

	// ========== enum ==========

	private enum DownloadStatusDecorator {
		NONE(DownloadStatus.NONE), NEW(DownloadStatus.NEW), DOWNLOADING(DownloadStatus.NEW, BUTTON_PAUSE
				| BUTTON_CANCEL), PAUSED(DownloadStatus.NEW, BUTTON_RESUME | BUTTON_CANCEL), COMPLETE(
				DownloadStatus.NEW, BUTTON_CLEAR), CANCELLED(DownloadStatus.NEW, BUTTON_CLEAR), ERROR(
				DownloadStatus.NEW, BUTTON_RESUME | BUTTON_CLEAR);

		private final DownloadStatus status;
		private final int availableButtons;

		private DownloadStatusDecorator(DownloadStatus status) {
			this(status, 0x0);
		}

		private DownloadStatusDecorator(DownloadStatus status, int availableButtons) {
			this.status = status;
			this.availableButtons = availableButtons;
		}

		public final void updateButtons(DownloadManagerExample panel) {
			panel.pauseButton.setEnabled(isBitSet(availableButtons, BUTTON_PAUSE));
			panel.resumeButton.setEnabled(isBitSet(availableButtons, BUTTON_PAUSE));
			panel.cancelButton.setEnabled(isBitSet(availableButtons, BUTTON_PAUSE));
			panel.clearButton.setEnabled(isBitSet(availableButtons, BUTTON_PAUSE));
		}
		
		// ========== static ==========

		public static DownloadStatusDecorator parseStatus(DownloadStatus status) {
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