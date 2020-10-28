package com.hyf.testDemo.DataStructureAndAlgorithm.string;

import java.util.Stack;

/**
 * @author Howinfun
 * @desc 给定一个字符串，逐个翻转字符串中的每个单词。例如，输入: "the sky is blue"，输出: "blue is sky the"。
 * 注意：遍历栈，使用 isEmpty 来判断栈是否为空，不为空就调用 pop() 弹出
 * @date 2020/8/10
 */
public class Test {

    public static void main(String[] args) {
        String s = "the sky is blue";
        Stack<Character> s1 = new Stack();
        Stack<Character> s2 = new Stack();
        for (int i = 0;i < s.length();i++){
            char c = s.charAt(i);
            if (c == ' '){
                while (!s1.isEmpty()){
                    char temp = s1.pop();
                    s2.push(temp);
                }
                s2.push(c);
            }else {
                s1.push(c);
            }
        }
        while (!s1.isEmpty()){
            char temp = s1.pop();
            s2.push(temp);
        }

        while (!s2.isEmpty()){
            char temp = s2.pop();
            System.out.print(temp);
        }
    }
}
