package cop.test.lists;

public class LinkedListSort {
	public static void main(String... args) {
		LinkedListNode node = ListUtils.createList(20, true);

		ListUtils.print(node);
		node = sortList(node);
		ListUtils.print(node);
	}

	private static LinkedListNode sortList(LinkedListNode parent) {
		LinkedListNode node1 = parent != null ? parent.getNext() : null;
		LinkedListNode node2 = node1 != null ? node1.getNext() : null;

		// if (parent == null || node1 == null || node2 == null || node1.getValue() <= node2.getValue())
		// return;

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
}
