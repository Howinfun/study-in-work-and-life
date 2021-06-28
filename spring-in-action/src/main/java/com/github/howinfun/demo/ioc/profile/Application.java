package com.github.howinfun.demo.ioc.profile;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author winfun
 * @date 2021/6/28 9:12 上午
 **/
public class Application {

    public static void main(String[] args) {
        /**
         * 1、不能成功注入 BasketBall 和 People，因为Spring默认的环境变量 profile 为 default，而这两个实例需要的 profile 为 sunny。
         * 2、所以我们如果想要成功注入上面的两个实例，需要将环境变量profile改为sunny
         *    - 但是需要注意的是，AnnotationConfigApplicationContext创建时不能直接传入配置类了，因为这样会触发refresh()方法的调用，而AnnotationConfigApplicationContext继承与GenericApplicationContext，GenericApplicationContext的refresh()方法只能被调用一次
         *    - 所以我们需要利用无参构造函数来创建AnnotationConfigApplicationContext，然后再修改环境变量 profile，最后记得手动调用 refresh() 方法
         *    - 除了上面的方法，我们还可以利用JVM进程启动选项来指定环境变量profile的值：-Dspring.profiles.active=sunny
         */
        //AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BasketBallCourt.class);
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.getEnvironment().setActiveProfiles("sunny");
        applicationContext.register(BasketBallCourt.class);
        applicationContext.refresh();
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }
}
