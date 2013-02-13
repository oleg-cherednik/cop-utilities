package cop.test;

public class LinedListSort {
	public static void main(String... args) {
		Node node = createList();

		print(node);
		node = sortList(node);
		print(node);
	}

	private static Node sortList(Node parent) {
		Node node1 = parent != null ? parent.getNext() : null;
		Node node2 = node1 != null ? node1.getNext() : null;

//		if (parent == null || node1 == null || node2 == null || node1.getValue() <= node2.getValue())
//			return;

		parent.setNext(node2);
		node2.setNext(node1);
		node1.setNext(node2.getNext());
		
		
		
		return merge(node1, node2);
	}
	
	private static Node merge(Node node1, Node node2) {
		if(node1 == null && node2 == null)
			return null;
		if(node1 == null)
			return node2;
		if(node2 == null)
			return node1;
		
		Node first = null;
		Node prv = null;
		
		while(node1 != null && node2 != null) {
			Node next = node1.getValue() <= node2.getValue() ? node1 : node2;
			
			if(prv != null)
				prv.setNext(next);
			else
				prv = next;
			
			if(first != null)
				first = prv;
			
			if(node1 == next)
				node1 = node1.getNext();
			else
				node2 = node2.getNext();
		}
		
		if(node1 == null)
			prv.setNext(node2);
		else if(node2 == null)
			prv.setNext(node1);
		
		return first;
	}

	private static Node createList() {
		Node parent = null;
		Node prv = null;

		for (int i = 0; i < 20; i++) {
			Node node = new Node((int)(Math.random() * 100));// , prv);

			if (prv != null)
				prv.setNext(node);

			prv = node;
			parent = parent == null ? node : parent;
		}

		return parent;
	}

	private static void print(Node node) {
		do {
			System.out.print(node.getValue() + " ");
			node = node.getNext();
		} while (node != null);

		System.out.println();
	}
	
	private static class Node {
		private final int value;
		private Node next;
//		private Node prv;

		public Node(int value) {
			this.value = value;
		}

//		public Node(int value, Node prv) {
//			this.value = value;
//			this.prv = prv;
//		}

		public void setNext(Node node) {
			next = node;
		}

//		public void setPrv(Node node) {
//			prv = node;
//		}

		public int getValue() {
			return value;
		}

		public Node getNext() {
			return next;
		}

//		public Node getPrv() {
//			return prv;
//		}

		@Override
		public String toString() {
			return "" + value;
		}
	}
}
