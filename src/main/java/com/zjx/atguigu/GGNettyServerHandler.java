package com.zjx.atguigu;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

public class GGNettyServerHandler extends ChannelHandlerAdapter {
    //读取数据事件
    public void channelRead(ChannelHandlerContext ctx, Object msg){
       /*  System.out.println("Server:" + ctx);
        ByteBuf buf=(ByteBuf) msg;
        System.out.println("客户端发来的消息：" + buf.toString(CharsetUtil.UTF_8));
        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline();  //pipeline 本质是一个双向链表, 入栈出栈
        System.out.println("客户端地址：" + channel.remoteAddress());*/

       //比如这里我们有一个非常耗时长的业务 -> 一步执行 -> 提交该channel 对应的 NIOEventLoop 的 taskQueue中

        //解决方案1 用户程序自定义的普通任务
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("开始执行耗时任务1========");
                    System.out.println("执行任务1线程" + Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("耗时业务结束1===========");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("开始执行耗时任务2========");
                    System.out.println("执行任务2线程" + Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println("耗时业务结束2===========");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //用户自定义定时任务 ->> 该任务是提交到scheduleTaskQueue中
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("开始执行定时任务========");
                    System.out.println("执行定时任务线程" + Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println("定时任务结束===========");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 5, TimeUnit.SECONDS);

        System.out.println("debug===");
    }

    //数据读取完毕
    public void channelReadComplete(ChannelHandlerContext ctx){
        System.out.println("服务器读取线程 " + Thread.currentThread().getName());
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello ,客户端", CharsetUtil.UTF_8));
    }

    //异常发生事件处理
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable t){
        ctx.close();
    }
}
