package com.sardine.algorithm;

/**
 * 删除链表的倒数第n个节点
 */
public class RemoveLinkedListNodeFromEnd {

    public static void main(String[] args) {
        Node head = new Node(1, new Node(2, new Node(3, null)));
        Node afterRemove = removeNodeFromEnd(head, 2);
        System.out.println(afterRemove);
    }

    private static Node removeNodeFromEnd(Node head, int n){
        // 创建一个临时节点在head节点之前
        int length = getLength(head);
        int offset = length - n;
        Node curr = head;
        for (int i = 0; i < offset; i++) {
            curr = curr.next;
        }
        curr.next = curr.next.next;
        return head;
    }

    private static int getLength(Node head){
        int length = 0;
        while (head != null) {
            head = head.next;
            length++;
        }
        return length;
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
