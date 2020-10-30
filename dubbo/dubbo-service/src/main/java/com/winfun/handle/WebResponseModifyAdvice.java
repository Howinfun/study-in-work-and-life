package com.winfun.handle;

import com.winfun.entity.pojo.ApiResult;
import com.winfun.utils.TraceUtil;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Response Advice
 * @author winfun
 * @date 2020/10/30 3:47 下午
 **/
@RestControllerAdvice
public class WebResponseModifyAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(final MethodParameter methodParameter, final Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(final Object body, final MethodParameter methodParameter, final MediaType mediaType, final Class aClass,
                                  final ServerHttpRequest serverHttpRequest, final ServerHttpResponse serverHttpResponse) {
        if (body instanceof ApiResult) {
            ((ApiResult<?>) body).setTraceId(MDC.get(TraceUtil.TRACE_ID));
        }
        return body;
    }
}
