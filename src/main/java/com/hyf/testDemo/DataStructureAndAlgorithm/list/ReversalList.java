package com.hyf.testDemo.DataStructureAndAlgorithm.list;

/**
 * @author Howinfun
 * @desc 反转列表
 * @date 2020/8/3
 */
public class ReversalList {

    public static void main(String[] args) {
        MyList<Integer> list = new MyList<>();
        list.add(1);list.add(2);list.add(3);list.add(4);list.add(5);

        /**
         * 反转列表
         *
         *     利用三指针，prev 指向前节点、currr 指向当前节点、 next 执行后节点
         *     假设头节点是1，那么 prev 就是null，currr 就是 1，next 就是2：
         *     在方法中，更新当前节点的 next 指针，然后将 prev 指针指向当前节点，接着让当 currr 指向 next 指针，表示往后继续遍历，如此类推。
         *     null -> 1 -> 2 -> 3
         */
        MyListEntry<Integer> prev = null;
        MyListEntry<Integer> next;
        MyListEntry<Integer> curr = list.head;
        while (curr != null){
            next = curr.next;
            curr.next = prev;
            prev = curr;
            // 用列表头节点保存替换后的 curr 节点
            list.head = curr;
            curr = next;

        }
        MyListEntry<Integer> head = list.head;
        while (head != null){
            System.out.println(head.value);
            head = head.next;
        }
    }
}
