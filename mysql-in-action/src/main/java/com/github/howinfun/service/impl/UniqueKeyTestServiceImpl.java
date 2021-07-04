package com.github.howinfun.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.howinfun.entity.UniqueKeyTest;
import com.github.howinfun.mapper.UniqueKeyTestMapper;
import com.github.howinfun.service.UniqueKeyTestService;
import org.springframework.stereotype.Service;

/**
 * @author: winfun
 * @date: 2021/7/3 12:22 下午
 **/
@Service
public class UniqueKeyTestServiceImpl extends ServiceImpl<UniqueKeyTestMapper, UniqueKeyTest> implements UniqueKeyTestService {
}
