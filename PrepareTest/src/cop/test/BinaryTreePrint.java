package cop.test;

import java.util.Random;

public class BinaryTreePrint {
	public static void main(String... args) {
		Root<Integer> root = new Root<Integer>();
		Random rand = new Random();
		long state = 0;
		int tmp;
		for (int i = 0; i < 10000; i++) {
			tmp = rand.nextInt(100000);
			root.add(tmp);
			root.addSort(tmp + 1);
			state += 2 * tmp + 1;
		}
		System.out.println(state == root.inject(0, new Callable<Integer, Integer>() {
			public Integer call(Integer a, Integer b) {
				return a + b;
			}
		}));
	}

	interface Callable<T1, T2> {
		public T1 call(T1 acc, T2 e);
	}

	// ========== Root ==========

	private static class Root<T extends Comparable<T>> {
		private Node<T> root;

		public Root() {
		}

		public int getSize() {
			return root.getSize();
		}

		public void add(T value) {
			if (root == null) {
				root = new Node<T>(value);
			} else {
				root.add(value);
			}
		}

		public void addSort(T value) {
			if (root == null) {
				root = new Node<T>(value);
			} else {
				root.addSort(value);
			}
		}

		public Node<T> getRoot() {
			return root;
		}

		private Node<T> store(Node<T> branch, Node<T> edit) {
			Node<T> node = branch;
			while (!node.isLeaf()) {
				node = node.getNext();
			}
			node.setLeft(branch);
			node.setRight(edit);
			return node;
		}

		private Node<T> fetch(Node<T> node) {
			Node<T> tmp = node.getRight();
			node.setLeft(null);
			node.setRight(null);
			return tmp;
		}

		public <TAcc> TAcc inject(TAcc state, Callable<TAcc, T> fun) {
			if (root == null)
				return null;
			Node<T> current = root;
			Node<T> edit = null;
			while (!(edit == null && current.isLeaf())) {
				state = fun.call(state, current.getValue());
				if (current.isNormalBranch()) {
					current = current.getNext();
				} else {
					if (current.isLeaf()) {
						current = edit.getLeft().getRight();
						edit = fetch(edit);
					} else {
						edit = store(current, edit);
						current = current.getLeft();
					}
				}
			}
			state = fun.call(state, current.getValue());
			return state;
		}
	}

	// ========== Node ==========

	private static class Node<T extends Comparable<T>> {
		private final T value;
		private Node<T> left;
		private Node<T> right;

		public Node(T value) {
			this.value = value;
		}

		public void add(T value) {
			if (left == null)
				left = new Node<T>(value);
			else if (right == null)
				right = new Node<T>(value);
			else if (left.getSize() < right.getSize())
				left.add(value);
			else
				right.add(value);
		}

		public void addSort(T value) {
			if (this.value.compareTo(value) == -1) {
				if (left == null) {
					left = new Node<T>(value);
				} else {
					left.addSort(value);
				}
			} else {
				if (right == null) {
					right = new Node<T>(value);
				} else {
					right.addSort(value);
				}
			}
		}

		public int getSize() {
			return 1 + (left == null ? 0 : left.getSize()) + (right == null ? 0 : right.getSize());
		}

		public Node<T> getNext() {
			if (!(right == null))
				return right;
			if (!(left == null))
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

		public Node<T> getLeft() {
			return left;
		}

		public void setLeft(Node<T> left) {
			this.left = left;
		}

		public Node<T> getRight() {
			return right;
		}

		public void setRight(Node<T> right) {
			this.right = right;
		}

		public T getValue() {
			return value;
		}
	}
}
