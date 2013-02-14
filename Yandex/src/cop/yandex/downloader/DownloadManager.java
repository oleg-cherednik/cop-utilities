package cop.yandex.downloader;

public final class DownloadManager {
	private static final DownloadManager INSTANCE = new DownloadManager();

	public static DownloadManager getInstance() {
		return INSTANCE;
	}

	private DownloadManager() {
	}
}
