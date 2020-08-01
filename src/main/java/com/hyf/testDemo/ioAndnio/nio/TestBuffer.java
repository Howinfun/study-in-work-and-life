package com.hyf.testDemo.ioAndnio.nio;

import lombok.extern.slf4j.Slf4j;

import java.nio.IntBuffer;
import java.util.stream.IntStream;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/31
 */
@Slf4j
public class TestBuffer {
    public static void main(String[] args) {

        IntBuffer intBuffer = IntBuffer.allocate(10);
        log.info("初始化容量为10的IntBuffer,此时的属性值：");
        log.info("Buffer position:"+intBuffer.position());
        log.info("Buffer limit:"+intBuffer.limit());
        log.info("Buffer capacity:"+intBuffer.capacity());
        // 添加 3 个数字
        IntStream.range(1,4).forEach(n -> intBuffer.put(n));
        log.info("添加3个数字,此时的属性值：");
        log.info("Buffer position:"+intBuffer.position());
        log.info("Buffer limit:"+intBuffer.limit());
        log.info("Buffer capacity:"+intBuffer.capacity());

        // 读取上面添加的数字

        // 调用 filp() 方法，将写模式转为读模式，重置 position 和 limit
        intBuffer.flip();
        log.info("调用 filp 方法转为读模式,此时的属性值：");
        log.info("Buffer position:"+intBuffer.position());
        log.info("Buffer limit:"+intBuffer.limit());
        log.info("Buffer capacity:"+intBuffer.capacity());
        for (int i = 0;i < 3;i++){
            System.out.println(intBuffer.get());
        }
        log.info("读取所有数字,此时的属性值：");
        log.info("Buffer position:"+intBuffer.position());
        log.info("Buffer limit:"+intBuffer.limit());
        log.info("Buffer capacity:"+intBuffer.capacity());

        // 重复读
        // 调用 rewind() 方法，将 position 置为 0。
        intBuffer.rewind();
        log.info("调用 rewind 方法重新读取数据,此时的属性值：");
        log.info("Buffer position:"+intBuffer.position());
        log.info("Buffer limit:"+intBuffer.limit());
        log.info("Buffer capacity:"+intBuffer.capacity());
        for (int i = 0;i < 3;i++){
            System.out.println(intBuffer.get());
        }
        log.info("读取所有数字,此时的属性值：");
        log.info("Buffer position:"+intBuffer.position());
        log.info("Buffer limit:"+intBuffer.limit());
        log.info("Buffer capacity:"+intBuffer.capacity());

        // 将 position 重新置为 0，limit = capacity
        intBuffer.clear();
        // 添加 3 个数字
        IntStream.range(1,4).forEach(n -> intBuffer.put(n));
        log.info("调用 clear 方法重新进入写模式，此时的属性值：");
        log.info("Buffer position:"+intBuffer.position());
        log.info("Buffer limit:"+intBuffer.limit());
        log.info("Buffer capacity:"+intBuffer.capacity());

        intBuffer.flip();
        for (int i = 0;i < 3;i++){
            if (i == 1){
                // 当读取第二个元素时，打个标记
                intBuffer.mark();
            }
            System.out.println(intBuffer.get());
        }
        // 重新回到第二个元素位置
        intBuffer.reset();
        System.out.println(intBuffer.get());
    }
}
