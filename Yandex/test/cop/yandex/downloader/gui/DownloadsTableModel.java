package cop.yandex.downloader.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JProgressBar;
import javax.swing.table.AbstractTableModel;

import cop.yandex.downloader.DownloadManager;
import cop.yandex.downloader.DownloadManagerListener;

class DownloadsTableModel extends AbstractTableModel implements DownloadManagerListener {
	private static final long serialVersionUID = 2547586107231907732L;

	private static final int COL_SRC = 0;
	private static final int COL_SIZE = 1;
	private static final int COL_PROGRESS = 2;
	private static final int COL_STATUS = 3;

	private static final String[] COL_NAMES = { "Source", "Size", "Progress", "Status" };
	private static final Class<?>[] COL_CLASSES = { String.class, String.class, JProgressBar.class, String.class };

	private final List<Integer> ids = new ArrayList<Integer>();
	private final DownloadManager manager;

	DownloadsTableModel(DownloadManager manager) {
		this.manager = manager;
		manager.addListener(this);
	}

	public int getColumnCount() {
		return COL_NAMES.length;
	}

	public int getTaskId(int pos) {
		return pos >= 0 && pos < ids.size() ? ids.get(pos) : -1;
	}

	public void remove(Set<Integer> ids) {
		if (ids == null)
			return;
		this.ids.removeAll(ids);
		fireTableDataChanged();
	}

	// ========== TableModel ==========

	@Override
	public String getColumnName(int col) {
		return COL_NAMES[col];
	}

	@Override
	public Class<?> getColumnClass(int col) {
		return COL_CLASSES[col];
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
			return getSize(manager.getBytesTotal(id));
		case COL_PROGRESS:
			long total = manager.getBytesTotal(id);
			long downloaded = manager.getBytesDownloaded(id);

			return total < 0 || downloaded < 0 ? 0 : ((double)downloaded / total) * 100;
		case COL_STATUS:
			return manager.getTaskStatus(id).getName();
		}

		return "";
	}

	// ========== DownloadManagerListener ==========

	@Override
	public void onTaskUpdate(int id) {
		if (ids.contains(id)) {
			int pos = ids.indexOf(id);
			fireTableRowsUpdated(pos, pos);
		} else {
			ids.add(id);
			int pos = ids.indexOf(id);
			fireTableRowsInserted(pos, pos);
		}
	}

	// ========== static ==========

	private static String getSize(long size) {
		return size != -1 ? Long.toString(size) : "N/A";
	}
}
