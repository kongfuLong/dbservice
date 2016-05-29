package com.test;

import java.io.*;
import java.net.Socket;

/**
 * Created by rcl on 2016/2/5.
 */
public class SocketWapper {

    private InputStream inputStream;
    private OutputStream outputStream;
    private Socket socket;

    //服务端
    public SocketWapper(Socket socket) throws IOException {
        this.socket = socket;
        initStream();
    }
    //客户端
    public SocketWapper(String host,int port) throws IOException {
        this.socket = new Socket(host,port);
        socket.setKeepAlive(true);
        initStream();
    }


    public void initStream()throws IOException{
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
    }

    public void inttrupt() throws IOException {
        inputStream.close();
        outputStream.close();
        socket.close();
    }
}
