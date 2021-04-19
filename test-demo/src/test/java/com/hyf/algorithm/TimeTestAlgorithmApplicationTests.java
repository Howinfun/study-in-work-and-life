package com.hyf.algorithm;

import com.hyf.testDemo.mybatis.testEnum.GenderEnum;
import com.hyf.testDemo.mybatis.testEnum.User;
import com.hyf.testDemo.mybatis.testEnum.UserEnumMapper;
import com.hyf.testDemo.mybatis.testEnum.UserTypeEnum;
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
public class TimeTestAlgorithmApplicationTests {

    @Resource
    private AreaInfoMapper areaInfoMapper;

    @Resource
    private UserEnumMapper userEnumMapper;

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

    @Test
    public void insertUser(){
        User user = new User();
        user.setName("jianfeng");
        user.setGender(GenderEnum.MALE);
        user.setUserType(UserTypeEnum.STUDENT);
        userEnumMapper.insert(user);

        System.out.println(userEnumMapper.selectById(user.getId()));
    }

}
