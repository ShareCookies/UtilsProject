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
/**
 官方操作手册：https://logback.qos.ch/manual/
 Java logback日志的简单使用：
    https://blog.csdn.net/p1830095583/article/details/116804892?spm=1001.2101.3001.6650.2&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-2-116804892-blog-123127684.pc_relevant_3mothn_strategy_and_data_recovery&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-2-116804892-blog-123127684.pc_relevant_3mothn_strategy_and_data_recovery&utm_relevant_index=5
    注：//有缓存时(target)，logback.xml可能无法生效
    ？
        String logConfig = environment.getProperty(CONFIG_PROPERTY);
        LoggingInitializationContext initializationContext = new LoggingInitializationContext();
        loggingSystem.initialize(initializationContext, null, logFile);
    ？调用父类，父类在调用子类
    ？
        logback全自动，无需特意指定配置文件原理是什么
        既然全自动，那log4j、logback 会包冲突之类吧
 Springboot集成log4j
    https://blog.csdn.net/xiaoguangtouqiang/article/details/122483372
 logback介绍：
    默认情况下，Spring Boot会用Logback来记录日志，并用INFO级别输出到控制台。
     logback当前分为以下三个模块：
         logback-core：其它两个模块的基础模块。
         logback-classic：它是log4j的一个改良版本，同时它完整实现了slf4j API使你可以很方便地更换成其它日志系统如log4j或JDK14 Logging。
         logback-access：访问模块与Servlet容器集成提供通过Http来访问日志的功能。
        依赖：
            logback需要logback-core、logback-classic、slf4j-api、logback-access这4个依赖。
            其中logback-classic已经包含了logback-core和slf4j-api依赖，由于Maven依赖的传递性，所以我们只需导入logback-classic和logback-access依赖即可。
        logBack配置文件
             logback默认在类路径下会依次读取以下配置文件
                logback-test.xml
                logback.groovy
                logback.xml
                如果都不存在会采用默认配置
            配置文件介绍：
                https://blog.csdn.net/Student111w/article/details/123127684
                https://zhuanlan.zhihu.com/p/474844021
    常用技巧
         1. 使用占位符
             logger.debug("我是" + name + "，我今年" + age + "岁，很高兴认识你！");//普通方式
             logger.debug("我是{}，我今年{}岁，很高兴认识你！", name, age);//占位符方式(推荐)
             当debug日志被禁用时，普通方式中，参数依然会被构造拼接，而在占位符方式中，参数不会进行构造拼接。
         2. 使用时应该使用slf4j的API而不是使用logback的API(依赖日志门面，而不是依赖具体的日志实现，便于更换其他日志框架)
         3. 自动重新加载配置文件
            将元素的scan属性设置为true，logback会定时的扫描配置文件，如果配置文件发生了更改，将自动重新加载配置文件。默认每分钟扫描一次，可以设置scanPeriod属性来指定扫描间隔。

 解决logback不能输出e.printStackTrace()的日志问题
    https://www.jianshu.com/p/6e258a7d1026


 logback不同业务的日志打印到不同文件：
     如何自定义logback日志文件的名称
        http://www.heartthinkdo.com/?p=2945
        https://www.modb.pro/db/149711
     logback不同业务的日志打印到不同文件的xml配置：
        https://www.bbsmax.com/A/gVdn0rpaJW/
 logback日志滚动生成：
    https://blog.csdn.net/qq_33121481/article/details/93485180
 logback实现一个日志文件配置多个level级别
    https://blog.csdn.net/x_ing_j_ing/article/details/119926626
 ?
     为啥logback直接引入，通过slf4j的api运行就能生效？
 */