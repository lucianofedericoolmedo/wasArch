package com.isban.javaapps.reporting.service.rest;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.io.Files;
import com.isban.javaapps.reporting.pagination.PageResponse;
import com.isban.javaapps.reporting.service.RechazoJoinService;
import com.isban.javaapps.reporting.util.RestResponseHandler;

@Transactional
@Service
@Path("/rechazos-join")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RechazoJoinRestService {

	@Autowired
    protected RestResponseHandler responseHandler;
    
	@Autowired
	private RechazoJoinService rechazoJoinService;
	
	@GET
	@Path("filtro")
    public Response getFiltros(@Context HttpServletRequest request) {
        try {
            Object result = rechazoJoinService.getFiltros();
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }
	
	@GET
    public Response get(@Context HttpServletRequest request,
    		@QueryParam("page") Integer page,
    		@QueryParam("pageSize") Integer pageSize,
    		@QueryParam("cod_sist_origen") String codigoSistemaOrigen,
    		@QueryParam("content") String contenido,
    		@QueryParam("date") String fecha) {
        try {
        	PageResponse<ObjectNode> result = rechazoJoinService.getSearch(codigoSistemaOrigen, contenido, fecha, page, pageSize);
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }
	
	@GET
    @Path("file")
    public Response fileGeneration(@Context HttpServletRequest request,
    		@QueryParam("filtro") String filtro) {
        try {
        	File generatedFile = rechazoJoinService.fileGeneration(filtro);
            return responseHandler.buildSuccessResponse(new StreamingOutput() {
                @Override
                public void write(OutputStream output) throws IOException, WebApplicationException {
                    byte[] out = Files.toByteArray(generatedFile);
                    output.write(out);
                }
            });
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }
	
}
