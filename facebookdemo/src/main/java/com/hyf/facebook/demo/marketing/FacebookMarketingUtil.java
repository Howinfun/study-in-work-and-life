package com.hyf.facebook.demo.marketing;

import com.hyf.facebook.demo.contants.HttpConstant;
import com.hyf.facebook.demo.marketing.dto.FacebookAccessTokenRequest;
import com.hyf.facebook.demo.marketing.dto.FacebookAccessTokenResponse;
import com.hyf.facebook.demo.marketing.dto.FacebookBusinessConfigResponse;
import com.hyf.facebook.demo.marketing.dto.FacebookBusinessInfoResponse;
import com.hyf.facebook.demo.marketing.dto.FacebookBusinessPrivateInfoResponse;
import com.hyf.facebook.demo.marketing.dto.FacebookPageInfoResponse;
import com.hyf.facebook.demo.marketing.dto.FacebookPageTabsResponse;
import com.hyf.facebook.demo.marketing.dto.FacebookUserAccountsResponse;
import com.hyf.facebook.demo.marketing.dto.FacebookUserInfoResponse;
import com.hyf.facebook.demo.utils.http.OkHttpClientUtils;
import com.hyf.facebook.demo.utils.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Facebook 行销工具
 * @author winfun
 * @date 2020/11/26 5:40 下午
 **/
@Slf4j
@Component
public class FacebookMarketingUtil {

    public static final String API = "https://graph.facebook.com";

    public static final String API_VERSION = "v6.0";

    /***
     * 获取 FBE 安装信息
     * 参考文档：https://developers.facebook.com/docs/marketing-api/fbe/fbe2/guides/get-features#fbe-installation-api
     * @author winfun
     * @param businessId businessId
     * @param accessToken accessToken
     * @return {@link FacebookBusinessInfoResponse }
     **/
    public FacebookBusinessInfoResponse queryBusinessExtensionInfo(String businessId, String accessToken){
        String url = API + HttpConstant.PATH_PREFIX + API_VERSION + HttpConstant.PATH_PREFIX + "fbe_business" +
                "/fbe_installs?fbe_external_business_id=" + businessId + "&access_token=" + accessToken;
        try (Response response = OkHttpClientUtils.get(url)) {
            String result = response.body().string();
            if (response.isSuccessful()){
                log.info("FacebookMarketingUtil#queryBusinessExtensionInfo result is {}",result);
                FacebookBusinessInfoResponse businessResponse = JsonUtil.fromStr(result,
                                                                                 FacebookBusinessInfoResponse.class);
                return businessResponse;
            }else {
                log.error("FacebookMarketingUtil#queryBusinessExtensionInfo fail，businessId is {},accessToken is {}," +
                                  "result is {}",
                          businessId,accessToken,result);
                return null;
            }
        } catch (IOException e) {
            log.error("FacebookMarketingUtil#queryBusinessExtensionInfo throws exception，businessId is {},accessToken is {}",businessId,accessToken);
            return null;
        }
    }

    /***
     * 获取商业扩充套件的信息
     * @author winfun
     * @param businessManagerId FB商业扩充套件的 businessManagerId
     * @param accessToken accessToken
     * @return {@link FacebookBusinessPrivateInfoResponse }
     **/
    public FacebookBusinessPrivateInfoResponse queryBusinessPrivateInfo(String businessManagerId, String accessToken){
        String url =
                API + HttpConstant.PATH_PREFIX + API_VERSION + HttpConstant.PATH_PREFIX + businessManagerId + HttpConstant.PATH_PREFIX +
                        "?fields=id,name,profile_picture_uri&access_token="+accessToken+"";
        try (Response response = OkHttpClientUtils.get(url)) {
            String result = response.body().string();
            if (response.isSuccessful()){
                log.info("FacebookMarketingUtil#queryBusinessPrivateInfo result is {}",result);
                FacebookBusinessPrivateInfoResponse businessResponse = JsonUtil.fromStr(result, FacebookBusinessPrivateInfoResponse.class);
                return businessResponse;
            }else {
                log.error("FacebookMarketingUtil#queryBusinessPrivateInfo fail，businessManagerId is {},accessToken is {}," +
                                  "result is {}",
                          businessManagerId,accessToken,result);
                return null;
            }
        } catch (IOException e) {
            log.error("FacebookMarketingUtil#queryBusinessPrivateInfo throws exception，businessManagerId is {},accessToken is {}",businessManagerId,accessToken);
            return null;
        }
    }

