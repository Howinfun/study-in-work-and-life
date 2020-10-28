package com.hyf.algorithm.剑指offer;

/**
 * @author Howinfun
 * @desc
 * @date 2019/10/15
 */
public class 旋转数组的最小数字 {

    /**
     * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
     * 输入一个非递减排序的数组的一个旋转，输出旋转数组的最小元素。
     * 例如数组{3,4,5,1,2}为{1,2,3,4,5}的一个旋转，该数组的最小值为1。
     * NOTE：给出的所有元素都大于0，若数组大小为0，请返回0。
     * @param array
     * @return
     */
    public int minNumberInRotateArray(int [] array) {
        if (array.length == 0){
            return 0;
        }
        int low = 0;
        int high = array.length-1;
        int mid = low;
        // 如果是递增的
        if (array[high] > array[low]){
            return array[low];
        }
        // 确保low在前一个排好序的部分，high在排好序的后一个部分，证明还没找到最小的数
        while (array[low] >= array[high]){
            // 如果low和high相隔一个，最小的数为array[high]
            if (high - low == 1){
                return array[high];
            }
            // 中间位置
            mid = (high+low)/2;
            // 判断mid对应的值是否在前一个排好序的部分，如果是则将low设置为mid
            if (array[mid] >= array[low]){
                low = mid;
            // 判断mid对应的值是否在后一个排好序的部分，如果是则将high设置为mid
            }else if(array[mid] <= array[high]){
                high = mid;
            }
        }
        return array[mid];
    }

    public static void main(String[] args) {
        旋转数组的最小数字 h = new 旋转数组的最小数字();
        // 1, 0, 1, 1, 1
        int min = h.minNumberInRotateArray(new int[]{3,4,5,1,2});
        System.out.println(min);
    }
}
