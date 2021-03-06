package com.hyf.testDemo.redis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyf.algorithm.抽奖概率.common.Result;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Howinfun
 * @desc
 * @date 2020/3/24
 */
@RestController
@RequestMapping("/redis")
public class BookController2 {

    @Resource
    private BookMapper2 bookMapper2;
    @Resource
    private BookMapper3 bookMapper3;

    @GetMapping("/selectOne")
    public Result selectOne(){
        return new Result(bookMapper3.selectOne());
    }

    @GetMapping("/book")
    public Result queryBookList(){
        QueryWrapper<Book2> wrapper  = new QueryWrapper<>();
        return new Result(this.bookMapper2.selectList(wrapper));
    }

    @GetMapping("/book/{id}")
    public Result queryBook(@PathVariable(name = "id") Long id){
        return new Result(this.bookMapper2.selectById(id));
    }

    @PostMapping("/book")
    public Result addBook(@RequestBody Book2 book2){
        //return new Result(this.bookMapper2.insert0(book2));
        Map<String,Object> map = new HashMap<>(3);
        map.put("bookName","哈哈测试");
        this.bookMapper2.insert2(map);
        return new Result();
    }

    @DeleteMapping("/book/{id}")
    public Result delBook(@PathVariable(name = "id") Long id){
        return new Result(this.bookMapper2.deleteById(id));
    }

    @PutMapping("/book")
    public Result updateBook(@RequestBody Book2 book2){
        //return new Result(this.bookMapper2.updateBook0(book2));
        Map<String,Object> map = new HashMap<>(3);
        map.put("id","60");
        map.put("id",60L);
        this.bookMapper2.update2(map);
        return new Result();
    }

}
