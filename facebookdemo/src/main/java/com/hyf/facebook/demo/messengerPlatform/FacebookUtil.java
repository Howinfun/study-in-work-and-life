package com.hyf.facebook.demo.messengerPlatform;

import com.hyf.facebook.demo.contants.FacebookGraphApiErrorCode;
import com.hyf.facebook.demo.contants.FacebookMessengerConstant;
import com.hyf.facebook.demo.contants.StoreThirdMenuConstant;
import com.hyf.facebook.demo.messengerPlatform.dto.FacebookGetStarted;
import com.hyf.facebook.demo.messengerPlatform.dto.FacebookGreeting;
import com.hyf.facebook.demo.messengerPlatform.dto.FacebookMenu;
import com.hyf.facebook.demo.messengerPlatform.dto.FacebookMenuAction;
import com.hyf.facebook.demo.messengerPlatform.dto.FacebookSetRequest;
import com.hyf.facebook.demo.messengerPlatform.dto.FacebookSetResponse;
import com.hyf.facebook.demo.messengerPlatform.dto.StoreThirdMenuConfig;
import com.hyf.facebook.demo.utils.http.OkHttpClientUtils;
import com.hyf.facebook.demo.utils.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Facebook 工具类
 * @author: winfun
 * https://developers.facebook.com/docs/messenger-platform/reference/messenger-profile-api/?translation
 * @date: 2020/11/7 10:04 下午
 **/
@Slf4j
@Component
public class FacebookUtil {

    /** success */
    private static final String SUCCESS = "success";
    /** result */
    private static final String RESULT = "result";
    private String url = "https://graph.facebook.com/v9.0/me/messenger_profile";

    /**
     * 是否设置了 getStarted
     * @param accessToken
     * @return Boolean
     */
    public Boolean hasGetStarted(String accessToken){

        Map<String, String> map = new HashMap<>(5);
        map.put("access_token", accessToken);
        map.put("fields", JsonUtil.toJson(Collections.singletonList(FacebookMessengerConstant.GET_STARTED)));
        try (Response response = OkHttpClientUtils.get(url, map)) {
            if (response.isSuccessful()) {
                String result = response.body().string();
                if (result.contains(FacebookMessengerConstant.GET_STARTED)){
                    return true;
                }
            }
        } catch (IOException e) {
            log.error("get facebook setting throws exception，accessToken is {}",accessToken);
            return false;
        }
        return false;
    }

    /**
     * 删除设置
     * @param accessToken
     * @param fields
     * @return
     */
    public FacebookSetResponse deleteSetting(String accessToken, List<String> fields){

        String api = url + "?access_token=" + accessToken;
        FacebookSetRequest request = new FacebookSetRequest();
        FacebookSetResponse response = new FacebookSetResponse();
        request.setFields(fields);
        try (Response res = OkHttpClientUtils.delete(api, request)) {
            if (res.isSuccessful()) {
                String result = res.body().string();
                Map<String,String> resultMap = JsonUtil.fromStr(result,Map.class);
                if (!resultMap.isEmpty() && SUCCESS.equals(resultMap.get(RESULT))){
                    response.setSuccess(true);
                    response.setMessage("删除成功！");
                    log.info("delete facebook setting {} success! request is {},result is {}",fields,request,result);
                }else {
                    response.setSuccess(false);
                    response.setMessage(res.message());
                    log.warn("delete facebook setting {} fail! request is {},code is {},message is {}",fields,request,
                             res.code(),res.message());
                }
            }else {
                String result = res.body().string();
                response = JsonUtil.fromStr(result,FacebookSetResponse.class);
                if (FacebookGraphApiErrorCode.ACCESS_TOKEN_EXPIRE.equals(response.getError().getCode())){
                    response.setMessage("设置失败，Facebook AccessToken 失效，请重新串接粉丝页！");
                }else if (FacebookGraphApiErrorCode.PERMISSIONS_ERROR.equals(response.getError().getCode())){
                    response.setMessage("设置失败，Facebook 串接用户没有操作权限！");
                }else {
                    response.setMessage("设置失败，Facebook API 调用失败！");
                }
                response.setSuccess(false);
                log.warn("delete facebook setting {} fail! request is {},code is {},message is {}",fields,request,
                         response.getError().getCode(),
                         response.getError().getMessage());
            }
        } catch (IOException e) {
            log.error("delete facebook setting {} throws exception，accessToken is {}",fields,accessToken);
            response.setSuccess(false);
            response.setMessage("删除失败，服务器异常！");
        }
        return response;
    }



