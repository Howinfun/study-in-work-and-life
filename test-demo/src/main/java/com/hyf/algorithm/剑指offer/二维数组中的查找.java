package com.hyf.algorithm.剑指offer;

/**
 * @author Howinfun
 * @desc 在一个二维数组中（每个一维数组的长度相同），
 *      每一行都按照从左到右递增的顺序排序，
 *      每一列都按照从上到下递增的顺序排序。
 *      请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 * @date 2019/10/14
 */
public class 二维数组中的查找 {
    public static void main(String[] args) {
        boolean flag = find2(7,new int[][]{{1,2,8,9},{4,7,10,13}});
        System.out.println(flag);
    }
    /**
     * 思路：把每一行看成有序递增的数组，利用二分查找，过遍历每一行得到答案，
     * @param target
     * @param array
     * @return
     */
    public static boolean find(int target, int[][] array) {
        for (int i = 0; i < array.length; i++) {
            int beginIndex = 0;
            int endIndex = array[i].length-1;
            while (beginIndex <= endIndex){
                int midIndex = (endIndex+beginIndex)/2;
                int mid = array[i][midIndex];
                if (target == mid){
                    return true;
                }else if (target < mid){
                    endIndex = midIndex - 1;
                }else {
                    beginIndex = midIndex + 1;
                }
            }
        }
        return false;
    }

    /**
     * 利用二维数组由上到下，由左到右递增的规律，
     * 那么选取右上角或者左下角的元素a[row][col]与target进行比较，
     * 当target小于元素a[row][col]时，那么target必定在元素a所在行的左边,
     * 即col--；
     * 当target大于元素a[row][col]时，那么target必定在元素a所在列的下边,
     * 即row++；
     * @return
     */
    public static boolean find2(int target, int[][] array){
        // 左下角为始点
        int row = array.length-1;
        int col = 0;
        while(row >= 0 && col <= array[0].length-1){
            if (target == array[row][col]){
                return true;
            }else if (target < array[row][col]){
                row--;
            }else if (target > array[row][col]){
                col++;
            }
        }
        return false;
    }
}
