package cop.yandex.downloader;

public enum Status {
	NONE("None") {
		@Override
		public boolean isAvailableFrom(Status status) {
			return false;
		}
	},
	NEW("New") {
		@Override
		public boolean isAvailableFrom(Status status) {
			return status == NONE;
		}
	},
	DOWNLOADING("Downloading") {
		@Override
		public boolean isAvailableFrom(Status status) {
			return status == NEW || status == PAUSED;
		}
	},
	PAUSED("Paused") {
		@Override
		public boolean isAvailableFrom(Status status) {
			return status == DOWNLOADING;
		}
	},
	COMPLETE("Complete") {
		@Override
		public boolean isAvailableFrom(Status status) {
			return status == DOWNLOADING;
		}
	},
	CANCELLED("Cancelled") {
		@Override
		public boolean isAvailableFrom(Status status) {
			return status == NEW || status == DOWNLOADING || status == PAUSED;
		}
	},
	ERROR("Error") {
		@Override
		public boolean isAvailableFrom(Status status) {
			return status == DOWNLOADING || status == PAUSED;
		}
	};

	private final String name;

	Status(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract boolean isAvailableFrom(Status status);
}
