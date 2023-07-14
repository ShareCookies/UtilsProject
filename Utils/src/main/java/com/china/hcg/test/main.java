package com.china.hcg.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.china.hcg.utils.email.EmailUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Console;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @autor hecaigui
 * @date 2021-3-3
 * @description
 */
@Configuration
@ComponentScan(basePackages = {"com.china.hcg.utils.email"})
@EnableAutoConfiguration
//@SpringBootApplication
public class main {
//	public static void main(String[] args) {
//		JSONObject updateLogJson = JSONObject.parseObject("{}");
//		StringBuilder nostring = new StringBuilder("11");
//
//		System.err.println(nostring.toString());
//		List<String> urgerTaskSignUnits = new ArrayList<>();
//		for (String urgerTaskSignUnit : urgerTaskSignUnits){
//			System.err.println(111);
//		}
////		int b = 4;
////		System.out.println((b++) < 5);
////		System.out.println(b);
////
////		++i,i++区别
////		原理：
////		https://www.jb51.net/article/179905.htm
//
//		  /*
//  创建一个变量i，里面放i的初始值3，先开辟一个临时存储区，
//  将i的值复制到存储区，此时存储区里面i的值为3
//   */int i = 3;
//		i ++;//i自身加1，此时i的值为4
//		System.out.print(i);//打印输出i，因为i没有被调用，故输出i自身的值4
//		System.out.print(i++);
//		System.out.print(i++);
//		System.out.print(i++);
//		System.out.print(i++);
//		System.out.print(i);
//		System.out.print(i);
//
//
////		Java中 a+=b和a=a+b有什么区别？
////		https://blog.csdn.net/m0_38022029/article/details/79606104
//	}
static SynchronousQueue blockingQueue = new SynchronousQueue<>();
public static String handlePeriodDate;


    public static void main(String[] args) throws Exception{
//        SpringApplication application = new SpringApplication( main.class );
//
//        // 如果是web环境，默认创建AnnotationConfigEmbeddedWebApplicationContext，因此要指定applicationContextClass属性
//        application.setApplicationContextClass( AnnotationConfigApplicationContext.class );
//        application.run( args );
//        System.err.println(11);
//        ConfigurableApplicationContext applicationContext = SpringApplication.run(main.class, args);
//        EmailUtils emailUtils = applicationContext.getBean("emailUtils",EmailUtils.class);
//        emailUtils.sendSimpleMail("982791027@qq.com","测试","测试");
//        String tokenId = "1014UserCenterNewTokenid;111";
//        //tokenId = tokenId.substring(tokenId.indexOf("0"));
//        tokenId = tokenId.split("1014UserCenterNewTokenid;")[1];
//        JSONObject jsonObject = JSONObject.parseObject("{\"guanteeNo\":\"10388131000224700001\",\"privateKey\":\"103881310002247_abc123123-egCKpJBsENPNfPa35uhS.pfx\",\"password\":\"abc123123\",\"settleMode\":1,\"isSplit\":0,\"splitItems\":[],\"outPaymentMode\":0,\"reservedAmount\":\"0\"}");
//        System.err.println(jsonObject);
        System.err.println("e4d45885af5b5301f56e19a349ae02dfde1c8dd7e8f1004c0fabdcea07c873f7".toLowerCase(Locale.ROOT));
        System.err.println("E4D45885AF5B5301F56E19A349AE02DFDE1C8DD7E8F1004C0FABDCEA07C873F7".toLowerCase(Locale.ROOT));
        System.err.println("e4d45885af5b5301f56e19a349ae02dfde1c8dd7e8f1004c0fabdcea07c873f7".toLowerCase(Locale.ROOT).equals("E4D45885AF5B5301F56E19A349AE02DFDE1C8DD7E8F1004C0FABDCEA07C873F7".toLowerCase(Locale.ROOT)));

    }
    public static Long yuan2Feng(Double yuan) {
        if (yuan == null) {
            yuan = 0D;
        }
        return BigDecimal.valueOf(yuan).movePointRight(2).longValue();
    }
    static void   test(HashConflictObj i,String string ){
        i.integer+=1;
        string="11";
    }
    static class HashConflictObj{
        String p1;
        String p2;Integer integer = 0;

        public HashConflictObj(String p1, String p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        @Override
        public int hashCode() {
            return p1.hashCode()+p2.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    }
}
