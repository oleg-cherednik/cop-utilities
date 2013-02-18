package cop.test;

/**
 * Swap linked list elements
 * 
 * @author Oleg Cherednik
 * @since 18.02.2013
 */
public class ReverseLinkedList {
	public static void main(String... args) {
		LinkedListNode node = createList();

		print(node);
		node = reverseList(node);
		print(node);
	}

	private static LinkedListNode reverseList(LinkedListNode node) {
		if (node == null || node.getNext() == null)
			return node;

		LinkedListNode node1 = node.getNext();
		LinkedListNode node2 = node1.getNext();

		node.setNext(null);

		do {
			node1.setNext(node);
			node = node1;
			node1 = node2;
			node2 = node1.getNext();
		} while (node2 != null);

		node1.setNext(node);

		return node1;
	}

	private static LinkedListNode createList() {
		LinkedListNode parent = null;
		LinkedListNode prv = null;

		for (int i = 1; i <= 20; i++) {
			LinkedListNode node = new LinkedListNode(i);

			if (prv != null)
				prv.setNext(node);

			prv = node;
			parent = parent == null ? node : parent;
		}

		return parent;
	}

	private static void print(LinkedListNode node) {
		do {
			System.out.printf(" %2d", node.getValue());
			node = node.getNext();
		} while (node != null);

		System.out.println();
	}

}
