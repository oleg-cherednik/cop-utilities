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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cop.yandex.downloader.requests.DownloadRequest;
import cop.yandex.downloader.tasks.DownloadTask;

/**
 * @author Oleg Cherednik
 * @since 16.02.2013
 */
public final class DownloadManager implements Observer {
	private static final Logger log = LoggerFactory.getLogger(DownloadManager.class);

	private final ExecutorService pool;
	private final Map<Integer, DownloadTask> tasks = new HashMap<Integer, DownloadTask>();
	private final Set<DownloadManagerListener> listeners = new HashSet<DownloadManagerListener>();

	public DownloadManager(int maxActiveTasks) {
		log.debug("create download manager, max active tasks {}", maxActiveTasks);

		pool = Executors.newFixedThreadPool(maxActiveTasks > 0 ? maxActiveTasks : 10);
	}

	public void addListener(DownloadManagerListener listener) {
		if (listener != null)
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
		log.debug("addTask())");

		if (request == null) {
			log.error("Request can't be 'null'");
			throw new IllegalArgumentException("Request can't be 'null'");
		}

		DownloadTask task = request.createTask();
		task.addObserver(this);
		addTaskToPool(task);
		tasks.put(task.getId(), task);

		fireOnTaskUpdate(task.getId());

		log.debug("task {} is created", task);

		return task.getId();
	}

	private void addTaskToPool(DownloadTask task) {
		log.debug("addTaskToPool(id={})", task.getId());
		pool.execute(task);
	}

	public void pauseTask(int id) {
		log.debug("pauseTask({})", id);

		DownloadTask task = tasks.get(id);

		if (task == null)
			log.debug("task id={} is not found", id);
		else if (!Status.PAUSED.isAvailableFrom(task.getStatus()))
			log.debug("can't change status {} from {}", task.getStatus().getName(), Status.PAUSED.getName());
		else
			task.setStatus(Status.PAUSED);
	}

	public void resumeTask(int id) {
		log.debug("resumeTask({})", id);

		DownloadTask task = tasks.get(id);

		if (task == null)
			log.debug("task id={} is not found", id);
		else if (!Status.DOWNLOADING.isAvailableFrom(task.getStatus()))
			log.debug("can't change status {} from {}", task.getStatus().getName(), Status.DOWNLOADING.getName());
		else
			addTaskToPool(task);
	}

	public void cancelTask(int id) {
		log.debug("cancelTask({})", id);

		DownloadTask task = tasks.get(id);

		if (task == null)
			log.debug("task id={} is not found", id);
		else if (!Status.CANCELLED.isAvailableFrom(task.getStatus()))
			log.debug("can't change status {} from {}", task.getStatus().getName(), Status.CANCELLED.getName());
		else
			task.setStatus(Status.CANCELLED);
	}

	public Set<Integer> removeNotActiveTasks() {
		log.debug("removeNotActiveTasks()");

		Set<Integer> ids = new HashSet<Integer>();
		Iterator<DownloadTask> it = tasks.values().iterator();

		while (it.hasNext()) {
			DownloadTask task = it.next();

			if (task.getStatus().isActive())
				continue;

			it.remove();
			ids.add(task.getId());
		}

		log.debug("task removed: {}", ids.size());

		return ids.isEmpty() ? Collections.<Integer> emptySet() : Collections.unmodifiableSet(ids);
	}

	public void dispose() {
		log.debug("dispose()");

		for (DownloadTask task : tasks.values())
			if (task.getStatus().isActive())
				cancelTask(task.getId());
	}

	public TaskStatus getTaskStatus(int id) {
		log.trace("getTaskStatus({})", id);

		DownloadTask task = tasks.get(id);

		if (task == null)
			log.debug("task id={} is not found", id);

		return task != null ? task.getTaskStatus() : TaskStatus.createBuilder().setStatus(Status.NONE).createStatus();
	}

	public long getBytesTotal(int id) {
		log.trace("getBytesTotal({})", id);

		DownloadTask task = tasks.get(id);

		if (task == null)
			log.debug("task id={} is not found", id);

		return task != null ? task.getBytesTotal() : -1;
	}

	public long getBytesDownloaded(int id) {
		log.trace("getBytesDownloaded({})", id);

		DownloadTask task = tasks.get(id);

		if (task == null)
			log.debug("task id={} is not found", id);

		return task != null ? task.getBytesDownloaded() : -1;
	}

	public String getTaskSrc(int id) {
		log.trace("getTaskSrc({})", id);

		DownloadTask task = tasks.get(id);

		if (task == null)
			log.debug("task id={} is not found", id);

		return task != null ? task.getSrc() : null;
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
