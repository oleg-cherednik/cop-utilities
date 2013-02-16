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
import java.util.concurrent.Future;

import cop.yandex.downloader.requests.DownloadRequest;

public final class DownloadManager extends Observable implements Observer {
	private final Map<Integer, Node> nodes = new HashMap<Integer, Node>();
	private final ExecutorService pool;
	private final Set<DownloadManagerListener> listeners = new HashSet<DownloadManagerListener>();

	Future<DownloadTask> fut;

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
		nodes.put(task.getId(), new Node(task, pool.submit(task)));

		fireOnTaskUpdate(task.getId());

		return task.getId();
	}

	public Status getTaskStatus(int id) {
		Node node = nodes.get(id);
		return node != null ? node.task.getStatus() : Status.NONE;
	}

	public void pauseTask(int id) {
		Node node = nodes.get(id);

		if (node.task != null && Status.PAUSED.isAvailableFrom(node.task.getStatus()))
			node.task.setStatus(Status.PAUSED);
	}

	public void setTaskStatus(int id, Status status) {
		Node node = nodes.get(id);

		if (node.task != null && node.task.getStatus().isAvailableFrom(status))
			node.task.setStatus(status);
	}

	public void resumeTask(int id) {
		Node node = nodes.get(id);

		if (node.task != null && Status.DOWNLOADING.isAvailableFrom(node.task.getStatus()))
			node.future = pool.submit(node.task);
	}

	public void cancelTask(int id) {
		Node node = nodes.get(id);

		if (node.task != null && Status.CANCELLED.isAvailableFrom(node.task.getStatus()))
			node.task.setStatus(Status.CANCELLED);
	}

	public Set<Integer> removeNotActiveTasks() {
		Set<Integer> ids = new HashSet<Integer>();
		Iterator<Node> it = nodes.values().iterator();

		while (it.hasNext()) {
			Node node = it.next();

			if (node.task.getStatus().isActive())
				continue;

			it.remove();
			ids.add(node.task.getId());
		}

		return ids.isEmpty() ? Collections.<Integer>emptySet() : Collections.unmodifiableSet(ids);
	}

	public void dispose() {
		pool.shutdownNow();
		// for (DownloadTask task : tasks)
		// task.setStatus(Status.CANCELLED);
	}

	public long getBytesTotal(int id) {
		Node node = nodes.get(id);
		return node != null ? node.task.getBytesTotal() : -1;
	}

	public long getBytesDownloaded(int id) {
		Node node = nodes.get(id);
		return node != null ? node.task.getBytesDownloaded() : -1;
	}

	public float getProgress(int id) {
		Node node = nodes.get(id);
		return node != null ? node.task.getProgress() : 0;
	}

	public String getTaskSrc(int id) {
		Node node = nodes.get(id);
		return node != null ? node.task.getSrc() : null;
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

	// ========== inner class ==========

	private static class Node {
		public final DownloadTask task;
		public Future<?> future;

		public Node(DownloadTask task, Future<?> future) {
			this.task = task;
			this.future = future;
		}

		// ========== Object ==========

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((task == null) ? 0 : task.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node)obj;
			if (task == null) {
				if (other.task != null)
					return false;
			} else if (!task.equals(other.task))
				return false;
			return true;
		}
	}
}
