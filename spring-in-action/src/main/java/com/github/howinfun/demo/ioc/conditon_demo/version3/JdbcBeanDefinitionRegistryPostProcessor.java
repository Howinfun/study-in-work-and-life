package com.github.howinfun.demo.ioc.conditon_demo.version3;

import com.alibaba.druid.pool.DruidDataSource;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.CollectionUtils;

/**
 * 动态注入数据库链接
 * @author winfun
 * @date 2021/7/19 8:53 上午
 **/
public class JdbcBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {

    private Environment environment;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // 利用Spring的SPI读取支持的数据库的Driver，这里我们利用的是 loadFactoryNames 方法来读取 spring.factories 的配置
        List<String> driverClassNames = SpringFactoriesLoader.loadFactoryNames(EnableJdbc.class, this.getClass().getClassLoader());
        if (!CollectionUtils.isEmpty(driverClassNames)){
            AtomicReference<String> finalDriverClassName = new AtomicReference<>();
            driverClassNames.forEach(driverClassName ->{
                try {
                    // 判断是否引入了对应的 Driver
                    Class.forName(driverClassName);
                    finalDriverClassName.set(driverClassName);
                }catch (Exception e){
                    System.out.println("不支持："+driverClassName);
                }
            });
            BeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(DruidDataSource.class)
                    .addPropertyValue("url",environment.getProperty("jdbc.url"))
                    .addPropertyValue("username",environment.getProperty("jdbc.username"))
                    .addPropertyValue("password",environment.getProperty("jdbc.password"))
                    .addPropertyValue("driverClassName",finalDriverClassName.get())
                    .getBeanDefinition();
            registry.registerBeanDefinition("dataSource",beanDefinition);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    /**
     * Set the {@code Environment} that this component runs in.
     * @param environment
     */
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
