package cop.yandex.downloader.requests;

import java.io.File;

import cop.yandex.downloader.tasks.DownloadTask;
import cop.yandex.downloader.tasks.LanDownloadTask;

/**
 * @author Oleg Cherednik
 * @since 16.02.2013
 */
public final class LanDownloadRequest implements DownloadRequest {
	private final File src;
	private final File dest;
	private final int buffSize;

	public LanDownloadRequest(File src, File dest, int buffSize) {
		this.src = src;
		this.dest = dest;
		this.buffSize = buffSize;
	}

	// ========== DownloadRequest

	public DownloadTask createTask() {
		return new LanDownloadTask(src, dest, buffSize);
	}
}
