package cop.yandex.downloader;

import java.io.File;

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
