package com.hyf.testDemo.DataStructureAndAlgorithm.string;

/**
 * @author Howinfun
 * @desc 我们在字符串 A 中查找字符串 B，则 A 就是主串，B 就是模式串。
 * @date 2020/8/10
 */
public class StringMatching {

    public static void main(String[] args) {
        String a = "goodgoogle";
        String b = "google";
        boolean flag = false;
        for (int i = 0;i < (a.length() - b.length() + 1);i++){
            // 每次都先判断主串的字符是否和模式串第一个字符相等，相等才有后续的匹配
            if (a.charAt(i) == b.charAt(0)){
                // 记录匹配的长度，如果匹配长度达到模式串的长度，那么表示匹配成功
                int resultLength = 0;
                for (int j = 0; j < b.length(); j++){
                    if (a.charAt(i+j) != b.charAt(j)){
                        break;
                    }
                    if (++resultLength == b.length()-1){
                        flag = true;
                    }
                }
            }
        }
        System.out.println(flag);
    }
}
