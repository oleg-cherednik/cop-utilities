package cop.yandex.downloader;

import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;


public final class HttpDownloadTask extends DownloadTask {
	private static final int MAX_BUFFER_SIZE = 1024;

	private final URL url;
	private final File dest;
	private int size; // size of download in bytes
	private int downloaded; // number of bytes downloaded

	// Constructor for Download.
	public HttpDownloadTask(URL src, File dest) {
		this.url = src;
		this.dest = dest;
		size = -1;
		downloaded = 0;

		// Begin the download.
		download();
	}

	// Get this download's URL.
	public String getUrl() {
		return url.toString();
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public float getProgress() {
		return ((float)downloaded / size) * 100;
	}
	
	@Override
	public String getSrc() {
		return url.toString();
	}

	public void pause() {
		status = Status.PAUSED;
		stateChanged();
	}

	public void resume() {
		status = Status.DOWNLOADING;
		stateChanged();
		download();
	}

	public void cancel() {
		status = Status.CANCELLED;
		stateChanged();
	}

	private void error() {
		status = Status.ERROR;
		stateChanged();
	}

	private void download() {
		Thread thread = new Thread(this);
		thread.start();
	}

	// Get file name portion of URL.
	private static String getFileName(URL url) {
		String fileName = url.getFile();
		return fileName.substring(fileName.lastIndexOf('/') + 1);
	}

	// ========== Runnable ==========

	public void run() {
		status = Status.DOWNLOADING;
		RandomAccessFile file = null;
		InputStream stream = null;

		try {
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestProperty("Range", "bytes=" + downloaded + "-");
			connection.connect();

			// Make sure response code is in the 200 range.
			if (connection.getResponseCode() / 100 != 2)
				error();

			// Check for valid content length.
			int contentLength = connection.getContentLength();
			if (contentLength < 1) {
				error();
			}

			/*
			 * Set the size for this download if it hasn't been already set.
			 */
			if (size == -1) {
				size = contentLength;
				stateChanged();
			}

			// Open file and seek to the end of it.
			file = new RandomAccessFile(new File(dest, getFileName(url)), "rw");
			file.seek(downloaded);

			stream = connection.getInputStream();
			while (status == Status.DOWNLOADING) {
				/*
				 * Size buffer according to how much of the file is left to download.
				 */
				byte buffer[];
				if (size - downloaded > MAX_BUFFER_SIZE) {
					buffer = new byte[MAX_BUFFER_SIZE];
				} else {
					buffer = new byte[size - downloaded];
				}

				// Read from server into buffer.
				int read = stream.read(buffer);
				if (read == -1)
					break;

				// Write buffer to file.
				file.write(buffer, 0, read);
				downloaded += read;
				stateChanged();
			}

			/*
			 * Change status to complete if this point was reached because downloading has finished.
			 */
			if (status == Status.DOWNLOADING) {
				status = Status.COMPLETE;
				stateChanged();
			}
		} catch (Exception e) {
			error();
		} finally {
			close(file);
			close(stream);
		}
	}

	private void stateChanged() {
		setChanged();
		notifyObservers();
	}

	// ========== static ==========

	private static void close(Closeable obj) {
		if (obj != null) {
			try {
				obj.close();
			} catch (Exception ignored) {}
		}
	}
}
