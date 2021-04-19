package com.hyf.xss.wrapper;

import com.hyf.xss.utils.JsoupUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 拦截防止xss注入
 * @Author: winfun
 * @Date: 2020/12/23 11:21 上午
 **/
@Slf4j
public class JsoupXssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /** request */
    private HttpServletRequest orgRequest;
    /** 编码 */
    private String encoding;
    /** JSON 格式 */
    private final static String JSON_CONTENT_TYPE = "application/json";
    /** Content-Type */
    private final static String CONTENT_TYPE = "Content-Type";
    private final static String CONTENT_TYPE2 = "content-type";


    /***
     * 构造
     * @author winfun
     * @param request 请求
     * @param encoding 编码
     * @return {@link null }
     **/
    public JsoupXssHttpServletRequestWrapper(HttpServletRequest request, String encoding) {
        super(request);
        orgRequest = request;
        this.encoding = encoding;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 非Json格式请求体，此处不做处理
        String contentType = super.getHeader(CONTENT_TYPE);
        if (StringUtils.isBlank(contentType)) {
            contentType = super.getHeader(CONTENT_TYPE2);
        }
        if (!contentType.contains(JSON_CONTENT_TYPE)) {
            log.info("JsoupXssHttpServletRequestWrapper getInputStream,the content-type is {}", contentType);
            return super.getInputStream();
        }
        InputStream in = super.getInputStream();
        String body = IOUtils.toString(in, encoding);
        log.info("JsoupXssHttpServletRequestWrapper handle json requestBody,before content is {}", body);
        IOUtils.closeQuietly(in, null);

        // 空串处理直接返回
        if (StringUtils.isBlank(body)) {
            return super.getInputStream();
        }

        /**
         * 小于号处理
         */
        body = body.replaceAll("<","&lt;");
        body = body.replaceAll("&lt;script","<script");
        body = body.replaceAll("&lt;/script","</script");

        /**
         * 双引号转义处理（富文本）
         * 因为 Jsoup 对双引号的转义处理会出现误判现象:
         * 情况一：标签属性
         * before clean：<img src=\"https://123.jpeg\" data-ke-src=\"/ke4/attached/123.jpg\" alt=\"\">
         * after clean：<img src="\&quot;https://123.jpeg\&quot;" alt="\&quot;\&quot;">
         * 情况二：直接输入双引号，前端直接传转义-> &quot; 过滤器需要将其转为 \&quot;
         */
        body = body.replaceAll("&quot;", "\\\\&quot;");
        // xss过滤
        body = JsoupUtil.clean(body);
        /**
         * 双引号转义处理（富文本）
         */
        body = body.replaceAll("\"\\\\&quot;", "\\\\&quot;");
        body = body.replaceAll("\\\\&quot;\"", "\\\\&quot;");
        // 反转义处理
        body = StringEscapeUtils.unescapeHtml4(body);
        log.info("JsoupXssHttpServletRequestWrapper handle json requestBody,after content is {}", body);
        return new RequestCachingInputStream(body.getBytes(encoding));


    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(JsoupUtil.clean(name));
        log.info("JsoupXssHttpServletRequestWrapper handle parameter,before value is {}", value);
        if (StringUtils.isNotBlank(value)) {
            value = JsoupUtil.clean(value);
        }
        log.info("JsoupXssHttpServletRequestWrapper handle parameter ,before value is {}", value);
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] parameters = super.getParameterValues(name);
        if (parameters == null || parameters.length == 0) {
            return null;
        }

        for (int i = 0; i < parameters.length; i++) {
            log.info("JsoupXssHttpServletRequestWrapper handle parameter,before value is {}", parameters[i]);
            parameters[i] = JsoupUtil.clean(parameters[i]);
            log.info("JsoupXssHttpServletRequestWrapper handle parameter,before value is {}", parameters[i]);
        }
        return parameters;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = new LinkedHashMap<>();
        Map<String, String[]> parameters = super.getParameterMap();
        for (String key : parameters.keySet()) {
            String[] values = parameters.get(key);
            for (int i = 0; i < values.length; i++) {
                log.info("JsoupXssHttpServletRequestWrapper handle parameter,before value is {}", values[i]);
                values[i] = JsoupUtil.clean(values[i]);
                log.info("JsoupXssHttpServletRequestWrapper handle parameter,after value is {}", values[i]);
            }
            map.put(key, values);
        }
        return map;
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(JsoupUtil.clean(name));
        if (StringUtils.isNotBlank(value)) {
            value = JsoupUtil.clean(value);
        }
        return value;
    }

    /**
     * servlet中的InputStream只能一次读取，后续不能再次读取InputStream
     * xss过滤body后，重新把流放入ServletInputStream中
     */
    private static class RequestCachingInputStream extends ServletInputStream {

        private final ByteArrayInputStream inputStream;

        public RequestCachingInputStream(byte[] bytes) {
            inputStream = new ByteArrayInputStream(bytes);
        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }

        @Override
        public boolean isFinished() {
            return inputStream.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
        }

    }
}