package com.hyf.facebook.demo.conversions;

import cn.hutool.core.thread.ThreadUtil;
import com.hyf.facebook.demo.contants.HttpConstant;
import com.hyf.facebook.demo.conversions.dto.FacebookConversionRequest;
import com.hyf.facebook.demo.conversions.dto.FacebookConversionResponse;
import com.hyf.facebook.demo.utils.http.OkHttpClientUtils;
import com.hyf.facebook.demo.utils.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

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
    public Integer addEvent(String pixelId, String accessToken, FacebookConversionRequest data) {
        String url = API + HttpConstant.PATH_PREFIX + API_VERSION + HttpConstant.PATH_PREFIX + pixelId +
                HttpConstant.PATH_PREFIX + "events";
        try (Response response = OkHttpClientUtils.post(url, data)) {
            if (response.isSuccessful()) {
                String result = response.body().string();
                log.info("ConversionUtil#addEvent result is {}", result);
                FacebookConversionResponse conversionResponse = JsonUtil.fromStr(result,
                                                                                 FacebookConversionResponse.class);
                return conversionResponse.getEventsReceived();
            } else {
                log.error("ConversionUtil#addEvent fail,data is {},accessToken is {}", data, accessToken);
            }
        } catch (Exception e) {
            log.error("ConversionUtil#addEvent throws exception，data is {},message is {}", data, e.getMessage());
        }
        return 0;
    }

    public static void main(String[] args) {
        /**
         * curl --location --request POST 'https://graph.facebook.com/v9.0/{pixelId}/events' \
         * --header 'Content-Type: application/json' \
         * --data-raw '{
         *     "access_token":"",
         *     "data": [
         *         {
         *             "event_name": "AddToCart",
         *             "event_time": 1612429570,
         *             "user_data": {
         *                 "fn": ""
         *             }
         *         }
         *     ],
         *     "partner_agent": "",
         *     "test_event_code": ""
         * }'
         */
        /*String pixelId = "";
        String accessToken = "";
        FacebookConversionRequest request = new FacebookConversionRequest();
        request.setAccessToken(accessToken);
        request.setPartnerAgent("");
        request.setTestEventCode("");
        FacebookConversionData data = new FacebookConversionData();
        data.setEventName("AddToCart");
        data.setEventTime(1612429570L);
        UserData userData = new UserData();
        userData.setFn("6a2d68a4d63341d206f2b735f45fd64dd00e5a286ade91ab1c30141590c1cf2c");
        data.setUserData(userData);
        request.setData(Collections.singletonList(data));
        ConversionUtil util = new ConversionUtil();
        for (int i = 0; i < 50000; i++) {
            try {
                Thread.sleep(1000);
                Integer receivedCount = util.addEvent(pixelId,accessToken,request);
                if (receivedCount == 0){
                    break;
                }
                log.info("第{}次调用成功，received_count is {}",i,receivedCount);
            }catch (Exception e){
                log.error("调用失败！");
            }

        }*/
        String token =
                "";
        String url = "https://graph.facebook.com/v9.0/{id}/campaigns?access_token=" + token;
        int count = 0;
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(1L);
        while (true){
            LocalDateTime now = LocalDateTime.now();
            log.info("开始第{}波刷API, now is {}",++count,now);
            for(int i = 0; i< 299;i++) {
                try {
                    // 每隔一秒调用接口
                    ThreadUtil.sleep(1,TimeUnit.SECONDS);
                    Response response = OkHttpClientUtils.get(url);
                    String result = response.body().string();
                    if (response.isSuccessful()) {
                        log.info("第{}次调用成功，result is {}", i, result);
                    } else {
                        log.error("call fail,the result is {}", result);
                        break;
                    }
                } catch (Exception e) {
                    log.error("调用失败！");
                    break;
                }
            }
            // 休眠1.5小时，再重新刷
            ThreadUtil.sleep(1.5, TimeUnit.HOURS);
            // 刷到明天
            if (now.isAfter(end)){
                break;
            }
        }
    }
}
