package com.sky.annotation;


import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    OperationType value();

    // 注解中限定类型：基本数据类型、String、Class类型、枚举、数组、注解
//    int a() default 1;
//    String s() default "s";
//    Class<?> type();
//    String[] ss();
}
