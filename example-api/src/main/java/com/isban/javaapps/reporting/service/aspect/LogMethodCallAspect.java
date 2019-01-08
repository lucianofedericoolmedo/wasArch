package com.isban.javaapps.reporting.service.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.isban.javaapps.reporting.util.CustomLoggerFactory;;

@Aspect
@Component
@Scope("prototype")
public class LogMethodCallAspect {

    @Before("@annotation(com.isban.javaapps.reporting.service.annotation.LogMethodCall)")
    public void logExecutionTime(JoinPoint joinPoint) throws Throwable {
        String signatureName = joinPoint.getSignature().getName();
        Object targetObject = joinPoint.getTarget();
        Object[] args = joinPoint.getArgs();
        
        String logMessage = String.format("%s.%s(%s)", 
                targetObject.getClass().getName(),
                signatureName,
                args.toString());
        
        CustomLoggerFactory.getLogger(targetObject).info(logMessage);
    }
    
}