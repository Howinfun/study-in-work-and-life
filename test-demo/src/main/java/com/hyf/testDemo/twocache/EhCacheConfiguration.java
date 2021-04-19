package com.hyf.testDemo.twocache;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/25
 */
//@Configuration
public class EhCacheConfiguration {

    /*@Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new
                EhCacheManagerFactoryBean();
        Resource resource = new ClassPathResource("ehcache.xml");
        cacheManagerFactoryBean.setConfigLocation(resource);
        cacheManagerFactoryBean.setShared(true);
        return cacheManagerFactoryBean;
    }

    @Bean
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean ehCacheManagerFactoryBean){
        EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager(ehCacheManagerFactoryBean.getObject());
        return ehCacheCacheManager;
    }*/
}
