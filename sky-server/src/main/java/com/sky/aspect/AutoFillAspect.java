package com.sky.aspect;


import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Slf4j
@Aspect   // @Pointcut、@Before、@After、@Around
@Component
public class AutoFillAspect {

    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {
        // * com.sky.mapper.*.*(..)：匹配所有返回值 + mapper包下所有类的所有方法，且参数可以是任意类型、任意个数
    }


    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("开始进行公共字段自动填充");

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        AutoFill annotation = methodSignature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = annotation.value();
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        Object entity = args[0];
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        if (operationType == OperationType.INSERT) {
            try {
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);

                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                setCreateTime.invoke(entity, now);
                setCreateUser.invoke(entity, currentId);
                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentId);
            } catch (Exception e) {
                throw new RuntimeException();
            }
        } else if (operationType == OperationType.UPDATE) {
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentId);
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }

    }
}