    /***
     * 卸载 FBE
     * 参考文档：https://developers.facebook.com/docs/marketing-api/fbe/fbe2/guides/uninstall
     * @author winfun
     * @param businessId businessId
     * @param accessToken accessToken
     * @return {@link Boolean }
     **/
    public Boolean deleteBusiness(String businessId,String accessToken){
        String url = API + HttpConstant.PATH_PREFIX + API_VERSION + HttpConstant.PATH_PREFIX +"fbe_business/fbe_installs?fbe_external_business_id="+businessId+"&access_token="+accessToken;
        try (Response response = OkHttpClientUtils.delete(url)) {
            String result = response.body().string();
            if (response.isSuccessful()) {
                log.info("FacebookMarketingUtil#deleteBusiness result is {}",result);
                return true;
            }else {
                log.error("FacebookMarketingUtil#deleteBusiness fail，userId is {},accessToken is {},result is {}",
                          businessId,
                          accessToken,result);
                return false;
            }
        } catch (IOException e) {
            log.error("FacebookMarketingUtil#deleteBusiness throws exception，userId is {},accessToken is {}",businessId,accessToken);
            return false;
        }
    }

    /***
     * 获取系统 accessToken
     * 参考文档：https://developers.facebook.com/docs/marketing-api/fbe/fbe2/guides/get-features#system-user-creation
     * @author winfun
     * @param clientBusinessManagerId FB商业扩充套件的 businessManagerId
     * @param accessToken accessToken
     * @return {@link FacebookAccessTokenResponse }
     **/
    public FacebookAccessTokenResponse getSystemAccessToken(String facebookAppId, String clientBusinessManagerId,
                                                            String businessId,
                                                            String accessToken, String scope){
        String url =
                API + HttpConstant.PATH_PREFIX + API_VERSION + HttpConstant.PATH_PREFIX + clientBusinessManagerId + HttpConstant.PATH_PREFIX
                        + "access_token?app_id="+facebookAppId+"&scope="+scope+"&access_token="+accessToken+
                        "&fbe_external_business_id="+businessId;
        try (Response response = OkHttpClientUtils.post(url,new FacebookAccessTokenRequest())) {
            String result = response.body().string();
            if (response.isSuccessful()){
                log.info("FacebookMarketingUtil#getSystemAccessToken result is {}",result);
                FacebookAccessTokenResponse tabResponse = JsonUtil.fromStr(result, FacebookAccessTokenResponse.class);
                return tabResponse;
            }else {
                log.error("FacebookMarketingUtil#getSystemAccessToken fail，url is {},result is {}",url,result);
                return null;
            }
        } catch (IOException e) {
            log.error("FacebookMarketingUtil#getSystemAccessToken throws exception，url is {}",url);
            return null;
        }
    }

    /***
     * 查询 FBE 商业管理平台配置
     * 参考文档；https://developers.facebook.com/docs/marketing-api/fbe/fbe2/guides/business-configurations#feature-mgmt-view
     * @author winfun
     * @param businessId businessId
     * @param accessToken accessToken
     * @return {@link FacebookBusinessConfigResponse }
     **/
    public FacebookBusinessConfigResponse getBusinessConfig(String businessId, String accessToken){
        String url = API + HttpConstant.PATH_PREFIX + API_VERSION + HttpConstant.PATH_PREFIX + "fbe_business?fbe_external_business_id=" + businessId + "&access_token=" + accessToken;
        try (Response response = OkHttpClientUtils.get(url)) {
            String result = response.body().string();
            if (response.isSuccessful()){
                log.info("FacebookMarketingUtil#getBusinessConfig result is {}",result);
                FacebookBusinessConfigResponse businessInfoResponse = JsonUtil.fromStr(result, FacebookBusinessConfigResponse.class);
                return businessInfoResponse;
            }else {
                log.error("FacebookMarketingUtil#getBusinessConfig fail，businessId is {},accessToken is {},result is " +
                                  "{}",
                          businessId,
                          accessToken,result);
                return null;
            }
        } catch (IOException e) {
            log.error("FacebookMarketingUtil#getBusinessConfig throws exception，businessId is {},accessToken is {}",businessId,accessToken);
            return null;
        }
    }

