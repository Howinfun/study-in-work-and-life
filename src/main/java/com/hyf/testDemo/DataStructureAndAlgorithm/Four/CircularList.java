package com.hyf.testDemo.DataStructureAndAlgorithm.Four;

import com.hyf.testDemo.DataStructureAndAlgorithm.MyList;
import com.hyf.testDemo.DataStructureAndAlgorithm.MyListEntry;

/**
 * @author Howinfun
 * @desc 循环链表
 * @date 2020/8/4
 */
public class CircularList {

    public static void main(String[] args) {
        MyList<Integer> list = new MyList<>();
        list.add(1);list.add(2);list.add(3);list.add(4);list.add(1);list.add(5);list.add(6);

        /**
         * 判断循环连接，主要判断是否有重复节点，所以最重要的是，我们需要重写节点的 equals 和 hashCode。改为直接判断 value 值。
         * 判断的原理是：利用快指针和慢指针，每次遍历，快指针走两步，慢指针走一步，如果存在循环链表，那么快指针会遇到慢指针
         *
         * TODO ps：模拟不出来循环链表，因为自己整的链表，如果带循环逻辑，那么在添加节点时会栈溢出~
         */
        MyListEntry<Integer> fast = list.head;
        MyListEntry<Integer> slow = fast;
        while (fast != null && fast.next != null && fast.next.next != null){
            fast = fast.next.next;
            slow = slow.next;
            if (fast.equals(slow)){
                System.out.println("是循环链表");
                break;
            }
        }
    }
}
