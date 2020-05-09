package com.hyf.testDemo.redis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/27
 */
@Repository
@CacheConfig(cacheNames = {"bookCache"})
public interface BookMapper2 extends BaseMapper<Book2> {

    @Insert("insert into book(book_name,read_frequency)values(#{bookName},#{readFrequency})")
    void insert2(Map<String,Object> map);

    @Update("update book set book_name = #{bookName} where id = #{id}")
    void update2(Map<String,Object> map);

    @Cacheable(key = "#id",unless = "#result == null")
    Book2 selectById(Long id);

    @CachePut(key = "#book2.id", condition = "#book2.bookName != null and #book2.bookName != ''")
    default Book2 insert0(Book2 book2) {
        // 插入
        this.insert(book2);
        // 返回
        return book2;
    }

    @CacheEvict(key = "#id")
    int deleteById(Long id);

    @Caching(
            evict = {@CacheEvict(key = "#book2.id", beforeInvocation = true)},
            put = {@CachePut(key = "#book2.id")}
    )
    default Book2 updateBook0(Book2 book2){
        // 更新
        this.updateById(book2);
        // 返回
        return book2;
    }
}
