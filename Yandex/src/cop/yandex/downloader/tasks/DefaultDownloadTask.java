package cop.yandex.downloader.tasks;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cop.yandex.downloader.Status;

/**
 * @author Oleg Cherednik
 * @since 16.02.2013
 */
public abstract class DefaultDownloadTask extends DownloadTask {
	private static final Logger log = LoggerFactory.getLogger(DefaultDownloadTask.class);

	protected DefaultDownloadTask(File dest, int buffSize) {
		super(dest, buffSize);
	}

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

			while (getStatus() == Status.DOWNLOADING) {
				if (Thread.currentThread().isInterrupted())
					setStatus(Status.PAUSED);
				else if (bytesDownloaded > bytesTotal)
					log.error("too many bytes downloaded, {} > {}", bytesDownloaded, bytesTotal);
				else {
					int read = in.read(buff);
					log.trace("id={}, read {} bytes", read);

					if (read == -1)
						break;

					file.write(buff, 0, read);
					bytesDownloaded += read;
					stateChanged();

					continue;
				}

				break;
			}

			if (bytesDownloaded > bytesTotal)
				setStatus(Status.ERROR, "too many bytes downloaded, " + bytesDownloaded + " > " + bytesTotal);
			else if (getStatus() == Status.DOWNLOADING)
				setStatus(Status.COMPLETE);
		} catch (Exception e) {
			setStatus(Status.ERROR, e.getClass().getSimpleName() + ", " + e.getMessage());
		} finally {
			close(file);
			close(in);
			releaseResources();
		}
	}
}
