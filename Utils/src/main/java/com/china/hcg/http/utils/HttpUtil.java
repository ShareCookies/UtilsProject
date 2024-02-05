package com.china.hcg.http.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;

/**
 * @autor hecaigui
 * @date 2020/6/14
 * @description
 */
public class HttpUtil {
    public static  String getPrefix(String url){
        String pre = null;
        try {
            URL a = new URL(url);
            pre = a.getProtocol() + "://";
            pre += a.getHost();
            if (a.getPort() != -1){
               // pre += ":" + a.getPort() + "/";
                pre += ":" + a.getPort() + "";
            } else {
                //pre += "/";
                pre += "";
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return pre;
    }
    /*
     * spring获取当前请求
     */
    public static HttpServletRequest getHttpServletRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }
    public static void main(String[] args){
        System.out.println(HttpUtil.getPrefix("https://fanyi.baidu.com:8080/"));
    }
}
