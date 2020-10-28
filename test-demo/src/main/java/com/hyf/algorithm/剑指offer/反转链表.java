package com.hyf.algorithm.剑指offer;

/**
 * @author Howinfun
 * @desc 输入一个链表，反转链表后，输出新链表的表头
 * @date 2019/10/17
 */
public class 反转链表 {

    /**
     * ps:变量pre作为反转后的链表的链头。
     *  1->2->3->4
     * 1、首先pre等于null，然后先用next保存着head.next(以便后面的继续操作)，然后head.next = pre，作用是先反转原链头，让head指向pre。
     *  (pre)null<-(head)1  2->3->4
     *  我们可以看到，因为反转链头执行pre后，链表就断开了，所以要用next来保存head.next，即右边的链表。
     * 2、然后我们再将head赋值給pre，next赋值給head
     *  null<-(pre)1  (head)2->3->4
     * 3、然后一直重复上面1和2步骤，知道head等于null，就是链表遍历完了。
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        if (head == null){
            return null;
        }
        ListNode pre = null;
        ListNode next = null;
        while (head != null){
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    /**
     * 遍历链表
     * @param head
     */
    public static void printListNode(ListNode head){
        if (head == null){
            System.out.println("null");
        }
        while(head != null){
            System.out.print(head.val+"->");
            head = head.next;
        }
        System.out.print("null");
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
        反转链表 h = new 反转链表();
        printListNode(h.reverseList(n1));

    }
}
