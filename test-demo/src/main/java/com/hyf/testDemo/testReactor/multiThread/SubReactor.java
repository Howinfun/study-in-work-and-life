package com.hyf.testDemo.testReactor.multiThread;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/4
 */
public class SubReactor implements Runnable{

    private Selector selector;

    public SubReactor(Selector selector){
        this.selector = selector;
    }

    @Override
    public void run() {
        try {
            while (true){
                if (selector.select() > 0){
                    Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeySet.iterator();
                    while (iterator.hasNext()){
                        SelectionKey sk = iterator.next();
                        dispatch(sk);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void dispatch(SelectionKey sk){
        Runnable handler = (Runnable) sk.attachment();
        handler.run();
    }
}
