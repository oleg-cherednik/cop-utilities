package cop.yandex.downloader;

import java.io.Closeable;
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public final class HttpDownloadTask extends DownloadTask {
	private final URL url;
	private final File dest;
	private final int buffSize;

	public HttpDownloadTask(URL src, File dest, int buffSize) {
		this.url = src;
		this.dest = dest;
		this.buffSize = buffSize;

		bytesTotal = -1;
		bytesDownloaded = 0;

		// Begin the download.
		download();
	}

	@Override
	public float getProgress() {
		return ((float)bytesDownloaded / bytesTotal) * 100;
	}

	@Override
	public String getSrc() {
		return url.toString();
	}

	@Override
	public void setStatus(Status status) {
		super.setStatus(status);
		stateChanged();
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
		setStatus(Status.DOWNLOADING);

		RandomAccessFile file = null;
		InputStream in = null;
		HttpURLConnection conn = null;

		try {
			conn = (HttpURLConnection)url.openConnection();

			conn.setRequestProperty("Range", "bytes=" + bytesDownloaded + "-");
			conn.connect();

			// Make sure response code is in the 200 range.
			if (conn.getResponseCode() / 100 != 2)
				error();

			int contentLength = conn.getContentLength();

			if (contentLength < 1)
				error();

			if (bytesTotal == -1) {
				bytesTotal = contentLength;
				stateChanged();
			}

			file = new RandomAccessFile(new File(dest, getFileName(url)), "rw");
			file.seek(bytesDownloaded = file.length());

			in = conn.getInputStream();

			final byte buff[] = new byte[Math.max(512, buffSize)];

			while (status == Status.DOWNLOADING) {
				if (Thread.currentThread().isInterrupted()) {
					setStatus(Status.PAUSED);
					break;
				}

				int read = in.read(buff);

				if (read == -1)
					break;

				file.write(buff, 0, read);
				bytesDownloaded += read;
				stateChanged();
			}

			if (status == Status.DOWNLOADING) {
				status = Status.COMPLETE;
				stateChanged();
			}
		} catch (Exception e) {
			error();
		} finally {
			close(file);
			close(in);

			if (conn != null)
				conn.disconnect();
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
