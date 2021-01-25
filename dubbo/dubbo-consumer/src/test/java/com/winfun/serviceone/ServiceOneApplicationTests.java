package com.winfun.serviceone;

import com.winfun.DubboServiceApplication;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@SpringBootTest(classes = DubboServiceApplication.class)
@RunWith(SpringRunner.class)
public class ServiceOneApplicationTests {

    @Autowired
    private MockMvc mockMvc;

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

}
