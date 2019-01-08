package com.isban.javaapps.reporting.service;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

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
public class LogProcesosApuService extends BaseService<Object>{

	@Autowired
    private EntityManager entityManager;
	
	private final String codigoTablaAPU = "198";
	
	final JsonNodeFactory factory = JsonNodeFactory.instance;
	
	private static final Logger LOGGER = Logger.getLogger(LogProcesosApuService.class.getName());

	/**
	 * Obtiene todos los valores para los filtros segun codigo de tabla
	 * @param codigoTabla
	 * @return
	 */
	public Object getFiltrosProcesosApu() {//210
		ObjectNode resultJson = factory.objectNode();
		resultJson.set("fechas", this.getFiltroFecha());
		resultJson.set("contenidos", this.getFiltroContenidos());
		resultJson.set("sistemasOrigen", this.getFiltroSistemaOrigen());
		resultJson.set("tabla", this.getFiltroTabla());
		return resultJson;
	}

	private ArrayNode getFiltroFecha() {
		String sql = "SELECT DISTINCT TO_CHAR(FEC_DATA) AS FEC_DATA FROM HE0_DV_LOG_PROC ORDER BY FEC_DATA";
		Query query = entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<String> items = query.getResultList();
		ArrayNode nodos = factory.arrayNode();
		items.forEach(item-> nodos.add(item));
		return nodos;
	}
	
	private ArrayNode getFiltroContenidos() {
		String sql = "SELECT DISTINCT COD_CONTENIDO, DESC_CONTENIDO FROM HE0_DV_LOG_PROC ORDER BY COD_CONTENIDO";
		Query query = entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> items = query.getResultList();
		ArrayNode contenidos = factory.arrayNode();
		
		items.forEach(item-> contenidos.add(this.makeJsonNode(item)));
		
		return contenidos;
	}

	private ArrayNode getFiltroSistemaOrigen() {
		String sql = "SELECT DISTINCT DESC_SIS_ORIGEN FROM HE0_DV_LOG_PROC ORDER BY DESC_SIS_ORIGEN";
		Query query = entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<String> items = query.getResultList();
		ArrayNode sistemasOrigen = factory.arrayNode();
		items.forEach(item-> sistemasOrigen.add(item));
		return sistemasOrigen;
	}

	private ArrayNode getFiltroTabla() {
		String sql = "SELECT DISTINCT TARGET FROM HE0_DV_LOG_PROC ORDER BY TARGET";
		Query query = entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<String> items = query.getResultList();
		ArrayNode tablas = factory.arrayNode();
		items.forEach(item-> tablas.add(item));
		return tablas;
	}

	private JsonNode makeJsonNode(Object[] item) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put("codigo", (String) item[0]);
		node.put("descripcion", (String) item[1]);
		return node;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public PageResponse<ObjectNode> getSearch(Integer page, Integer pageSize, String fecha, String contenido, String sistemaOrigen, String tabla) {
		FilterBuilder filter = new FilterBuilder();
		filter.addFilter("FEC_DATA", fecha)
			.addFilter("DESC_CONTENIDO", contenido)
			.addFilter("DESC_SIS_ORIGEN", sistemaOrigen)
			.addFilter("TARGET", tabla);
		return this.get(filter.getFilter(), page, pageSize, codigoTablaAPU);
	}

	@Override
	protected ObjectNode createDTO(ResultSet rs) {
		ObjectNode resultJson = factory.objectNode();
		try {
			resultJson.put("fecha", (BigDecimal) rs.getObject(1));
			resultJson.put("tabla", (String) rs.getObject(2));
			resultJson.put("descripcionTabla", (String) rs.getObject(3));
			resultJson.put("codigoContenido", (String) rs.getObject(4));
			resultJson.put("contenido", (String) rs.getObject(5));
			resultJson.put("codigoSistemaOrigen", (String) rs.getObject(6));
			resultJson.put("sistemaOrigen", (String) rs.getObject(7));
			resultJson.put("estado", (String) rs.getObject(8));
			resultJson.put("inicio", (String) rs.getObject(9));
			resultJson.put("fin", (String) rs.getObject(10));
			resultJson.put("duracion", (BigDecimal) rs.getObject(11));
			resultJson.put("periodo", (String) rs.getObject(12));
			resultJson.put("codigoProceso", (String) rs.getObject(13));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultJson;
	}

	public File fileGeneration(String fecha, String contenido, String sistemaOrigen, String tabla) {
		File generatedFile = null;
		FilterBuilder filter = new FilterBuilder()
			.addFilter("FEC_DATA", fecha)
			.addFilter("DESC_CONTENIDO", contenido)
			.addFilter("DESC_SIS_ORIGEN", sistemaOrigen)
			.addFilter("TARGET", tabla);
		TableDTO table = this.getDataToExcel(filter.getFilter(), codigoTablaAPU);
		try {
			generatedFile = ExcelFileUtil.generateGeneric(table.getHeader(), table.getRows(), table.getFields());
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE,e.getMessage());
		}
		return generatedFile;
	}

}
