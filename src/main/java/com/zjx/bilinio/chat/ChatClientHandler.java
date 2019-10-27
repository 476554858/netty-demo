package com.zjx.bilinio.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ChatClientHandler extends SimpleChannelInboundHandler<String>{
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {

    }

    protected void channelRead0(ChannelHandlerContext ctx,String s){

    }
}
