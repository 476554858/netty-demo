package com.zjx.bilinio.basic;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

//客户端业务处理类
public class NettyClientHandler extends ChannelInboundHandlerAdapter{
    private ChannelHandlerContext ctx;

    private Object response;

    private ChannelPromise promise;

    //通道就绪事件
    public void channelActive(ChannelHandlerContext ctx){
        this.ctx = ctx;
        System.out.println("Client:"+ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("老板，还钱吧", CharsetUtil.UTF_8));
    }
    //读取数据事件
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        ByteBuf buf=(ByteBuf)msg;
//        System.out.println("服务器端发来的消息"+buf.toString(CharsetUtil.UTF_8));
        response = buf.toString(CharsetUtil.UTF_8);
        promise.setSuccess();
    }

    public synchronized ChannelPromise sendMessage(Object message) {
        while (ctx == null) {
            try {
                TimeUnit.MILLISECONDS.sleep(1);
                //logger.error("等待ChannelHandlerContext实例化");
            } catch (InterruptedException e) {
            }
        }
        promise = ctx.newPromise();
        ctx.writeAndFlush(message);
        return promise;
    }

    public Object getResponse(){
        return response;
    }
}
