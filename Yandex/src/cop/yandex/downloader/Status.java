package cop.yandex.downloader;

public enum Status {
	NONE("None", true) {
		@Override
		public boolean isAvailableFrom(Status status) {
			return false;
		}
	},
	NEW("New", true) {
		@Override
		public boolean isAvailableFrom(Status status) {
			return status == NONE;
		}
	},
	DOWNLOADING("Downloading", true) {
		@Override
		public boolean isAvailableFrom(Status status) {
			return status == NEW || status == PAUSED;
		}
	},
	PAUSED("Paused", true) {
		@Override
		public boolean isAvailableFrom(Status status) {
			return status == DOWNLOADING;
		}
	},
	COMPLETE("Complete", false) {
		@Override
		public boolean isAvailableFrom(Status status) {
			return status == DOWNLOADING;
		}
	},
	CANCELLED("Cancelled", false) {
		@Override
		public boolean isAvailableFrom(Status status) {
			return status == NEW || status == DOWNLOADING || status == PAUSED;
		}
	},
	ERROR("Error", false) {
		@Override
		public boolean isAvailableFrom(Status status) {
			return status == DOWNLOADING || status == PAUSED;
		}
	};

	private final String name;
	private final boolean active;

	Status(String name, boolean active) {
		this.name = name;
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public boolean isActive() {
		return active;
	}

	public abstract boolean isAvailableFrom(Status status);
}
