package linkedlists;

public class InsertNodeInPosition {

    // Complete the insertNodeAtPosition function below.

    private static class  SinglyLinkedListNode {
        int data;
        SinglyLinkedListNode next;

        SinglyLinkedListNode(int data) {
            this.data = data;
        }
     }

    static SinglyLinkedListNode insertNodeAtPosition(SinglyLinkedListNode head, int data, int position) {
         SinglyLinkedListNode node = new SinglyLinkedListNode(data);

        if (head == null) {
            head = node;
            return head;
        }

        int currentPos = 0;
        SinglyLinkedListNode current = head;
        while (currentPos + 1 < position) {
            current = current.next;
            currentPos++;
        }

        node.next = current.next;
        current.next = node;
        return head;
    }

}
