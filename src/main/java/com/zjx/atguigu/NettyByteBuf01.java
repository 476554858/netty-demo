package com.zjx.atguigu;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyByteBuf01 {

    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.buffer(10);
        for(int i = 0; i < 10; i++){
            byteBuf.writeByte(i);
        }

        System.out.println("capacity" + byteBuf.capacity());

        for(int i = 0; i < byteBuf.capacity(); i++){
            System.out.println(byteBuf.readByte());
        }
        System.out.println("执行完毕");
    }
}
