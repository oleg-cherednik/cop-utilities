package cop.test;

public final class LinkedListNode {
	private final int value;
	private LinkedListNode next;

	// private Node prv;

	public LinkedListNode(int value) {
		this.value = value;
	}

	// public Node(int value, Node prv) {
	// this.value = value;
	// this.prv = prv;
	// }

	public void setNext(LinkedListNode node) {
		next = node;
	}

	// public void setPrv(Node node) {
	// prv = node;
	// }

	public int getValue() {
		return value;
	}

	public LinkedListNode getNext() {
		return next;
	}

	// public Node getPrv() {
	// return prv;
	// }

	@Override
	public String toString() {
		return "" + value;
	}
}
