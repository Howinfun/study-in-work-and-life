package com.hyf.algorithm.剑指offer;

import java.util.Stack;

/**
 * @author Howinfun
 * @desc 思路：stack1拿来做push操作，而stack2拿来做pop操作。当pop时，判断stack2是否为空，如果为空，则将stack1的元素一个一个弹出来放入stack2
 *          此时你会发现，元素push进stack1的顺序反了，最先push的在栈底，而当stack1元素弹出放入stack2后，元素的顺序又为正了，能达到队列的先进先出的特性。
 *          如果不为空，则从stack2中弹出一个元素
 * @date 2019/10/15
 */
public class 用两个栈实现队列 {
    /** push操作 */
    Stack<Integer> stack1 = new Stack<>();
    /** pop操作 */
    Stack<Integer> stack2 = new Stack<>();

    /**
     * 元素进队列
     * @param node
     */
    public void push(int node) {
        stack1.push(node);
    }

    /**
     * 元素出队列
     * @return
     */
    public int pop() {
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }

    public static void main(String[] args) {
        用两个栈实现队列 h = new 用两个栈实现队列();
        h.push(1);
        h.push(2);
        h.push(3);
        System.out.print(h.pop());
        System.out.print(h.pop());
        h.push(4);
        System.out.print(h.pop());
        h.push(5);
        System.out.print(h.pop());
        System.out.print(h.pop());
    }
}
