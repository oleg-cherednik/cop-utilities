package cop.yandex.downloader.requests;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Observable;

import cop.yandex.downloader.DownloadStatus;

public final class Download extends Observable implements Runnable {
	private static final int MAX_BUFFER_SIZE = 1024;

	private URL url; // download URL
	private int size; // size of download in bytes
	private int downloaded; // number of bytes downloaded
	private DownloadStatus status = DownloadStatus.NEW; // current status of download

	// Constructor for Download.
	public Download(URL url) {
		this.url = url;
		size = -1;
		downloaded = 0;

		// Begin the download.
		download();
	}

	// Get this download's URL.
	public String getUrl() {
		return url.toString();
	}

	// Get this download's size.
	public int getSize() {
		return size;
	}

	// Get this download's progress.
	public float getProgress() {
		return ((float) downloaded / size) * 100;
	}

	public DownloadStatus getStatus() {
		return status;
	}

	public void pause() {
		status = DownloadStatus.PAUSED;
		stateChanged();
	}

	public void resume() {
		status = DownloadStatus.DOWNLOADING;
		stateChanged();
		download();
	}

	public void cancel() {
		status = DownloadStatus.CANCELLED;
		stateChanged();
	}

	private void error() {
		status = DownloadStatus.ERROR;
		stateChanged();
	}

	private void download() {
		Thread thread = new Thread(this);
		thread.start();
	}

	// Get file name portion of URL.
	private String getFileName(URL url) {
		String fileName = url.getFile();
		return fileName.substring(fileName.lastIndexOf('/') + 1);
	}

	// Download file.
	public void run() {
		status = DownloadStatus.DOWNLOADING;
		RandomAccessFile file = null;
		InputStream stream = null;

		try {
			// Open connection to URL.
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// Specify what portion of file to download.
			connection.setRequestProperty("Range", "bytes=" + downloaded + "-");

			// Connect to server.
			connection.connect();

			// Make sure response code is in the 200 range.
			if (connection.getResponseCode() / 100 != 2) {
				error();
			}

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
			file = new RandomAccessFile(getFileName(url), "rw");
			file.seek(downloaded);

			stream = connection.getInputStream();
			while (status == DownloadStatus.DOWNLOADING) {
				/*
				 * Size buffer according to how much of the file is left to
				 * download.
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
			 * Change status to complete if this point was reached because
			 * downloading has finished.
			 */
			if (status == DownloadStatus.DOWNLOADING) {
				status = DownloadStatus.COMPLETE;
				stateChanged();
			}
		} catch (Exception e) {
			error();
		} finally {
			// Close file.
			if (file != null) {
				try {
					file.close();
				} catch (Exception e) {}
			}

			// Close connection to server.
			if (stream != null) {
				try {
					stream.close();
				} catch (Exception e) {}
			}
		}
	}

	private void stateChanged() {
		setChanged();
		notifyObservers();
	}
}