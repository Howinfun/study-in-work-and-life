package com.winfun.demo.stub;

import com.winfun.contants.ApiContants;
import com.winfun.entity.pojo.ApiResult;
import com.winfun.service.DubboServiceOne;
import lombok.extern.slf4j.Slf4j;

/**
 * DubboServiceOne 本地存根
 * @author winfun
 * @date 2021/2/1 9:59 上午
 **/
@Slf4j
public class DubboServiceOneStub implements DubboServiceOne {

    private final DubboServiceOne dubboServiceOne;

    public DubboServiceOneStub(DubboServiceOne dubboServiceOne){
        this.dubboServiceOne = dubboServiceOne;
    }

    /***
     *  say hello
     * @author winfun
     * @param name name
     * @return {@link ApiResult <String> }
     **/
    @Override
    public ApiResult<String> sayHello(String name) {
        try {
            ApiResult<String> result = this.dubboServiceOne.sayHello(name);
            if (ApiContants.SUCCESS.equals(result.getCode())){
                return ApiResult.fail(ApiContants.FAIL,"业务异常",result.getData());
            }
        }catch (Exception e){
            log.error("call DubboServiceOne throw exception!message is {}",e.getMessage());
            return ApiResult.fail("调用失败");
        }
        return null;
    }
}
