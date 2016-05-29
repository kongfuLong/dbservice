package com.test;

import java.io.IOException;

/**
 * Created by rcl on 2016/2/6.
 */
public class Worker implements Runnable {

    private SocketWapper socketWapper;
    private String threadName;

    public Worker(SocketWapper socketWapper, String threadName) {
        this.socketWapper = socketWapper;
        this.threadName = threadName;
        this.run();
    }

    @Override
    public void run() {

    }

    public void inttrupt() throws IOException {
        socketWapper.inttrupt();
    }
}
