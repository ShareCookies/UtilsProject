package com.china.hcg.http.other_utils;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author hcg
 * @desc 获取服务器地址
 **/
@Component
public class ServerIpUtil implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {
    private int serverPort;
    @Resource
    private Environment env;

    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        this.serverPort = event.getEmbeddedServletContainer().getPort();
    }

    public int getServerPort() {
        return this.serverPort;
    }

    private String getIp() {
        InetAddress address=null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            throw new RuntimeException("服务IP获取失败");
        }
        return  address.getHostAddress();
    }
    /**
     * @description 当服务器很纯粹时，可使用该方法获取服务器地址
     * @author hecaigui
     * @date 2020-9-15
     * @param
     * @return 服务器地址 ip:端口
     */
    public String getPureServerAddress() {
        return this.getServerAddress()+":"+this.serverPort;
    }
    /**
     * @description 配合spring配置，来获取服务器地址
     * @author hecaigui
     * @date 2020-8-25
     * @param
     * @return 服务器地址 配置ip:配置端口
     */
    public String getServerAddress() {
        String server = "";
        // 例服务器进行中转了，有专门的对外接口
        if (StringUtils.isNotBlank(env.getProperty("serverIpAddress")) && StringUtils.isNotBlank(env.getProperty("serverPort"))){
            server= env.getProperty("serverIpAddress")+":"+env.getProperty("serverPort");
        }
        // 例有多网卡
        else if (StringUtils.isNotBlank(env.getProperty("serverIpAddress"))){
            server= env.getProperty("serverIpAddress")+":"+env.getProperty("server.port");
        }
        // 例无多网卡纯粹的linux系统
        else {
            server= this.getIp()+":"+env.getProperty("server.port");
        }
        return server;
    }

    /**
     * @description
     *     //传入request对象，获得客户端ip
     *     //注意，本地不行，本地会获取到0:0:0:0:0:0:0:1；服务器上是正常的
     * @date 2023-2-3
     * @return
     */
    public  String getRequestIp(HttpServletRequest request) {
        if (request == null){
            return null;
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            //本地会获取到0:0:0:0:0:0:0:1
            ip = request.getRemoteAddr();
        }
        if (ip.contains(",")) {
            return ip.split(",")[0];
        } else {
            return ip;
        }
    }
}
