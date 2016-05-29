package com.system;

import java.util.LinkedList;

/**
 * Created by rcl on 2016/1/6.
 * 读写数据库选择器
 */
public class SwitchDataBase {

    public static final String MASTER = "master";

    private static final ThreadLocal contextHolder = new ThreadLocal();

    private static LinkedList<String> slaves = new LinkedList<String>();//从机可能多台 初始化的时候建立好

    public static void addSlave(String slave){
        slaves.add(slave);
    }

    public static void setDataSource(String dataSource) {
        contextHolder.set(dataSource);
    }

    public static void setMaster(){
        setDataSource(MASTER);
    }

    public static void setSlave() {
        //获取一个从机号
        synchronized (slaves) {
            String slaveName = slaves.pollFirst();
            if (slaveName != null) {
                slaves.offerLast(slaveName);//放到最后面去
                slaves.notifyAll();
            } else {
                setDataSource(MASTER);
            }
            setDataSource(slaveName);
        }
    }

    public static String getDataSource() {
        if(contextHolder.get()==null){
            System.out.println("--------数据库保持器取不到,使用默认主库");
            return MASTER;
        }
        return (String) contextHolder.get();
    }

    public static  void clearDataSource() {
        contextHolder.remove();
    }
}
