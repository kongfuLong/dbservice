package com.test.NIO;

import com.sun.corba.se.spi.orbutil.threadpool.ThreadPool;
import com.test.Worker;
import org.apache.commons.net.SocketClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

/**
 * Created by rcl on 2016/2/23.
 */
public class Client {

    private   Selector selector;
    private  SocketChannel socketChannel;

    private final int ByteBufferSize = 8192;

    private ByteBuffer byteBuffer = ByteBuffer.allocate(ByteBufferSize);

    private Map<String,SocketChannel> channelMap = new HashMap<>();


    public void handle(SelectionKey selectionKey) throws IOException {
        //判断状态 并作出相应的处理
                       /*selectionKey.isAcceptable()
                       selectionKey.isConnectable()
                       selectionKey.isReadable()
                       selectionKey.isValid()
                       selectionKey.isWritable()*/
        SocketChannel channel = (SocketChannel) selectionKey.channel();


        if(selectionKey.isAcceptable()){
            System.out.println("isAcceptable");
        }
        if(selectionKey.isConnectable()){
            System.out.println("isConnectable "+System.currentTimeMillis());
            // 如果正在连接，则完成连接
            if(channel.isConnectionPending()){
                channel.finishConnect();
            }
            // 设置成非阻塞
            channel.configureBlocking(false);
            //在这里可以给服务端发送信息哦
            channel.write(ByteBuffer.wrap(new String("cName:client1").getBytes()));
            //在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限。
            channel.register(this.selector, SelectionKey.OP_READ);

        }
        if(selectionKey.isReadable()){
            System.out.println("isReadable");
           String msg = read(channel);
            if(msg.split(":").length==2 && msg.split(":")[0].equals("sName")){
                channelMap.put(msg.split(":")[1],channel);
            }
            System.out.println("客户端收到信息："+msg);
        }
        if(selectionKey.isValid()){
            System.out.println("isValid");
        }
        if(selectionKey.isWritable()){
            System.out.println("isWritable");
        }
    }
    /**
     * 处理读取客户端发来的信息 的事件
     * @param channel
     * @throws IOException
     */
    public String read(SocketChannel channel) throws IOException{
        // 服务器可读取消息:得到事件发生的Socket通道
        // 创建读取的缓冲区
        byte[] bytes;
        String msg = "";
        channel.read(byteBuffer);
        bytes = new byte[byteBuffer.position()];
        byteBuffer.flip();//指针重新偏移
        byteBuffer.get(bytes);
        byteBuffer.clear();//清除
        msg += new String(bytes,"utf-8");
        return msg;
      //  ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
       // channel.write(outBuffer);// 将消息回送给客户端
    }

    public void init(){
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            InetSocketAddress address = new InetSocketAddress(InetAddress.getLocalHost(),8888);
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(address);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10000);
                        //Scanner sc = new Scanner(System.in);
                        //启动线程只是为了模拟 主动发消息的动作 同理服务端也可以
                        String msg = "你好服务器";
                        while(true){
                            Thread.sleep(3000);
                            channelMap.get("server1").write(ByteBuffer.wrap(msg.getBytes()));
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            System.out.println("客户端启动完毕");

            //new sendMsg(selector).run();
            autoRun();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void autoRun() throws IOException {
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(selector.select()>0){
                Set selectionKeys = selector.selectedKeys();

                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while(iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    handle(selectionKey);
                }
            }
        }
    }
}
