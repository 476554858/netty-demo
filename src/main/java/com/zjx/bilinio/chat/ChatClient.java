package com.zjx.bilinio.chat;

import com.zjx.bilinio.basic.NettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

//聊天程序客户端
public class ChatClient {
    private final String host;//服务器IP地址
    private final int port;//服务器端口号

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run(){
        //1.创建一个线程组
        EventLoopGroup group=new NioEventLoopGroup();
        try {
            //2.创建客户端的启动助手，完成相关配置
            Bootstrap b=new Bootstrap();
            b.group(group) //3.设置线程组
                    .channel(NioSocketChannel.class)  //4.设置客户端通道的实现类
                    .handler(new ChannelInitializer<SocketChannel>() { //5.创建一个初始胡对象
                        @Override//6.往pipeline通道中添加自定义的handler
                        protected void initChannel(SocketChannel sc) throws Exception {
                            ChannelPipeline pipeline = sc.pipeline();
                            //往pipeline链中添加一个解码器
                            pipeline.addLast("decoder",new StringDecoder());
                            //往pipeline链中添加一个编码器
                            pipeline.addLast("encoder",new StringEncoder());
                            //往pipeline链中添加自定义的handler业务处理类;
                            pipeline.addLast(new ChatClientHandler());
                        }
                    });

            //7.启动客户端去连接服务器端 connect方法是异步的 sync方法是同步非阻塞的
            ChannelFuture cf = b.connect("127.0.0.1",9999).sync();
            Channel channel = cf.channel();
            System.out.println("------------"+channel.localAddress().toString().substring(1)+"---------");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                String msg=scanner.nextLine();
                channel.writeAndFlush(msg+"\r\n");
            }
            //8.关闭连接（异步非阻塞）
            cf.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new ChatClient("127.0.0.1",9999).run();
    }
}
