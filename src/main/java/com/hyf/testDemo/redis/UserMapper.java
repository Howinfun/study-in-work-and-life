package com.hyf.testDemo.redis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/25
 */
@Repository
@CacheConfig(cacheNames = {"users"})
public interface UserMapper extends BaseMapper<User> {

    @Cacheable(key = "#id",unless = "#result == null")
    User selectById(Long id);

    @CachePut(key = "#user.id", condition = "#user.name != null and #user.name != ''")
    default User insert0(User user) {
        // 插入
        this.insert(user);
        // 返回
        return user;
    }

    @CacheEvict(key = "#id")
    int deleteById(Long id);

    @Caching(
            evict = {@CacheEvict(key = "#user.id", beforeInvocation = true)},
            put = {@CachePut(key = "#user.id")}
    )
    default User updateUser0(User user){
        // 更新
        this.updateById(user);
        // 返回
        return user;
    }
}
