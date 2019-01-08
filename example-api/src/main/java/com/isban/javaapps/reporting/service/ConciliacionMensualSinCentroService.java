package com.isban.javaapps.reporting.service;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.isban.javaapps.reporting.util.FilterBuilder;
import com.isban.javaapps.reporting.util.JSONObjectConverter;

@Service
public class ConciliacionMensualSinCentroService extends ConciliacionService {

	private static final Logger LOGGER = Logger.getLogger(ConciliacionService.class.getName());

	private final String CODIGO_TABLA_CONCILIACION_MENSUAL_SIN_CENTRO = "210";

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Object getSearch(String page,
			String rowPage,
			String fecha,
			String contenido,
			String codigoCuentaContable, String entidad
			) {
	        FilterBuilder filter = new FilterBuilder();
			filter.addFilter("FEC_DATA", fecha)
				.addFilter("COD_CONTENIDO", contenido)
				.addFilter("COD_CTA_CONT", codigoCuentaContable)
				.addFilter("COD_ENTIDAD", entidad);
	        return super.get(filter.getFilter(), new Integer(page), new Integer(rowPage), CODIGO_TABLA_CONCILIACION_MENSUAL_SIN_CENTRO);
	}

	/**
	 * Obtiene todos los valores para los filtros segun codigo de tabla
	 * @param codigoTabla
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Object getFiltrosMensualSinCentro() {//210
		ObjectNode resultJson = factory.objectNode();
		resultJson.set("fechas", this.getFiltroFecha());
		resultJson.set("contenidos", this.getFiltroContenidos());
		resultJson.set("codigoCuentaContable", this.getFiltroCodigoCuentaContableMensualSinCentro());
		resultJson.set("entidades", this.getFiltroEntidades());
		return resultJson;
	}
	
	private JsonNode getFiltroCodigoCuentaContableMensualSinCentro() {
		String sql = "select COD_CTA_CONT from (select distinct COD_CTA_CONT from HE0_DV_VALIDA_CONT_SC_MES_PMO where COD_PAIS='54' and COD_CTA_CONT is not null) order by 1";
		return this.getFiltroString(sql);
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
			resultJson.put("importeDesviacionCoc", (BigDecimal) rs.getObject(index++));
			resultJson.put("codigoPais",           (BigDecimal) rs.getObject(index++));
			resultJson.put("tipoCuenta",           (String) rs.getObject(index++));
			resultJson.put("codigoProceso",        (String) rs.getObject(index++));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultJson;
	}

	public File generarArchivo(String fecha, String contenido, String codigoCuentaContable) {
        FilterBuilder filter = new FilterBuilder()
			.addFilter("FEC_DATA", fecha)
			.addFilter("COD_CONTENIDO", contenido)
			.addFilter("COD_CTA_CONT", codigoCuentaContable);
		return super.fileGeneration(filter.getFilter(), CODIGO_TABLA_CONCILIACION_MENSUAL_SIN_CENTRO);
	}

}
