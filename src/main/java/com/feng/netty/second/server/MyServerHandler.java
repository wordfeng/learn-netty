package com.feng.netty.second.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * @param ctx 客户端地址等信息
     * @param msg 客户端传来的实际对象
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        try {

            Channel channel = ctx.channel();
            System.out.println(channel.remoteAddress() + ", " + msg);
            channel.writeAndFlush("from server msg: " + UUID.randomUUID());
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * 出现异常执行方法
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
