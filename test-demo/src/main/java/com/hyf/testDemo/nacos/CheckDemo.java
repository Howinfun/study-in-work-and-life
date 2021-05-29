package com.hyf.testDemo.nacos;

import com.hyf.testDemo.netty.json.JsonUtil;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;

/**
 *
 * @author winfun
 * @date 2021/5/11 2:07 下午
 **/
public class CheckDemo {

    public static void main(String[] args) {
        String checkUrl = "";
        query(checkUrl);
    }

    @SneakyThrows
    public static void query(String url){
        String result = HttpClientHelper.sendGet(url);
        List<NotSuccessResult> notSuccessList = new LinkedList<>();
        NacosResult nacosResult = JsonUtil.parseFromJson(result, NacosResult.class);
        if (null != nacosResult && CollectionUtils.isNotEmpty(nacosResult.getDoms())) {

            Map<String, List<String>> resultMap = new LinkedHashMap<>();
            nacosResult.getDoms().stream().filter(data -> data.contains("providers") && !data.contains("meta") && !data.contains("risk")).forEach(data -> {
                String[] keyArr = data.split(":");
                String key = keyArr[0] + ":" + keyArr[1];
                List<String> list = resultMap.get(key);
                if (CollectionUtils.isEmpty(list)) {
                    list = new LinkedList<>();
                    resultMap.put(key, list);
                }
                list.add(data);
            });
            resultMap.entrySet().forEach(entry -> {
                if (!entry.getValue().contains(entry.getKey() + ":1.0.0:")) {
                    NotSuccessResult noSuccessResult = new NotSuccessResult();
                    notSuccessList.add(noSuccessResult.setKey(entry.getKey()).setData(entry.getValue()));
                }
            });
            notSuccessList.forEach(System.out::println);
        }
    }

    /**
     * nacos 查询结果
     */
    @Data
    static class NacosResult implements Serializable {
        private static final long serialVersionUID = -2861239209018481632L;

        private List<String> doms;
    }

    /**
     * 不符合条件的提供者
     */
    @Data
    @Accessors(chain = true)
    static class NotSuccessResult implements Serializable {

        private static final long serialVersionUID = 7476150558163120738L;
        /**
         * provider 前缀
         */
        private String key;
        /**
         * provider 列表
         */
        private List<String> data;
    }
}