package com.china.hcg.test;


import com.china.hcg.utils.logback.LoggerUtils;
import org.apache.commons.lang3.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liaohm
 * @date 2020/8/14
 */
@RestController
public class TestController {

    //controller是单例。例：任何线程 打印的class 。且赋值为2后 任何线程也都打印2 都一样后续打印都是单
    int controllerScope;
    @Autowired
    private Environment env;


    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    Map<String, Thread> threads = new HashMap<>();
    /**
     *
     */
    @GetMapping("/test")
    public String test() {
//        LoggerUtils.getLoggerForCustomLogFileName("test").info("111");
//        logger.error("error");
//        logger.info("info");
//        logger.warn("WARN");
        System.err.println(Thread.currentThread().getName());
        System.err.println(this);
        System.err.println(controllerScope);

        //？一个实例为何可以被并发了，java是如何实现的了
//        try {
//            Thread.sleep(10000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        return "";
    }

    public String threadWait() {
//        LoggerUtils.getLoggerForCustomLogFileName("test").info("111");
//        logger.error("error");
//        logger.info("info");
//        logger.warn("WARN");
        System.err.println(Thread.currentThread().getName());
        try {
            threads.put(Thread.currentThread().getName(),Thread.currentThread());
            synchronized (Thread.currentThread()){
                Thread.currentThread().wait(10000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     *
     */
    @GetMapping("/threadNotice")
    public String threadNotice(String threadName) {
        synchronized (threads.get(threadName)){
            threads.get(threadName).notify();
        }
        return "";
    }
}
