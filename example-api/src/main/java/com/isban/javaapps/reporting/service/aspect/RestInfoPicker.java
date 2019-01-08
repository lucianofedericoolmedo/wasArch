package com.isban.javaapps.reporting.service.aspect;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isban.javaapps.reporting.util.RequestHandler;

@Service
@Aspect
public class RestInfoPicker {
    
    @Autowired
    private RequestHandler requestHandler;
    
    @Around("execution(* com.isban.javaapps.reporting.service.rest..*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    	
        HttpServletRequest request = requestHandler.getRequest(joinPoint);
        if (request != null) {
            RequestInfo requestInfo = requestHandler.getRequestInfoOrCreateNew(request);
            requestInfo.setServiceClass(joinPoint.getSignature().getDeclaringTypeName());
            requestInfo.setServiceMethod(joinPoint.getSignature().getName());
            requestInfo.setParameters(getInvokationParameters(joinPoint));
            requestHandler.saveRequestInfo(request, requestInfo);
        }
        
        Object returnValue = joinPoint.proceed();

        return returnValue;
    }
    
    private List<Object> getInvokationParameters(ProceedingJoinPoint joinPoint) {

        List<Object> parameters = new ArrayList<Object>();
        Object[] args = joinPoint.getArgs();
        if (args != null) {
            for (Object arg : args) {
                if (!(arg instanceof HttpServletRequest)) {
                    parameters.add(arg);
                }
            }
        }
        return parameters;
    }
    
}
