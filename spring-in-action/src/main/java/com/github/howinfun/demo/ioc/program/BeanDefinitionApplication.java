package com.github.howinfun.demo.ioc.program;

import java.util.Optional;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author winfun
 * @date 2021/7/15 9:25 上午
 **/
public class BeanDefinitionApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        // 注册person
        BeanDefinition personBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Person.class).getBeanDefinition();
        personBeanDefinition.getPropertyValues().addPropertyValue("name","laowang");
        context.registerBeanDefinition("laowang",personBeanDefinition);

        // 注册cat，如果是属性是需要注入其他Bean实例对象的，需要在 BeanDefinitionBuilder 构建 BeanDefinition 时完成，不然只能添加常量的属性值
        BeanDefinition catBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Cat.class)
                .addPropertyValue("name","small cat")
                .addPropertyReference("person","laowang")
                .getBeanDefinition();
        context.registerBeanDefinition("myCat",catBeanDefinition);

        // 注入dog，测试原型bean  ApplicationContext#refresh 方法执行时不会对myDog进行初始化，即不会构建原型Bean的实例，只有当获取Bean实例时才会开始初始化。
        BeanDefinition dogBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Dog.class)
                .addPropertyValue("name","small dog")
                .addPropertyReference("person","laowang")
                .setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
                .getBeanDefinition();
        context.registerBeanDefinition("myDog",dogBeanDefinition);

        // 注入rabbit，测试延迟加载 ApplicationContext#refresh 方法执行时不会对myRabbit进行初始化，即不会构建原型Bean的实例，只有当获取Bean实例时才会开始初始化。
        BeanDefinition rabbitBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Rabbit.class)
                .addPropertyValue("name","small rabbit")
                .addPropertyReference("person","laowang")
                .setLazyInit(true)
                .getBeanDefinition();
        context.registerBeanDefinition("myRabbit",rabbitBeanDefinition);

        // 因为没有传入配置类，需要手动刷新
        context.refresh();
        System.out.println("application context refreshed.....");
        Optional.ofNullable((Person) context.getBean("laowang")).ifPresent(System.out::println);
        Optional.ofNullable((Cat) context.getBean("myCat")).ifPresent(System.out::println);
        Optional.ofNullable((Dog) context.getBean("myDog")).ifPresent(System.out::println);
        Optional.ofNullable((Rabbit) context.getBean("myRabbit")).ifPresent(System.out::println);
        context.close();
    }
}
