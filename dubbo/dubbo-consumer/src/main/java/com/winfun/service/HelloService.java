package com.winfun.service;

/**
 *
 * 利用 @SentinelResource 给 dubbo 接口增加流控和熔断，因为 @SentinelResource 利用的是 AOP ，底层默认是使用 JDK 的动态代理
 * @author winfun
 * @date 2021/1/22 2:01 下午
 **/
public interface HelloService {

    String sayHello(String name);
}
