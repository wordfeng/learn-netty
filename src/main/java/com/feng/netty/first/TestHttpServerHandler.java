package com.feng.netty.first;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 读取客户端发送的请求，并且响应
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        System.out.println(msg.getClass());
        System.out.println(ctx.channel().remoteAddress());

        //fix IOException
        if (!(msg instanceof HttpRequest)) return;
        System.out.println("channelRead0");
        Thread.sleep(8000);//作为配合 请求后使用 lsof -i:8899 查看结果

        HttpRequest httpRequest = (HttpRequest) msg;
        System.out.println("请求方法名：" + httpRequest.method().name());

        URI uri = new URI(httpRequest.uri());
        if ("/favicon.ico".equals(uri.getPath())) {
            System.out.println("滚犊子");
            return;
        }

        //向客户端响应的内容
        ByteBuf content = Unpooled.copiedBuffer("Hi,Netty", CharsetUtil.UTF_8);
        //与Servlet无关，由Netty提供
        //构建响应对象
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK, content);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

        ctx.writeAndFlush(response);
        String text = httpRequest.protocolVersion().text();
        System.out.println(text);
        //标准需要根据http协议版本 判断情况关闭
        ctx.channel().close();
    }

    /**
     * 通道活动状态下执行的方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel Active");
        super.channelActive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel Registered");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel Unregistered ");
        super.channelUnregistered(ctx);
    }

    /**
     * 通道被添加
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel Inactive");
        super.channelInactive(ctx);
    }
}