    /**
     * 设置欢迎语
     * @param greeting
     * @param accessToken
     * @return 是否成功
     */
    public FacebookSetResponse setGreeting(String greeting, String accessToken) {

        final String api = url+"?access_token="+accessToken;
        FacebookSetRequest request = new FacebookSetRequest();
        FacebookSetResponse response = new FacebookSetResponse();
        request.setGreeting(Collections.singletonList(new FacebookGreeting().setLocale(FacebookMessengerConstant.LOCALE_DEFAULT).setText(greeting)));
        try (Response res = OkHttpClientUtils.post(api, request)) {
            if (res.isSuccessful()) {
                String result = res.body().string();
                Map<String,String> resultMap = JsonUtil.fromStr(result,Map.class);
                if (!resultMap.isEmpty() && SUCCESS.equals(resultMap.get(RESULT))){
                    response.setSuccess(true);
                    response.setMessage("设置 greeting 成功！");
                    log.info("set facebook greeting success! request is {},result is {}",request,result);
                }else {
                    response.setSuccess(false);
                    response.setMessage(res.message());
                    log.warn("set facebook greeting fail! request is {},result is {}",request,result);
                }
            }else {
                String result = res.body().string();
                response = JsonUtil.fromStr(result,FacebookSetResponse.class);
                if (FacebookGraphApiErrorCode.ACCESS_TOKEN_EXPIRE.equals(response.getError().getCode())){
                    response.setMessage("设置失败，Facebook AccessToken 失效，请重新串接粉丝页！");
                }else if (FacebookGraphApiErrorCode.PERMISSIONS_ERROR.equals(response.getError().getCode())){
                    response.setMessage("设置失败，Facebook 串接用户没有操作权限！");
                }else {
                    response.setMessage("设置失败，Facebook API 调用失败！");
                }
                response.setSuccess(false);
                log.warn("set facebook greeting fail! request is {},code is {},message is {}",request,
                         response.getError().getCode(),
                         response.getError().getMessage());
            }
        } catch (IOException e) {
            log.error("set facebook greeting throws exception，request is {}",request);
            response.setSuccess(false);
            response.setMessage("服务器异常！");
        }
        return response;
    }

    /**
     * 设置 getStarted
     * @param accessToken
     * @param getStartedPayload
     * @return
     */
    public FacebookSetResponse setGetStarted(String accessToken,String getStartedPayload){

        String api = url + "?access_token=" + accessToken;
        FacebookSetResponse response = new FacebookSetResponse();
        FacebookSetRequest request = new FacebookSetRequest();
        FacebookGetStarted getStarted = new FacebookGetStarted().setPayload(getStartedPayload);
        request.setGetStarted(getStarted);
        try (Response res = OkHttpClientUtils.post(api, request)) {
            if (res.isSuccessful()) {
                String result = res.body().string();
                Map<String,String> resultMap = JsonUtil.fromStr(result,Map.class);
                if (!resultMap.isEmpty() && SUCCESS.equals(resultMap.get(RESULT))){
                    response.setSuccess(true);
                    response.setMessage("设置GetStarted成功！");
                    log.info("set facebook GetStarted success! request is {},result is {}",request,result);
                }else {
                    response.setSuccess(false);
                    response.setMessage(res.message());
                    log.warn("set facebook GetStarted fail! request is {},result is {}",request,result);
                }
            }else {
                String result = res.body().string();
                response = JsonUtil.fromStr(result,FacebookSetResponse.class);
                if (FacebookGraphApiErrorCode.ACCESS_TOKEN_EXPIRE.equals(response.getError().getCode())){
                    response.setMessage("设置失败，Facebook AccessToken 失效，请重新串接粉丝页！");
                }else if (FacebookGraphApiErrorCode.PERMISSIONS_ERROR.equals(response.getError().getCode())){
                    response.setMessage("设置失败，Facebook 串接用户没有操作权限！");
                }else {
                    response.setMessage("设置失败，Facebook API 调用失败！");
                }
                response.setSuccess(false);
                log.warn("set facebook GetStarted fail! request is {},code is {},message is {}",request,
                         response.getError().getCode(),
                         response.getError().getMessage());
            }
        } catch (IOException e) {
            log.error("set facebook GetStarted throws exception，request is {}",request);
            response.setSuccess(false);
            response.setMessage("服务器异常！");
        }
        return response;
    }

