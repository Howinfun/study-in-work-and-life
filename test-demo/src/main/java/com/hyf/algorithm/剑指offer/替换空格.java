package com.hyf.algorithm.剑指offer;

/**
 * @author Howinfun
 * @desc
 * @date 2019/10/14
 */
public class 替换空格 {
    public String replaceSpace(StringBuffer str) {
        return str.toString().replaceAll(" ","%20");
    }
}
