package com.hyf.testDemo.DataStructureAndAlgorithm.array;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Howinfun
 * @desc 给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素只出现一次，返回移除后的数组和新的长度，你不需要考虑数组中超出新长度后面的元素。要求，空间复杂度为 O(1)，即不要使用额外的数组空间。
 * 例如，给定数组 nums = [1,1,2]，函数应该返回新的长度 2，并且原数组 nums 的前两个元素被修改为 1, 2。 又如，给定 nums = [0,0,1,1,1,2,2,3,3,4]，函数应该返回新的长度 5，并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4。
 * @date 2020/8/7
 */
public class Exercise {

    public static void main(String[] args) {

        /**
         * 利用 map
         */
        Map<Integer,Integer> result = new HashMap<>(10);
        Integer[] intArr = {0,0,1,3,5,5,6};
        System.out.print("初始时数组数据：");
        for (Integer integer : intArr) {
            System.out.print(integer);
        }
        System.out.println();
        for (int i = 0; i < intArr.length; i++){
            Integer temp = intArr[i];
            Integer count = result.get(temp);
            if (count == null){
                result.put(temp,1);
            }
        }
        System.out.println("处理后的数组数量："+result.size());
        System.out.print("处理后的数据：");
        result.keySet().forEach(integer -> System.out.print(integer));
    }
}
