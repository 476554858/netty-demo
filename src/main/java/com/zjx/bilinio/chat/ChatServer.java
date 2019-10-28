package com.zjx.bilinio.chat;

import com.zjx.bilinio.basic.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ChatServer {
    private int port;//服务器端口号

    public ChatServer(int port){
        this.port = port;
    }

    public void run()throws Exception{
        //1.创建一个线程组,接收客户端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //2.创建一个线程组，处理网络操作
        EventLoopGroup workerGroup= new NioEventLoopGroup();
        try {
            //3.创建服务器启动助手来配置参数
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup) //4.设置两个线程组
                    .channel(NioServerSocketChannel.class)  //5.使用NioServerSocketChannel作为服务器通道的实现
                    .option(ChannelOption.SO_BACKLOG,128) //6.设置线程队列中等待连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true) //7.保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {//8.创建一个通道的初始化参数
                        @Override//9.往Pipeline链中添加自定义的handler类
                        protected void initChannel(SocketChannel sc) throws Exception {
                            ChannelPipeline pipeline = sc.pipeline();
                            //往pipeline链中添加一个解码器
                            pipeline.addLast("decoder",new StringDecoder());
                            //往pipeline链中添加一个编码器
                            pipeline.addLast("encoder",new StringEncoder());
                            //往pipeline链中添加自定义的handler业务处理类;
                            pipeline.addLast(new ChatServeHandler());
                        }
                    });
            System.out.println("Netty Chat Server启动.........");
            ChannelFuture cf = b.bind(9999).sync(); //10.绑定端口 bind方法是异步的 sync方法是同步阻塞的
            //11.关闭通道，关闭线程组
            cf.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new ChatServer(9999).run();
    }
}
