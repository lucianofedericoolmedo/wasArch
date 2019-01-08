package com.isban.javaapps.reporting.service.rest;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
import com.isban.javaapps.reporting.dto.ConciliacionDTO;
import com.isban.javaapps.reporting.pagination.PageResponse;
import com.isban.javaapps.reporting.service.ConciliacionDiariaConCentroService;
import com.isban.javaapps.reporting.service.ConciliacionDiariaSinCentroService;
import com.isban.javaapps.reporting.service.ConciliacionMensualConCentroService;
import com.isban.javaapps.reporting.service.ConciliacionMensualSinCentroService;
import com.isban.javaapps.reporting.util.RestResponseHandler;

@Transactional
@Service
@Path("/conciliaciones")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConciliacionRestService {
	
	@Autowired
    protected RestResponseHandler responseHandler;
    
	@Autowired
	private ConciliacionDiariaConCentroService conciliacionDiariaConCentroService;
	
	@Autowired
	private ConciliacionDiariaSinCentroService conciliacionDiariaSinCentroService;
	
	@Autowired
	private ConciliacionMensualConCentroService conciliacionMensualConCentroService;
	
	@Autowired
	private ConciliacionMensualSinCentroService conciliacionMensualSinCentroService;

	@GET
	@Path("mensual-sin-centro/query")
    public Response queryConciliacionMensualSinCentro(@Context HttpServletRequest request,
            @QueryParam("page") String page,
            @QueryParam("pageSize") String rowPage,
            @QueryParam("fecha") String fecha,
            @QueryParam("contenido") String contenido,
            @QueryParam("codigoCuentaContable") String codigoCuentaContable,
            @QueryParam("cuentaContable") String cuentaContable,
            @QueryParam("centroContable") String centroContable,
            @QueryParam("entidades") String entidad
		) {
        try {
        	Object result = conciliacionMensualSinCentroService.getSearch(page, rowPage, fecha, contenido, codigoCuentaContable, entidad);
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }
	
	@GET
	@Path("mensual-sin-centro/filtro")
    public Response getFiltrosMensualSinCentro(@Context HttpServletRequest request) {
        try {
        	Object result = conciliacionMensualSinCentroService.getFiltrosMensualSinCentro();
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }
	
	@GET
	@Path("mensual-sin-centro/descarga-xls")
    public Response getArchivoMensualSinCentro(@Context HttpServletRequest request,
            @QueryParam("contenido") String contenido,
            @QueryParam("fecha") String fecha,
            @QueryParam("codigoCuentaContable") String codigoCuentaContable,
            @QueryParam("cuentaContable") String cuentaContable,
            @QueryParam("centroContable") String centroContable
		) {
        try {
        	File generatedFile = conciliacionMensualSinCentroService.generarArchivo(fecha, contenido, codigoCuentaContable);
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
	@Path("mensual-con-centro/query")
    public Response queryConciliacionMensualConCentro(@Context HttpServletRequest request,
            @QueryParam("page") String page,
            @QueryParam("pageSize") String rowPage,
            @QueryParam("fecha") String fecha,
            @QueryParam("contenido") String contenido,
            @QueryParam("codigoCuentaContable") String codigoCuentaContable,
            @QueryParam("cuentaContable") String cuentaContable,
            @QueryParam("centroContable") String centroContable,
            @QueryParam("entidades") String entidad
		) {
        try {
        	Object result = conciliacionMensualConCentroService.getSearch(page, rowPage, fecha, contenido, cuentaContable, centroContable, entidad);
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }

	@GET
	@Path("mensual-con-centro/filtro")
    public Response getFiltrosMensualConCentro(@Context HttpServletRequest request) {
        try {
        	Object result = conciliacionMensualConCentroService.getFiltrosMensualConCentro();
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }

	@GET
	@Path("mensual-con-centro/descarga-xls")
    public Response getArchivoMensualConCentro(@Context HttpServletRequest request,
            @QueryParam("contenido") String contenido,
            @QueryParam("fecha") String fecha,
            @QueryParam("codigoCuentaContable") String codigoCuentaContable,
            @QueryParam("cuentaContable") String cuentaContable,
            @QueryParam("centroContable") String centroContable
		) {
        try {
        	File generatedFile = conciliacionMensualConCentroService.generarArchivo(fecha, contenido, codigoCuentaContable, centroContable);
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
	@Path("diaria-sin-centro/query")
    public Response queryConciliacionDiariaSinCentro(@Context HttpServletRequest request,
            @QueryParam("page") String page,
            @QueryParam("pageSize") String rowPage,
            @QueryParam("fecha") String fecha,
            @QueryParam("contenido") String contenido,
            @QueryParam("codigoCuentaContable") String codigoCuentaContable,
            @QueryParam("cuentaContable") String cuentaContable,
            @QueryParam("centroContable") String centroContable,
            @QueryParam("entidades") String entidad
		) {
        try {
        	Object result = conciliacionDiariaSinCentroService.getSearch(page, rowPage, fecha, contenido, codigoCuentaContable, entidad);
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }
	
	@GET
	@Path("diaria-sin-centro/filtro")
    public Response getFiltrosDiariaSinCentro(@Context HttpServletRequest request) {
        try {
        	Object result = conciliacionDiariaSinCentroService.getFiltrosDiariaSinCentro();
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }
	
	@GET
	@Path("diaria-sin-centro/descarga-xls")
    public Response getArchivoDiariaSinCentro(@Context HttpServletRequest request,
            @QueryParam("contenido") String contenido,
            @QueryParam("fecha") String fecha,
            @QueryParam("codigoCuentaContable") String codigoCuentaContable,
            @QueryParam("cuentaContable") String cuentaContable,
            @QueryParam("centroContable") String centroContable
		) {
        try {
        	File generatedFile = conciliacionDiariaSinCentroService.generarArchivo(fecha, contenido, codigoCuentaContable);
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
	@Path("diaria-con-centro/query")
    public Response queryConciliacionDiariaConCentro(@Context HttpServletRequest request,
            @QueryParam("page") String page,
            @QueryParam("pageSize") String rowPage,
            @QueryParam("fecha") String fecha,
            @QueryParam("contenido") String contenido,
            @QueryParam("codigoCuentaContable") String codigoCuentaContable,
            @QueryParam("cuentaContable") String cuentaContable,
            @QueryParam("centroContable") String centroContable,
            @QueryParam("entidades") String entidad
		) {
        try {
        	Object result = conciliacionDiariaConCentroService.getSearch(page, rowPage, fecha, contenido, cuentaContable, centroContable, entidad);
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }

	@GET
	@Path("diaria-con-centro/filtro")
    public Response getFiltrosDiariaConCentro(@Context HttpServletRequest request) {
        try {
        	Object result = conciliacionDiariaConCentroService.getFiltrosDiariaConCentro();
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }

	@GET
	@Path("diaria-con-centro/descarga-xls")
    public Response getArchivoDiariaConCentro(@Context HttpServletRequest request,
            @QueryParam("contenido") String contenido,
            @QueryParam("fecha") String fecha,
            @QueryParam("codigoCuentaContable") String codigoCuentaContable,
            @QueryParam("cuentaContable") String cuentaContable,
            @QueryParam("centroContable") String centroContable
		) {
        try {
        	File generatedFile = conciliacionDiariaConCentroService.generarArchivo(fecha, contenido, codigoCuentaContable, centroContable);
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

	/**
	 * TEMPORAL ENDPOINT FOR MOCKING PURPOSE
	 * @param request
	 * @return
	 */
	@GET
	@Path("mocks")
    public Response getConciliacionesMock(@Context HttpServletRequest request,
            @QueryParam("page") String page,
            @QueryParam("rowPage") String rowPage,
            @QueryParam("filtro") String filtro,
            @QueryParam("fecha") String fecha,
            @QueryParam("particion") String particion
		) {
        try {
        	Integer pageSize = 10;
        	Integer pageNumber = new Integer(page);
        	Integer nextPageFirstValue = (pageNumber + 1) * pageSize;
        	
        	List<ConciliacionDTO> conciliaciones = new LinkedList<ConciliacionDTO>();
        	
        	for (int i = pageSize * pageNumber; i < nextPageFirstValue; i++) {
				ConciliacionDTO conciliacionDTOInIterating = new ConciliacionDTO(new Date(),
					"codigoContenido_" + i,
					"entidad_" + i,
					"codigoCuentaContable_" + i, 
					"descripcionCuentaContable_" + i,
					"saldoContable_" + i,
					"saldoOperacional_" + i,
					"codigoDivisa_" + i,
					"importeDesviacionMis_" + i,
					"importeDesviacionCoc_" + i,
					"codigoSistemaOrigen_" + i,
					"codigoCentroContable_" + i
				);
				conciliaciones.add(conciliacionDTOInIterating);
			}

        	PageResponse<ConciliacionDTO> result = new PageResponse<>(conciliaciones, pageNumber, pageSize, 35l);
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }

}
