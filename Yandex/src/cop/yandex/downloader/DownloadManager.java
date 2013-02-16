package cop.yandex.downloader;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Oleg Cherednik
 * @since 16.02.2013
 */
public final class DownloadManager implements Observer {
	private final Map<Integer, DownloadTask> tasks = new HashMap<Integer, DownloadTask>();
	private final ExecutorService pool;
	private final Set<DownloadManagerListener> listeners = new HashSet<DownloadManagerListener>();

	public DownloadManager(int maxActiveTasks) {
		pool = Executors.newFixedThreadPool(maxActiveTasks > 0 ? maxActiveTasks : 10);
	}

	public void addListener(DownloadManagerListener listener) {
		if(listener != null)
			listeners.add(listener);
	}

	/**
	 * Add new task, that based on given <tt>request</tt>, to the manager and run it as soon as possible. Created task
	 * can be controlled by returned unique <tt>id</tt>.
	 * 
	 * @param request
	 * @return task id
	 */
	public int addTask(DownloadRequest request) {
		if(request == null)
			throw new IllegalArgumentException("Request can't be 'null'");

		DownloadTask task = request.createTask();
		task.addObserver(this);
		pool.execute(task);
		tasks.put(task.getId(), task);

		fireOnTaskUpdate(task.getId());

		return task.getId();
	}

	public void pauseTask(int id) {
		DownloadTask task = tasks.get(id);

		if(task != null && Status.PAUSED.isAvailableFrom(task.getStatus()))
			task.setStatus(Status.PAUSED);
	}

	public void resumeTask(int id) {
		DownloadTask task = tasks.get(id);

		if(task != null && Status.DOWNLOADING.isAvailableFrom(task.getStatus()))
			pool.execute(task);
	}

	public void cancelTask(int id) {
		DownloadTask task = tasks.get(id);

		if(task != null && Status.CANCELLED.isAvailableFrom(task.getStatus()))
			task.setStatus(Status.CANCELLED);
	}

	public Set<Integer> removeNotActiveTasks() {
		Set<Integer> ids = new HashSet<Integer>();
		Iterator<DownloadTask> it = tasks.values().iterator();

		while(it.hasNext()) {
			DownloadTask task = it.next();

			if(task.getStatus().isActive())
				continue;

			it.remove();
			ids.add(task.getId());
		}

		return ids.isEmpty() ? Collections.<Integer> emptySet() : Collections.unmodifiableSet(ids);
	}

	public void dispose() {
		for(Integer id : tasks.keySet())
			cancelTask(id);
	}

	public TaskStatus getTaskStatus(int id) {
		DownloadTask task = tasks.get(id);
		return task != null ? task.getTaskStatus() : TaskStatus.createBuilder().setStatus(Status.NONE).createStatus();
	}

	public long getBytesTotal(int id) {
		DownloadTask task = tasks.get(id);
		return task != null ? task.getBytesTotal() : -1;
	}

	public long getBytesDownloaded(int id) {
		DownloadTask task = tasks.get(id);
		return task != null ? task.getBytesDownloaded() : -1;
	}

	public String getTaskSrc(int id) {
		DownloadTask task = tasks.get(id);
		return task != null ? task.getSrc() : null;
	}

	private void fireOnTaskUpdate(int id) {
		for(DownloadManagerListener listener : listeners)
			listener.onTaskUpdate(id);
	}

	// ========== Observer ==========

	@Override
	public void update(Observable obj, Object arg) {
		if(obj instanceof DownloadTask)
			fireOnTaskUpdate(((DownloadTask)obj).getId());
	}
}
