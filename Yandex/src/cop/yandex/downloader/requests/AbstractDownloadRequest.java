package cop.yandex.downloader.requests;

import cop.yandex.downloader.DownloadStatus;

public abstract class AbstractDownloadRequest implements DownloadRequest {
	protected DownloadStatus status = DownloadStatus.NEW;
}
