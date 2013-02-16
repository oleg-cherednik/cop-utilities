package cop.yandex.downloader;

public enum Status {
	NONE("None"),
	NEW("New"),
	DOWNLOADING("Downloading"),
	PAUSED("Paused"),
	COMPLETE("Complete"),
	CANCELLED("Cancelled"),
	ERROR("Error");

	private final String name;

	Status(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
