package com.zjx.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static void main(String[] args) throws Exception{
        int port = 6789;
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //设置非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //获取socket绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        //注册selector到channel上，并且制定感兴趣的事情
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("[server]"+port+" starting");

        while (true){
            //获取selector事件，阻塞的方法，阻塞1秒
            selector.select(1000);
            //获取通道上响应的事件
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();

            while (it.hasNext()){
                SelectionKey key = it.next();
                //处理不同的事件
                //连接是否还有效
                if(key.isAcceptable()){
                    //客户端和服务器连接建立完成
                    ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                    //获取客户端socket通道
                    SocketChannel clientChannel = ssc.accept();
                    //设置非阻塞模式
                    clientChannel.configureBlocking(false);
                    //注册selector和感兴趣的事件
                    clientChannel.register(selector,SelectionKey.OP_READ);
                }else if(key.isReadable()){
                    //有数据可以读取
                    //获取客户端的channel
                    SocketChannel channel = (SocketChannel)key.channel();


                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int len = channel.read(byteBuffer);

                    if(len>0){
                        //获取客户端数据
                        byteBuffer.flip();
                        byte[] datas = new byte[byteBuffer.remaining()];
                        byteBuffer.get(datas);

                        String content = new String(datas,"utf-8");
                        System.out.println("[server]"+content);
                    }

                }

            }
        }
    }
}
