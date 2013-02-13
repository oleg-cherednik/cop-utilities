package cop.test;

import java.util.Random;

public class BinaryTreePrint {
	public static void main(String... args) {
		Root root = makeTree(1000);
		long state = root.inject(0);

		System.out.println(state);
	}

	private static Root makeTree(int total) {
		Root root = new Root();
		Random rand = new Random();
		long state = 0;
		int tmp;

		for (int i = 0; i < total; i++) {
			root.add(tmp = rand.nextInt(100000));
			root.addSort(tmp + 1);
			state += 2 * tmp + 1;
		}
		
		System.out.println(state);

		return root;
	}

	// ========== Root ==========

	private static class Root {
		private Node root;

//		public int getSize() {
//			return root.getSize();
//		}

		public void add(int value) {
			if (root == null)
				root = new Node(value);
			else
				root.add(value);
		}

		public void addSort(int value) {
			if (root == null)
				root = new Node(value);
			else
				root.addSort(value);
		}

//		public Node getRoot() {
//			return root;
//		}

		private Node store(Node branch, Node edit) {
			Node node = branch;

			while (!node.isLeaf()) {
				node = node.getNext();
			}

			node.setLeft(branch);
			node.setRight(edit);

			return node;
		}

		private Node fetch(Node node) {
			Node tmp = node.getRight();
			node.setLeft(null);
			node.setRight(null);
			return tmp;
		}

		public int inject(int state) {
			if (root == null)
				return -1;

			Node curr = root;
			Node edit = null;

			while (!(edit == null && curr.isLeaf())) {
				state += curr.getValue();

				if (curr.isNormalBranch())
					curr = curr.getNext();
				else if (curr.isLeaf()) {
					curr = edit.getLeft().getRight();
					edit = fetch(edit);
				} else {
					edit = store(curr, edit);
					curr = curr.getLeft();
				}
			}
			
			state += curr.getValue();

			return state;
		}
	}

	// ========== Node ==========

	private static class Node {
		private final int value;
		private Node left;
		private Node right;

		public Node(int value) {
			this.value = value;
		}

		public void add(int value) {
			if (left == null)
				left = new Node(value);
			else if (right == null)
				right = new Node(value);
			else if (left.getSize() < right.getSize())
				left.add(value);
			else
				right.add(value);
		}

		public void addSort(int value) {
			if (Integer.compare(this.value, value) == -1) {
				if (left == null)
					left = new Node(value);
				else
					left.addSort(value);
			} else {
				if (right == null)
					right = new Node(value);
				else
					right.addSort(value);
			}
		}

		public int getSize() {
			return 1 + (left == null ? 0 : left.getSize()) + (right == null ? 0 : right.getSize());
		}

		public Node getNext() {
			if (right != null)
				return right;
			if (left != null)
				return left;
			return null;
		}

		public boolean isNormalBranch() {
			if (left == null && right != null)
				return true;
			if (left != null && right == null)
				return true;
			return false;
		}

		public boolean isLeaf() {
			return left == null && right == null;
		}

		public Node getLeft() {
			return left;
		}

		public void setLeft(Node left) {
			this.left = left;
		}

		public Node getRight() {
			return right;
		}

		public void setRight(Node right) {
			this.right = right;
		}

		public int getValue() {
			return value;
		}
	}
}
