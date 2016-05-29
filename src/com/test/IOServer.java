package com.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rcl on 2016/2/5.
 */
public class IOServer {

    private static List<Worker> workers = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(8888);

        while (true){
            SocketWapper socketWapper = new SocketWapper(serverSocket.accept());
            workers.add(new Worker(socketWapper,workers.size()+""));
        }
    }

    public static void interruptAll() throws IOException {
        for(Worker worker : workers){
            worker.inttrupt();
        }
    }
}
