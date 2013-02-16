package cop.yandex.downloader;

/**
 * To hide task implementations from client, he creates {@link DownloadRequest} to add new task to the
 * {@link DownloadManager}
 * 
 * @author Oleg Cherednik
 * @since 16.02.2013
 */
public interface DownloadRequest {
	DownloadTask createTask();
}
