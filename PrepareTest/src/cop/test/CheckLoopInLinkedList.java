package cop.test;

/**
 * Checks whether linked list has loop or not
 * 
 * @author Oleg Cherednik
 * @since 13.02.2013
 */
public class CheckLoopInLinkedList {
	public static void main(String... args) {
		Node head = makeList(10, 4);
		System.out.println(loopInList(head));
	}

	private static boolean loopInList(Node head) {
		Node fastMarker = head;
		Node slowMarker = head;

		if (fastMarker.getNext() == null)
			return true;
		fastMarker = fastMarker.getNext();

		while (true) {
			if (fastMarker.getNext() == null || fastMarker.getNext().getNext() == null)
				return false;
			if (fastMarker.getNext() == slowMarker || fastMarker.getNext().getNext() == slowMarker)
				return true;

			fastMarker = fastMarker.getNext().getNext();
			slowMarker = slowMarker.getNext();
		}
	}

	private static Node makeList(int all, int inLoop) {
		int value = 1;
		Node head = new Node(value++);
		Node curr = head;

		for (int i = 0; i < all - inLoop; i++) {
			Node node = new Node(value++);
			curr.setNext(node);
			curr = node;
		}

		if (inLoop > 0) {
			Node loopStart = curr;

			for (int i = 0; i < inLoop; i++) {
				Node node = new Node(value++);
				curr.setNext(node);
				curr = node;
			}
			curr.setNext(loopStart.getNext());
		}

		return head;
	}

	// ========== LinkedList ==========

	private static class Node {
		private final int value;
		private Node next;

		Node(int value) {
			this.value = value;
		}

		public void setNext(Node next) {
			this.next = next;
		}

		public Node getNext() {
			return next;
		}
	};
}
