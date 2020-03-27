package com.hyf.testDemo.twocache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/25
 */
//@Configuration
//@EnableCaching
public class MyCacheManagerConfiguration {


    @Bean
    @Primary
    public CacheManager cacheManager(MyCacheTemplate myCacheTemplate){
        MyCacheManager myCacheManager = new MyCacheManager();
        myCacheManager.setMyCacheTemplate(myCacheTemplate);
        return myCacheManager;
    }

    @Bean
    public MyCacheTemplate myCacheTemplate(RedisCacheManager redisCacheManager, EhCacheCacheManager ehCacheCacheManager){
        MyCacheTemplate myCacheTemplate = new MyCacheTemplate();
        myCacheTemplate.setRedisCacheManager(redisCacheManager);
        myCacheTemplate.setEhCacheCacheManager(ehCacheCacheManager);
        return myCacheTemplate;
    }
}
