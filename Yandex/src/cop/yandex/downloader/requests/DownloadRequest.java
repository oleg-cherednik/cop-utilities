package cop.yandex.downloader.requests;

import cop.yandex.downloader.DownloadManager;
import cop.yandex.downloader.tasks.DownloadTask;

/**
 * {@link DownloadRequest is used to incapsulate {@link DownloadTask} inside {@link DownloadManager}. To create and add
 * new task to {@link DownloadManager}, client should create {@link DownloadRequest} first and then give it to the
 * {@link DownloadManager}.
 * 
 * @author Oleg Cherednik
 * @since 16.02.2013
 */
public interface DownloadRequest {
	DownloadTask createTask();
}
