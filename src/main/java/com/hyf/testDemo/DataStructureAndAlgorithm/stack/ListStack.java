package com.hyf.testDemo.DataStructureAndAlgorithm.stack;

/**
 * @author Howinfun
 * @desc 链式栈：基于链表
 * @date 2020/8/5
 */
public class ListStack<T> {

    public ListStackNode<T> top;

    public ListStack(){
        top = null;
    }

    public void push(T data){
        if (top == null){
            ListStackNode<T> node = new ListStackNode();
            node.value = data;
            node.next = null;
            top = node;
        }else {
            final ListStackNode t = top;
            ListStackNode<T> node = new ListStackNode();
            node.value = data;
            node.next = t;
            top = node;
        }
    }

    public T pop(){
        if (top == null){
            return null;
        }else {
            ListStackNode<T> result = top;
            ListStackNode<T> n = top.next;
            top = n;
            return result.value;
        }
    }


    class ListStackNode<T>{

        public T value;
        public ListStackNode<T> next;
    }

    public static void main(String[] args) {
        ListStack<Integer> stack = new ListStack<>();
        stack.push(1);stack.push(2);stack.push(3);stack.push(4);stack.push(5);
        for (int i = 0; i < 4 ;i++){
            System.out.println(stack.pop());
        }
        stack.push(6);stack.push(7);
        for (int i = 0; i < 3 ;i++){
            System.out.println(stack.pop());
        }
    }
}
