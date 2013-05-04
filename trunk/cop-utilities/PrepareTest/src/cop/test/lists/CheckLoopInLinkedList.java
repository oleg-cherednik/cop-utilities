package cop.test.lists;

/**
 * Checks whether linked list has loop or not
 * 
 * @author Oleg Cherednik
 * @since 13.02.2013
 */
public class CheckLoopInLinkedList {
	public static void main(String... args) {
		LinkedListNode head = ListUtils.createList(10, 4, false);
		System.out.println(loopInList(head));
	}

	private static boolean loopInList(LinkedListNode head) {
		LinkedListNode fastMarker = head;
		LinkedListNode slowMarker = head;

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

}
