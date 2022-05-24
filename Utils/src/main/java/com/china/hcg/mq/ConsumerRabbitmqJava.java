package com.china.hcg.mq;
import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
/**
 * @autor hecaigui
 * @date 2022-2-15
 * @description
 */
public class ConsumerRabbitmqJava {

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
        //以下非必要的吧。只是为了防止交换器和队列不存在吧？
            //声明交换器
            String exchangeName = "hello-exchange2";
            channel.exchangeDeclare(exchangeName, "direct", true);
            //声明队列
            String queueName = channel.queueDeclare().getQueue();
            String routingKey = "hola";
            //绑定队列，通过键 hola 将队列和交换器绑定起来
            //消费者只能绑定一个队列吗！
            channel.queueBind(queueName, exchangeName, routingKey);
            //声明交换器和队列的目的：https://blog.csdn.net/qq_24598601/article/details/105756515
        while(true) {
            //消费消息
            boolean autoAck = false;
            //标识当前消费者吗？
            String consumerTag = "";
            //基础消费
            //？不断的进行消费 那是任意一个消费请求接收到消息 然后进行处理吗 那其他的了
            //所以具体消费与服务器是怎么进行的了?
            channel.basicConsume(queueName, autoAck, consumerTag, new DefaultConsumer(channel) {
                //接收到消息的回调处理
                @Override
                public void handleDelivery(String consumerTag,
                                           Envelope envelope,//？
                                           AMQP.BasicProperties properties,
                                           byte[] body) throws IOException {
                    String routingKey = envelope.getRoutingKey();
                    String contentType = properties.getContentType();
                    System.out.println("消费的路由键：" + routingKey);
                    //?
                    System.out.println("消费的内容类型：" + contentType);
                    //ack确认消息
                    long deliveryTag = envelope.getDeliveryTag();

                    //deliveryTag（唯一标识 ID）：当一个消费者向 RabbitMQ 注册后，会建立起一个 Channel ，RabbitMQ 会用 basic.deliver 方法向消费者推送消息，这个方法携带了一个 delivery tag， 它代表了 RabbitMQ 向该 Channel 投递的这条消息的唯一标识 ID，是一个单调递增的正整数，delivery tag 的范围仅限于 Channel
                    //multiple：为了减少网络流量，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
                    channel.basicAck(deliveryTag, false);
//                    basicNack方法需要传递三个参数
//
//                    deliveryTag（唯一标识 ID）：上面已经解释了。
//                    multiple：上面已经解释了。
//                    requeue： true ：重回队列，false ：丢弃，我们在nack方法中必须设置 false，否则重发没有意义。
                    System.out.println("消费的消息体内容：");
                    String bodyStr = new String(body, "UTF-8");
                    System.out.println(bodyStr);
                }
            });
        }
    }
}
