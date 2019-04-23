package stacks;

import java.util.Scanner;

public class TaleOfTwoStacks {

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    private static class MyQueue<T> {

        Node<T> head;
        Node<T> tail;

        MyQueue() {
            head = null;
            tail = null;
        }

        void enqueue(T data) {
            if (head == null) {
                head = new Node<>(data);
                tail = head;
            } else {
                tail.next = new Node<>(data);
                tail = tail.next;
            }
        }

        void dequeue() {
            head = head.next;
            if (head == null) {
                tail = null;
            }
        }

        T peek() {
            return head.data;
        }
    }

    public static void main(String[] args) {
        MyQueue<Integer> queue = new MyQueue<>();

        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();

        for (int i = 0; i < n; i++) {
            int operation = scan.nextInt();
            if (operation == 1) { // enqueue
                queue.enqueue(scan.nextInt());
            } else if (operation == 2) { // dequeue
                queue.dequeue();
            } else if (operation == 3) { // print/peek
                System.out.println(queue.peek());
            }
        }
        scan.close();
    }

}
