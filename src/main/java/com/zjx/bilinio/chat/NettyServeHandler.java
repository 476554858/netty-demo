package com.zjx.bilinio.chat;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.List;

public class NettyServeHandler extends SimpleChannelInboundHandler<String>{
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {

    }
    public static List<Channel> channels = new ArrayList<Channel>();

    //通道就绪
    public void channelActive(ChannelHandlerContext ctx){

    }

    public void channelInactive(ChannelHandlerContext ctx){

    }

    public void channelRead0(ChannelHandlerContext ctx,String s){

    }
}
