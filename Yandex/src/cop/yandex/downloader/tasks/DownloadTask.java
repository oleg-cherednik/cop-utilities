package cop.yandex.downloader.tasks;

import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Observable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cop.yandex.downloader.DownloadManager;
import cop.yandex.downloader.Status;
import cop.yandex.downloader.TaskStatus;
import cop.yandex.downloader.requests.DownloadRequest;

/**
 * Download task is created by {@link DownloadManager} using giving {@link DownloadRequest}. Download task basic
 * implementation. By default it works with input and output stream. To define other implementation
 * {@link DownloadTask#run()} method can be overridden.
 * 
 * @author Oleg Cherednik
 * @since 16.02.2013
 */
public abstract class DownloadTask extends Observable implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(DownloadTask.class);

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

	public final void setStatus(Status status) {
		setStatus(status, null);
	}

	public final void setStatus(Status status, String description) {
		if(status == Status.ERROR)
			log.error("setStatus(id={}, {}, {})", new String[] { Integer.toString(id), status.getName(),
					isEmpty(description) ? description : "---" });
		else
			log.debug("setStatus(id={}, {}, {})", new String[] { Integer.toString(id), status.getName(),
					isEmpty(description) ? description : "---" });

		this.status.setStatus(status);
		this.status.setDescription(description);
		stateChanged();
	}

	public final long getBytesTotal() {
		log.trace("getBytesTotal(id={}, {})", id, bytesTotal);
		return bytesTotal;
	}

	public final long getBytesDownloaded() {
		log.trace("getBytesDownloaded(id={}, {})", id, bytesDownloaded);
		return bytesDownloaded;
	}

	protected void stateChanged() {
		setChanged();
		notifyObservers();
	}

	public Status getStatus() {
		log.trace("getStatus(id={}, {})", id, status.getStatus().getName());
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
			File dest = createDestFile();
			log.debug("Open destination: id={}, {}", id, dest);

			file = new RandomAccessFile(createDestFile(), "rw");

			file.seek(bytesDownloaded = file.length());
			stateChanged();

			in = createInputStream();
			in.skip(bytesDownloaded);

			log.debug("Skip bytes: id={}, {}", id, bytesDownloaded);

			final byte buff[] = new byte[Math.max(512, buffSize)];

			while(status.getStatus() == Status.DOWNLOADING) {
				if(Thread.currentThread().isInterrupted())
					setStatus(Status.PAUSED);
				else if(bytesDownloaded > bytesTotal)
					log.error("too many bytes downloaded, {} > {}", bytesDownloaded, bytesTotal);
				else {
					int read = in.read(buff);
					log.debug("id={}, read {} bytes", read);

					if(read == -1)
						break;

					file.write(buff, 0, read);
					bytesDownloaded += read;
					stateChanged();

					continue;
				}

				break;
			}

			if(status.getStatus() == Status.DOWNLOADING)
				setStatus(Status.COMPLETE);
		} catch(Exception e) {
			setStatus(Status.ERROR, e.getClass().getSimpleName() + ", " + e.getMessage());
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

	@Override
	public String toString() {
		return "id=" + id + ", src=" + getSrc() + ", dest=" + dest.getAbsolutePath();
	}

	// ========== static ==========

	protected static void close(Closeable obj) {
		if(obj != null) {
			try {
				obj.close();
			} catch(Exception ignored) {}
		}
	}

	public static boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}
}
