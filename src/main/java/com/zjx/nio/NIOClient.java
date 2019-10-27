package com.zjx.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOClient {
    public static void main(String[] args) throws Exception{
        Selector selector = Selector.open();
        //获取客户端SocketChannel
        SocketChannel channel = SocketChannel.open();
        //设置非阻塞
        channel.configureBlocking(false);
        //判断连接是否建立完成
        if (channel.connect(new InetSocketAddress("127.0.0.1",6789))){
            System.out.println("连接建立完成");
            //发送消息
            String message = "hello zzb";
            ByteBuffer byteBuffer = ByteBuffer.allocate(message.getBytes().length);

            byteBuffer.put(message.getBytes());
            byteBuffer.flip();
            channel.write(byteBuffer);

        }else{
            System.out.println("连接建立中");
            //给通道添加监听建立完成的事件
            channel.register(selector, SelectionKey.OP_CONNECT);
        }
        while (true){
            //阻塞1秒，获取事件
            int num = selector.select(1000);
            //获取该兴趣的事件集合
            Set<SelectionKey> keys = selector.selectedKeys();

            Iterator<SelectionKey> it = keys.iterator();
            //遍历事件
            while (it.hasNext()){
                SelectionKey key = it.next();
                //是否连接建立完成
                if (key.isConnectable()){
                    //获取通道
                    SocketChannel sc = (SocketChannel) key.channel();

                    //判断是否正在进行连接
                    if (sc.isConnectionPending()){
                        sc.finishConnect();
                    }
                    //发送消息
                    String message = "hello yidiankt";
                    ByteBuffer byteBuffer = ByteBuffer.allocate(message.getBytes().length);

                    byteBuffer.put(message.getBytes());
                    byteBuffer.flip();
                    channel.write(byteBuffer);
                }else if(key.isReadable()){
                    //有数据可以读取
                    //获取客户端的channel
                    SocketChannel channel1 = (SocketChannel)key.channel();


                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int len = channel1.read(byteBuffer);

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
