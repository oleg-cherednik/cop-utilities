package cop.test;

import java.util.Stack;

/**
 * Create queue using 2 stacks
 * 
 * @author Oleg Cherednik
 * @since 12.02.2013
 */
public class QueueStack2 {
	public static void main(String[] args) {
		Queue<Integer> queue = new Queue<Integer>();

		queue.add(1);
		queue.add(2);
		queue.add(3);
		print(queue);

		// ==========

		queue.add(1);
		queue.add(2);
		queue.add(3);
		System.out.print(" " + queue.remove());
		System.out.print(" " + queue.remove());
		queue.add(4);
		queue.add(5);
		System.out.print(" " + queue.remove());
		System.out.print(" " + queue.remove());
		System.out.print(" " + queue.remove());
	}

	private static <T> void print(Queue<T> queue) {
		while (!queue.isEmpty()) {
			System.out.print(" " + queue.remove());
		}

		System.out.println();
	}

	private static class Queue<T> {
		private final Stack<T> stack1 = new Stack<T>();
		private final Stack<T> stack2 = new Stack<T>();

		public synchronized void add(T item) {
			stack1.push(item);
		}

		public synchronized T remove() {
			if (!stack2.isEmpty())
				return stack2.pop();

			while (stack1.size() > 1)
				stack2.push(stack1.pop());

			return stack1.pop();
		}

		public synchronized boolean isEmpty() {
			return stack1.isEmpty() && stack2.isEmpty();
		}
	}

}
