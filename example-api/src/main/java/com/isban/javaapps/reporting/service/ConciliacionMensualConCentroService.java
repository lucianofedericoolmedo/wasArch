package com.isban.javaapps.reporting.service;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.isban.javaapps.reporting.util.FilterBuilder;

@Service
public class ConciliacionMensualConCentroService extends ConciliacionService {

	private static final Logger LOGGER = Logger.getLogger(ConciliacionService.class.getName());

	private final String CODIGO_TABLA_CONCILIACION_MENSUAL_CON_CENTRO = "206";
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Object getSearch(String page,
			String rowPage,
			String fecha,
			String contenido,
			String codigoCuentaContable,
			String codigoCentroContable, String entidad
			) {
	        FilterBuilder filter = new FilterBuilder();
			filter.addFilter("FEC_DATA", fecha)
				.addFilter("COD_CONTENIDO", contenido)
				.addFilter("COD_CTA_CONT", codigoCuentaContable)
				.addFilter("COD_CENTRO_CONT", codigoCentroContable)
				.addFilter("COD_ENTIDAD", entidad);
	        return super.get(filter.getFilter(), new Integer(page), new Integer(rowPage), CODIGO_TABLA_CONCILIACION_MENSUAL_CON_CENTRO);
	}

	/**
	 * Obtiene todos los valores para los filtros segun codigo de tabla
	 * @param codigoTabla
	 * @return
	 */
	public Object getFiltrosMensualConCentro() {//115
		ObjectNode resultJson = factory.objectNode();
		resultJson.set("fechas", this.getFiltroFecha());
		resultJson.set("contenidos", this.getFiltroContenidos());
		resultJson.set("entidades", this.getFiltroEntidades());
		return resultJson;
	}

	@Override
	protected ObjectNode createDTO(ResultSet rs) {
		ObjectNode resultJson = factory.objectNode();
		try {
			Integer index = 1;
			resultJson.put("fecha",                (BigDecimal) rs.getObject(index++));
			resultJson.put("codigoDivisa",         (String) rs.getObject(index++));
			resultJson.put("codigoContenido",      (String) rs.getObject(index++));
			resultJson.put("codigoEntidad",        (String) rs.getObject(index++));
			resultJson.put("entidad",              (String) rs.getObject(index++));
			resultJson.put("codigoCuentaContable", (String) rs.getObject(index++));
			resultJson.put("descripcionCuentaContable", (String) rs.getObject(index++));
			resultJson.put("saldoContable",        (BigDecimal) rs.getObject(index++));
			resultJson.put("saldoOperacional",     (BigDecimal) rs.getObject(index++));
			resultJson.put("importeDesviacionMis", (BigDecimal) rs.getObject(index++));
			resultJson.put("codigoPais",           (BigDecimal) rs.getObject(index++));
			resultJson.put("tipoAcceso",           (String) rs.getObject(index++));
			resultJson.put("codigoProceso",        (String) rs.getObject(index++));
			resultJson.put("porcentajeDesviacionCoc", (BigDecimal) rs.getObject(index++));
			resultJson.put("porcentajeDesviacionMis", (BigDecimal) rs.getObject(index++));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultJson;
	}

	public File generarArchivo(String fecha, String contenido, String codigoCuentaContable, String centroContable) {
        FilterBuilder filter = new FilterBuilder();
		filter.addFilter("FEC_DATA", fecha)
			.addFilter("COD_CONTENIDO", contenido)
			.addFilter("COD_CTA_CONT", codigoCuentaContable)
			.addFilter("COD_CENTRO_CONT", centroContable);
		return super.fileGeneration(filter.getFilter(), CODIGO_TABLA_CONCILIACION_MENSUAL_CON_CENTRO);
	}

}
