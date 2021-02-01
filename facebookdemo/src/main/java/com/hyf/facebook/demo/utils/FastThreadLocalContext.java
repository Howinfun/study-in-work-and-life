package com.hyf.facebook.demo.utils;

import io.netty.util.concurrent.FastThreadLocal;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @Description: ThreadLocal
 * @Author: heshishan
 * @Date: 2020/9/25 7:38 下午
 **/
public class FastThreadLocalContext {

    private static FastThreadLocal<Map<String, Object>> fastThreadLocal = new FastThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<>(8);
        }
    };

    public static  Object put(String key, Object value) {
        return fastThreadLocal.get().put(key, value);
    }

    public static Object get(String key) {
        return fastThreadLocal.get().get(key);
    }

    public static Object remove(String key) {
        return fastThreadLocal.get().remove(key);
    }

    public static Map<String, Object> entries() {
        return fastThreadLocal.get();
    }
}
