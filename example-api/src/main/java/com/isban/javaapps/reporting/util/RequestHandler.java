package com.isban.javaapps.reporting.util;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

import com.isban.javaapps.reporting.service.aspect.RequestInfo;
import com.isban.javaapps.reporting.service.exception.UnauthorizedException;

@Service
public class RequestHandler {
    
    private static final String REQUEST_INFO_KEY = "requestInfo";
    
    
    public RequestInfo getRequestInfoOrCreateNew(HttpServletRequest request) {
        RequestInfo requestInfo = getRequestInfo(request);
        if (requestInfo == null) {
            requestInfo = new RequestInfo();
        }
        return requestInfo;
    }
    
    public void saveRequestInfo(HttpServletRequest request, RequestInfo requestInfo) {
        request.setAttribute(REQUEST_INFO_KEY, requestInfo);
    }
    
    private RequestInfo getRequestInfo(HttpServletRequest request) {
        return (RequestInfo) request.getAttribute(REQUEST_INFO_KEY);
    }
    
    public HttpServletRequest getRequest(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args != null) {
            for (Object arg : args) {
                if (arg instanceof HttpServletRequest) {
                    return (HttpServletRequest) arg;
                }
            }
        }
        return null;
    }
    
    public String getToken(HttpServletRequest request) {
        String value = request.getHeader("Authorization");
        if (StringUtils.isBlank(value)) {
            throw new UnauthorizedException("Token not found");
        }
        String valueTrimmed = value.trim();
        int index = valueTrimmed.indexOf(" ");
        if (index == -1) {
            throw new UnauthorizedException("Invalid Token");
        }
        String tokenType = valueTrimmed.substring(0, index);
        if (!"Bearer".equals(tokenType)) {
            throw new UnauthorizedException(
                    MessageFormat.format("Token {0} not supported",
                            tokenType));
        }
        String token = valueTrimmed.substring(index + 1);
        return token;
    }
    
}
