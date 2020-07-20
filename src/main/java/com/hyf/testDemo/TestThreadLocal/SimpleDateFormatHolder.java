package com.hyf.testDemo.TestThreadLocal;

import java.text.SimpleDateFormat;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/20
 */
public class SimpleDateFormatHolder {

    public static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MMh-dd HH:mm:ss");
        }
    };
}
