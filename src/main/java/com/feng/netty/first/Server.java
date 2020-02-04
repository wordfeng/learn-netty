package com.feng.netty.first;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

    public static void main(String[] args) throws InterruptedException {
        //获取连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //处理连接
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //start server
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //关联两个事件循环组
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    //自己编写的请求处理器
                    .childHandler(new ServerInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
