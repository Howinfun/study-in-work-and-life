package com.hyf.xss.filter;

import com.hyf.xss.wrapper.JsoupXssHttpServletRequestWrapper;
import com.hyf.xss.constants.JsoupXssFilterConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * XSS Filter
 * @Author: winfun
 * @Date: 2020/12/23 11:21 上午
 **/
@Slf4j
public class JsoupXssFilter implements Filter {

    /**
     * 排除的URL
     */
    private List<String> excludes = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig){

        String temp = filterConfig.getInitParameter(JsoupXssFilterConstant.EXCLUDES);
        if (StringUtils.isNotBlank(temp)) {
            String[] url = temp.split(",");
            for (int i = 0; url != null && i < url.length; i++) {
                excludes.add(url[i]);
            }
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException,
                                                                                                     ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getServletPath();
        if (handleExcludeUrls(url)){
            filterChain.doFilter(request, response);
        }else {
            log.info("JsoupXssFilter has doFilter,the request url is {}",url);
            JsoupXssHttpServletRequestWrapper xssRequest =
                    new JsoupXssHttpServletRequestWrapper(request, JsoupXssFilterConstant.ENCODING);
            filterChain.doFilter(xssRequest, response);
        }
    }

    /***
     * 处理 ExcludesUrls
     * @author winfun
     * @param requestUrl 请求URL
     * @return {@link Boolean }
     **/
    private Boolean handleExcludeUrls(String requestUrl) {

        if (null == excludes || excludes.isEmpty()) {
            return false;
        }
        for (String pattern : excludes) {
            Pattern p = Pattern.compile("^" + pattern);
            Matcher m = p.matcher(requestUrl);
            if (m.find()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {}  
  
}