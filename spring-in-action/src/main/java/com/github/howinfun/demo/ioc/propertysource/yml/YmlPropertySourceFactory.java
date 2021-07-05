package com.github.howinfun.demo.ioc.propertysource.yml;

import java.io.IOException;
import java.util.Properties;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

/**
 *
 * @author winfun
 * @date 2021/7/5 10:49 上午
 **/
public class YmlPropertySourceFactory implements PropertySourceFactory {

    /**
     * Create a {@link PropertySource} that wraps the given resource.
     * @param name the name of the property source
     * (can be {@code null} in which case the factory implementation
     * will have to generate a name based on the given resource)
     * @param resource the resource (potentially encoded) to wrap
     * @return the new {@link PropertySource} (never {@code null})
     * @throws IOException if resource resolution failed
     */
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {

        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        // 传入resource资源文件
        yamlPropertiesFactoryBean.setResources(resource.getResource());
        // 直接解析获得Properties对象
        Properties properties = yamlPropertiesFactoryBean.getObject();
        // 如果@PropertySource没有指定name，则使用资源文件的文件名
        return new PropertiesPropertySource((name != null ? name : resource.getResource().getFilename()), properties);
    }
}
