package com.china.hcg.utils.email;



import com.china.hcg.test.main;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import javax.mail.MessagingException;
import java.util.concurrent.*;

/**
 * @autor hecaigui
 * @date 2020-5-30
 * @description
 * yml配置：
 *      #  ssl版邮箱配置
 *      spring:
 *         mail:
 *           host: smtp.163.com
 *           port: 465
 *           #    default-encoding: UTF-8
 *           #    #公用的邮件服务器，发送者账号要和验证账号一样的！
 *           username: 18350027142@163.com
 *           password: no1314test
 *           #设置为SSL协议
 *           properties:
 *             mail:
 *               smtp:
 *                 socketFactory:
 *                   class: javax.net.ssl.SSLSocketFactory
 * main运行demo：
 *         ConfigurableApplicationContext applicationContext = SpringApplication.run(main.class, args);
 *         EmailUtils emailUtils = applicationContext.getBean("emailUtils",EmailUtils.class);
 *         emailUtils.sendSimpleMail("@qq.com","测试","测试");
 */
@Service
public class EmailUtils {
    //
    static ExecutorService emailThreadPool = new ThreadPoolExecutor(1, 100, 0L, TimeUnit.MICROSECONDS, new LinkedBlockingDeque<Runnable>(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("邮件发送用的线程池");
            System.out.println("创建线程："+t.getName()+t.getId());
            return  t;
        }
    });
    class mailRunnable implements Runnable{
       JavaMailSender mailSender;
       SimpleMailMessage simpleMailMessage;
       mailRunnable(JavaMailSender mailSender, SimpleMailMessage simpleMailMessage){
            this.mailSender = mailSender;
            this.simpleMailMessage = simpleMailMessage;
        }
        @Override
        public void run(){
            mailSender.send(simpleMailMessage);
        }
    }
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    String emailFrom;
    /**
     * @description 发送简易邮件
     * @return
     */
    public void sendSimpleMail(String receiverEamil , String emailContent , String emailSubject) throws MessagingException {
        if (StringUtils.isBlank(emailSubject)){
            emailSubject = "来自学家系统的回复";
        }
        //简单邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailFrom);
        simpleMailMessage.setTo(receiverEamil);
        simpleMailMessage.setSubject(emailSubject);
        simpleMailMessage.setText(emailContent);
        EmailUtils.emailThreadPool.execute(new mailRunnable(mailSender,simpleMailMessage));
    }

}
