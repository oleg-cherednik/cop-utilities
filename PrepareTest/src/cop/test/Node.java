package cop.test;

public class Node {
	private final int value;
	private Node next;
//	private Node prv;

	public Node(int value) {
		this.value = value;
	}

//	public Node(int value, Node prv) {
//		this.value = value;
//		this.prv = prv;
//	}

	public void setNext(Node node) {
		next = node;
	}

//	public void setPrv(Node node) {
//		prv = node;
//	}

	public int getValue() {
		return value;
	}

	public Node getNext() {
		return next;
	}

//	public Node getPrv() {
//		return prv;
//	}

	@Override
	public String toString() {
		return "" + value;
	}
}
