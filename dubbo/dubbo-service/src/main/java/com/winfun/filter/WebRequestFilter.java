package com.winfun.filter;

import com.winfun.utils.TraceUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Web Request Filter
 * @author winfun
 * @date 2020/10/30 3:02 下午
 **/
@Slf4j
@Component
public class WebRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        long currTime = System.currentTimeMillis();
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        String uri = requestWrapper.getRequestURI();
        TraceUtil.initTrace(uri);
        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            TraceUtil.clearTrace();
        }
    }
}
