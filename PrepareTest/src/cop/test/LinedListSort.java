package cop.test;

public class LinedListSort {
	public static void main(String... args) {
		LinkedListNode node = createList();

		print(node);
		node = sortList(node);
		print(node);
	}

	private static LinkedListNode sortList(LinkedListNode parent) {
		LinkedListNode node1 = parent != null ? parent.getNext() : null;
		LinkedListNode node2 = node1 != null ? node1.getNext() : null;

		parent.setNext(node2);
		node2.setNext(node1);
		node1.setNext(node2.getNext());

		return merge(node1, node2);
	}

	private static LinkedListNode merge(LinkedListNode node1, LinkedListNode node2) {
		if (node1 == null && node2 == null)
			return null;
		if (node1 == null)
			return node2;
		if (node2 == null)
			return node1;

		LinkedListNode first = null;
		LinkedListNode prv = null;

		while (node1 != null && node2 != null) {
			LinkedListNode next = node1.getValue() <= node2.getValue() ? node1 : node2;

			if (prv != null)
				prv.setNext(next);
			else
				prv = next;

			if (first != null)
				first = prv;

			if (node1 == next)
				node1 = node1.getNext();
			else
				node2 = node2.getNext();
		}

		if (node1 == null)
			prv.setNext(node2);
		else if (node2 == null)
			prv.setNext(node1);

		return first;
	}

	private static LinkedListNode createList() {
		LinkedListNode parent = null;
		LinkedListNode prv = null;

		for (int i = 0; i < 20; i++) {
			LinkedListNode node = new LinkedListNode((int)(Math.random() * 100));// , prv);

			if (prv != null)
				prv.setNext(node);

			prv = node;
			parent = parent == null ? node : parent;
		}

		return parent;
	}

	private static void print(LinkedListNode node) {
		do {
			System.out.print(node.getValue() + " ");
			node = node.getNext();
		} while (node != null);

		System.out.println();
	}
}
