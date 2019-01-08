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
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.io.Files;
import com.isban.javaapps.reporting.service.CongelamientoService;
import com.isban.javaapps.reporting.util.RestResponseHandler;

@Transactional
@Service
@Path("/congelamientos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CongelamientoRestService {
	
	@Autowired
    protected RestResponseHandler responseHandler;
    
	@Autowired
	private CongelamientoService congelamientoService;

	@GET
	@Path("altair")
    public Response getSearchAltair(@Context HttpServletRequest request,
            @QueryParam("page") String page,
            @QueryParam("rowPage") String pageSize,
            @QueryParam("fechaCongelamiento") String fechaCongelamiento
            ) {
        try {
        	Object result = congelamientoService.getSearch(fechaCongelamiento, true, page, pageSize);
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }
	
	@GET
	@Path("filial")
    public Response getSearchFilial(@Context HttpServletRequest request,
            @QueryParam("page") String page,
            @QueryParam("rowPage") String pageSize,
            @QueryParam("fechaCongelamiento") String fechaCongelamiento
            ) {
        try {
        	Object result = congelamientoService.getSearch(fechaCongelamiento, false, page, pageSize);
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }
	
	@GET
    @Path("altair/file")
    public Response fileGenerationAltair(@Context HttpServletRequest request,
    		@QueryParam("filtro") String filtro) {
        try {
        	File generatedFile = congelamientoService.fileGeneration(filtro, true);
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
	
	@GET
    @Path("filial/file")
    public Response fileGenerationFilial(@Context HttpServletRequest request,
    		@QueryParam("filtro") String filtro) {
        try {
        	File generatedFile = congelamientoService.fileGeneration(filtro, false);
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
	
	@GET
    @Path("fechas-congelamientos")
    public Response getFechasCongelamientos(@Context HttpServletRequest request) {
        try {
            Object result = congelamientoService.getFechasCongelamientos();
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }
	
}
