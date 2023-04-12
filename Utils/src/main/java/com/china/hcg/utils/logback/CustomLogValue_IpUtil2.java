package com.china.hcg.utils.logback;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.china.hcg.http.other_utils.ServerIpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * @autor hecaigui
 * @date 2023-1-31
 * @description logback自定义日志格式的参数值
 */
public class CustomLogValue_IpUtil2 extends ClassicConverter {


    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        if (webApplicationContext == null){
            return "1111";
        }
		ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        ServerIpUtil serverIpUtil = ctx.getBean("ServerIpUtil",ServerIpUtil.class);
        if (serverIpUtil!=null){
            return serverIpUtil.getServerAddress();
        }
        return "1111";
    }
}