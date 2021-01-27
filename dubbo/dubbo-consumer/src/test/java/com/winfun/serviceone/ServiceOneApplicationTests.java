package com.winfun.serviceone;

import com.winfun.DubboServiceApplication;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest(classes = DubboServiceApplication.class)
@RunWith(SpringRunner.class)
public class ServiceOneApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    /***
     * Sentinel-core & @SentinelResource 单元测试
     * @author winfun
     * @return {@link Void }
     **/
    @SneakyThrows
    @Test
    public void sayHello(){
        for (int i = 0; i < 100; i++) {
            // 休眠500毫秒，即1秒两次调用，可以出发流控规则
            Thread.sleep(500);
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/hello/winfun"))
                    .andReturn();
            System.out.println(result.getResponse().getContentAsString());
        }
    }

    /***
     * sentinel-apache-dubbo-adapter 组件接入单元测试
     * @author winfun
     * @return {@link Void }
     **/
    @Test
    public void sayHello2(){
        for (int i = 0; i < 100; i++) {
            try {
                // 休眠500毫秒，即1秒两次调用，可以出发流控规则
                Thread.sleep(500);
                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/hello/winfun"))
                        .andReturn();
                System.out.println(result.getResponse().getContentAsString());
            } catch (Exception e) {
                log.error("业务处理发生异常，exception is {}", e.getMessage());
            }
        }
    }
}
