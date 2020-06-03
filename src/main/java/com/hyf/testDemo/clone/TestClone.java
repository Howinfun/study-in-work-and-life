package com.hyf.testDemo.clone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;

/**
 * @author Howinfun
 * @desc
 * @date 2020/6/3
 */
public class TestClone {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    static class User{
        private Long id;
        private String name;
    }

    public static void main(String[] args){

        User[] u1= {new User(1L,"Howinfun")};
        User[] u2 = Arrays.copyOf(u1,u1.length);

        u1[0].setName("pengpeng");
        System.out.println("u1:" + u1[0].getName());
        System.out.println("u2:" + u2[0].getName());
    }
}
