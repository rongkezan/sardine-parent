package com.sardine.algorithm;

/**
 * 单向链表反转
 */
public class ReverseLinkedList {

    public static void main(String[] args) {
        Node head = new Node(1, new Node(2, new Node(3, null)));
        System.out.println(head);
        Node reverse = reverse(head);
        System.out.println(reverse);
    }

    private static Node reverse(Node head){
        Node prev = null;
        Node curr = head;
        while (curr != null) {
            Node next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    private static class Node {

        int element;

        Node next;

        Node(int element, Node next) {
            this.element = element;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "element=" + element +
                    ", next=" + next +
                    '}';
        }
    }
}
