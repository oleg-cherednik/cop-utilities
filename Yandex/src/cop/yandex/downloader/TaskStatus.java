package cop.yandex.downloader;

import cop.yandex.downloader.tasks.DownloadTask;

/**
 * @author Oleg Cherednik
 * @since 16.02.2013
 */
public final class TaskStatus {
	private final Status status;
	private final String description;

	public static Builder createBuilder() {
		return new Builder();
	}

	private TaskStatus(Builder builder) {
		status = builder.status;
		description = builder.description;
	}

	public Status getStatus() {
		return status;
	}

	public String getDescription() {
		return description;
	}

	public String getInfo() {
		return status.getName() + (DownloadTask.isEmpty(description) ? "" : ": " + description);
	}

	// ========== Builder ==========

	public static class Builder {
		private Status status = Status.NEW;
		private String description;

		private Builder() {}

		public TaskStatus createStatus() {
			return new TaskStatus(this);
		}

		public Builder setStatus(Status status) {
			this.status = status;
			return this;
		}

		public Builder setDescription(String description) {
			this.description = description != null && !description.isEmpty() ? description : null;
			return this;
		}

		public Status getStatus() {
			return status;
		}
	}
}
