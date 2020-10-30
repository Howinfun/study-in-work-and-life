package com.winfun.utils;

import lombok.extern.slf4j.Slf4j;

import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TraceId 生成工具
 * @author winfun
 * @date 2020/10/29 5:12 下午
 **/
@Slf4j
public class TraceIdUtil {

    /**
     * 机器信息
     */
    private static int MACHINE_CODE;
    /**
     * 线程安全的下一个随机数,每次生成自增+1
     */
    private static AtomicInteger nextInc = new AtomicInteger();

    /** * 初始化机器信息 = 机器码 + 进程码 */
    static {
        try {
            // 机器码
            int machinePiece;
            machinePiece = getMachinePiece();
            // 进程码
            // 因为静态变量类加载可能相同,所以要获取进程ID + 加载对象的ID值
            final int processPiece;
            // 进程ID初始化
            int processId = getProcessId();

            ClassLoader loader = TraceIdUtil.class.getClassLoader();

            // 返回对象哈希码,无论是否重写hashCode方法
            int loaderId = loader != null ? System.identityHashCode(loader) : 0;

            // 进程ID + 对象加载ID
            StringBuilder processSb = new StringBuilder();
            processSb.append(Integer.toHexString(processId));
            processSb.append(Integer.toHexString(loaderId));
            // 保留前2位
            processPiece = processSb.toString().hashCode() & 0xFFFF;

            // 生成机器信息 = 取机器码的后2位和进程码的前2位
            MACHINE_CODE = machinePiece | processPiece;

        } catch (Exception e) {
            log.error("生成唯一ID异常", e);
        }
    }

    private TraceIdUtil() {

    }

    private static int getProcessId() {
        int processId;
        try {
            // 获取进程ID
            processId = java.lang.management.ManagementFactory.getRuntimeMXBean().getName().hashCode();
        } catch (Exception e) {
            log.error("进程ID获取失败", e);
            processId = new Random().nextInt();
        }
        return processId;
    }

    private static int getMachinePiece() {
        int machinePiece;
        try {
            StringBuilder netSb = new StringBuilder();
            // 返回机器所有的网络接口
            Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
            // 遍历网络接口
            while (e.hasMoreElements()) {
                NetworkInterface ni = e.nextElement();
                // 网络接口信息
                netSb.append(ni.toString());
            }
            // 保留后两位
            machinePiece = netSb.toString().hashCode() << 16;
        } catch (Exception e) {
            // 出问题随机生成,保留后两位
            machinePiece = (new Random().nextInt()) << 16;
        }
        return machinePiece;
    }

    public static String next() {

        ByteBuffer bb = getByteBuffer();
        StringBuilder buf = new StringBuilder(24);
        // 原来objectId格式化太慢
        for (byte t : bb.array()) {
            // 小于两位左端补0
            int i = t & 0xff;
            if (i < 16) {
                buf.append("0").append(Integer.toHexString(i));
            } else {
                buf.append(Integer.toHexString(i));
            }

        }
        return buf.toString();
    }

    private static ByteBuffer getByteBuffer() {
        ByteBuffer bb = ByteBuffer.wrap(new byte[12]);
        //4位
        bb.putInt((int) (System.currentTimeMillis() / 1000));
        //4位
        bb.putInt(MACHINE_CODE);
        //4位
        bb.putInt(nextInc.getAndIncrement());
        return bb;
    }

    public static String nextWithUnderline() {

        ByteBuffer bb = getByteBuffer();
        StringBuilder buf = new StringBuilder(24);
        // 原来objectId格式化太慢
        byte[] array = bb.array();
        for (int i = 0; i < array.length; i++) {
            if (i % 4 == 0 && i != 0) {
                buf.append("-");
            }
            int t = array[i] & 0xff;
            if (t < 16) {
                buf.append("0").append(Integer.toHexString(t));
            } else {
                buf.append(Integer.toHexString(t));
            }

        }
        return buf.toString();
    }

    public static String nextNumber() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.currentTimeMillis() / 1000);
        if (MACHINE_CODE < 0) {
            stringBuilder.append(-MACHINE_CODE);
        } else {
            stringBuilder.append(MACHINE_CODE);
        }
        stringBuilder.append(nextInc.incrementAndGet());
        return stringBuilder.toString();
    }
}