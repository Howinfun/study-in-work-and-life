package com.hyf.testDemo.TestAbstract;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/16
 */
public interface InterfaceTest {

    void method1();

    void method2();

    default void method3(){
        System.out.println("接口方法3");
    }
}
