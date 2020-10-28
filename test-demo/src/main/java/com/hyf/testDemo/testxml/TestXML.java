package com.hyf.testDemo.testxml;

import com.thoughtworks.xstream.XStream;

import java.io.File;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/20
 */
public class TestXML {

    public static void main(String[] args) {
        XStream xStream = new XStream();
        xStream.processAnnotations(RECORDS.class);
        xStream.setClassLoader(RECORDS.class.getClassLoader());
        RECORDS records = (RECORDS) xStream.fromXML(new File("D:\\万城万充\\文档\\车系统\\后台天气预警\\城市列表\\城市列表.xml"));
        for (RECORD record : records.records) {
            System.out.println(record.getName());
        }
    }
}
