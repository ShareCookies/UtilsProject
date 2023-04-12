package com.china.hcg.utils.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogBackTest {

    /**
     * 日志记录器对象
     */


    private static final Logger LOGGER= LoggerFactory.getLogger(LogBackTest.class);

    public static void main(String[] args) {
        LoggerUtils.getLoggerForCustomLogFileName("test").info("111");
        LOGGER.error("error");
        testQuick();
    }
    public static void testQuick() {
        /*默认级别为info*/
        LOGGER.info("info");
        LOGGER.error("error");
        LOGGER.warn("warn");
        LOGGER.debug("debug");
        LOGGER.trace("trace");
//        //使用占位符输出日志信息
//        String name="fei";
//        Integer age=20;
//        LOGGER.info("用户信息为--->姓名：{},年龄：{}",name,age);
    }

}