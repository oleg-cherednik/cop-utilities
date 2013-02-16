package cop.yandex.downloader;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cop.yandex.downloader.requests.DownloadRequest;

public final class DownloadManager extends Observable implements Observer {
	private final Set<DownloadTask> tasks = new HashSet<DownloadTask>();
	private final ExecutorService pool;
	private final Set<DownloadManagerListener> listeners = new HashSet<DownloadManagerListener>();

	public DownloadManager(int maxActiveTasks) {
		pool = Executors.newFixedThreadPool(maxActiveTasks > 0 ? maxActiveTasks : 10);
	}

	// public float getProgress() {
	// return ((float)downloaded / size) * 100;
	// }

	public void addListener(DownloadManagerListener listener) {
		if (listener != null)
			listeners.add(listener);
	}

	public int addTask(DownloadRequest request) {
		if (request == null)
			throw new IllegalArgumentException("Request can't be 'null'");

		DownloadTask task = request.createTask();

		task.addObserver(this);
		tasks.add(task);
		pool.execute(task);

		fireOnTaskUpdate(task.getId());

		return task.getId();
	}

	private DownloadTask getTaskById(int id) {
		for (DownloadTask task : tasks)
			if (task.getId() == id)
				return task;
		return null;
	}

	public Status getRequestStatus(int id) {
		DownloadTask task = getTaskById(id);
		return task != null ? task.getStatus() : Status.NONE;
	}

	public void doPauseRequest(int id) {
		DownloadTask task = getTaskById(id);

		if (task != null && task.getStatus() == Status.DOWNLOADING)
			task.doPause();
	}

	public void doResumeRequest(int id) {
		DownloadTask task = getTaskById(id);

		if (task != null && task.getStatus() == Status.PAUSED)
			task.doResume();
	}

	public void doCancelTask(int id) {
		DownloadTask task = getTaskById(id);

		if (task == null)
			return;

		switch (task.getStatus()) {
		case PAUSED:
		case DOWNLOADING:
		case NEW:
			task.doCancelTask();
			break;
		default:
			break;
		}
	}

	public int getSize(int id) {
		DownloadTask task = getTaskById(id);
		return task != null ? task.getSize() : -1;
	}

	public float getProgress(int id) {
		DownloadTask task = getTaskById(id);
		return task != null ? task.getProgress() : 0;
	}

	public String getTaskSrc(int id) {
		DownloadTask task = getTaskById(id);
		return task != null ? task.getSrc() : null;
	}

	private void stateChanged() {
		setChanged();
		notifyObservers();
	}

	private void fireOnTaskUpdate(int id) {
		for (DownloadManagerListener listener : listeners)
			listener.onTaskUpdate(id);
	}

	// ========== Observer ==========

	@Override
	public void update(Observable obj, Object arg) {
		if (obj instanceof DownloadTask)
			fireOnTaskUpdate(((DownloadTask)obj).getId());
	}
}
