package com.system;

import com.annotations.DBType;
import com.annotations.RmiService;
import com.beans.Types;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by rcl on 2015/1/6.
 */
@Component
@Aspect
@Order(99)//切面优先级   高于事务执行
public class DbRoutAspect {

    @Pointcut(value = "execution(* com.tools.SpringDbHelper.*(..))")
    public void pointcutClass(){}

    @Before(value = "pointcutClass()" )
    public void selectDb(JoinPoint point){
        DBType dbType = ((MethodSignature)point.getSignature()).getMethod().getAnnotation(DBType.class);
        if(dbType !=null){
            if(dbType.readOnly()){
                SwitchDataBase.setSlave();
            }else{
                SwitchDataBase.setMaster();
            }
        }else{
            SwitchDataBase.setSlave();
        }
    }
}