    /**
     * 更新聊天插件状态
     * 参考文档；https://developers.facebook.com/docs/marketing-api/fbe/fbe2/guides/business-configurations#feature-mgmt-view
     * @author winfun
     * @param state
     * @param businessId
     * @param accessToken
     * @return
     */
    public Boolean updateMessengerBusinessConfig(Integer state,String businessId,String accessToken){
        String url = API + HttpConstant.PATH_PREFIX + API_VERSION + HttpConstant.PATH_PREFIX + "fbe_business";
        FacebookBusinessConfigResponse.FBEMessengerChatConfigData messengerChatConfigData = new FacebookBusinessConfigResponse.FBEMessengerChatConfigData();
        if (state == 0){
            messengerChatConfigData.setEnabled(false);
        }else if (state == 1){
            messengerChatConfigData.setEnabled(true);
        }
        Map<String,Object> requestMap = new HashMap<>(3);
        requestMap.put("fbe_external_business_id",businessId);
        requestMap.put("access_token",accessToken);
        requestMap.put("business_config",messengerChatConfigData);
        try (Response response = OkHttpClientUtils.post(url, requestMap)) {
            if (response.isSuccessful()) {
                String result = response.body().string();
                log.info("FacebookMarketingUtil#updateMessengerBusinessConfig result is {}",result);
                return true;
            }else {
                log.error("FacebookMarketingUtil#updateMessengerBusinessConfig fail，businessId is {},accessToken is " +
                                  "{}",businessId,accessToken);

                return false;
            }
        } catch (IOException e) {
            log.error("FacebookMarketingUtil#updateMessengerBusinessConfig throws exception，businessId is {},accessToken is {}",businessId,accessToken);
            return false;
        }
    }

    /**
     * 更新 Facebook shop 状态
     * 参考文档；https://developers.facebook.com/docs/marketing-api/fbe/fbe2/guides/business-configurations#feature-mgmt-view
     * @author winfun
     * @param state
     * @param businessId
     * @param accessToken
     * @return
     */
    public Boolean updateShopBusinessConfig(Integer state,String businessId,String accessToken){
        String url = API + HttpConstant.PATH_PREFIX + API_VERSION + HttpConstant.PATH_PREFIX + "fbe_business";
        FacebookBusinessConfigResponse.FBECatalogFeedConfigData catalogFeedConfigData = new FacebookBusinessConfigResponse.FBECatalogFeedConfigData();
        if (state == 0){
            catalogFeedConfigData.setEnabled(false);
        }else if (state == 1){
            catalogFeedConfigData.setEnabled(true);
        }
        Map<String,Object> requestMap = new HashMap<>(3);
        requestMap.put("fbe_external_business_id",businessId);
        requestMap.put("access_token",accessToken);
        requestMap.put("business_config",catalogFeedConfigData);
        try (Response response = OkHttpClientUtils.post(url, requestMap)) {
            if (response.isSuccessful()) {
                String result = response.body().string();
                log.info("FacebookMarketingUtil#updateShopBusinessConfig result is {}",result);
                return true;
            }else {
                log.error("FacebookMarketingUtil#updateShopBusinessConfig fail，businessId is {},accessToken is {}",
                          businessId,accessToken);
                return false;
            }
        } catch (IOException e) {
            log.error("FacebookMarketingUtil#updateShopBusinessConfig throws exception，businessId is {},accessToken is {}",businessId,accessToken);
            return false;
        }
    }

