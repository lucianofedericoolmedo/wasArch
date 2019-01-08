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
import com.isban.javaapps.reporting.service.LogProcesosApuService;
import com.isban.javaapps.reporting.service.LogProcesosOdsService;
import com.isban.javaapps.reporting.util.RestResponseHandler;

@Transactional
@Service
@Path("/log-procesos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LogProcesosRestService {

	@Autowired
    protected RestResponseHandler responseHandler;
    
	@Autowired
	private LogProcesosOdsService logProcesosOdsService;

	@Autowired
	private LogProcesosApuService logProcesosApuService;
	
	
	@GET
	@Path("apu/filtros")
    public Response getFiltrosProcesosApu(@Context HttpServletRequest request) {
        try {
        	Object result = logProcesosApuService.getFiltrosProcesosApu();
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }

	@GET
	@Path("apu/query")
    public Response queryLogProcessApu(@Context HttpServletRequest request,
            @QueryParam("page") Integer page,
            @QueryParam("pageSize") Integer pageSize,
            @QueryParam("fecha") String fecha,
            @QueryParam("contenido") String contenido,
            @QueryParam("sistemaOrigen") String sistemaOrigen,
            @QueryParam("tabla") String tabla
		) {
        try {
        	PageResponse<ObjectNode> result = logProcesosApuService.getSearch(page, pageSize, fecha, contenido, sistemaOrigen, tabla);
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }

	@GET
    @Path("apu/descarga-xls")
    public Response apuProcessFileGeneration(@Context HttpServletRequest request,
    		@QueryParam("fecha") String fecha,
            @QueryParam("contenido") String contenido,
            @QueryParam("sistemaOrigen") String sistemaOrigen,
            @QueryParam("tabla") String tabla
        ) {
        try {
        	File generatedFile = logProcesosApuService.fileGeneration(fecha, contenido, sistemaOrigen, tabla);
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
	@Path("ods/filtros")
    public Response getFiltrosProcesosOds(@Context HttpServletRequest request) {
        try {
        	Object result = logProcesosOdsService.getFiltrosProcesosOds();
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }
	
	@GET
	@Path("ods/query")
    public Response getLogs(@Context HttpServletRequest request,
            @QueryParam("page") Integer page,
            @QueryParam("pageSize") Integer pageSize,
            @QueryParam("filtro") String filtro,
            @QueryParam("fecha") String fecha,
            @QueryParam("contenido") String contenido,
            @QueryParam("periodicidad") String periodicidad
		) {
        try {
        	PageResponse<ObjectNode> result = logProcesosOdsService.getSearch(page, pageSize, fecha, contenido, periodicidad);
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }
	
	@GET
    @Path("ods/descarga-xls")
    public Response fileGeneration(@Context HttpServletRequest request,
    		@QueryParam("fecha") String fecha,
            @QueryParam("contenido") String contenido,
            @QueryParam("periodicidad") String periodicidad
        ) {
        try {
        	File generatedFile = logProcesosOdsService.fileGeneration(fecha, contenido, periodicidad);
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
