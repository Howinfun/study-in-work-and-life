package com.winfun.fallback;

import com.alibaba.csp.sentinel.adapter.dubbo.fallback.DubboFallback;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.winfun.entity.pojo.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.AsyncRpcResult;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;

/**
 *
 * @author winfun
 * @date 2021/1/25 6:40 下午
 **/
@Slf4j
public class DefaultDubboFallback implements DubboFallback {
    /**
     * Handle the block exception and provide fallback result.
     *
     * @param invoker Dubbo invoker
     * @param invocation Dubbo invocation
     * @param ex block exception
     * @return fallback result
     */
    @Override
    public Result handle(Invoker<?> invoker, Invocation invocation, BlockException ex) {
        ApiResult<String> result;
        if (ex instanceof DegradeException){
            log.error("资源：{} 被熔断了,message is {}",ex.getRule().getResource(),ex.getMessage());
            result = ApiResult.fail("hello fallback");
        }else {
            log.error("资源：{} 被流控了",ex.getRule().getResource());
            result = ApiResult.fail("hello block");
        }
        return AsyncRpcResult.newDefaultAsyncResult(result, invocation);
    }
}
