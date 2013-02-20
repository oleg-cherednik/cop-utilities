package cop.test;

import java.util.ArrayList;
import java.util.List;

public final class Queue<T> {
	private final int size;
	private final List<T> objs;
	private final Object lock = new Object();

	public Queue(int size) {
		this.size = Math.max(1, size);
		objs = new ArrayList<T>(size);
	}

	public void add(T obj) throws InterruptedException {
		synchronized (lock) {
			while (objs.size() >= size) {
				wait();
			}

			notifyAll();
			objs.add(obj);
		}
	}

	public T get() throws InterruptedException {
		synchronized (lock) {
			while (objs.isEmpty()) {
				wait();
			}

			notifyAll();
			return objs.get(objs.size() - 1);
		}
	}

}
