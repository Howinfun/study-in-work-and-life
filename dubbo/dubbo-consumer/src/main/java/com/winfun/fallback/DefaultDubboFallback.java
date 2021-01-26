package com.winfun.fallback;

import com.alibaba.csp.sentinel.adapter.dubbo.fallback.DubboFallback;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.winfun.entity.pojo.ApiResult;
import org.apache.dubbo.rpc.AsyncRpcResult;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;

/**
 *
 * @author winfun
 * @date 2021/1/25 6:40 下午
 **/
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

        return AsyncRpcResult.newDefaultAsyncResult(ApiResult.fail("SentinelDubboConsumerFilter-被熔断限流了"), invocation);
    }
}
