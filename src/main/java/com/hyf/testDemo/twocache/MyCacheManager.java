package com.hyf.testDemo.twocache;

import lombok.Data;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/25
 */
@Data
public class MyCacheManager implements CacheManager {

    private MyCacheTemplate myCacheTemplate;

    @Override
    public Cache getCache(String name) {
        return this.myCacheTemplate;
    }

    @Override
    public Collection<String> getCacheNames() {
        return Collections.emptyList();
    }
}
