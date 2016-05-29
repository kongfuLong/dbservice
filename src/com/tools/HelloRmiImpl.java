package com.tools;

import com.annotations.RmiService;
import com.interfaces.HelloRmi;
import org.springframework.stereotype.Service;

/**
 * Created by rcl on 2015/10/9.
 */
@Service
public class HelloRmiImpl implements HelloRmi {
    @Override
    public String say() {
        return "hello gril!! hao are you---------------------";
    }
}
