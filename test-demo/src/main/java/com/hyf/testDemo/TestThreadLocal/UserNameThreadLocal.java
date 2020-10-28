package com.hyf.testDemo.TestThreadLocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Howinfun
 * @desc
 * @date 2020/7/20
 */
public class UserNameThreadLocal {

    public static void main(String[] args){

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        UserNameThreadLocal userNameThreadLocal = new UserNameThreadLocal();
        for (int i = 0; i< 10; i++){
            executorService.execute(new Task(userNameThreadLocal,"用户"+i));
        }
    }

    public void service1(String userName){

        System.out.println("线程ID" + Thread.currentThread().getId() + "用户："+ userName +"来了！");
        UserNameHolder.threadLocal.set(userName);
    }

    public void service2(){
        System.out.println("线程ID" + Thread.currentThread().getId() + "Service2 获取到用户："+ UserNameHolder.threadLocal.get());
    }

    public void service3(){
        System.out.println("线程ID" + Thread.currentThread().getId() + "Service3 获取到用户："+ UserNameHolder.threadLocal.get());
    }

    static class Task implements Runnable{

        private UserNameThreadLocal userNameThreadLocal;
        private String userName;

        public Task(UserNameThreadLocal userNameThreadLocal, String userName){
            this.userNameThreadLocal = userNameThreadLocal;
            this.userName = userName;
        }

        @Override
        public void run() {
            userNameThreadLocal.service1(userName);
            userNameThreadLocal.service2();
            userNameThreadLocal.service3();
        }
    }
}
