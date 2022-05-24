package com.china.hcg.io.nettyStudy;

import io.netty.buffer.ByteBuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * Handles a server-side channel.
 * 处理器是由Netty生成用来处理I/O事件的。
 * 1. DisCardServerHandler 继承自 ChannelHandlerAdapter，这个类实现了ChannelHandler接口，ChannelHandler规定了许多事件处理的接口方法，然后你要实现这些方法。现在仅仅只需要继承ChannelHandlerAdapter类而不是你自己去实现接口方法。
 */
public class DiscardServerHandler extends ChannelHandlerAdapter { // (1)

    /**
     * @description 2. 这里我们覆盖了chanelRead()事件处理方法。每当从客户端收到新的数据时，这个方法会在收到消息时被调用。?
     * //    3.  为了实现DISCARD协议，处理器不得不忽略所有接受到的消息。
     * //    ? ByteBuf是一个引用计数对象，这个对象必须显示地调用release()方法来释放。请记住处理器的职责是释放所有传递到处理器的引用计数对象。通常，channelRead()方法的实现就像下面的这段代码：
     *     try {
     *
     *         // Do something with msg
     *
     *     } finally {
     *
     *         ReferenceCountUtil.release(msg);
     *
     *     }
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        // Discard the received data silently.
        ((ByteBuf) msg).release(); // (3)
    }

//    view sourceprint?

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}