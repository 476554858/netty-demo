package com.zjx.bio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    public static void main(String[] args) throws Exception{
        int port = 7799;
        ServerSocket server = new ServerSocket(port);
        System.out.println("==等待客户端连接==");
        Socket socket = server.accept();
        System.out.println("==建立连接==");

        InputStream ins = socket.getInputStream();

        byte[] content = new byte[1024];
        int len = ins.read(content);

        System.out.println(new String(content,0,len));

        ins.close();

        socket.close();
        server.close();
    }
}
