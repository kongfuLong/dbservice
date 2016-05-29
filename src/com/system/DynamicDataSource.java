package com.system;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.tools.ReadWriteProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by rcl on 2016/1/6.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private String masterProName;

    private String[] slavesProName;

    final private String MASTER = SwitchDataBase.MASTER;

    final private String SLAVE = "slave";

    @Autowired
    private ReadWriteProperties readWriteProperties;

    public void setMasterProName(String masterProName) {
        this.masterProName = masterProName;
    }

    public void setSlavesProName(String[] slavesProName) {
        this.slavesProName = slavesProName;
    }

    @PostConstruct
    public void init(){
        System.out.println("------------------数据源初始化");
        Map<Object,Object> targetDataSources = new HashMap<>();
        Properties properties = readWriteProperties.getProperties(masterProName + ".properties");
        DataSource dataSource = null;
        //添加主写库
        try {
            dataSource = DruidDataSourceFactory.createDataSource(properties);
            targetDataSources.put(MASTER,dataSource);
            this.setDefaultTargetDataSource(dataSource);
            System.out.println("主数据源" + masterProName + "初始化成功..");
        } catch (Exception e) {
            System.out.println("主数据源" + masterProName + "初始化失败");
        }

        //添加从读库
        if(slavesProName!=null && slavesProName.length>0){
            for(int i=0;i<slavesProName.length;i++){
                try {
                    properties = readWriteProperties.getProperties(slavesProName[i] + ".properties");
                    dataSource = DruidDataSourceFactory.createDataSource(properties);
                    targetDataSources.put(SLAVE+i,dataSource);
                    SwitchDataBase.addSlave(SLAVE+i);
                    System.out.println("从数据源" + slavesProName[i] + "初始化成功..");
                } catch (Exception e) {
                    System.out.println("从数据源" + slavesProName[i] + "初始化失败");
                }
            }
        }
        this.setTargetDataSources(targetDataSources);
    }

    /**
     *
     * @return  java.util.Map<java.lang.Object,java.lang.Object> targetDataSources 数据源所对应的key
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return SwitchDataBase.getDataSource();
    }
}
