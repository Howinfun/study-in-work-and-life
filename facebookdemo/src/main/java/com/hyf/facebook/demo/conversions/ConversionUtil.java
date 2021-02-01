package com.hyf.facebook.demo.conversions;

import com.hyf.facebook.demo.contants.HttpConstant;
import com.hyf.facebook.demo.conversions.dto.FacebookConversionData;
import com.hyf.facebook.demo.utils.http.OkHttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

/**
 *
 * @author winfun
 * @date 2021/2/1 4:14 下午
 **/
@Slf4j
public class ConversionUtil {

    public static final String API = "https://graph.facebook.com";

    public static final String API_VERSION = "v9.0";

    /***
     * 发送新事件
     * @author winfun
     * @param pixelId pixelId
     * @param accessToken accessToken
     * @param data data
     * @return {@link Void }
     **/
    public void addEvent(String pixelId,String accessToken,FacebookConversionData data){
        String url = API + HttpConstant.PATH_PREFIX + API_VERSION + HttpConstant.PATH_PREFIX + pixelId +
                HttpConstant.PATH_PREFIX + "events?access_token="+accessToken;
        try (Response response = OkHttpClientUtils.post(url, data)) {
            if (response.isSuccessful()) {
                String result = response.body().string();
                log.info("ConversionUtil#addEvent result is {}",result);
            }else {
                log.error("ConversionUtil#addEvent fail,data is {},accessToken is {}",data,accessToken);
            }
        } catch (Exception e) {
            log.error("ConversionUtil#addEvent throws exception，data is {},message is {}",data,e.getMessage());
        }
    }

}
