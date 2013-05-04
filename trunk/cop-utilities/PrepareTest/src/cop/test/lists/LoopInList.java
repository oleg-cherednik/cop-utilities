package cop.test.lists;

/**
 * Find loop in list, it's size and first element
 * 
 * @author Oleg Cherednik
 * @since 18.02.2013
 */
public class LoopInList {
	public static void main(String... args) {
		LinkedListNode node = ListUtils.createList(20, 5, false);
		ListUtils.print(node, 20);

		int[] res = loop(node);

		if (res != null)
			System.out.println("first: " + res[0] + ", size: " + res[1]);
	}

	private static int[] loop(final LinkedListNode node) {
		LinkedListNode loopNode = findNodeInLoop(node);

		if (loopNode == null)
			return null;

		int first = getFirstLoopNode(node, loopNode);
		int size = getLoopSize(loopNode);

		return new int[] { first, size };

	}

	private static LinkedListNode findNodeInLoop(LinkedListNode node) {
		LinkedListNode fastMarker = node;
		LinkedListNode slowMarker = node;

		if (fastMarker.getNext() == null)
			return null;
		fastMarker = fastMarker.getNext();

		while (true) {
			if (fastMarker.getNext() == null || fastMarker.getNext().getNext() == null)
				return null;
			if (fastMarker.getNext() == slowMarker || fastMarker.getNext().getNext() == slowMarker)
				return slowMarker;

			fastMarker = fastMarker.getNext().getNext();
			slowMarker = slowMarker.getNext();
		}
	}

	private static int getLoopSize(final LinkedListNode loopNode) {
		int size = 0;
		LinkedListNode node = loopNode;

		do {
			size++;
			node = node.getNext();
		} while (node.getNext() != loopNode);

		return size;
	}

	private static int getFirstLoopNode(LinkedListNode node, final LinkedListNode loopNode) {
		while (!isInLoop(node, loopNode)) {
			node = node.getNext();
		}

		return node.getValue();
	}

	private static boolean isInLoop(final LinkedListNode node, LinkedListNode loopNode) {
		final LinkedListNode tmp = loopNode;

		do {
			loopNode = loopNode.getNext();
		} while (loopNode != node && loopNode != tmp);

		return loopNode != node;

	}
}
