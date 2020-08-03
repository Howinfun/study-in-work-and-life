package com.hyf.testDemo.DataStructureAndAlgorithm;

/**
 * @author Howinfun
 * @desc 对于一个包含 5 个元素的数组，如果要把这个数组元素的顺序翻转过来。你可以试着分析该过程需要对数据进行哪些操作？
 * @date 2020/8/3
 */
public class ThreeClass {

    public static void main(String[] args) {
        int[] intArr = new int[]{1,2,3,4,5};
        int[] newArr = new int[5];
        int length = intArr.length;
        /**
         * 时间复杂度 O(n) 遍历整个数组
         * 空间复杂度 O(n) 用一个长度一样的新数组保存数据
         */
        /*for (int i = length-1;i>=0;i--){
            newArr[(length-1-i)] = intArr[i];
        }
        for (int i : newArr) {
            System.out.println(i);
        }*/

        /**
         * 利用二分法
         */
        int len = length / 2;
        for (int i = 0; i< len; i++){
            int temp = intArr[i];
            intArr[i] = intArr[length-1-i];
            intArr[length-1-i] = temp;
        }
        for (int i : intArr) {
            System.out.println(i);
        }
    }


}
