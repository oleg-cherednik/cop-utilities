package cop.yandex.downloader;

import java.util.Observable;

public abstract class DownloadTask extends Observable implements Runnable {
	private static int nextId = 1;

	protected final int id;
	protected Status status = Status.NEW;
	protected long bytesTotal;
	protected long bytesDownloaded;

	protected DownloadTask() {
		id = nextId++;
	}

	public final int getId() {
		return id;
	}

	public final Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		System.out.println("Task: " + id + ", status: " + status.getName());
		this.status = status;
	}

	public void doResume() {}

	public void doCancelTask() {}

	public abstract float getProgress();

	public abstract String getSrc();

	public final long getBytesTotal() {
		return bytesTotal;
	}

	public final long getBytesDownloaded() {
		return bytesDownloaded;
	}

	// ========== Object ==========

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DownloadTask other = (DownloadTask)obj;
		if (id != other.id)
			return false;
		return true;
	}

}
