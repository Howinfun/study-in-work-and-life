package com.hyf.algorithm.抽奖概率;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Howinfun
 * @desc
 * @date 2019/8/5
 */
public class TestTreeMap2 {
    public static void main(String[] args) {
        /**
         * 转盘抽奖需求：一共有三个奖项，一等奖中奖率10%、二等奖中奖率20%、三等奖中奖率70%
         * 奖品一共有十份，三等奖五份、二等奖三份、一等奖两份。十份抽完就没了。
         * 奖品也有抽中概率：三等奖的分别是30%、30%、20%、10%、10%。二等奖的分别是40%、30%、30%。一等奖的分别是70、30%
         *
         *
         * 需求实现：三个奖项还是使用权重随机算法：[0,10)是一等奖，[10,30)是二等奖，[30,100)是三等奖
         *         三等奖使用权重随机算法：[0-10)是奖品一，[10-20)是奖品二，[30-50)是奖品三，[50-70)是奖品四，[70-100)是奖品五
         *         二等奖使用权重随机算法：[0-30)是奖品一，[30-60)是奖品二，[60-100)是奖品三
         *         一等奖使用权重随机算法：[0-30)是奖品一，[70-100)是奖品二
         *
         * 后续增强需求：一等奖需要定时抽
         */
        try {
            for (int i = 0; i < 1000; i++) {
                TreeMap<Double, TreeMap<Double,String>> treeMap = new TreeMap<>();
                // 一等奖
                TreeMap<Double,String> drawOne = new TreeMap<>();
                drawOne.put(30d,"一等奖奖品一");
                drawOne.put(100d,"一等奖奖品二");
                // 二等奖
                TreeMap<Double,String> drawTwo = new TreeMap<>();
                drawTwo.put(30d,"二等奖奖品一");
                drawTwo.put(60d,"二等奖奖品二");
                drawTwo.put(100d,"二等奖奖品三");
                // 三等奖
                TreeMap<Double,String> drawThree = new TreeMap<>();
                drawThree.put(10d,"三等奖奖品一");
                drawThree.put(20d,"三等奖奖品二");
                drawThree.put(50d,"三等奖奖品三");
                drawThree.put(70d,"三等奖奖品四");
                drawThree.put(100d,"三等奖奖品五");
                treeMap.put(10d,drawOne);
                treeMap.put(30d,drawTwo);
                treeMap.put(100d,drawThree);
                // 抽奖结果
                List<String> prizeResult = new ArrayList<>();
                // 多线程测试
                CyclicBarrier cb = new CyclicBarrier(3);
                CountDownLatch countDownLatch = new CountDownLatch(3);
                ExecutorService pool = Executors.newFixedThreadPool(3);
                pool.submit(new PrizeThread(cb,countDownLatch,treeMap,prizeResult));
                pool.submit(new PrizeThread(cb,countDownLatch,treeMap,prizeResult));
                pool.submit(new PrizeThread(cb,countDownLatch,treeMap,prizeResult));
                pool.shutdown();
                countDownLatch.await();
                System.out.println("中奖列表："+prizeResult.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static class PrizeThread implements Runnable{

        private CyclicBarrier cb;
        private CountDownLatch countDownLatch;
        private TreeMap<Double, TreeMap<Double,String>> treeMap;
        private List<String> prizeResult;

        public PrizeThread(CyclicBarrier cb,CountDownLatch countDownLatch,TreeMap<Double, TreeMap<Double,String>> treeMap,List<String> prizeResult){
            this.cb = cb;
            this.countDownLatch = countDownLatch;
            this.treeMap = treeMap;
            this.prizeResult = prizeResult;
        }

        @Override
        public void run() {
            try {
                cb.await();
                // 如果还有奖项则继续抽奖
                synchronized (this.treeMap){
                    while (treeMap.size() > 0){
                        if (prizeResult.size()==2){
                            System.out.println("666");
                            break;
                        }
                        // 奖项随机数
                        Double random = treeMap.lastKey()*Math.random();
                        SortedMap<Double, TreeMap<Double,String>> prizeMap = treeMap.tailMap(random,false);
                        // 抽中的奖项
                        Double drawKey = prizeMap.firstKey();
                        TreeMap<Double,String> draw = prizeMap.get(drawKey);
                        // 奖品随机数
                        Double prizeRandom = draw.lastKey()*Math.random();
                        SortedMap<Double,String> resultMap = draw.tailMap(prizeRandom,false);
                        // 抽中的奖品
                        Double prizeKey = resultMap.firstKey();
                        String prize = resultMap.get(prizeKey);
                        prizeResult.add(prize);
                        // 移除抽中的奖品
                        treeMap.get(drawKey).remove(prizeKey);
                        // 判断奖项是否还有奖品，如果没有则将奖项也移除
                        if (treeMap.get(drawKey).size() <=0 ){
                            treeMap.remove(drawKey);
                        }
                    }
                }
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }finally {
                countDownLatch.countDown();
            }
        }
    }
}
