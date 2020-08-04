package com.hyf.testDemo.DataStructureAndAlgorithm.Four;

import com.hyf.testDemo.DataStructureAndAlgorithm.MyList;
import com.hyf.testDemo.DataStructureAndAlgorithm.MyListEntry;

/**
 * @author Howinfun
 * @desc 查询奇数节点数的链表的中间位置
 * @date 2020/8/4
 */
public class FindListMiddleIndex {

    public static void main(String[] args) {
        MyList<Integer> list = new MyList<>();
        list.add(1);list.add(2);list.add(3);list.add(4);list.add(1);

        /**
         * 这个主要还是利用快慢指针，快指针的步数是慢指针的两倍，当快指针走到链表尾部，那么慢指针正好到链表的中间。
         *
         */
        MyListEntry<Integer> fast = list.head;
        MyListEntry<Integer> slow = fast;
        int tempIndex = 1;
        while (fast != null && fast.next != null && fast.next.next != null){
            fast = fast.next.next;
            slow = slow.next;
            ++tempIndex;
        }
        System.out.println(tempIndex);
    }
}
