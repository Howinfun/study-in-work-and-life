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
     * 初始化traceId,由consumer调用
     */
    public static void initTrace(String uri) {
        if(StringUtils.isBlank(getTraceId())) {
            String traceId = generateTraceId();
            setTraceId(traceId);
            MDC.put(TRACE_URI, uri);
        }
    }

    /**
     * 从Dubbo中获取traceId，provider调用
     * @param context   RPC上下文
     */
    public static void getTraceFrom(RpcContext context) {
        String traceId = context.getAttachment(TRACE_ID);
        if (StringUtils.isBlank(traceId)) {
            traceId = context.getAttachment("traceId");
            if (StringUtils.isBlank(traceId)) {
                traceId = generateTraceId();
            }
        }

        setTraceId(traceId);
        String uri = context.getAttachment(TRACE_URI);
        if (StringUtils.isNotEmpty(uri)) {
            MDC.put(TRACE_URI, uri);
        }
    }

    /**
     * 把traceId放入dubbo远程调用中，consumer调用
     * @param context   RPC上下文
     */
    public static void putTraceInto(RpcContext context) {
        String traceId = getTraceId();
        if (StringUtils.isNotBlank(traceId)) {
            context.setAttachment(TRACE_ID, traceId);
        }

        String uri = MDC.get(TRACE_URI);
        if (StringUtils.isNotBlank(uri)) {
            context.setAttachment(TRACE_URI, uri);
        }
    }

    /**
     * 从MDC中清除traceId
     */
    public static void clearTrace() {
        MDC.clear();
    }

    /**
     * 从MDC中获取traceId
     * */
    private static String getTraceId() {
        return StringUtils.isBlank(MDC.get(TRACE_ID))?MDC.get("traceId"):MDC.get(TRACE_ID);
    }

    /**
     * 将traceId放入MDC
     * @param traceId   链路ID
     */
    private static void setTraceId(String traceId) {
        if (StringUtils.isNotEmpty(traceId)) {
            traceId = StringUtils.left(traceId, 36);
        }
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
