package com.china.hcg.test;

import com.china.hcg.utils.email.EmailUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * @autor hecaigui
 * @date 2021-3-3
 * @description
 */
@Configuration
@ComponentScan(basePackages = {"com.china.hcg.utils.email"})
@EnableAutoConfiguration
//@SpringBootApplication
public class test_email {
    public static void main(String[] args) throws Exception{
        SpringApplication application = new SpringApplication( main.class );

        // 如果是web环境，默认创建AnnotationConfigEmbeddedWebApplicationContext，因此要指定applicationContextClass属性
        application.setApplicationContextClass( AnnotationConfigApplicationContext.class );
        application.run( args );
        System.err.println(11);
        ConfigurableApplicationContext applicationContext = SpringApplication.run(main.class, args);
        EmailUtils emailUtils = applicationContext.getBean("emailUtils",EmailUtils.class);
        emailUtils.sendSimpleMail("982791027@qq.com","测试","测试");
    }
}