    /**
     * 设置固定菜单栏
     * 设置固定菜单栏前，需设置了 get_started，所以要做一个判断
     * @param accessToken
     * @param menuConfigList
     * @return
     */
    public FacebookSetResponse setMenu(String accessToken, List<StoreThirdMenuConfig> menuConfigList){

        String api = url + "?access_token=" + accessToken;
        // 判断 get_started 是否存在
        Boolean getStarted = hasGetStarted(accessToken);
        // 创建 get_started
        if (!getStarted){
            FacebookSetResponse getStartedResponse = setGetStarted(accessToken,"GetStarted");
            if (!getStartedResponse.getSuccess()){
                throw new RuntimeException(getStartedResponse.getMessage());
            }
        }
        FacebookSetResponse response = new FacebookSetResponse();
        FacebookSetRequest request = new FacebookSetRequest();
        FacebookMenu facebookMenu = new FacebookMenu();
        List<FacebookMenuAction> actionList = new ArrayList<>(menuConfigList.size());
        for (StoreThirdMenuConfig menu : menuConfigList) {
            FacebookMenuAction action = new FacebookMenuAction();
            action.setTitle(menu.getMenuTitle());
            // 设置菜单内容
            setMenuContent(menu, action);
            actionList.add(action);
        }
        // 设置菜单
        facebookMenu.setLocale(FacebookMessengerConstant.LOCALE_DEFAULT);
        facebookMenu.setCallToActions(actionList);
        request.setPersistentMenu(Collections.singletonList(facebookMenu));
        try (Response res = OkHttpClientUtils.post(api, request)) {
            if (res.isSuccessful()) {
                String result = res.body().string();
                Map<String,String> resultMap = JsonUtil.fromStr(result,Map.class);
                if (!resultMap.isEmpty() && SUCCESS.equals(resultMap.get(RESULT))){
                    response.setSuccess(true);
                    response.setMessage("设置菜单栏成功！");
                    log.info("set facebook menu success! request is {},result is {}",request,result);
                }else {
                    response.setSuccess(false);
                    response.setMessage(res.message());
                    log.warn("set facebook menu fail! request is {},result is {}",request,result);
                }
            }else {
                String result = res.body().string();
                response = JsonUtil.fromStr(result,FacebookSetResponse.class);
                if (FacebookGraphApiErrorCode.ACCESS_TOKEN_EXPIRE.equals(response.getError().getCode())){
                    response.setMessage("设置失败，Facebook AccessToken 失效，请重新串接粉丝页！");
                }else if (FacebookGraphApiErrorCode.PERMISSIONS_ERROR.equals(response.getError().getCode())){
                    response.setMessage("设置失败，Facebook 串接用户没有操作权限！");
                }else {
                    response.setMessage("设置失败，Facebook API 调用失败！");
                }
                response.setSuccess(false);
                log.warn("set facebook menu fail! request is {},code is {},message is {}",request,
                         response.getError().getCode(),
                         response.getError().getMessage());
            }
        } catch (IOException e) {
            log.error("set facebook menu throws exception，request is {}",request);
            response.setSuccess(false);
            response.setMessage("服务器异常！");
        }
        return response;
    }

    /**
     * 设置菜单内容
     * @param menu
     * @param action
     */
    private void setMenuContent(StoreThirdMenuConfig menu, FacebookMenuAction action) {
        switch(menu.getMenuType()){
            case 1:
            case 3:
                action.setType(StoreThirdMenuConstant.FACEBOOK_MENU_TYPE_POSTBACK);
                action.setPayload(menu.getMenuPayload());
                break;
            case 2:
                action.setType(StoreThirdMenuConstant.FACEBOOK_MENU_TYPE_URL);
                action.setUrl(menu.getMenuUrl());
                break;
            default:
                break;
        }
    }
}
