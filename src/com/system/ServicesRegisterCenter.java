package com.system;

import com.annotations.RmiService;
import com.beans.Types;
import com.interfaces.QshDbSuperInterface;
import org.I0Itec.zkclient.ZkClient;
import org.apache.log4j.Logger;
import org.springframework.remoting.rmi.RmiServiceExporter;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

/**
 * Created by rcl on 2016/1/7.
 */
public class ServicesRegisterCenter {

    private final static Logger log = Logger.getLogger(ServicesRegisterCenter.class);

    private String serverName;

    private int dataPort;//数据端口

    private int vistPort;//访问端口

    private String zookeeperUrl;

    private String host;

    private ZkClient zk;

    private String zookeeper_root;

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public void setDataPort(int dataPort) {
        this.dataPort = dataPort;
    }

    public void setVistPort(int vistPort) {
        this.vistPort = vistPort;
    }

    public void setZookeeperUrl(String zookeeperUrl) {
        this.zookeeperUrl = zookeeperUrl;
    }


    private QshDbSuperInterface qshDbSuperInterface;//专用数据库接口

    public void setQshDbSuperInterface(QshDbSuperInterface qshDbSuperInterface) {
        this.qshDbSuperInterface = qshDbSuperInterface;
    }
    public void setZookeeper_root(String zookeeper_root) {
        this.zookeeper_root = zookeeper_root;
    }

    @PostConstruct
    public void init(){
        System.out.println("注册中心初始化~~~~~~");
        zk = new ZkClient(zookeeperUrl);
        InetAddress localAddress = null;
        try {
            localAddress = InetAddress.getLocalHost();
        } catch(UnknownHostException e) {}
        this.host = localAddress.getHostAddress();
        //由于aop对象特殊所以在此写死
        registerRemote(null, qshDbSuperInterface);
    }

    public void registerRemote(RmiService rmiService,Object bean){
        publishInterface(bean,rmiService);
    }

    //发布接口
    private void publishInterface(Object bean,RmiService rmiService){
        Class<?> serviceInterface = bean.getClass().getInterfaces()[0];
        String publishName = "";
        if(rmiService!=null){
            publishName = rmiService.name();
        }
        if("".equals(publishName)){
            publishName = serviceInterface.getSimpleName();//未定义名称则使用接口名
        }
        try {
            //初始化使用代理 远程服务可自动切换
            RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
            rmiServiceExporter.setServiceName(publishName);
            rmiServiceExporter.setServiceInterface(serviceInterface);
            rmiServiceExporter.setService(bean);
            rmiServiceExporter.setServicePort(dataPort);
            rmiServiceExporter.setRegistryPort(vistPort);
            rmiServiceExporter.prepare();
            //写入zookeeper
            String type = null;
            if(rmiService==null){//aop原因导致此处特殊处理
                type = Types.DB;
            }else{
                type = rmiService.serverType();
            }
            registZookeeperAdd(type,publishName,serviceInterface.getSimpleName());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @param publishName   发布名称
     * @param interfaceName  接口名称
     */
    //注册接口到服务中心
    private void registZookeeperAdd(String serverType,String publishName,String interfaceName){
        String rootPath = String.format("%s/%s",zookeeper_root,serverType);
        String interfacePath = String.format("%s/%s",rootPath,interfaceName);
        String allPath = String.format("%s/%s",interfacePath,serverName);


        if(!zk.exists(zookeeper_root)){//根目录是否存在
            zk.createPersistent(zookeeper_root);
        }
        if(!zk.exists(rootPath)){//根服务目录是否存在
            zk.createPersistent(rootPath);
        }
        if(!zk.exists(interfacePath)){//接口目录是否存在
            zk.createPersistent(interfacePath);
        }
        if(zk.exists(allPath)){
            zk.delete(allPath);//数据目录 已存在则删除再生成 达到覆盖目的
        }
        String zkRegisterInfo = String.format("rmi://%s/%s", String.format("%s:%s", host, vistPort), publishName);
        byte[] zkRegisterData = zkRegisterInfo.getBytes();
        zk.createEphemeral(allPath,zkRegisterData);

        log.info(String.format("接口:%s 注册中心:%s 服务地址: %s",
                interfaceName,
                allPath,
                zkRegisterInfo));
    }

}
