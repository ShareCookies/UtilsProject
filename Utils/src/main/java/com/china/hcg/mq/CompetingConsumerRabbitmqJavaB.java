package com.china.hcg.mq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @autor hecaigui
 * @date 2022-2-15
 * @description
 */
public class CompetingConsumerRabbitmqJavaB {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setHost("192.168.0.40");
        factory.setPort(5672);
        //建立到代理服务器到连接
        Connection conn = factory.newConnection();
        //创建信道
        final Channel channel = conn.createChannel();


        String QUEUE_NAME = "hello-exchange2Competing2";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);    // 声明队列，只有他不存在的时候创建

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String recv = new String(body, "UTF-8");
                System.out.println("Receive:" + recv);
                try {
                    char c = recv.charAt(recv.length() - 1);
                    for (int i = 0; i < Integer.parseInt(c+""); i++)
                        Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("Done");
                }
            }
        };

        // true代表接收到消息后，给兔子发消息，让这条消息失效
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
