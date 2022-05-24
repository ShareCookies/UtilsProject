package com.china.hcg.io.tcp.nio;

/**
 * @description https://blog.csdn.net/u010889616/article/details/80686236
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;


public class NIOTcpServer
{
    /**
     * 选择器
     */
    private Selector selector;

    /**
     * 通道
     */
    ServerSocketChannel serverSocketChannel;

//    void tcpTest() throws Exception{
//        //建立服务器
//        ServerSocket server = new ServerSocket(6677);
//        //阻塞式接收Socket
//        Socket client = server.accept();
//
//        //新建客户端Socket
//        //Socket client = new Socket("localhost",6677);
//    }
    public void initServer(int port) throws IOException
    {
        //1.1 打开一个通道
            //即调用的是new ServerSocketChannelImpl(this)
//                super(var1);
//                this.fd = Net.serverSocket(true);
                        //IOUtil.newFD(socket0(isIPv6Available(), var0, true, fastLoopback));
                            //private static native int socket0(boolean var0, boolean var1, boolean var2, boolean var3);
                            //通过本地方法调用操作系统socketApi，socket函数对应于普通文件的打开操作。socket()用于创建一个socket描述符（socket descriptor），它唯一标识一个socket。
                                //正如可以给fopen的传入不同参数值，以打开不同的文件。创建socket的时候，也可以指定不同的参数创建不同的socket描述符，socket函数的三个参数分别为：
                                //domain：即协议域。type：指定socket类型。protocol：指定协议。
                            //newFD 把socket描述符保存起来
//                this.fdVal = IOUtil.fdVal(this.fd);//？
//                this.state = 0;

        serverSocketChannel = ServerSocketChannel.open();

        //通道设置非阻塞
            //public static native void configureBlocking(FileDescriptor var0, boolean var1) throws IOException;
            //根据文件描述，通过本地方法调用把对应的socket设为非阻塞
        serverSocketChannel.configureBlocking(false);

        //1.2 绑定端口号
            //当我们调用socket创建一个socket时，返回的socket描述字它存在于协议族（address family，AF_XXX）空间中？，但没有一个具体的地址。
            //如果想要给它赋值一个地址，就必须调用bind()函数，否则就当调用connect()、listen()时系统会自动随机分配一个端口。

            //getImpl().bind(epoint.getAddress(), epoint.getPort());//接着就调本地方法 //bind()函数把一个地址族中的特定地址赋给socket。
            //getImpl().listen(backlog);//接着就调本地方法 //如果作为一个服务器，在调用socket()、bind()之后就会调用listen()来监听这个socket， //如果客户端这时调用connect()发出连接请求，服务器端就会接收到这个请求。
        serverSocketChannel.socket().bind(new InetSocketAddress("localhost", port));

        //2.1 创建选择器
        this.selector = Selector.open();
        //2.2 将通道注册到选择器上 // 所以注册具体是做了什么工作内容了？
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void listen() throws IOException
    {
        System.out.println("server started succeed!");

        while (true)
        {
            //2.3  通过选择器监听事件
                //select()阻塞到至少有一个通道在你注册的事件上就绪了。
                    // * Selects a set of keys whose corresponding channels are ready for I/O operations.
                    // 具体做了什么？调本地poll方法，该方法做了什么
            selector.select();
            //2.4 获取准备就绪的通道
            Iterator<SelectionKey> ite = selector.selectedKeys().iterator();
            while (ite.hasNext())
            {
                SelectionKey key = ite.next();
                // Tests whether this key's channel is ready to accept a new socket connection.
                // 该key对应的通道是否准备好接受新连接了
                // 一个端口能接收很多个请求，那这里为什么要去管这个通道是否准备好了？
                if (key.isAcceptable())
                {
                    //Accepts a connection made to this channel's socket.
                    //通过 ServerSocketChannel.accept() 方法监听新进来的连接。
                    // 当 accept()方法返回的时候,它返回一个包含新进来的连接的 SocketChannel。因此, accept()方法会一直阻塞到有新连接到达。


                    //在接收是生成新的通道？那旧的了
                    // 为什么要用这个通道？
                    SocketChannel channel = serverSocketChannel.accept();
                    channel.configureBlocking(false);
                    // 这里为什么要在注册了？旧key了
                    channel.register(selector, SelectionKey.OP_READ);
                }
                else if (key.isReadable())
                {
                    recvAndReply(key);
                }
                ite.remove();
            }
        }
    }

    public void recvAndReply(SelectionKey key) throws IOException
    {
        // 通过key获取到对应的通道
        SocketChannel channel = (SocketChannel) key.channel();

        // 把通道内容读入缓存，
        // 话说http的内容为什么可以直接读入了，底层是怎么实现的了，网络突然中断了？
        ByteBuffer buffer = ByteBuffer.allocate(256);
        int i = channel.read(buffer);
        if (i != -1)
        {
            String msg = new String(buffer.array()).trim();
            System.out.println("NIO server received message =  " + msg);
            msg += "服务端口的答复";

            channel.write(ByteBuffer.wrap( msg.getBytes()));
        }
        else
        {
            //为什么要关了？可以一直开着吗
            channel.close();
        }
    }


    public static void main(String[] args) throws IOException
    {
        NIOTcpServer server = new NIOTcpServer();
        server.initServer(8080);
        server.listen();
    }

}
