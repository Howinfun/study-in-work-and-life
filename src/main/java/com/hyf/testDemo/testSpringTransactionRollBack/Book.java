package com.hyf.testDemo.testSpringTransactionRollBack;

import lombok.Data;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/9
 */
@Data
public class Book {

    private Integer id;
    private String bookName;
    private Long readFrequency;
}
