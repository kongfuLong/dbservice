package com.annotations;

import java.lang.annotation.*;

/**
 * Created by rcl on 2015/10/9.
 *service端
 * 自定义rmi注解
 *
 * @Target 表示该注解用于什么地方，可能的 ElemenetType 参数包括：
ElemenetType.CONSTRUCTOR   构造器声明
ElemenetType.FIELD   域声明（包括 enum 实例）
ElemenetType.LOCAL_VARIABLE   局部变量声明
ElemenetType.METHOD   方法声明
ElemenetType.PACKAGE   包声明
ElemenetType.PARAMETER   参数声明
ElemenetType.TYPE   类，接口（包括注解类型）或enum声明

 @Retention 表示在什么级别保存该注解信息。可选的 RetentionPolicy 参数包括：
 RetentionPolicy.SOURCE   注解将被编译器丢弃
 RetentionPolicy.CLASS   注解在class文件中可用，但会被VM丢弃
 RetentionPolicy.RUNTIME   VM将在运行期也保留注释，因此可以通过反射机制读取注解的信息。
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RmiService {
    Class<?> value() default Object.class;
    String version() default "";
    String serverType();
    String name() default "";//定义发布接口名称 默认使用接口名称
}
