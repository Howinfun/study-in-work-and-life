package com.hyf.testDemo.DataStructureAndAlgorithm.stack;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @author Howinfun
 * @desc 下我们的习题是，给定一个包含 n 个元素的链表，现在要求每 k 个节点一组进行翻转，打印翻转后的链表结果。其中，k 是一个正整数，且 n 可被 k 整除。
 *       例如，链表为 1 -> 2 -> 3 -> 4 -> 5 -> 6，k = 3，则打印 321654。仍然是这道题，我们试试用栈来解决它吧。
 * @date 2020/8/5
 */
public class Exercise {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        System.out.println("list size:"+list.size());
        Stack<Integer> stack = new Stack<>();
        int k = 5;
        int count = 0;
        for (int i = 0; i < list.size();i++){
            stack.push(list.get(i));
            if (++count == k) {
                for (int j = 1; j <= k; j++) {
                    System.out.println(stack.pop());
                    --count;
                }
            }
        }
    }
}
