package com.zjx.bilinio.chat;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

//自定义一个服务器端业务处理类
public class ChatServeHandler extends SimpleChannelInboundHandler<String>{

    public static List<Channel> channels = new ArrayList<Channel>();

    //通道就绪
    public void channelActive(ChannelHandlerContext ctx){
        Channel inChannel=ctx.channel();
        channels.add(inChannel);
        System.out.println("[Server]:"+inChannel.remoteAddress().toString().substring(1)+"上线");
    }

    //通道未就绪
    public void channelInactive(ChannelHandlerContext ctx){
        Channel inChannel=ctx.channel();
        channels.remove(inChannel);
        System.out.println("[Server]:"+inChannel.remoteAddress().toString().substring(1)+"离线");
    }

    //读取数据
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String s) throws Exception {
        Channel inChannel=ctx.channel();
        for (Channel channel:channels){
            if(channel!=inChannel){
                channel.writeAndFlush("["+inChannel.remoteAddress().toString().substring(1)+"]说："+s+"\n");
            }
        }
    }
/*    public void channelRead0(ChannelHandlerContext ctx,String s){

    }*/

    //异常发生事件处理
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable t){
        ctx.close();
    }

}
