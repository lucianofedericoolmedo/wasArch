package com.isban.javaapps.reporting.service.rest;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.isban.javaapps.reporting.pagination.PageResponse;
import com.isban.javaapps.reporting.service.TablasAuxiliaresService;
import com.isban.javaapps.reporting.service.TablasDimensionService;
import com.isban.javaapps.reporting.util.RestResponseHandler;

@Transactional
@Service
@Path("/tablas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TablasRestService {
	
	@Autowired
    protected RestResponseHandler responseHandler;
    
	@Autowired
	private TablasDimensionService tablasDimensionService;
	
	@Autowired
	private TablasAuxiliaresService tablasAuxiliaresService;

	@GET
	@Path("dimension/centro/query")
    public Response getDimensonCentro(@Context HttpServletRequest request,
    		@QueryParam("page") Integer page,
            @QueryParam("pageSize") Integer rowPage,
            @QueryParam("codigoCentro") String codigoCentro
            ) {
        try {
        	PageResponse<ObjectNode> tablaDimensionCentroData = tablasDimensionService.getTablaDimensionCentro(page, rowPage, codigoCentro);
            return responseHandler.buildResponse(tablaDimensionCentroData, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }

	@GET
	@Path("auxiliar/tipificacion-cuentas/query")
    public Response getMensualSinCentro(@Context HttpServletRequest request,
    		@QueryParam("page") Integer page,
            @QueryParam("pageSize") Integer rowPage,
            @QueryParam("valor") String valor,
            @QueryParam("entidad") String entidad
            ) {
        try {
        	PageResponse<ObjectNode> tablaAuxiliarTipificacionCuentaData = tablasAuxiliaresService.getTablaAuxiliarTipificacionCuenta(page, rowPage, valor, entidad);
            return responseHandler.buildResponse(tablaAuxiliarTipificacionCuentaData, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }

	@GET
	@Path("auxiliar/tipificacion-cuentas/values")
    public Response getTablaAuxiliarTipificacionCuentaSelectorsValues(@Context HttpServletRequest request) {
        try {
        	Object selectorsValues = tablasAuxiliaresService.getSelectorsValues();
            return responseHandler.buildResponse(selectorsValues, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }

	@GET
	@Path("auxiliar/tipificacion-cuentas/archivo")
    public Response getArchivoTablaAuxiliarTipificacionCuenta(@Context HttpServletRequest request,
    		@QueryParam("valor") String valor,
            @QueryParam("entidad") String entidad
        ) {
        try {
        	File selectorsValues = tablasAuxiliaresService.fileGeneration(valor, entidad);
            return responseHandler.buildFileResponse(selectorsValues);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }

}
