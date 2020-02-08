package com.feng.netty.third.chat.server;


import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        group.forEach(ch -> {
            if (channel != ch) {
                ch.writeAndFlush(channel.remoteAddress() + "发送的消息：" + msg + "\n");
            } else {
                ch.writeAndFlush("[自己]: " + msg + "\n");
            }
        });

    }

    /**
     * 连接建立
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //组播
        group.writeAndFlush("[服务器消息-上线通知] - " + channel.remoteAddress() + "加入\n");
        //写在组播后，防止给刚加入的用户发送自己已加入
        group.add(channel);
        System.out.println("群聊人数："+group.size());
    }

    /**
     * 断开连接
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        group.writeAndFlush("[服务器消息-下线通知] - " + channel.remoteAddress() + "退出\n");
        //自动进行，可省略
        group.remove(channel);
        System.out.println("群聊人数："+group.size());

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "上线\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "下线 \n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
