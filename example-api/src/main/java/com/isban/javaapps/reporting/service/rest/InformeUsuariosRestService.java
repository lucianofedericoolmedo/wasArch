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
import com.isban.javaapps.reporting.service.UsuarioInformeService;
import com.isban.javaapps.reporting.util.RestResponseHandler;

@Transactional
@Service
@Path("/informe-usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InformeUsuariosRestService{

    @Autowired
    protected RestResponseHandler responseHandler;

	@Autowired
	private UsuarioInformeService usuarioInformeService;

    @GET
    @Path("query")
    public Response getUsuarioInformes(@Context HttpServletRequest request,
        @QueryParam("page") String pageString,
        @QueryParam("pageSize") String pageSizeString,
        @QueryParam("numeroPersona") String numeroPersona,
        @QueryParam("codigoEntidad") String codigoEntidad
    ) {
        try {
        	Integer page = new Integer(pageString);
        	Integer pageSize = new Integer(pageSizeString);
        	Object result = usuarioInformeService.search(page, pageSize, codigoEntidad, numeroPersona);
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
        	e.printStackTrace();
            return responseHandler.buildErrorResponse(e);
        }
    }
    
    @GET
    @Path("file")
    public Response fileGeneration(@Context HttpServletRequest request,
            @QueryParam("numeroPersona") String numeroPersona,
            @QueryParam("codigoEntidad") String codigoEntidad) {
        try {
        	File generatedFile = usuarioInformeService.fileGeneration(numeroPersona, codigoEntidad);
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