package com.hyf.xss.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

/**
 * xss非法标签过滤
 * @Author: winfun
 * @Date: 2020/12/23 11:21 上午
 **/
public class JsoupUtil {

    private static final String HTTPS = "https://";
    /**
     * 默认使用Jsoup自带的relaxed白名单
     */
    private static final Whitelist WHITE_LIST = Whitelist.relaxed();
    /** 配置过滤化参数,不对代码进行格式化 */
    private static final Document.OutputSettings OUTPUT_SETTINGS = new Document.OutputSettings().prettyPrint(false);
    static {
        /**
         * 富文本编辑时一些样式是使用style来进行实现的
         * 比如红色字体 style="color:red;"
         * 所以需要给所有标签添加style属性
         */
        WHITE_LIST.addAttributes(":all", "style");
        /**
         * 允许相对链接，但是同时一定要设置 baseUri，不然相对链接一样会被干掉
         */
        WHITE_LIST.preserveRelativeLinks(true);
    }


    /***
     * XSS清洗
     * @author winfun
     * @param content 需要被清洗内容
     * @return {@link String }
     **/
    public static String clean(String content) {
        return JsoupUtil.clean(content, HTTPS);
    }

    /***
     * XSS清洗
     * @author winfun
     * @param content 需要被清洗内容
     * @param baseUri 用于解析相对URL，可设置为 http://或 https://；如不设置，不带协议的 URL 将无法解析成功，出现误判
     * @return {@link String }
     **/
    public static String clean(String content, String baseUri){
        return JsoupUtil.clean(content,baseUri,WHITE_LIST);
    }

    /***
     * XSS清洗
     * @author winfun
     * @param content 需要被清洗内容
     * @param baseUri 用于解析相对URL，可设置为 http:// 或 https://；如不设置，不带协议的 URL 将无法解析成功，出现误判
     * @param whitelist 自定义白名单，可添加标签、属性、URL 协议等；默认使用 Jsoup 提供的 Whitelist.basicWithImages()。{@link Whitelist}
     * @return {@link String }
     **/
    public static String clean(String content, String baseUri, Whitelist whitelist){
        return JsoupUtil.clean(content,baseUri,whitelist,OUTPUT_SETTINGS);
    }

    /***
     * XSS清洗
     * @author winfun
     * @param content 需要被清洗内容
     * @param baseUri 用于解析相对URL，可设置为 http:// 或 https://；如不设置，不带协议的 URL 将无法解析成功，出现误判
     * @param whitelist 自定义白名单，可添加标签、属性、URL 协议等；默认使用 Jsoup 提供的 Whitelist.basicWithImages()。{@link Whitelist}
     * @param outputSettings 配置过滤化参数，可设置字符集、代码格式化等等 {@link Document.OutputSettings}
     * @return {@link String }
     **/
    public static String clean(String content, String baseUri, Whitelist whitelist, Document.OutputSettings outputSettings){
        return Jsoup.clean(content, baseUri, whitelist, outputSettings);
    }
}