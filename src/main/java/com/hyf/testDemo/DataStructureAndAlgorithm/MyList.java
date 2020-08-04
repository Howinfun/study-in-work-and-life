package com.hyf.testDemo.DataStructureAndAlgorithm;

import lombok.Data;

/**
 * @author Howinfun
 * @desc 链表
 * @date 2020/8/4
 */
@Data
public class MyList<T>{

    /** 头节点 */
    public MyListEntry<T> head;
    /** 尾节点 */
    public MyListEntry<T> tail;

    /**
     * 添加节点
     * @param node
     */
    public void add(T node){
        final MyListEntry<T> l = tail;
        final MyListEntry<T> newNode = new MyListEntry<>();
        newNode.setValue(node);
        newNode.setNext(null);
        tail = newNode;
        if (l == null) {
            head = newNode;
        }else{
            l.next = newNode;
        }
    }
}