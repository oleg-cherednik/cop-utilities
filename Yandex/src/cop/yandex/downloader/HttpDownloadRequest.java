package cop.yandex.downloader;

import java.io.File;
import java.net.URL;

/**
 * @author Oleg Cherednik
 * @since 16.02.2013
 */
public final class HttpDownloadRequest implements DownloadRequest {
	private final URL src;
	private final File dest;
	private final int buffSize;

	public HttpDownloadRequest(URL src, File dest, int buffSize) {
		this.src = src;
		this.dest = dest;
		this.buffSize = buffSize;
	}

	// ========== DownloadRequest

	public DownloadTask createTask() {
		return new HttpDownloadTask(src, dest, buffSize);
	}
}
