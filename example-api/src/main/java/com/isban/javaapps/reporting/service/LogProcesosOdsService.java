package com.isban.javaapps.reporting.service;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.isban.javaapps.reporting.dto.TableDTO;
import com.isban.javaapps.reporting.pagination.PageResponse;
import com.isban.javaapps.reporting.util.ExcelFileUtil;
import com.isban.javaapps.reporting.util.FilterBuilder;

@Service
public class LogProcesosOdsService extends BaseService<Object>{

	@Autowired
    private EntityManager entityManager;
	
	private final String codigoTablaODS = "199";
	
	final JsonNodeFactory factory = JsonNodeFactory.instance;
	
	private static final Logger LOGGER = Logger.getLogger(LogProcesosOdsService.class.getName());


	/**
	 * Obtiene todos los valores para los filtros segun codigo de tabla
	 * @param codigoTabla
	 * @return
	 */
	public Object getFiltrosProcesosOds() {//210
		ObjectNode resultJson = factory.objectNode();
		resultJson.set("fechas", this.getFiltroFecha());
		resultJson.set("contenidos", this.getFiltroContenidos());
		resultJson.set("periodicidad", this.getFiltroPeriodicidad());
		return resultJson;
	}

	private ArrayNode getFiltroFecha() {
		String sql = "SELECT DISTINCT TO_CHAR(FEC_DATA) AS FEC_DATA FROM HE0_DV_LOG_PROC_ODS ORDER BY 1";
		Query query = entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<String> items = query.getResultList();
		ArrayNode nodos = factory.arrayNode();
		items.forEach(item-> nodos.add(item));
		return nodos;
	}

	private ArrayNode getFiltroContenidos() {
		String sql = "SELECT DISTINCT COD_CONTENIDO, DES_CONTENIDO FROM HE0_DV_LOG_PROC_ODS ORDER BY 1";
		Query query = entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> items = query.getResultList();
		ArrayNode contenidos = factory.arrayNode();
		
		items.forEach(item-> contenidos.add(this.makeJsonNode(item)));
		
		return contenidos;
	}

	private ArrayNode getFiltroPeriodicidad() {
		String sql = "SELECT DISTINCT COD_PERIODICIDAD FROM HE0_DV_LOG_PROC_ODS ORDER BY 1";
		Query query = entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<String> items = query.getResultList();
		ArrayNode sistemasOrigen = factory.arrayNode();
		items.forEach(item-> sistemasOrigen.add(item));
		return sistemasOrigen;
	}

	private JsonNode makeJsonNode(Object[] item) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put("codigo", (String) item[0]);
		node.put("descripcion", (String) item[1]);
		return node;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public PageResponse<ObjectNode> getSearch(Integer page, Integer pageSize, String fecha, String contenido, String periodicidad) {
		FilterBuilder filter = new FilterBuilder()
			.addFilter("FEC_DATA", fecha)
			.addFilter("COD_CONTENIDO", contenido)
			.addFilter("COD_PERIODICIDAD", periodicidad);
		return this.get(filter.getFilter(), page, pageSize, codigoTablaODS);
	}

	@Override
	protected ObjectNode createDTO(ResultSet rs) {
		ObjectNode resultJson = factory.objectNode();
		try {
			String fechaFin = rs.getObject(9) == null ? null : String.valueOf((Timestamp) rs.getObject(9));
			String fechaAnterior = rs.getObject(10) == null ? null : String.valueOf((Timestamp) rs.getObject(10));
			resultJson.put("fecha", (BigDecimal) rs.getObject(1));
			resultJson.put("codigoContenido", (String) rs.getObject(2));
			resultJson.put("descripcionContenido", (String) rs.getObject(3));
			resultJson.put("codigoPeriodicidad", (String) rs.getObject(4));
			resultJson.put("codigoEstadoExtraccion", (String) rs.getObject(5));
			resultJson.put("descripcionEstadoExtraccion", (String) rs.getObject(6));
			resultJson.put("indExtraccionFinalizada", (String) rs.getObject(7));
			resultJson.put("fechaInicioExtraccion", String.valueOf((Timestamp) rs.getObject(8)));
			resultJson.put("fechaFinExtraccion", fechaFin);
			resultJson.put("fechaFinExtraccionAnterior", fechaAnterior);
			resultJson.put("indExtraccionManual", (String) rs.getObject(11));
			resultJson.put("inicio", (String) rs.getObject(12));
			resultJson.put("fin", (String) rs.getObject(13));
			resultJson.put("duracion", (BigDecimal) rs.getObject(14));
			resultJson.put("codigoPais", (BigDecimal) rs.getObject(15));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultJson;
	}

	public File fileGeneration(String fecha, String contenido, String periodicidad) {
		File generatedFile = null;
		FilterBuilder filter = new FilterBuilder()
			.addFilter("FEC_DATA", fecha)
			.addFilter("COD_CONTENIDO", contenido)
			.addFilter("COD_PERIODICIDAD", periodicidad);
		TableDTO table = this.getDataToExcel(filter.getFilter(), codigoTablaODS);
		try {
			generatedFile = ExcelFileUtil.generateGeneric(table.getHeader(), table.getRows(), table.getFields());
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE,e.getMessage());
		}
		return generatedFile;
	}

}
