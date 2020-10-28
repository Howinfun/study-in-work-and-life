package com.hyf.testDemo.DataStructureAndAlgorithm.string;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Howinfun
 * @desc 查找出两个字符串的最大公共字串，假设有且仅有 1 个最大公共子串。
 * @date 2020/8/10
 */
public class MaxCommonSubString {

    public static void main(String[] args) {
        Map<String,Integer> resultMap = new HashMap<>(10);
        // 最大公共字符串为 helloworld
        String a = "helloworldhaximeme";
        String b = "memehahahelloworld";
        String maxSubStr = "";
        int max_len = 0;
        for (int i = 0;i < (a.length() - b.length() + 1);i++){
            for (int j = 0; j < b.length(); j++){
                if (a.charAt(i) == b.charAt(j)){
                    for (int m = i,n = j; m<a.length() && n<b.length();m++,n++){
                        if (a.charAt(m) != b.charAt(n)){
                            break;
                        }
                        if (max_len < m-i+1){
                            max_len = m-i+1;
                            maxSubStr = a.substring(i, m+1);
                        }
                    }
                }
            }
        }
        System.out.println(maxSubStr);
    }
}
