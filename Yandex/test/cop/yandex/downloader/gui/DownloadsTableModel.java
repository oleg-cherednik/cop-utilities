package cop.yandex.downloader.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JProgressBar;
import javax.swing.table.AbstractTableModel;

import cop.yandex.downloader.DownloadManager;
import cop.yandex.downloader.DownloadManagerListener;
import cop.yandex.downloader.DownloadTask;

class DownloadsTableModel extends AbstractTableModel implements DownloadManagerListener {
	private static final long serialVersionUID = 2547586107231907732L;

	private static final int COL_SRC = 0;
	private static final int COL_SIZE = 1;
	private static final int COL_PROGRESS = 2;
	private static final int COL_STATUS = 3;

	private static final String[] columnNames = { "Source", "Size", "Progress", "Status" };
	private static final Class[] columnClasses = { String.class, String.class, JProgressBar.class, String.class };

	private final List<Integer> ids = new ArrayList<Integer>();
	private final DownloadManager manager;

	DownloadsTableModel(DownloadManager manager) {
		this.manager = manager;
		manager.addListener(this);
	}

	public void addDownload(DownloadTask download) {
		// download.addObserver(this);
		// downloadList.add(download);
		// fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
	}

	// public DownloadTask getDownload(int row) {
	// return downloadList.get(row);
	// }

	public void clearDownload(int row) {
		// downloadList.remove(row);
		// fireTableRowsDeleted(row, row);
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	// ========== TableModel ==========

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Class getColumnClass(int col) {
		return columnClasses[col];
	}

	public int getRowCount() {
		return ids.size();
	}

	public Object getValueAt(int row, int col) {
		int id = ids.get(row);

		switch (col) {
		case COL_SRC:
			return manager.getTaskSrc(id);
		case COL_SIZE:
			return getSize(manager.getSize(id));
		case COL_PROGRESS:
			return manager.getProgress(id);
		case COL_STATUS:
			return manager.getRequestStatus(id).getName();
		}

		return "";
	}

	// ========== DownloadManagerListener ==========

	@Override
	public void onTaskUpdate(int id) {
		if (!ids.contains(id))
			ids.add(id);

		int index = ids.indexOf(id);
		fireTableRowsInserted(index, index);
	}

	// ========== static ==========

	private static String getSize(int size) {
		return size != -1 ? Integer.toString(size) : "N/A";
	}
}
