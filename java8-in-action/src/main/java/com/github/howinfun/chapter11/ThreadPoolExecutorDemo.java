package com.github.howinfun.chapter11;

import cn.hutool.core.thread.ThreadUtil;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.SneakyThrows;

/**
 * 自定义线程池
 * @author winfun
 * @date 2021/4/26 11:11 上午
 **/
public class ThreadPoolExecutorDemo {

    @SneakyThrows
    public static void main(String[] args) {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(5, 10, 10, TimeUnit.SECONDS,
                                                                    new LinkedBlockingQueue<>(10), new MyDefaultThreadFactory("TestThreadPool", false, 0),
                                                                    new ThreadPoolExecutor.AbortPolicy());
        // 设置使核心线程空闲时间超过keepAliveTime时间也被回收，这样线程池没有任务执行最终会自动销毁
        //executorService.allowCoreThreadTimeOut(true);
        Future<String> future = executorService.submit(()->"hello");
        System.out.println(future.get());
        System.out.println("---------------------------");
        String result = "测试submit(runnable, result)";
        Future<String> future2 = executorService.submit(()-> System.out.println("我玩玩，不返回"), result);
        System.out.println(future2.get());
        System.out.println("==========end===========");
        // 休眠11秒，使得空闲时间超过keepAliveTIme
        ThreadUtil.sleep(11, TimeUnit.SECONDS);
        System.out.println("新任务来了。。。。。。");
        Future<String> future3 = executorService.submit(()->"hello,haha");
        System.out.println(future3.get());
        System.out.println("---------------------------");

        // 休眠20秒
        ThreadUtil.sleep(5,TimeUnit.MINUTES);
        System.out.println("新任务来了。。。。。。");
        future3 = executorService.submit(()->"hello,xixi");
        System.out.println(future3.get());
        System.out.println("---------------------------");
    }

    /**
     * 搬的Executors中的DefaultThreadFactory类，修改一下作为线程工厂
     */
    static class MyDefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;
        private final boolean isDaemon;
        private final long stackSize;

        /**
         * @param poolName
         *            自定义的线程池名称
         * @param isDaemon
         *            是否是守护线程
         * @param stackSize
         *            新线程所需的堆栈大小，或者为零以指示要忽略此参数
         */
        MyDefaultThreadFactory(String poolName, boolean isDaemon, long stackSize) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" + poolName + poolNumber.getAndIncrement() + "-thread-";
            this.isDaemon = isDaemon;
            this.stackSize = stackSize;

        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), stackSize);
            t.setDaemon(isDaemon);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
