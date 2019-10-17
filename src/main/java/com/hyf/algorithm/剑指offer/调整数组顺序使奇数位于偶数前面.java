package com.hyf.algorithm.剑指offer;

/**
 * @author Howinfun
 * @desc 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，所有的偶数位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。
 * @date 2019/10/17
 */
public class 调整数组顺序使奇数位于偶数前面 {

    public static void main(String[] args) {
        调整数组顺序使奇数位于偶数前面 h = new 调整数组顺序使奇数位于偶数前面();
        int[] arr = new int[]{2,4,5,3,6,1};
        h.reOrderArray(arr);
        printArray(arr);
    }

    /**
     *  利用冒泡排序，两数两两比较：指针指向数组的第一个数，如果第一个是偶数，第二个是奇数，则换位置，否则不换，指针指向下一个
     * @param array
     */
    public void reOrderArray(int [] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length-i; j++) {
                if (j < array.length-1-i){
                    if (isEven(array[j]) && !isEven(array[j+1])){
                        int temp = array[j+1];
                        array[j+1] = array[j];
                        array[j] = temp;
                    }
                }
            }
        }
    }

    /**
     * 判断数值是否为偶数
     * @param n
     * @return
     */
    private boolean isEven(int n){
        if(n%2==0) {
            return true;
        }
        return false;
    }

    /**
     * 打印数组
     * @param arr
     */
    public static void printArray(int[] arr) {
        if (arr != null && arr.length > 0) {
            for (int i : arr) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }
}
