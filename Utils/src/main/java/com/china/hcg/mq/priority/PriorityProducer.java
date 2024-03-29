package com.china.hcg.mq.priority;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created by hidden on 2017/2/14.
 */
public class PriorityProducer {
    public static final String ip = "192.168.0.40";
    public static final int port = 5672;
    public static final String username = "guest";
    public static final String password = "guest";

    public static void main(String[] arggs) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setPassword(password);
        connectionFactory.setUsername(username);
        connectionFactory.setPort(port);
        connectionFactory.setHost(ip);


        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        //create exchange
        channel.exchangeDeclare("exchange_priority","direct",true);

        //create queue with priority
        Map<String,Object> args = new HashMap<String,Object>();
        args.put("x-max-priority", 10);
        channel.queueDeclare("queue_priority", true, false, false, args);
        channel.queueBind("queue_priority", "exchange_priority", "rk_priority");

        //send message with priority
        for(int i=0;i<10;i++) {
            AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
            if(i%2!=0)
                builder.priority(5);
            AMQP.BasicProperties properties = builder.build();
            channel.basicPublish("exchange_priority","rk_priority",properties,("messages-"+i).getBytes());
        }

        channel.close();
        connection.close();
    }
}
