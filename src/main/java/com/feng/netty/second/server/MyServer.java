package com.feng.netty.second.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

public class MyServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //启动服务器的辅助类   也可以使用通道来直接启动服务，但是很麻烦
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workerGroup)
                    //NioServerSocketChannel.class 用于实例化新的通道Channel以接收输入连接
                    .channel(NioServerSocketChannel.class)
                    //根据grop方法，handler针对于bossGroup起作用，childHandler针对于workerGroup
                    .handler(new LoggingHandler())
                    .childHandler(new MySeverInitializer());
            //sync 表示一直等待
            ChannelFuture channelFuture = serverBootstrap.bind(80).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
