package com.hyf.testDemo.twocache;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/24
 */
@Configuration
public class RedisConfiguration {

    @Bean(name="jsonRedisTemplate")
    public RedisTemplate<String,?> jsonRedisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String,?> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(keySerializer());
        template.setValueSerializer(valueSerializer());
        return template;
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory){

        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>(2);

        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                // 30s缓存失效
                .entryTtl(Duration.ofSeconds(30))
                // 设置key的序列化方式
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                // 设置value的序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
                // 不缓存null值
                .disableCachingNullValues();

        RedisCacheConfiguration userConfig = RedisCacheConfiguration.defaultCacheConfig()
                // 10s缓存失效
                .entryTtl(Duration.ofSeconds(66))
                // 设置key的序列化方式
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                // 设置value的序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
                // 不缓存null值
                .disableCachingNullValues();
        cacheConfigurations.put("userCache",userConfig);

        RedisCacheConfiguration bookConfig = RedisCacheConfiguration.defaultCacheConfig()
                // 10s缓存失效
                .entryTtl(Duration.ofSeconds(66))
                // 设置key的序列化方式
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                // 设置value的序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
                // 不缓存null值
                .disableCachingNullValues();
        cacheConfigurations.put("bookCache",bookConfig);

        RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
                .transactionAware()
                .build();
        return redisCacheManager;
    }
    
    @Bean(name = "myKeyGenerator")
    public KeyGenerator keyGenerator(){
        // 缓存 key 的生成策略
        return (o, method, objects) -> {
            // 将方法的多个入参转成字符串
            return Arrays.asList(objects).toString();
        };
    }

    // key键序列化方式
    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    // value值序列化方式
    private GenericJackson2JsonRedisSerializer valueSerializer(){
        /**
         * Jackson Default Typing 机制序列化 会在序列化结果里自己加上一个“@class”属性，属性值是被序列化的对象的类
         * 例如：
         * {
         * "@class":"com.hyf.algorithm.抽奖概率.common.Result",
         *      "code":0,"
         *      msg":"success",
         *      "data":
         *          {
         *          "@class":"com.hyf.testDemo.redis.User",
         *          "id":100007,
         *          "name":"测试",
         *          "age":1,
         *          "phone":"15627230001"
         *      }
         * }
         */

        return new GenericJackson2JsonRedisSerializer();
    }
}