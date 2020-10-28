package com.hyf.testDemo.TestThreadLocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/20
 */
public class SimpleDateFormatNoThreadLocal {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 20; i++){
            
            executorService.execute(new Task(i));
        }
    }

    static class Task implements Runnable{

        private int seconds;

        public Task(int seconds){
            this.seconds = seconds;
        }

        @Override
        public void run() {
            Date date = new Date(1000 * seconds);
            String dateStr = SimpleDateFormatNoThreadLocal.simpleDateFormat.format(date);
            System.out.println(dateStr);
        }
    }
}
