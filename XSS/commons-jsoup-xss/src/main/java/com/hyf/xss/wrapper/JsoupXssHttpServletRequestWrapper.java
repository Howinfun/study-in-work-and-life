package com.hyf.xss.wrapper;

import com.hyf.xss.utils.JsoupUtil;
import org.apache.commons.io.IOUtils;
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
public class JsoupXssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /** request */
    private HttpServletRequest orgRequest;
    /** 编码 */
    private String encoding;
    /** JSON 格式 */
    private final static String JSON_CONTENT_TYPE =  "application/json";
    /** Content-Type */
    private final static String CONTENT_TYPE = "Content-Type";

    /***
     * 构造
     * @author winfun
     * @param request 请求
     * @param encoding 编码
     * @return {@link null }
     **/
    public JsoupXssHttpServletRequestWrapper(HttpServletRequest request, String encoding){
        super(request);
        orgRequest = request;
        this.encoding = encoding;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException{
        // 非Json格式请求体，此处不做处理
        if(!JSON_CONTENT_TYPE.equalsIgnoreCase(super.getHeader(CONTENT_TYPE))){
            return super.getInputStream();
        }
        InputStream in = super.getInputStream();
        String body = IOUtils.toString(in, encoding);
        IOUtils.closeQuietly( in,null );

        // 空串处理直接返回
        if(StringUtils.isBlank(body)){
            return super.getInputStream();
        }

        // xss过滤
        body = JsoupUtil.clean(body);
        return new RequestCachingInputStream(body.getBytes(encoding));

    }

    @Override
    public String getParameter( String name ){
        String value = super.getParameter(JsoupUtil.clean(name));
        if( StringUtils.isNotBlank(value)){
            value = JsoupUtil.clean(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues( String name ){
        String[] parameters = super.getParameterValues(name);
        if(parameters == null || parameters.length == 0){
            return null;
        }

        for( int i = 0; i < parameters.length; i++ ){
            parameters[i] = JsoupUtil.clean( parameters[i] );
        }
        return parameters;
    }

    @Override
    public Map<String, String[]> getParameterMap(){
        Map<String, String[]> map = new LinkedHashMap<>();
        Map<String, String[]> parameters = super.getParameterMap();
        for(String key : parameters.keySet()){
            String[] values = parameters.get(key);
            for(int i = 0; i < values.length; i++){
                values[i] = JsoupUtil.clean(values[i]);
            }
            map.put(key, values);
        }
        return map;
    }

    @Override
    public String getHeader( String name ){
        String value = super.getHeader(JsoupUtil.clean(name));
        if(StringUtils.isNotBlank(value)){
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
        public void setReadListener( ReadListener readListener ){
        }

    }
} 