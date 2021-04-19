package com.winfun.utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.MDC;

/**
 * Trace 工具
 * @author winfun
 * @date 2020/10/30 9:02 上午
 **/
public class TraceUtil {

    public final static String TRACE_ID = "trace_id";
    public final static String TRACE_URI = "uri";

    /**
     * 初始化 TraceId
     * @param uri 请求uri
     */
    public static void initTrace(String uri) {
        if(StringUtils.isBlank(MDC.get(TRACE_ID))) {
            String traceId = generateTraceId();
            setTraceId(traceId);
            MDC.put(TRACE_URI, uri);
        }
    }

    /**
     * 从 RpcContext 中获取 Trace 相关信息，包括 TraceId 和 TraceUri
     * 给 Dubbo 服务端调用
     * @param context Dubbo 的 RPC 上下文
     */
    public static void getTraceFrom(RpcContext context) {
        String traceId = context.getAttachment(TRACE_ID);
        if (StringUtils.isNotBlank(traceId)){
            setTraceId(traceId);
        }
        String uri = context.getAttachment(TRACE_URI);
        if (StringUtils.isNotEmpty(uri)) {
            MDC.put(TRACE_URI, uri);
        }
    }

    /**
     * 将 Trace 相关信息，包括 TraceId 和 TraceUri 放入 RPC上下文中
     * 给 Dubbo 消费端调用
     * @param context Dubbo 的 RPC 上下文
     */
    public static void putTraceInto(RpcContext context) {
        String traceId = MDC.get(TRACE_ID);
        if (StringUtils.isNotBlank(traceId)) {
            context.setAttachment(TRACE_ID, traceId);
        }

        String uri = MDC.get(TRACE_URI);
        if (StringUtils.isNotBlank(uri)) {
            context.setAttachment(TRACE_URI, uri);
        }
    }

    /**
     * 从 MDC 中清除当前线程的 Trace信息
     */
    public static void clearTrace() {
        MDC.clear();
    }

    /**
     * 将traceId放入MDC
     * @param traceId   链路ID
     */
    private static void setTraceId(String traceId) {
        traceId = StringUtils.left(traceId, 36);
        MDC.put(TRACE_ID, traceId);
    }

    /**
     * 生成traceId
     * @return  链路ID
     */
    private static String generateTraceId() {
        return TraceIdUtil.nextNumber();
    }
}
