package com.system;

import com.annotations.RmiService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.stereotype.Component;



/**
 * Created by rcl on 2016/1/7.
 * 服务bean生产中心
 */
@Component
public class ServiceBeanFactory extends InstantiationAwareBeanPostProcessorAdapter{

    @Autowired
    private ServicesRegisterCenter servicesRegisterCenter;

    //spring对bean加载后(此方法对于使用aop的bean居然拦截不到)
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        RmiService rmiService = bean.getClass().getAnnotation(RmiService.class);
        if(rmiService!=null){
            //1.发布服务接口rmi 2.接口注册到zookeeper注册中心
            servicesRegisterCenter.registerRemote(rmiService,bean);
        }
        return bean;
    }
}
