package com.hyf.algorithm.剑指offer;

/**
 * @author Howinfun
 * @desc 输入一个链表，输出该链表中倒数第k个结点。
 * @date 2019/10/17
 */
public class 链表中倒数第k个结点 {
    /**
     * 倒数第 k 的节点和最后一个节点相隔 k-1 个节点，可以利用此做文章。
     * 两指针，一个指针从头先走 k-1 步，第二个指针才开始头走，当第一个指针走到最后一个节点，第二个指针正指着倒数第 k 的节点。
     * 其实这做法和两次遍历链表的时间复杂度一样：先遍历链表有多少个节点，再遍历获取倒数第 k 的节点。
     * @param head
     * @param k
     * @return
     */
    public ListNode FindKthToTail(ListNode head,int k) {
        if (head == null){
            System.out.println("链表为空");
        }
        ListNode p = null,pre = null;
        // 指针1
        p = head;
        // 指针2
        pre = head;
        // 记录k值，拿来最后做判断
        int a = k;
        // 记录节点个数
        int count = 0;
        while (p!=null){
            p = p.next;
            count++;
            if (k<1){
                pre = pre.next;
            }
            k--;
        }
        if (count < a){
            return null;
        }
        return pre;
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
        链表中倒数第k个结点 h = new 链表中倒数第k个结点();
        System.out.println(h.FindKthToTail(n1,1).val);
        System.out.println(h.FindKthToTail(n1,5).val);
        System.out.println(h.FindKthToTail(n1,10).val);
    }
}
