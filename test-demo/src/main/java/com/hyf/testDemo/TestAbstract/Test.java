package com.hyf.testDemo.TestAbstract;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/16
 */
public class Test extends AbstractTest implements InterfaceTest{
    @Override
    public void method2() {
        System.out.println("实现类方法2");
    }

    @Override
    public void method3() {
        System.out.println("实现类方法3");
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.method2();
        test.method3();
        test.method1();
    }
}
