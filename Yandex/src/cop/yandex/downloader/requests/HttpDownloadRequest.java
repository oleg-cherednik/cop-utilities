package cop.yandex.downloader.requests;

import java.io.File;
import java.net.URL;

import cop.yandex.downloader.DownloadTask;
import cop.yandex.downloader.HttpDownloadTask;

public class HttpDownloadRequest implements DownloadRequest {
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

	public String getSrc() {
		return src.toString();
	}

	public String getDest() {
		return dest.getAbsolutePath();
	}
}