    /***
     * 获取粉丝页信息
     * @author winfun
     * @param pageId pageId
     * @param accessToken accessToken
     * @param fields fields
     * @return
     **/
    public FacebookPageInfoResponse getPageInfo(String pageId,String accessToken,String fields){
        String url = API + HttpConstant.PATH_PREFIX + pageId + "?fields=" + fields + "&access_token=" + accessToken;
        try (Response response = OkHttpClientUtils.get(url)) {
            String result = response.body().string();
            log.info("FacebookMarketingUtil#getPageInfo result is {}",result);
            FacebookPageInfoResponse pageInfoResponse = JsonUtil.fromStr(result, FacebookPageInfoResponse.class);
            if (response.isSuccessful()){
                return pageInfoResponse;
            }else {
                log.error("FacebookMarketingUtil#getPageInfo fail! userId is {},accessToken is {},fields is {},reason is {}",pageId,
                          accessToken,
                          fields,pageInfoResponse.getError().getMessage()
                          );
                return null;
            }
        } catch (IOException e) {
            log.error("FacebookMarketingUtil#getPageInfo throws exception! userId is {},accessToken is {},fields is {},exception is {}",
                      pageId,
                      accessToken,
                      fields,e.getMessage());
            return null;
        }
    }

    /**
     * 根据 AccessToken 获取用户信息
     * @author winfun
     * @param accessToken
     * @return
     */
    public FacebookUserInfoResponse getUserInfo(String accessToken){
        String url = API + HttpConstant.PATH_PREFIX + API_VERSION + HttpConstant.PATH_PREFIX + "me?fields=id,name&access_token=" + accessToken;
        try (Response response = OkHttpClientUtils.get(url)) {
            String result = response.body().string();
            log.info("FacebookMarketingUtil#getUserInfo result is {}",result);
            FacebookUserInfoResponse userInfoResponse = JsonUtil.fromStr(result,FacebookUserInfoResponse.class);
            if (response.isSuccessful()){
                return userInfoResponse;
            }else {
                log.error("FacebookMarketingUtil#getUserInfo fail!accessToken is {},reason is {}",
                        accessToken,userInfoResponse.getError().getMessage()
                );
                return null;
            }
        } catch (IOException e) {
            log.error("FacebookMarketingUtil#getUserInfo throws exception!accessToken is {},exception is {}",
                    accessToken,e.getMessage());
            return null;
        }
    }

    /**
     * 获取用户可操作的主页列表
     * @param userId
     * @param accessToken
     * @return
     */
    public FacebookUserAccountsResponse getUserAccounts(String userId, String accessToken){
        String url = API + HttpConstant.PATH_PREFIX + API_VERSION + HttpConstant.PATH_PREFIX + userId + HttpConstant.PATH_PREFIX + "accounts?access_token=" + accessToken;
        try (Response response = OkHttpClientUtils.get(url)) {
            String result = response.body().string();
            log.info("FacebookMarketingUtil#getUserAccounts result is {}",result);
            FacebookUserAccountsResponse userAccountsResponse = JsonUtil.fromStr(result,FacebookUserAccountsResponse.class);
            if (response.isSuccessful()){
                return userAccountsResponse;
            }else {
                log.error("FacebookMarketingUtil#getUserAccounts fail! userId is {},accessToken is {},reason is {}",
                        userId,accessToken,userAccountsResponse.getError().getMessage()
                );
                return null;
            }
        } catch (IOException e) {
            log.error("FacebookMarketingUtil#getUserAccounts throws exception! userId is {},accessToken is {},exception is {}",
                    userId,accessToken,e.getMessage());
            return null;
        }
    }

    /**
     * 获取公共页签列表
     * @param pageId
     * @param accessToken
     * @return
     */
    public FacebookPageTabsResponse getPageTabs(String pageId, String accessToken){
        String url = API + HttpConstant.PATH_PREFIX + API_VERSION + HttpConstant.PATH_PREFIX + pageId + HttpConstant.PATH_PREFIX + "tabs?access_token=" + accessToken;
        try (Response response = OkHttpClientUtils.get(url)) {
            String result = response.body().string();
            log.info("FacebookMarketingUtil#getPageTabs result is {}",result);
            FacebookPageTabsResponse userAccountsResponse = JsonUtil.fromStr(result,FacebookPageTabsResponse.class);
            if (response.isSuccessful()){
                return userAccountsResponse;
            }else {
                log.error("FacebookMarketingUtil#getPageTabs fail! pageId is {},accessToken is {},reason is {}",
                        pageId,accessToken,userAccountsResponse.getError().getMessage()
                );
                return null;
            }
        } catch (IOException e) {
            log.error("FacebookMarketingUtil#getPageTabs throws exception! pageId is {},accessToken is {},exception is {}",
                    pageId,accessToken,e.getMessage());
            return null;
        }
    }
}
