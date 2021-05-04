package com.winfun.filter;

import com.alibaba.fastjson.JSON;
import com.winfun.entity.dto.DubboRequestDTO;
import com.winfun.entity.dto.DubboResponseDTO;
import com.winfun.utils.TraceUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.registry.Constants;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Dubbo Trace Filter
 * @author winfun
 * @date 2020/10/30 9:46 上午
 **/
@Slf4j
@Activate(group = {Constants.PROVIDER_PROTOCOL,Constants.CONSUMER_PROTOCOL})
public class DubboTraceFilter implements Filter {

    public DubboTraceFilter(){
        log.info("Dubbo trace filter initialized");
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // 执行前
        handleTraceId();
        printRequest(invocation);
        long start = System.currentTimeMillis();
        Result result = invoker.invoke(invocation);
        long end = System.currentTimeMillis();
        // 执行后
        printResponse(invocation,result,end-start);
        return result;
    }

    /***
     *  打印请求
     * @author winfun
     * @param invocation invocation
     * @return {@link }
     **/
    private void printRequest(Invocation invocation){
        DubboRequestDTO requestDTO = new DubboRequestDTO();
        requestDTO.setInterfaceClass(invocation.getInvoker().getInterface().getName());
        requestDTO.setMethodName(invocation.getMethodName());
        requestDTO.setArgs(getArgs(invocation));
        //log.info("call Dubbo Api start , request is {}",requestDTO);
    }

    /***
     *  打印结果
     * @author winfun
     * @param invocation invocation
    	 * @param result result
     * @return {@link }
     **/
    private void printResponse(Invocation invocation,Result result,long spendTime){
        DubboResponseDTO responseDTO = new DubboResponseDTO();
        responseDTO.setInterfaceClassName(invocation.getInvoker().getInterface().getName());
        responseDTO.setMethodName(invocation.getMethodName());
        responseDTO.setResult(JSON.toJSONString(result.getValue()));
        responseDTO.setSpendTime(spendTime);
        //log.info("call Dubbo Api end , response is {}",responseDTO);
    }

    /***
     *  获取 Invocation 参数，过滤掉大参数
     * @author winfun
     * @param invocation invocation
     * @return {@link Object[] }
     **/
    private Object[] getArgs(Invocation invocation){
        Object[] args = invocation.getArguments();
        args = Arrays.stream(args).filter(arg->{
            if (arg instanceof byte[] || arg instanceof Byte[] || arg instanceof InputStream || arg instanceof File){
                return false;
            }
            return true;
        }).toArray();
        return args;
    }

    /***
     *  处理 TraceId，如果当前对象是服务消费者，则将 Trace 信息放入 RpcContext中
     *  如果当前对象是服务提供者，则从 RpcContext 中获取 Trace 信息。
     * @author winfun

     * @return {@link  }
     **/
    private void handleTraceId() {
        RpcContext context = RpcContext.getContext();
        if (context.isConsumerSide()) {
            TraceUtil.putTraceInto(context);
        } else if (context.isProviderSide()) {
            TraceUtil.getTraceFrom(context);
        }
    }

}
