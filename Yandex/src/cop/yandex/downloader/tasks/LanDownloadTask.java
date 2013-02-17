package cop.yandex.downloader.tasks;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author Oleg Cherednik
 * @since 16.02.2013
 */
public final class LanDownloadTask extends DefaultDownloadTask {
	private final File src;

	public LanDownloadTask(File src, File dest, int buffSize) {
		super(dest, buffSize);

		this.src = src;
	}

	// ========== DownloadTask ==========

	@Override
	public String getSrc() {
		return src.getAbsolutePath();
	}

	// ========== DefaultDownloadTask =========

	@Override
	protected File createDestFile() {
		return dest.isFile() ? dest : new File(dest, src.getName());
	}

	@Override
	protected InputStream createInputStream() throws Exception {
		bytesTotal = src.length();
		return new FileInputStream(src);
	}
}
