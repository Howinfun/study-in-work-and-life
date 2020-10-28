package com.hyf.testDemo.testSpringTransactionRollBack;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/9
 */
@Mapper
public interface BookMapper {

    @Insert("insert into book(book_name,read_frequency) values(#{bookName},#{readFrequency})")
    Integer addBook(Book book);
}
