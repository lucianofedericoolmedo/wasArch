package com.isban.javaapps.reporting.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isban.javaapps.reporting.dto.NodoDTO;
import com.isban.javaapps.reporting.service.JerarquiaService;
import com.isban.javaapps.reporting.util.RestResponseHandler;

@Transactional
@Service
@Path("/jerarquias")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class JerarquiaRestService{
	
    @Autowired
    protected RestResponseHandler responseHandler;
    
	@Autowired
	private JerarquiaService jerarquiaService;
	
	@GET
	@Path("codigos-dimensiones")
	public Response obtenerCodigosDimensiones() {
		try {
			List<String> codigosDimensiones = jerarquiaService.getCodigosDimensiones();
			return responseHandler.buildSuccessResponse(codigosDimensiones);
		} catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
	}
	
	@GET
	@Path("codigos-jerarquias")
	public Response obtenerCodigosJerarquias() {
		try {
			List<String> codigosJerarquias = jerarquiaService.getCodigosJerarquias();
			return responseHandler.buildSuccessResponse(codigosJerarquias);
		} catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
	}
	
	@GET
	@Path("niveles-padres")
	public Response obtenerNivelesPadres() {
		try {
			List<String> nivelesPadres = jerarquiaService.getNivelesPadres();
			return responseHandler.buildSuccessResponse(nivelesPadres);
		} catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
	}
	
	@GET
	@Path("valores-padres")
	public Response obtenerCodigosValoresPadres() {
		try {
			List<String> valoresPadres = jerarquiaService.getCodigosValoresPadres();
			return responseHandler.buildSuccessResponse(valoresPadres);
		} catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
	}
	
	@POST
	@Path("nodo")
    public Response saveNodo(NodoDTO dto
            ) {
        try {
            Object result = jerarquiaService.saveNodo(dto);
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }
	
	@DELETE
	@Path("nodo")
    public Response deleteProducto(NodoDTO dto
            ) {
        try {
            Object result = jerarquiaService.deleteNodo(dto);
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }
    
}
