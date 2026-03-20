package com.smartpark.app.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 日志切面
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(* com.smartpark.app.controller..*.*(..))")
    public void controllerPointcut() {}

    @Around("controllerPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        String methodName = point.getSignature().getName();
        String className = point.getTarget().getClass().getSimpleName();

        log.debug("方法执行开始 - {}.{}", className, methodName);

        long startTime = System.currentTimeMillis();
        Object result = point.proceed();
        long duration = System.currentTimeMillis() - startTime;

        log.debug("方法执行结束 - {}.{}, 耗时: {}ms", className, methodName, duration);

        return result;
    }
}
