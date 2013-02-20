package cop.test.lists;

final class ListUtils {
	private ListUtils() {}

	public static void print(LinkedListNode node) {
		do {
			System.out.print(node.getValue() + " ");
			node = node.getNext();
		} while (node != null);

		System.out.println();
	}

	public static void print(LinkedListNode node, int size) {
		do {
			System.out.print(node.getValue() + " ");
			node = node.getNext();
			size--;
		} while (node != null && size > 0);

		System.out.println();
	}

	public static LinkedListNode createList(int total, boolean rnd) {
		LinkedListNode parent = null;
		LinkedListNode prv = null;

		for (int i = 1; i <= total; i++) {
			LinkedListNode node = new LinkedListNode(rnd ? (int)(Math.random() * 100) : i);

			if (prv != null)
				prv.setNext(node);

			prv = node;
			parent = parent == null ? node : parent;
		}

		return parent;
	}

	public static LinkedListNode createList(int total, int loop, boolean rnd) {
		int value = 1;
		LinkedListNode head = new LinkedListNode(value++);
		LinkedListNode curr = head;

		for (int i = 1; i < total - loop; i++) {
			LinkedListNode node = new LinkedListNode(rnd ? (int)(Math.random() * 100) : value++);
			curr.setNext(node);
			curr = node;
		}

		if (loop > 0) {
			LinkedListNode loopStart = curr;

			for (int i = 0; i < loop; i++) {
				LinkedListNode node = new LinkedListNode(rnd ? (int)(Math.random() * 100) : value++);
				curr.setNext(node);
				curr = node;
			}

			curr.setNext(loopStart.getNext());
		}

		return head;
	}
}
