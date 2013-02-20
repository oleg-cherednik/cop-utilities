package cop.test.lists;

final class LinkedListNode {
	private final int value;
	private LinkedListNode next;

	public LinkedListNode(int value) {
		this.value = value;
	}

	public void setNext(LinkedListNode node) {
		next = node;
	}

	public int getValue() {
		return value;
	}

	public LinkedListNode getNext() {
		return next;
	}

	@Override
	public String toString() {
		return "" + value;
	}
}
