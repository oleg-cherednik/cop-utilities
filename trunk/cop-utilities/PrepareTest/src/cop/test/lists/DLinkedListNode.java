package cop.test.lists;

final class DLinkedListNode {
	private final int value;
	private DLinkedListNode next;
	private DLinkedListNode prv;

	public DLinkedListNode(int value) {
		this.value = value;
	}

	public void setNext(DLinkedListNode node) {
		next = node;
	}

	public void setPrv(DLinkedListNode node) {
		prv = node;
	}

	public int getValue() {
		return value;
	}

	public DLinkedListNode getNext() {
		return next;
	}

	public DLinkedListNode getPrv() {
		return prv;
	}

	@Override
	public String toString() {
		return "" + value;
	}
}
