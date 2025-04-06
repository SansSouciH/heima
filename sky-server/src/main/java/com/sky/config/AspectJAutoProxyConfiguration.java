package com.sky.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@Configuration
public class AspectJAutoProxyConfiguration {
    //@EnableAspectJAutoProxy 启用 AOP 代理，让 @Aspect 生效.
    //没有 @EnableAspectJAutoProxy，Spring 默认不会自动启用基于 @Aspect 的 AOP 代理，切面逻辑不会被执行。
}
