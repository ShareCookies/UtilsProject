package com.china.hcg;


//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.mybatis.spring.annotation.MapperScan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @Configuration 标识该类是一个spring配置类
 * @ComponentScan 默认扫描该包及子包下的spring的组件
 * @MappserScan 扫描指定包的mybatis mapper
 * @EnableTransactionManagement 启用事务管理
 * @EnableAutoConfiguration 启用spring boot的自动配置
 */

@Configuration
@ComponentScan
//@MapperScan("com.rongji.egov.urger.business.mapper")
//@EnableTransactionManagement(proxyTargetClass = true)
@EnableAutoConfiguration
public class ApplicationConfiguration
{
    public static void main( String[] args ){
        SpringApplication.run(ApplicationConfiguration.class, args);
    }
}
