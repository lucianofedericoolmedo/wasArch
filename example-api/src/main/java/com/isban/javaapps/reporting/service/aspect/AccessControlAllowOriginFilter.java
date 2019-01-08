package com.isban.javaapps.reporting.service.aspect;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.springframework.stereotype.Service;

@Service
public class AccessControlAllowOriginFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        responseContext.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().putSingle("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        responseContext.getHeaders()
                .putSingle("Access-Control-Allow-Headers", "Content-Type, X-Requested-With, Authorization");
        responseContext.getHeaders().putSingle("Access-Control-Allow-Credentials", "true");
    }

}
