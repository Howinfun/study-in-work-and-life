package com.hyf.testDemo.ip;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 *
 * @Description: IP工具类
 * @Author: winfun
 * @Date: 2020/9/16 4:05 下午
 **/
public class IpUtils {

    private static final String UNKNOWN = "unknown";

    /***
     * @desc 根据 request 获取客户端 IP
     * @author winfun
     * @param request request
     * @return {@link String }
     **/
    public static String getIpAddress(HttpServletRequest request) {
        String ip = null;

        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");
        if (ipAddresses == null || ipAddresses.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip)?"127.0.0.1":ip;
    }

    /** IPV4 正则表达式*/
    private static final Pattern IPV4_PATTERN =
            Pattern.compile(
                    "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
    /** IPV6 STD 正则表达式*/
    private static final Pattern IPV6_STD_PATTERN =
            Pattern.compile(
                    "^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");
    /** IPV6 HEX 正则表达式*/
    private static final Pattern IPV6_HEX_COMPRESSED_PATTERN =
            Pattern.compile(
                    "^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");

    /***
     * @desc 判断是否为 IPV4地址
     * @author winfun
     * @param input input
     * @return {@link boolean }
     **/
    public static boolean isIpV4Address(final String input) {
        return IPV4_PATTERN.matcher(input).matches();
    }
    /***
     * @desc 判断是否为 IPV6地址
     * @author winfun
     * @param input input
     * @return {@link boolean }
     **/
    public static boolean isIpV6Address(final String input) {
        return isIpV6StdAddress(input) || isIpV6HexCompressedAddress(input);
    }
    public static boolean isIpV6StdAddress(final String input) {
        return IPV6_STD_PATTERN.matcher(input).matches();
    }
    public static boolean isIpV6HexCompressedAddress(final String input) {
        return IPV6_HEX_COMPRESSED_PATTERN.matcher(input).matches();
    }
}
