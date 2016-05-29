package com.test;

import com.beans.DbTask;
import com.interfaces.QshDbSuperInterface;
import com.test.NIO.Client;
import com.tools.SpringDbHelper;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rcl on 2016/1/6.
 */
public class TestMain {

    public static void main(String[] args) throws Exception {
       /* ClassPathXmlApplicationContext  context = new ClassPathXmlApplicationContext("server.xml");
        //context.start();
        QshDbSuperInterface qshDbSuperInterface = context.getBean(QshDbSuperInterface.class);
       // Annotation[] an = SpringDbHelper.class.getAnnotations();
        List<DbTask> list = new ArrayList<>();
        DbTask dbTask = new DbTask();
        dbTask.setSql("update tb_raffle set used_number=3 where raffle_code=15");
        DbTask dbTask1 = new DbTask();
        dbTask1.setSql("update tb_raffle set used_number='kk' where raffle_code=14");
        list.add(dbTask);
        list.add(dbTask1);
        qshDbSuperInterface.batchUpdate(list);
        System.out.println("ok");*/
        new Client().init();

    }

}
