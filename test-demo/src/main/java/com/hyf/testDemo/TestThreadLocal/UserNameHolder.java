package com.hyf.testDemo.TestThreadLocal;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/20
 */
public class UserNameHolder {

    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();
}
