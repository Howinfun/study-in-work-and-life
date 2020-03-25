package com.hyf.testDemo.twocache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager;

import java.util.concurrent.Callable;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/25
 */
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class MyCacheTemplate implements Cache {

    private EhCacheCacheManager ehCacheCacheManager;
    private RedisCacheManager redisCacheManager;
    private String name;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return null;
    }

    @Override
    public ValueWrapper get(Object key) {
        Cache ehCache = ehCacheCacheManager.getCache(this.name);
        if(ehCache.get(key) != null){
            log.info("取数据ehcache库===key:{}",key);
            return ehCache.get(key);
        }

        Cache redisCache = redisCacheManager.getCache(this.name);
        if(redisCache.get(key)!=null){
            log.info("取数据reids库===key:{}",key);
            // 将数据存入到 ehcache
            ehCache.put(key,redisCache.get(key).get());
            return redisCache.get(key);
        }

        return null;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        return null;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
    }

    @Override
    public void put(Object key, Object value) {

        Cache ehCache = ehCacheCacheManager.getCache(this.name);
        log.info("插入ehcache库===key:{},value:{}",key,value);
        ehCache.put(key,value);

        Cache redisCache = redisCacheManager.getCache(this.name);
        log.info("插入reids库===key:{},value:{}",key,value);
        redisCache.put(key,value);

    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        return null;
    }

    @Override
    public void evict(Object key) {

        Cache ehCache = ehCacheCacheManager.getCache(this.name);
        log.info("删除ehcache库===key:{}",key);
        ehCache.evict(key);

        Cache redisCache = redisCacheManager.getCache(this.name);
        log.info("删除reids库===key:{}",key);
        redisCache.evict(key);
    }

    @Override
    public void clear() {
        Cache ehCache = ehCacheCacheManager.getCache(this.name);
        ehCache.clear();

        Cache redisCache = redisCacheManager.getCache(this.name);
        redisCache.clear();
    }
}
