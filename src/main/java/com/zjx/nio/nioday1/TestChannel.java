package com.zjx.nio.nioday1;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestChannel {
    @Test
    public void test2() throws Exception{
        FileInputStream fis = new FileInputStream("c:/1.jpg");
        FileOutputStream fos = new FileOutputStream("c:/2.jpg");

        //获取通道
        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(1024);

        while (inChannel.read(buf)!=-1){
            buf.flip();
            outChannel.write(buf);
            buf.clear();
        }
        outChannel.close();
        inChannel.close();
        fos.close();
        fis.close();
    }

    //分散和聚集
    @Test
    public void test4()throws Exception{
        RandomAccessFile raf1 = new RandomAccessFile("c:/1.txt","rw");

        //1.获取通道
        FileChannel channel1 = raf1.getChannel();

        //2.分配固定大小的缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);

        //分散读取
        ByteBuffer[] bufs = {buf1,buf2};
        channel1.read(bufs);

        for(ByteBuffer byteBuffer:bufs){
            byteBuffer.flip();
        }

        System.out.println(new String(bufs[0].array(),0,bufs[0].limit()));
        System.out.println("=====================================");
        System.out.println(new String(bufs[1].array(),0,bufs[1].limit()));

        //4.聚集写入
        RandomAccessFile raf2 = new RandomAccessFile("c:/2.txt","rw");
        FileChannel fileChanne2 = raf2.getChannel();
        fileChanne2.write(bufs);
    }

}
