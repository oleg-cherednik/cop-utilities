package cop.yandex.downloader;

public enum DownloadStatus {
	NONE("None"),
	NEW("New"),
	DOWNLOADING("Downloading"),
	PAUSED("Paused"),
	COMPLETE("Complete"),
	CANCELLED("Cancelled"),
	ERROR("Error");

	private final String name;

	DownloadStatus(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
