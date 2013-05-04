package cop.test;

import java.lang.Runnable;
import java.lang.Thread;
import java.util.concurrent.Semaphore;

public class Library {
	static Access a;
	static Semaphore sem;

	public static void main(String args[]) {
		a = new Access();
		// только один писатель может одновременно находиться в хранилище
		sem = new Semaphore(1);

		for (int i = 1; i <= 4; i++) {
			Reader r = new Reader(i, a);
		}
		for (int i = 1; i <= 4; i++) {
			new Writer(i, a, sem);
		}
	}
}

class Lock {}

class Access {

	Lock reader = new Lock();
	Lock writer = new Lock();

	public void reading(Reader t) {
		synchronized (reader) {
			try {
				System.out.println(t.id + " reader reads");
				t.sleep(100);
				System.out.println(t.id + " reader has finished reading");
			} catch (InterruptedException e) {
				System.out.println(" reader has excepted");
			}
		}
	}

	public void writing(Writer t) {
		synchronized (reader) {
			try {
				System.out.println(t.id + " writer writes");
				t.sleep(100);
				System.out.println(t.id + " writer has finished writing");
			} catch (InterruptedException e) {
				System.out.println(" writer has excepted");
			}
		}
	}
}

class Reader extends Thread {
	Access a;
	int id;

	Reader(int id, Access a) {
		this.id = id;
		this.a = a;
		start();
	}

	public void run() {
		try {
			a.reading(this);
			sleep(500);
		} catch (InterruptedException e) {}
	}
}

class Writer extends Thread {
	Access a;
	int id;
	Semaphore sem;

	Writer(int id, Access a, Semaphore sem) {
		this.id = id;
		this.a = a;
		this.sem = sem;
		start();
	}

	public void run() {
		try {
			sem.acquire();
			a.writing(this);
			sem.release();
			sleep(500);
		} catch (InterruptedException e) {}
	}
}
