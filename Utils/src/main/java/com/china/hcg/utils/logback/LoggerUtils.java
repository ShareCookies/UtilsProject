package com.china.hcg.utils.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * @autor hecaigui
 * @date 2022-11-30
 * @description
 */
public class LoggerUtils {
    //并发会整样？
    public static Logger getLoggerForCustomLogFileName(String logFileName){
        MDC.put("logFileName", logFileName);
        return LoggerFactory.getLogger("customlog");
    }
}
