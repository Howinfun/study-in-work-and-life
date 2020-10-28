package com.hyf.testDemo.DataStructureAndAlgorithm.stack;

import java.util.Stack;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/5
 */
public class CheckBrackets {

    public static void main(String[] args) {
        String str = "{[()]}";
        System.out.println(isLegal(str));
    }

    private static String isLegal(String str){
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i< str.length();i++){
            char curr = str.charAt(i);
            if (isLeft(curr) == 1){
                stack.push(curr);
            }else {
                if (stack.isEmpty()){
                    return "非法";
                }else {
                    char p = stack.pop();
                    if (isPair(p,curr) == 0){
                        return "非法";
                    }
                }
            }
        }
        if (stack.isEmpty()){
            return "合法";
        }else {
            return "非法";
        }
    }

    private static int isLeft(char c) {
        if (c == '{' || c == '(' || c == '[') {
            return 1;
        } else {
            return 2;
        }
    }
    private static int isPair(char p, char curr) {
        if ((p == '{' && curr == '}') || (p == '[' && curr == ']') || (p == '(' && curr == ')')) {
            return 1;
        } else {
            return 0;
        }
    }
}
