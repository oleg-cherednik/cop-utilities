package cop.yandex.downloader.tasks;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cop.yandex.downloader.Status;

/**
 * @author Oleg Cherednik
 * @since 16.02.2013
 */
public final class HttpDownloadTask extends DownloadTask {
	private final URL src;
	private HttpURLConnection conn;

	public HttpDownloadTask(URL src, File dest, int buffSize) {
		super(dest, buffSize);

		this.src = src;
	}

	// ========== DownloadTask ==========

	@Override
	protected File createDestFile() {
		return dest.isFile() ? dest : new File(dest, getFileName(src));
	}

	@Override
	public String getSrc() {
		return src.toString();
	}

	@Override
	protected InputStream createInputStream() throws Exception {
		conn = (HttpURLConnection)src.openConnection();

		conn.setRequestProperty("Range", "bytes=" + bytesDownloaded + "-");
		conn.connect();

		if(conn.getResponseCode() / 100 != 2)
			setStatus(Status.ERROR, "http error code " + conn.getResponseCode());
		else if((bytesTotal = conn.getContentLength()) < 0)
			setStatus(Status.ERROR, "total size " + bytesTotal);

		return getStatus() == Status.DOWNLOADING ? conn.getInputStream() : null;
	}

	@Override
	protected void releaseResources() {
		if(conn != null)
			conn.disconnect();
	}

	// ========== static ==========

	private static String getFileName(URL url) {
		String fileName = url.getFile();
		return fileName.substring(fileName.lastIndexOf('/') + 1);
	}
}
