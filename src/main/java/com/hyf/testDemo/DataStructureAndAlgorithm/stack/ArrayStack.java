package com.hyf.testDemo.DataStructureAndAlgorithm.stack;

/**
 * @author Howinfun
 * @desc 顺序栈：基于数组
 * @date 2020/8/5
 */
public class ArrayStack<T> {

    /** 栈顶指针 top */
    private int top;
    private int capacity;
    private Object[] data;

    public ArrayStack(int capacity){
        this.capacity = capacity;
        data = new Object[capacity];
        top = 0;
    }

    public void push(T node){
        if (top < capacity){
            data[top++] = node;
        }else {
            throw new RuntimeException("越界！");
        }
    }

    public T pop(){
        --top;
        if (top >= 0){
            return (T) data[top];
        }else {
            throw new RuntimeException("越界！");
        }
    }

    public int length(){
        return capacity;
    }

    public static void main(String[] args) {
        ArrayStack<Integer> stack = new ArrayStack<>(5);
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
