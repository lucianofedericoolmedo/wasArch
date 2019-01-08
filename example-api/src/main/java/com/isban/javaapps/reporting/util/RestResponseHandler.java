package com.isban.javaapps.reporting.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.security.InvalidParameterException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.isban.javaapps.reporting.util.CustomLoggerFactory;
import com.google.common.io.Files;
import com.isban.javaapps.reporting.service.exception.ForbiddenException;
import com.isban.javaapps.reporting.service.exception.InternalServerError;
import com.isban.javaapps.reporting.service.exception.InternalServerErrorFactory;
import com.isban.javaapps.reporting.service.exception.Message;
import com.isban.javaapps.reporting.service.exception.NotFoundException;
import com.isban.javaapps.reporting.service.exception.UnauthorizedException;

/**
 * See http://docs.oracle.com/javaee/6/api/javax/ws/rs/core/Response.Status.html
 *
 */
@Service
public class RestResponseHandler {

    @Autowired
    private InternalServerErrorFactory errorFactory;
    
    public <T> Response buildSuccessResponse(T entity) {
        return buildResponse(entity, Status.OK);
    }
    
    public Response buildFileResponse(File file) {
        return buildResponse(new StreamingOutput() {
            @Override
            public void write(OutputStream output) throws IOException, WebApplicationException {
                byte[] out = Files.toByteArray(file);
                output.write(out);
            }
        }, Status.OK);
    }
    
    public Response buildSuccessResponse(StatusType successStatus) {
        return buildResponse(null, successStatus);
    }

    public Response buildErrorResponse(Exception exc) {
    	return buildErrorResponse(exc, true);
    }

    public Response buildErrorResponse(Exception exc, boolean logException) {
    	
    	if (logException) {
    	    StackTraceElement ste = exc.getStackTrace()[0];
    	    String className = ste.getClassName();
    	    CustomLoggerFactory.getLogger(className).info("Failed! " +  exc.getMessage());
    	}

        if (exc instanceof UnauthorizedException) {
            return this.buildResponse(null, Status.UNAUTHORIZED);
        }
        if (exc instanceof NotFoundException) {
            return this.buildResponse(null, Status.NOT_FOUND);
        }
        if (exc instanceof ForbiddenException) {
            return this.buildResponse(errorFactory.buildInternalError(exc), Status.FORBIDDEN);
        }
        if (exc instanceof InvalidParameterException) {
            return this.buildResponse(null, Status.BAD_REQUEST);
        }
        if (exc instanceof RuntimeException) {
            return this.buildResponse(new Message(exc.getMessage()), Status.BAD_REQUEST);
        }
        InternalServerError internalError = errorFactory.buildInternalError(exc);
        return this.buildResponse(internalError, Status.INTERNAL_SERVER_ERROR);
    }
    
    public <T> Response buildResponse(T entity, StatusType status) {
        return Response
            .status(status)
            .entity(entity)
            .build();
    }

    public Response buildRedirectURI(URI location) {
        return Response.seeOther(location).build();
    }

}
