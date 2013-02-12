package cop.test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Create stack using 2 queues
 * 
 * @author Oleg Cherednik
 * @since 13.02.2013
 */
public class StackQueue2 {
	public static void main(String[] args) {
		Stack<Integer> stack = new Stack<Integer>();

		stack.push(1);
		stack.push(2);
		stack.push(3);
		print(stack);

		// ==========

		stack.push(1);
		stack.push(2);
		stack.push(3);
		System.out.print(" " + stack.pop());
		System.out.print(" " + stack.pop());
		stack.push(4);
		stack.push(5);
		System.out.print(" " + stack.pop());
		System.out.print(" " + stack.pop());
		System.out.print(" " + stack.pop());
	}

	private static <T> void print(Stack<T> stack) {
		while (!stack.isEmpty()) {
			System.out.print(" " + stack.pop());
		}

		System.out.println();
	}

	private static class Stack<T> {
		private final Queue<T> queue1 = new LinkedList<T>();
		private final Queue<T> queue2 = new LinkedList<T>();

		public synchronized void push(T item) {
			(queue2.isEmpty() ? queue1 : queue2).add(item);
		}

		public synchronized T pop() {
			if (queue1.isEmpty()) {
				while (queue2.size() > 1)
					queue1.add(queue2.remove());
				return queue2.remove();
			} else {
				while (queue1.size() > 1)
					queue2.add(queue1.remove());
				return queue1.remove();
			}
		}

		public boolean isEmpty() {
			return queue1.isEmpty() && queue2.isEmpty();
		}
	}
}
