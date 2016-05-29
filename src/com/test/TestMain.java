package com.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Created by rcl on 2016/1/6.
 */
public class TestMain {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext  context = new ClassPathXmlApplicationContext("server.xml");
      context.start();
    }

}
