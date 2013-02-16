package cop.yandex.downloader;

/**
 * @author Oleg Cherednik
 * @since 16.02.2013
 */
public interface DownloadManagerListener {
	/**
	 * Invokes on each update of the given task
	 * 
	 * @param id task id
	 */
	void onTaskUpdate(int id);
}
