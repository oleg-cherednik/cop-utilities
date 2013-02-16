package cop.yandex.downloader;

import java.util.Observable;

public abstract class DownloadTask extends Observable implements Runnable {
	private static int nextId = 1;

	protected final int id;
	protected Status status = Status.NEW;

	protected DownloadTask() {
		id = nextId++;
	}

	public final int getId() {
		return id;
	}

	public final Status getStatus() {
		return status;
	}

	public void doPause() {}

	public void doResume() {}

	public void doCancelTask() {}

	public abstract int getSize();

	public abstract float getProgress();
	
	public abstract String getSrc();
}
