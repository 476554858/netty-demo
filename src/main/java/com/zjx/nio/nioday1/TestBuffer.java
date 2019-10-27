package com.zjx.nio.nioday1;

import org.junit.Test;

import java.nio.ByteBuffer;

public class TestBuffer {

    @Test
    public void test2(){
        String str = "adbc";
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.put(str.getBytes());

        buf.flip();
        byte[] dst = new byte[buf.limit()];
        buf.get(dst,0,2);
        System.out.println(new String(dst,0,2));
        System.out.println(buf.position());

        //mark():标记
        buf.mark();

        buf.get(dst,2,2);
        System.out.println(new String(dst,2,2));
        System.out.println(buf.position());

        buf.reset();
        System.out.println(buf.position());

    }

    @Test
    public void test1(){
        String str = "abcde";

        //1.分配一个指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        System.out.println("==========allocate");
        System.out.println(buf.position());//0
        System.out.println(buf.limit());//1024
        System.out.println(buf.capacity());//1024

        //2.利用put()存入数据到缓冲区中
        buf.put(str.getBytes());

        System.out.println("==========put");
        System.out.println(buf.position());//5
        System.out.println(buf.limit());//1024
        System.out.println(buf.capacity());//1024

        //3.切换到读取数据模式
        buf.flip();
        System.out.println("==========flip");
        System.out.println(buf.position());//0
        System.out.println(buf.limit());//5
        System.out.println(buf.capacity());//1024

        //4.利用get()读取缓冲区中的数据
        byte[] dst = new byte[buf.limit()];
        buf.get(dst);
        System.out.println(new String(dst,0,dst.length));

        System.out.println("============get()");
        System.out.println(buf.position());//5
        System.out.println(buf.limit());//5
        System.out.println(buf.capacity());//1024

        //5. rewind() 可重复读
        buf.rewind();
        System.out.println("============rewind()");
        System.out.println(buf.position());//0
        System.out.println(buf.limit());//5
        System.out.println(buf.capacity());//1024

        //6.clear() :清空缓冲区，但是缓冲区中的数据依然存，但是处于‘被遗忘’状态
        buf.clear();

        System.out.println("===============clear===========");
        System.out.println(buf.position());//0
        System.out.println(buf.limit());//1024
        System.out.println(buf.capacity());//1024

        System.out.println((char) buf.get());
    }
}
