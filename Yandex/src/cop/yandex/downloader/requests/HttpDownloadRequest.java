package cop.yandex.downloader.requests;

import java.io.File;
import java.net.URL;

import cop.yandex.downloader.DownloadTask;
import cop.yandex.downloader.HttpDownloadTask;

public class HttpDownloadRequest implements DownloadRequest {
	private final URL src;
	private final File dest;

	public HttpDownloadRequest(URL src, File dest) {
		this.src = src;
		this.dest = dest;
	}

	// ========== DownloadRequest

	public DownloadTask createTask() {
		return new HttpDownloadTask(src, dest);
	}

	public String getSrc() {
		return src.toString();
	}
	
	public String getDest() {
		return dest.getAbsolutePath();
	}
}
