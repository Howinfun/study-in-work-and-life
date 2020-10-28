package com.hyf.testDemo.testSpringTransactionRollBack;

import com.hyf.algorithm.抽奖概率.common.Result;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/9
 */
@RestController
@RequestMapping("/book")
@AllArgsConstructor
public class BookController {

    private final BookMapper bookMapper;

    @GetMapping("/add")
    // 使用Spring的事务，正常如果遇到检查型异常，是不会回滚的。
    // 解决方法：可以使用 rollbackFor 指定异常回滚
    @Transactional(rollbackFor = Exception.class)
    public Result addBook() throws IOException {
        Book book = new Book();
        book.setBookName("嘻嘻嘻嘻");
        book.setReadFrequency(100L);
        Integer i = bookMapper.addBook(book);
        if (i != 0){
            System.out.println("新增成功");
        }
        throw new IOException("模拟检查型异常");
    }
}
