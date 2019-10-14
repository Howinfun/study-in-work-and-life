package com.hyf.algorithm.剑指offer;

import lombok.Data;

import java.util.ArrayList;

public class 从尾到头打印链表 {
    /** 成员变量，存放结果 **/
    private ArrayList<Integer> arrayList = new ArrayList<>();
    /**
     * 思路：利用递归：如果存在下一个节点则继续获取下一个节点，否则打印val。这样就会从里到外打印val，即从尾到头
     * @param listNode
     * @return
     */
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        if (listNode != null){
            if (listNode.next != null){
                this.printListFromTailToHead(listNode.next);
            }
            arrayList.add(listNode.val);
        }
        return this.arrayList;
    }

    public static void main(String[] args) {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);
        ListNode n6 = new ListNode(6);
        n1.setNext(n2);
        n2.setNext(n3);
        n3.setNext(n4);
        n4.setNext(n5);
        n5.setNext(n6);
        从尾到头打印链表 h = new 从尾到头打印链表();
        System.out.println(h.printListFromTailToHead(n1));
    }
}
@Data
class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}