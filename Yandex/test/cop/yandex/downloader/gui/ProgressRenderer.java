package cop.yandex.downloader.gui;

import java.awt.Component;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

class ProgressRenderer extends JProgressBar implements TableCellRenderer {
	private static final long serialVersionUID = 1L;

	public ProgressRenderer(int min, int max) {
		super(min, max);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		setValue((int)((Double)value).doubleValue());
		return this;
	}
}
