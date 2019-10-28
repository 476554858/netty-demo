package com.zjx.bilinio.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ChatClientHandler extends SimpleChannelInboundHandler<String>{
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(s.trim());
    }

   /* protected void channelRead0(ChannelHandlerContext ctx,String s){

    }*/

    //异常发生事件处理
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable t){
        ctx.close();
    }
}
