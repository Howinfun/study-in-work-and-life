package com.hyf.facebook.demo.utils.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hyf.facebook.demo.utils.FastThreadLocalContext;
import com.hyf.facebook.demo.utils.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Http请求工具类
 * @author pig
 */
@Slf4j
public abstract class OkHttpClientUtils {

    public static final MediaType JSON_TYPE_UTF8 = MediaType.parse("application/json; charset=utf-8");

    public static final int MAX_RETRY_TIMES=2;
    /**
     * 默认解析器
     */
    private static MessageConvertor messageConvertor = new MessageConvertor() {

        @Override
        public <T> String serialize(T t) {
            return JsonUtil.toJson(t);
        }

        @Override
        public <T> T deserialize(String responseBody, Class<T> classOfT) {
            return JsonUtil.fromStr(responseBody, classOfT);
        }

        @Override
        public <T> T deserialize(String responseBody, Type type) {
            return JsonUtil.fromStr(responseBody, new TypeReference<T>() {
                @Override
                public Type getType() {
                    return type;
                }
            });
        }
    };

    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .connectionPool(new ConnectionPool(30, 30, TimeUnit.MINUTES))
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY_NOT_HEAD))
            .build();

    public static MessageConvertor getMessageConvertor() {
        return messageConvertor;
    }

    public static void setMessageConvertor(MessageConvertor messageConvertor) {
        OkHttpClientUtils.messageConvertor = messageConvertor;
    }

    public static void  setNeedRetry(boolean isNeedRetry){
        FastThreadLocalContext.put("httpFailRetry",isNeedRetry);
    }

    public static OkHttpClient getClient() {
        return client;
    }

    public static void setClient(OkHttpClient client) {
        OkHttpClientUtils.client = client;
    }

    /**
     * 反序列化Post请求
     *
     * @param url   请求地址
     * @param classOfT  请求对象Class
     * @param <T>   泛型对象
     * @return  解析结果
     * @throws IOException
     */
    public static <T> T parsePost(@org.springframework.lang.NonNull String url, @org.springframework.lang.NonNull Class<T> classOfT) throws IOException {
        return messageConvertor.deserialize(postReturnBody(url), classOfT);
    }

    public static <T> T parsePost(@org.springframework.lang.NonNull String url, @org.springframework.lang.NonNull Type type) throws IOException {
        return messageConvertor.deserialize(postReturnBody(url), type);
    }

    /**
     * 反序列化Post请求，请求Body为json
     *
     * @param url
     * @param object
     * @param classOfT
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T parsePost(@org.springframework.lang.NonNull String url, @org.springframework.lang.NonNull Object object, @org.springframework.lang.NonNull Class<T> classOfT) throws IOException {
        return messageConvertor.deserialize(postReturnBody(url, object), classOfT);
    }

    public static <T> T parsePost(@org.springframework.lang.NonNull String url, @org.springframework.lang.NonNull Object object, @org.springframework.lang.NonNull Type type) throws IOException {
        return messageConvertor.deserialize(postReturnBody(url, object), type);
    }

    /**
     * 反序列化Post请求，请求Body为form
     *
     * @param url
     * @param form
     * @param classOfT
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T parsePost(@org.springframework.lang.NonNull String url, @org.springframework.lang.NonNull Map<String, String> form, @org.springframework.lang.NonNull Class<T> classOfT) throws IOException {
        return messageConvertor.deserialize(postReturnBody(url, form), classOfT);
    }

    public static <T> T parsePost(@org.springframework.lang.NonNull String url, @org.springframework.lang.NonNull Map<String, String> form, @org.springframework.lang.NonNull Type type) throws IOException {
        return messageConvertor.deserialize(postReturnBody(url, form), type);
    }

    /**
     * 反序列化Post请求
     *
     * @param url
     * @param headers
     * @param requestBody
     * @param classOfT
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T parsePost(@org.springframework.lang.NonNull String url, @org.springframework.lang.NonNull Headers headers, @org.springframework.lang.NonNull RequestBody requestBody, @org.springframework.lang.NonNull Class<T> classOfT) throws IOException {
        return messageConvertor.deserialize(postReturnBody(url, headers, requestBody), classOfT);
    }

    public static <T> T parsePost(@org.springframework.lang.NonNull String url, @org.springframework.lang.NonNull Headers headers, @org.springframework.lang.NonNull RequestBody requestBody, @org.springframework.lang.NonNull Type type) throws IOException {
        return messageConvertor.deserialize(postReturnBody(url, headers, requestBody), type);
    }


    public static String postReturnBody(@org.springframework.lang.NonNull String url) throws IOException {
        return postReturnBody(url, null, null);
    }

    /**
     * 以“application/json” post提交数据，提交对象为object
     *
     * @param url
     * @param object
     * @return
     */
    public static String postReturnBody(@org.springframework.lang.NonNull String url, @org.springframework.lang.NonNull Object object) throws IOException {
        return post(url, object).body().string();
    }

    /**
     * 以 form 格式提交
     *
     * @param url
     * @param form
     * @return
     */
    public static String postReturnBody(@org.springframework.lang.NonNull String url, @org.springframework.lang.NonNull Map<String, String> form) throws IOException {
        return post(url, form).body().string();
    }

    /**
     * OkHttpClient 通过formbody，post提交
     *
     * @param url
     * @param body
     * @return
     */
    public static String postReturnBody(@org.springframework.lang.NonNull String url, @org.springframework.lang.NonNull RequestBody body) throws IOException {
        return postReturnBody(url, null, body);
    }

    /**
     * OkHttpClient 以Post 方式提交
     *
     * @param url
     * @param headers
     * @param requestBody
     * @return
     * @throws IOException
     */
    public static String postReturnBody(@org.springframework.lang.NonNull String url, Headers headers,
                                        RequestBody requestBody) throws IOException {
        return post(url, headers, requestBody).body().string();
    }

    /**
     * 最简单直接Post请求
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static Response post(@org.springframework.lang.NonNull String url) throws IOException {
        return post(url, null, null);
    }

    /**
     * OkHttpClient 以“application/json” post提交数据
     *
     * @param url
     * @param object
     * @return Response
     * @throws IOException
     */
    public static Response post(@org.springframework.lang.NonNull String url, @org.springframework.lang.NonNull Object object) throws IOException {
        String json = messageConvertor.serialize(object);
        RequestBody body = RequestBody.create(json, JSON_TYPE_UTF8);
        return post(url, null, body);
    }

    /**
     * OkHttpClient 以“application/json” post提交数据
     *
     * @param url
     * @param form
     * @return Response
     * @throws IOException
     */
    public static Response post(@org.springframework.lang.NonNull String url, @org.springframework.lang.NonNull Map<String, String> form) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();
        form.entrySet().stream().forEach(t -> {
            builder.add(t.getKey(), t.getValue());
        });
        return post(url, null, builder.build());
    }

    /**
     * OkHttpClient 通过formbody，post提交,返回Response
     *
     * @param url
     * @param body
     * @return Response
     * @throws IOException
     */
    public static Response post(@org.springframework.lang.NonNull String url, @org.springframework.lang.NonNull RequestBody body) throws IOException {
        return post(url, null, body);
    }

    /**
     * OkHttpClient post 提交方法
     *
     * @param url
     * @param body
     * @param headers
     * @return
     * @throws IOException
     */
    public static Response post(@org.springframework.lang.NonNull String url, Headers headers, RequestBody body) throws IOException{
        //这里获取url数据
        HttpUrl.Builder httpUrlBuilder = HttpUrl.parse(url).newBuilder();

        Request.Builder builder = new Request.Builder().url(httpUrlBuilder.build());
        if (Objects.nonNull(headers)) {
            builder.headers(headers);
        }
        if (Objects.nonNull(body)) {
            builder.post(body);
        }
        int retryNum=0;
        try {
            return post(builder.build(),url,retryNum);
        } catch (Exception e){
            log.error("http post exception,url={}",url,e);
            throw e;
        } finally {
            FastThreadLocalContext.remove("httpFailRetry");
        }
    }

    /**
     * OkHttpClient 以“application/json” delete 提交数据
     * @author winfun
     * @param url
     * @return Response
     * @throws IOException
     */
    public static Response delete(@org.springframework.lang.NonNull String url) throws IOException {
        return delete(url, null, null);
    }

    /**
     * OkHttpClient 以“application/json” delete 提交数据
     * @author winfun
     * @param url
     * @param object
     * @return Response
     * @throws IOException
     */
    public static Response delete(@org.springframework.lang.NonNull String url, @org.springframework.lang.NonNull Object object) throws IOException {
        String json = messageConvertor.serialize(object);
        RequestBody body = RequestBody.create(json, JSON_TYPE_UTF8);
        return delete(url, null, body);
    }

    /**
     * OkHttpClient delete 提交方法
     * @author winfun
     * @param url
     * @param body
     * @param headers
     * @return
     * @throws IOException
     */
    public static Response delete(@org.springframework.lang.NonNull String url, Headers headers, RequestBody body) throws IOException{
        //这里获取url数据
        HttpUrl.Builder httpUrlBuilder = HttpUrl.parse(url).newBuilder();

        Request.Builder builder = new Request.Builder().url(httpUrlBuilder.build());
        if (Objects.nonNull(headers)) {
            builder.headers(headers);
        }
        if (Objects.nonNull(body)) {
            builder.delete(body);
        }else {
            builder.delete();
        }
        int retryNum=0;
        try {
            // 这里的 post() 方法其实是直接调用 client.newCall 执行 HTTP 请求
            return post(builder.build(),url,retryNum);
        } catch (Exception e){
            log.error("http delete exception,url={}",url,e);
            throw e;
        } finally {
            FastThreadLocalContext.remove("httpFailRetry");
        }
    }

    /**
     *
     * @author heshishan
     * @date 2020/9/27 11:38 上午
     * @param request request
     * @param url url
     * @param retryNum retryNum
     * @return {@link Response }
     **/
    public static Response post(Request request, @org.springframework.lang.NonNull String url, int retryNum) throws IOException {
        //这里RequestBody已经包含了MediaType
        Response response =null;
        try {
            response =client.newCall(request).execute();
        } catch (SocketTimeoutException e) {
            Boolean needRetry = FastThreadLocalContext.get("httpFailRetry")==null?false:
                    (Boolean) FastThreadLocalContext.get("httpFailRetry");

            if (needRetry && ++retryNum<=MAX_RETRY_TIMES){
                log.warn("http response fail, retry call http,request={},retryTime={}",
                         ReflectionToStringBuilder.toString(request),
                         retryNum+ "" );
                response =post(request, url,retryNum);
            }
        }
        return response;
    }

    /**
     *
     * @param url url
     * @param classOfT classOfT
     * @return {@link T }
     **/
    public static <T> T parseGet(@org.springframework.lang.NonNull String url, @org.springframework.lang.NonNull Class<T> classOfT) throws IOException {
        return messageConvertor.deserialize(getReturnBody(url), classOfT);
    }

    public static <T> T parseGet(@org.springframework.lang.NonNull String url, @org.springframework.lang.NonNull Type type) throws IOException {
        return messageConvertor.deserialize(getReturnBody(url), type);
    }

    /**
     * 反序列化Get请求
     *
     * @param url
     * @param headers
     * @param classOfT
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T parseGet(@org.springframework.lang.NonNull String url, @org.springframework.lang.NonNull Headers headers, @org.springframework.lang.NonNull Class<T> classOfT) throws IOException {
        return messageConvertor.deserialize(getReturnBody(url, headers), classOfT);
    }

    public static <T> T parseGet(@org.springframework.lang.NonNull String url, @org.springframework.lang.NonNull Headers headers, @org.springframework.lang.NonNull Type type) throws IOException {
        return messageConvertor.deserialize(getReturnBody(url, headers), type);
    }

    /**
     * 反序列化Get请求
     *
     * @param url
     * @param params
     * @param classOfT
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T parseGet(@org.springframework.lang.NonNull String url, @org.springframework.lang.NonNull Map<String, String> params, @org.springframework.lang.NonNull Class<T> classOfT) throws IOException {
        return messageConvertor.deserialize(getReturnBody(url, params), classOfT);
    }

    public static <T> T parseGet(@org.springframework.lang.NonNull String url, @org.springframework.lang.NonNull Map<String, String> params, @org.springframework.lang.NonNull Type type) throws IOException {
        return messageConvertor.deserialize(getReturnBody(url, params), type);
    }

    /**
     * 反序列化Get请求
     *
     * @param url
     * @param headers
     * @param params
     * @param classOfT
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T parseGet(@org.springframework.lang.NonNull String url, @org.springframework.lang.NonNull Headers headers, @org.springframework.lang.NonNull Map<String, String> params, @org.springframework.lang.NonNull Class<T> classOfT) throws IOException {
        return messageConvertor.deserialize(getReturnBody(url, headers, params), classOfT);
    }

    public static <T> T parseGet(@org.springframework.lang.NonNull String url, @org.springframework.lang.NonNull Headers headers, @org.springframework.lang.NonNull Map<String, String> params, @org.springframework.lang.NonNull Type type) throws IOException {
        return messageConvertor.deserialize(getReturnBody(url, headers, params), type);
    }

    /**
     * OkHttpClient通过url，get提交
     *
     * @param url 传送url和参数集
     * @return
     */
    public static String getReturnBody(@org.springframework.lang.NonNull String url) throws IOException {
        return getReturnBody(url, null, null);
    }

    /**
     * OkHttpClient通过设置Header与url，Get方法提交
     *
     * @param url
     * @param headers 请求头
     * @return
     */
    public static String getReturnBody(@org.springframework.lang.NonNull String url, @org.springframework.lang.NonNull Headers headers) throws IOException {
        return getReturnBody(url, headers, null);
    }

    /**
     * OkHttpClient 通过url 和 params 合拼,get提交方法
     *
     * @param url
     * @param params 参数集
     * @return
     * @throws IOException
     */
    public static String getReturnBody(@org.springframework.lang.NonNull String url, @org.springframework.lang.NonNull Map<String, String> params) throws IOException {
        return getReturnBody(url, null, params);
    }

    /**
     * OkHttpClient Get 方式请求
     *
     * @param url
     * @param headers
     * @param params
     * @return
     * @throws IOException
     */
    public static String getReturnBody(@org.springframework.lang.NonNull String url, Headers headers, Map<String, String> params) throws IOException {
        return get(url, headers, params).body().string();
    }

    /**
     * OkHttpClient通过url，get提交，返回Response对象
     *
     * @param url
     * @return Response
     * @throws IOException
     */
    public static Response get(@org.springframework.lang.NonNull String url) throws IOException {
        return get(url, null, null);
    }


    /**
     * OkHttpClient通过设置url与header,get提交，返回Response对象
     *
     * @param url
     * @param headers
     * @return
     * @throws IOException
     */
    public static Response get(@org.springframework.lang.NonNull String url, @org.springframework.lang.NonNull Headers headers) throws IOException {
        return get(url, headers, null);
    }

    /**
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    public static Response get(@org.springframework.lang.NonNull String url, @org.springframework.lang.NonNull Map<String, String> params) throws IOException {
        return get(url, null, params);
    }

    /**
     * OkHttpClient通过设置url、params、Header，Get方法提交
     *
     * @param url
     * @param headers
     * @param params
     * @return
     * @throws IOException
     */
    public static Response get(@org.springframework.lang.NonNull String url, Headers headers, Map<String, String> params) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        if (!CollectionUtils.isEmpty(params)) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("get params is null or empty url<{}>", url);
            }
        }
        Request.Builder requestBuilder = new Request.Builder().url(urlBuilder.build());
        if (headers != null) {
            requestBuilder.headers(headers);
        }
        return client.newCall(requestBuilder.build()).execute();
    }

    /**
     * OkHttpClient 异步post提交object对象
     *
     * @param url
     * @param object
     * @param callback 如果Callback为空则默认只打日志
     * @return Call
     */
    public static Call asyncPost(@org.springframework.lang.NonNull String url, Object object, Callback callback) throws Exception {
        String json = messageConvertor.serialize(object);
        return asyncPost(url, json, callback);
    }

    /**
     * OkHttpClient 异步post提交json对象
     *
     * @param url
     * @param json
     * @param callback 如果Callback为空则默认只打日志
     * @return
     */
    public static Call asyncPost(@org.springframework.lang.NonNull String url, String json, Callback callback) throws Exception {
        RequestBody body = RequestBody.create(json, JSON_TYPE_UTF8);
        return asyncPost(url, body, null, callback);
    }

    /**
     * OkHttpClient 异步post提交object对象
     *
     * @param url
     * @param object
     * @param headers
     * @param callback
     * @return
     * @throws Exception
     */
    public static Call asyncPost(@org.springframework.lang.NonNull String url, Object object, Headers headers, Callback callback) {
        String json = messageConvertor.serialize(object);
        return asyncPost(url, json, headers, callback);
    }

    /**
     * OkHttpClient 异步post提交json对象
     *
     * @param url
     * @param json
     * @param headers
     * @param callback
     * @return
     * @throws Exception
     */
    public static Call asyncPost(@org.springframework.lang.NonNull String url, String json, Headers headers, Callback callback) {
        RequestBody body = RequestBody.create(json, JSON_TYPE_UTF8);
        return asyncPost(url, body, headers, callback);
    }

    /**
     * OkHttpClient 异步post提交formbody对象
     *
     * @param url
     * @param body
     * @param callback 如果Callback为空则默认只打日志
     * @return
     */
    public static Call asyncPost(@NonNull String url, RequestBody body, Headers headers, Callback callback) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        Request.Builder builder = new Request.Builder().url(urlBuilder.build());
        if (Objects.nonNull(body)) {
            builder.post(body);
        }
        if (Objects.nonNull(headers)) {
            builder.headers(headers);
        }
        Call call = client.newCall(builder.build());
        if (callback == null) {
            callback = new LogCallBack();
        }
        call.enqueue(callback);
        log.info("async request! url<{}>, bodyM<{}>", url, body);
        return call;
    }

    /**
     * 默认Response 解析器
     */
    public static interface MessageConvertor {

        /**
         * 序列化
         *
         * @param t
         * @param <T>
         * @return
         */
        <T> String serialize(T t);

        /**
         * 反序列化
         *
         * @param responseBody
         * @param classOfT
         * @param <T>
         * @return
         */
        <T> T deserialize(String responseBody, Class<T> classOfT);

        /**
         * 反序列化
         *
         * @param responseBody
         * @param type
         * @param <T>
         * @return
         */
        <T> T deserialize(String responseBody, Type type);
    }

    /**
     * 默认日志记录回调类
     */
    @Slf4j
    public static class LogCallBack implements Callback {
        @Override
        public void onFailure(Call call, IOException e) {
            log.error(String.format("url<%s> params<%s>", call.request().url(), call.request().body()), e);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            InputStream is = response.body().byteStream();
            String result = IOUtils.toString(is, StandardCharsets.UTF_8);
            log.info(String.format("url<%s> params<%s> response<%s>", call.request().url(), call.request().body(), result));
        }
    }
}