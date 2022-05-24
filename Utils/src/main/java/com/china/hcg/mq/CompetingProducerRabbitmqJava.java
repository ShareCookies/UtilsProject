package com.china.hcg.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @autor hecaigui
 * @date 2022-2-15
 * @description 消息生产者
 */
public class CompetingProducerRabbitmqJava {
    public static void main(String[] args) throws IOException, TimeoutException,Exception {
        //1. 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        //设置 RabbitMQ 地址
        factory.setHost("192.168.0.40");
        factory.setPort(5672);
        //2. 建立到代理服务器到连接(如果每个线程都建立一个 那么会有多个tcp连接吗？)
        Connection conn = factory.newConnection();
        //3. 创建信道
        Channel channel = conn.createChannel();
            //声明交换器（这一步可以不用吧，服务器建好就行吧。2. ？那如果交换器重了 3. 没有的话 会用默认的吧）
            // 交换器是什么：
        String QUEUE_NAME = "hello-exchange2Competing";
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //发布消息
        byte[] messageBodyBytes = "quit".getBytes();

        // 发送多条消息
        for (int i = 0; i < 5; i++){
            channel.basicPublish("", QUEUE_NAME, null, (messageBodyBytes + "-" + i).getBytes());
            System.out.println("Sending:" + messageBodyBytes);
            Thread.sleep(500);
        }

        channel.close();
        conn.close();
    }
}
