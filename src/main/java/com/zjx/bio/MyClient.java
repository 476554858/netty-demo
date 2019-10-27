package com.zjx.bio;

import java.io.OutputStream;
import java.net.Socket;

public class MyClient {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("localhost",7799);
        OutputStream ous = socket.getOutputStream();

        String content = "hello bio";
        ous.write(content.getBytes());

        ous.close();
        socket.close();
    }
}
