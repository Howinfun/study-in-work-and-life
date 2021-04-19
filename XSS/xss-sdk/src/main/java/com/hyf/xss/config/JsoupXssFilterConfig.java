package com.hyf.xss.config;

import com.hyf.xss.constants.JsoupXssFilterConstant;
import com.hyf.xss.filter.JsoupXssFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import java.util.HashMap;
import java.util.Map;

/**
 * XSS Filter Configuration
 * @Author: winfun
 * @Date: 2020/12/23 11:21 上午
 **/
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "xss", name = "enabled", havingValue = "true")
public class JsoupXssFilterConfig {

    /**
     * 排除的URL
     */
    @Value("${xss.excludes:}")
    private String excludes;

    /**
     * 过滤URL正则
     */
    @Value("${xss.urlPatterns:/*}")
    private String urlPatterns;

    @Bean
    public FilterRegistrationBean<JsoupXssFilter> xssFilterRegistrationBean() {
        FilterRegistrationBean<JsoupXssFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new JsoupXssFilter());
        registration.addUrlPatterns(urlPatterns.split(","));
        registration.setName("JsoupXssFilter");
        registration.setOrder(Integer.MAX_VALUE);
        Map<String, String> initParameters = new HashMap<String, String>(2);
        initParameters.put(JsoupXssFilterConstant.EXCLUDES, excludes);
        registration.setInitParameters(initParameters);
        log.info("JsoupXssFilter 初始化完毕");
        return registration;
    }
}