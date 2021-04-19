package com.winfun.filter;

import com.winfun.utils.TraceUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Web Request Filter
 * @author winfun
 * @date 2020/10/30 3:02 下午
 **/
@Slf4j
@Order(1)
@WebFilter(urlPatterns = "/*")
public class WebRequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        // 初始化 TraceId
        TraceUtil.initTrace(uri);
        filterChain.doFilter(request,response);
        // 清除 TraceId 和 TraceUri
        TraceUtil.clearTrace();
    }

}
