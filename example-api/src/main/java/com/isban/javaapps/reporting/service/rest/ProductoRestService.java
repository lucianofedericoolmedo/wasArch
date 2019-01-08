package com.isban.javaapps.reporting.service.rest;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import com.isban.javaapps.reporting.dto.ProductoDTO;
import com.isban.javaapps.reporting.pagination.PageResponse;
import com.isban.javaapps.reporting.service.ProductoService;
import com.isban.javaapps.reporting.util.RestResponseHandler;

@Transactional
@Service
@Path("/productos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductoRestService{
	
    @Autowired
    protected RestResponseHandler responseHandler;
    
	@Autowired
	private ProductoService productoService;
	
    @GET
    @Path("recupera-producto-gest")
    public Response getRecuperaProductosGest(@Context HttpServletRequest request,
            @QueryParam("isOperacional") boolean isOperacional
            ) {
        try {
            Object result = productoService.getRecuperaProductos(isOperacional);
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }
    
    @GET
    @Path("recupera-subproducto-gest")
    public Response getRecuperaSubproductosGest(@Context HttpServletRequest request,
            @QueryParam("codigoProducto") String codigoProducto,
            @QueryParam("isOperacional") boolean isOperacional
            ) {
        try {
        	Object result = productoService.getRecuperaSubproductos(codigoProducto, isOperacional);
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }
    
    @GET
    @Path("recupera-prod")
    public Response getRecuperaProd(@Context HttpServletRequest request,
            @QueryParam("codSistemaOrigen") String codigoApli,
            @QueryParam("codigoProducto") String codigoProducto,
            @QueryParam("codigoSubproducto") String codigoSubproducto,
            @QueryParam("pageSize") String rowPage,
            @QueryParam("page") String page,
            @QueryParam("isOperacional") boolean isOperacional
            ) {
        try {
            PageResponse<ProductoDTO> result = null;
            result = productoService.getProductos(codigoApli, codigoProducto, codigoSubproducto, rowPage, page,
                    isOperacional);
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }

    @GET
    @Path("recupera-aplicativos-norm")
    public Response getRecuperaAplicativosNorm(@Context HttpServletRequest request,
    		@QueryParam("page") String page,
    		@QueryParam("pageSize") String pageSize,
            @QueryParam("codigoProducto") String codigoProducto,
            @QueryParam("codigoSubproducto") String codigoSubproducto
            ) {
        try {
//            Object result = productoService.getRecuperaAplicativosNorm(codigoProducto, codigoSubproducto, page, pageSize);
        	Object result = productoService.getRecuperaAplicativosNormAux(codigoProducto, codigoSubproducto);
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }
    
    @GET
    @Path("aplicativos-norm/query")
    public Response queryAplicativosNorm(@Context HttpServletRequest request,
    		@QueryParam("page") String page,
    		@QueryParam("pageSize") String pageSize,
            @QueryParam("codigoProducto") String codigoProducto,
            @QueryParam("codigoSubproducto") String codigoSubproducto
            ) {
        try {
            Object result = productoService.getRecuperaAplicativosNorm(codigoProducto, codigoSubproducto, page, pageSize);
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }
    
    @GET
    @Path("aplicativos-norm/descarga-xls")
    public Response queryAplicativosNorm(@Context HttpServletRequest request,
            @QueryParam("codigoProducto") String codigoProducto,
            @QueryParam("codigoSubproducto") String codigoSubproducto
            ) {
        try {
            File generatedFile = productoService.generarArchivoRecuperaAplicativosNorm(codigoProducto, codigoSubproducto);
            return responseHandler.buildFileResponse(generatedFile);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }
    
    @GET
    @Path("file")
    public Response queryAplicativosNorm(@Context HttpServletRequest request,
    		@QueryParam("codSistemaOrigen") String codigoApli,
            @QueryParam("codigoProducto") String codigoProducto,
            @QueryParam("codigoSubproducto") String codigoSubproducto,
            @QueryParam("isOperacional") boolean isOperacional
            ) {
        try {
            File generatedFile = productoService.generarArchivoProductos(codigoApli, codigoProducto, codigoSubproducto, isOperacional);
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

    @POST
    public Response insertarProducto(ProductoDTO productodto
            ) {
        try {
            Object result = productoService.insertarProductoSegunDatos(productodto);
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }
    
    @POST
    @Path("normalizados")
    public Response insertarProductosNormalizados(@Context HttpServletRequest request, List<ProductoDTO> dtos
            ) {
        try {
            Object result = productoService.insertarProductosNormalizados(dtos);
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }
    
    @PUT
    public Response modificarProducto(ProductoDTO productodto) {
        try {
            Object result = productoService.modificarProducto(productodto);
            return responseHandler.buildResponse(result, Status.OK);
        } catch (Exception e) {
            return responseHandler.buildErrorResponse(e);
        }
    }
}
