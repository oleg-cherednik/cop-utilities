package cop.test.lists;

/**
 * Swap linked list elements
 * 
 * @author Oleg Cherednik
 * @since 18.02.2013
 */
public class ReverseLinkedList {
	public static void main(String... args) {
		LinkedListNode node = ListUtils.createList(20, false);

		ListUtils.print(node);
		node = reverseList(node);
		ListUtils.print(node);
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
}
