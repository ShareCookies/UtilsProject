package com.china.hcg.io.tcp.io;

import java.io.DataInputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.net.*;
import java.nio.channels.ServerSocketChannel;

/**
 * @autor hecaigui
 * @date 2021-8-20
 * @description https://www.cnblogs.com/Scorpicat/p/12059021.html
 */
public class IOTCPServer {
    public static void main(String[] args) throws IOException {
//建立服务器,就是指
//        绑定到指定端口
//        并监听。
        //监听和绑定的作用？
/*
     * Creates a server socket, bound to the specified port. ...
	 创建服务端socket，绑定到指定的端口。
    public ServerSocket(int port) throws IOException {
            this(port, 50, null);
    }
	public ServerSocket(int port, int backlog, InetAddress bindAddr) throws IOException {
        ...
            bind(new InetSocketAddress(bindAddr, port), backlog);
		...
    }
     * Binds the {@code ServerSocket} to a specific address
    public void bind(SocketAddress endpoint, int backlog) throws IOException {
        getImpl().bind(epoint.getAddress(), epoint.getPort());
        getImpl().listen(backlog);
    }
 */
/* impl就是指TwoStacksPlainSocketImpl.java

ServerSocket.java:
     * Creates the socket implementation.
     * 创建socket的实现
     * @throws IOException if creation fails
     * @since 1.4
    void createImpl() throws SocketException {
        ...
            setImpl();
        ...
    }
    private void setImpl() {
        ...
            impl = new SocksSocketImpl();
        ...
    }
 * SOCKS (V4 & V5) TCP socket implementation (RFC 1928).？
SocksSocketImpl.java
      PlainSocketImpl.java
         SocksSocketImpl的父类。
         * Constructs an empty instance.
        PlainSocketImpl() {
            if (useDualStackImpl) {
                impl = new DualStackPlainSocketImpl(exclusiveBind);
            } else {
                impl = new TwoStacksPlainSocketImpl(exclusiveBind);
            }
        }
        protected synchronized void create(boolean stream) throws IOException {
            // impl又对应TwoStacksPlainSocketImpl，
            impl.create(stream);
            // set fd to delegate's fd to be compatible with older releases
            this.fd = impl.fd;
        }
DualStackPlainSocketImpl.java
        This class defines the plain SocketImpl that is used on Windows platforms greater or equal to Windows Vista.
         具体的socket实现类
        native void socketCreate(boolean isServer) throws IOException;
        static native void bind0(int fd, InetAddress localAddress, int localport,
        boolean exclBind)
        static native void listen0(int fd, int backlog) throws IOException;
       附：
            * The file descriptor object for this socket.
            protected FileDescriptor fd;
            public final class FileDescriptor {private int fd;}
 */
        //建立服务器
        ServerSocket server = new ServerSocket(6677);
        //阻塞式接收Socket
        Socket client = server.accept();//？
        //建立连接
        //DataInputStream从流中读取数据，并格式化成指定的基本类型数据
        DataInputStream dis = new DataInputStream(client.getInputStream());
        //接收数据并处理
        Object obj1 = dis.readUTF();
        Object obj2 = dis.readInt();
        Object obj3 = dis.readFloat();
        System.out.println(obj1);
        System.out.println(obj2);
        System.out.println(obj3);
        //关闭流及服务器
        dis.close();
        client.close();
        //server.close()//服务器无需关闭

    }
}