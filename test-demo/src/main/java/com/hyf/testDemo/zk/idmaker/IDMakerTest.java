package com.hyf.testDemo.zk.idmaker;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Howinfun
 * @desc
 * @date 2020/8/28
 */
@Slf4j
public class IDMakerTest {

    public static void main(String[] args) {
        String nodeName = "IDMaker";
        for (int i = 0; i < 10; i++) {
            String id = IDMaker.makeId(nodeName);
            log.info("成功创建全局唯一递增ID：{}",id);
        }
        ThreadUtil.sleep(Integer.MAX_VALUE);
    }
}
