package com.hyf.algorithm;

import com.hyf.testDemo.testxml.AreaInfoMapper;
import com.hyf.testDemo.testxml.RECORD;
import com.hyf.testDemo.testxml.RECORDS;
import com.thoughtworks.xstream.XStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAlgorithmApplicationTests {

    @Resource
    private AreaInfoMapper areaInfoMapper;

    @Test
    public void contextLoads() {
        XStream xStream = new XStream();
        xStream.processAnnotations(RECORDS.class);
        xStream.setClassLoader(RECORDS.class.getClassLoader());
        RECORDS records = (RECORDS) xStream.fromXML(new File("D:\\万城万充\\文档\\车系统\\后台天气预警\\城市列表\\城市列表.xml"));
        for (RECORD record : records.getRecords()) {
            areaInfoMapper.insert(record);
        }
    }

}
