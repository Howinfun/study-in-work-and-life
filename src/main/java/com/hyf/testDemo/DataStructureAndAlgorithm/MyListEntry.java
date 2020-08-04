package com.hyf.testDemo.DataStructureAndAlgorithm;

import lombok.Data;

import java.util.Objects;

/**
 * @author Howinfun
 * @desc 链表节点
 * @date 2020/8/4
 */
@Data
public class MyListEntry<T>{

        public T value;
        public MyListEntry<T> next;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            MyListEntry<?> that = (MyListEntry<?>) o;
            return Objects.equals(getValue(), that.getValue());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getValue());
        }
    }