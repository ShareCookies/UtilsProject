package com.china.hcg.utils.logback;

import com.china.hcg.http.other_utils.ServerIpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LogInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    @Autowired
    ServerIpUtil serverIpUtil;

    /**
     * 进入Controller层之前拦截请求，默认是拦截所有请求
     * @param httpServletRequest request
     * @param httpServletResponse response
     * @param o object
     * @return 是否拦截当前请求，true表示拦截当前请求，false表示不拦截当前请求
		preHandle()方法只有返回true，Controller中接口方法才能执行，否则不能执行，直接在preHandle()返回后false结束流程。
     * @throws Exception 可能出现的异常
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        System.err.println("go into preHandle method ... ");
//        Thread.sleep(20000);
//        String requestIp = serverIpUtil.getRequestIp(httpServletRequest)+httpServletRequest.getQueryString();
//        System.err.println(Thread.currentThread().getName());
//        System.err.println(requestIp);
//        System.err.println(MDC.get("requestIp"));
//
//        MDC.put("requestIp",requestIp);
        return true;
    }
    /**
     * 处理完请求后但还未渲染试图之前进行的操作
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("go into postHandle ... ");
        MDC.remove("requestIp");
    }



    /**
     * 视图渲染后但还未返回到客户端时的操作
     * @param httpServletRequest request
     * @param httpServletResponse response
     * @param o object
     * @param e exception
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        logger.info("go into afterCompletion ... ");
    }
}