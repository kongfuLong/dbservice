package com.annotations;

import java.lang.annotation.*;

/**
 * Created by rcl on 2015/11/5.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBType {
    boolean readOnly() default false;
}
