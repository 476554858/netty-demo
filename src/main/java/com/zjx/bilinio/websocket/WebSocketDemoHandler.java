package com.zjx.bilinio.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.CharsetUtil;


public class WebSocketDemoHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{


    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        System.out.println("读取客户端的内容：" + textWebSocketFrame.text());

        TextWebSocketFrame resp = new TextWebSocketFrame("嗯");

        channelHandlerContext.writeAndFlush(resp);
    }


    //异常发生事件处理
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable t){
        ctx.close();
    }
}
