package cop.yandex.downloader;

import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Observable;

/**
 * Download task is created by {@link DownloadManager} using giving {@link DownloadRequest}. Download task basic
 * implementation. By default it works with input and output stream. To define other implementation
 * {@link DownloadTask#run()} method can be overridden.
 * 
 * @author Oleg Cherednik
 * @since 16.02.2013
 */
public abstract class DownloadTask extends Observable implements Runnable {
	private static int nextId = 1;

	protected final int id;
	protected final int buffSize;
	protected final File dest;

	private final TaskStatus.Builder status = TaskStatus.createBuilder();
	protected long bytesTotal = -1;
	protected long bytesDownloaded = -1;

	protected DownloadTask(File dest, int buffSize) {
		id = nextId++;
		this.dest = dest;
		this.buffSize = buffSize;
	}

	public final int getId() {
		return id;
	}

	public final TaskStatus getTaskStatus() {
		return status.createStatus();
	}

	protected final void setStatus(Status status) {
		setStatus(status, null);
	}

	protected final void setStatus(Status status, String description) {
		System.out.print("Task: " + id + ", status: " + status.getName());
		System.out.println(isEmpty(description) ? "" : (", description: " + description));
		this.setStatus(status);
		stateChanged();
	}

	public final long getBytesTotal() {
		return bytesTotal;
	}

	public final long getBytesDownloaded() {
		return bytesDownloaded;
	}

	protected void stateChanged() {
		setChanged();
		notifyObservers();
	}

	protected Status getStatus() {
		return status.getStatus();
	}

	// ========== abstract ==========

	public abstract String getSrc();

	protected abstract File createDestFile();

	/**
	 * Create input stream based on current connection
	 * 
	 * @return input stream object or <tt>null</tt> if it can't be created
	 * @throws Exception
	 */
	protected abstract InputStream createInputStream() throws Exception;

	protected void releaseResources() {}

	// ========== Runnable ==========

	public void run() {
		setStatus(Status.DOWNLOADING);

		RandomAccessFile file = null;
		InputStream in = null;

		try {
			file = new RandomAccessFile(createDestFile(), "rw");
			file.seek(bytesDownloaded = file.length());

			stateChanged();

			in = createInputStream();

			final byte buff[] = new byte[Math.max(512, buffSize)];

			while(status.getStatus() == Status.DOWNLOADING) {
				if(Thread.currentThread().isInterrupted()) {
					setStatus(Status.PAUSED);
					break;
				}

				int read = in.read(buff);

				if(read == -1)
					break;

				file.write(buff, 0, read);
				bytesDownloaded += read;
				stateChanged();
			}

			if(status.getStatus() == Status.DOWNLOADING)
				setStatus(Status.COMPLETE);
		} catch(Exception e) {
			setStatus(Status.ERROR);
		} finally {
			close(file);
			close(in);
			releaseResources();
		}
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
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		DownloadTask other = (DownloadTask)obj;
		if(id != other.id)
			return false;
		return true;
	}

	// ========== static ==========

	protected static void close(Closeable obj) {
		if(obj != null) {
			try {
				obj.close();
			} catch(Exception ignored) {}
		}
	}

	static boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}
}
