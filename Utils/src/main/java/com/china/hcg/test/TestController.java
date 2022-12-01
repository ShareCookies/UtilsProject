package com.china.hcg.test;


import com.china.hcg.utils.logback.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liaohm
 * @date 2020/8/14
 */
@RestController
public class TestController {


    @Autowired
    private Environment env;


    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    /**
     *
     */
    @GetMapping("/test")
    public String dictDataMove() {
        LoggerUtils.getLoggerForCustomLogFileName("test").info("111");
        logger.error("error");
        return "";
    }


}
