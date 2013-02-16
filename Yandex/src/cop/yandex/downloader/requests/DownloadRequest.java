package cop.yandex.downloader.requests;

import cop.yandex.downloader.DownloadTask;

public interface DownloadRequest {
	DownloadTask createTask();

	String getSrc();

	String getDest();
}
