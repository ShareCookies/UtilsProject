package com.china.hcg.spring.utils;

import org.springframework.context.ApplicationContext;

/**
 * @autor hecaigui
 * @date 2023/5/13
 * @description
 */
public class SpringBeanUtil {
    static ApplicationContext ctx;

    public static ApplicationContext getCtx() {
        return ctx;
    }

    public static void setCtx(ApplicationContext ctx) {
        SpringBeanUtil.ctx = ctx;
    }

    public static <T> T getBean(String beanName, Class<T> c){
        if (ctx == null){throw new RuntimeException("请联系管理员在启动类注入ApplicationContext");}
        return  ctx.getBean(beanName,c);
    }
}
