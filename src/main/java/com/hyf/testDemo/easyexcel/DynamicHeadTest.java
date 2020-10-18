package com.hyf.testDemo.easyexcel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DynamicHeadTest {

    public static void main(String[] args) {
        String fileName = "dynamicHeadWrite" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName)
                // 这里放入动态头
                .head(head()).sheet("模板")
                // 当然这里数据也可以用 List<List<String>> 去传入
                .doWrite(dataList());
    }

    public static List<List<String>> dataList() {
        List<List<String>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<String> list1 = new ArrayList<>();
            list1.add("字符串"+i);
            list1.add("数字"+i);
            list.add(list1);
        }
        return list;
    }

    public static List<List<String>> head() {
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> head0 = new ArrayList<String>();
        head0.add("字符串" + System.currentTimeMillis());
        List<String> head1 = new ArrayList<String>();
        head1.add("数字" + System.currentTimeMillis());
        list.add(head0);
        list.add(head1);
        return list;
    }
}
