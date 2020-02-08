package com.feng.netty.fourth;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class MyInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //IdleStateHandler 在一段时间里没有读/写/读写操作 触发
        //writerIdleTimeSeconds 一段时间服务器没有写操作
        //allIdleTimeSeconds 一段时间既没有读也没有写
        pipeline.addLast(new IdleStateHandler(5,10,15, TimeUnit.SECONDS));
        pipeline.addLast(new MyServerHandler());
    }
}
