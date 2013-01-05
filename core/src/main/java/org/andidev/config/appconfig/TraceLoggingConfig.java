package org.andidev.config.appconfig;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@Slf4j
@Aspect
public class TraceLoggingConfig {

    @Before(
    value = "execution(public * org.andidev..controller..*.*(..))",
    argNames = "joinPoint")
    public void before(JoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String arguments = Arrays.asList(joinPoint.getArgs()).size() > 0 ? Arrays.asList(joinPoint.getArgs()).toString(): "";
        log.debug(className + "." + methodName + "(" + arguments + ")");
    }

    @AfterReturning(
    pointcut = "execution(public * org.andidev..controller..*.*(..))",
    returning = "returnValue")
    public void afterReturning(Object returnValue) throws Throwable {
        log.debug("return " + returnValue);
    }
}