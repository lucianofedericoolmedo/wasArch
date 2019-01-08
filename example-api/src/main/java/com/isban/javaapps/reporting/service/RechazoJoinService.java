package com.isban.javaapps.reporting.service;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.isban.javaapps.reporting.dto.TableDTO;
import com.isban.javaapps.reporting.pagination.PageResponse;
import com.isban.javaapps.reporting.util.ExcelFileUtil;
import com.isban.javaapps.reporting.util.JSONObjectConverter;
import com.isban.javaapps.reporting.util.FilterBuilder;

@SuppressWarnings("rawtypes")
@Service
public class RechazoJoinService extends BaseService {

	@Autowired
	private EntityManager entityManager;

	final JsonNodeFactory factory = JsonNodeFactory.instance;

	@Autowired
	private ConciliacionMensualConCentroService conciliacionService;

	private String codigoTabla = "188";

	/**
	 * Devuelve un objeto con los filtros necesarios para la grilla
	 * 
	 * @return
	 */
	public Object getFiltros() {
		ObjectNode resultJson = factory.objectNode();
		resultJson.set("fechas", this.getFiltroFechaDiaria());
		resultJson.set("contenidos", conciliacionService.getFiltroContenidos());
		resultJson.set("origen", this.getFiltroOrigen());
		return resultJson;
	}

	private JsonNode getFiltroOrigen() {
		String sql = "SELECT DISTINCT COD_APLICATIVO_FUENTE, COD_APLICATIVO_FUENTE||'-'|| DES_APLICATIVO_FUENTE FROM HE0_DT_AU_APLICATIVO_FUENTE ORDER BY 2 ASC";
		Query query = entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> items = query.getResultList();
		ArrayNode contenidos = factory.arrayNode();

		items.forEach(item -> contenidos.add(JSONObjectConverter.makeJsonNode(item)));

		return contenidos;
	}

	private JsonNode getFiltroFechaDiaria() {
		String sql = "SELECT FEC_DATA FROM (SELECT DISTINCT FEC_DATA FROM (SELECT SUBSTR(PARTITION_NAME,9,8) "
				+ "AS FEC_DATA FROM all_tab_partitions  WHERE table_name='HE0_LG_HEAD_JOIN' )WHERE  FEC_DATA IS NOT NULL)"
				+ "ORDER BY 1 DESC";
		return this.getFiltroString(sql);
	}

	private JsonNode getFiltroString(String sql) {
		Query query = entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<String> items = query.getResultList();
		ArrayNode nodos = factory.arrayNode();
		items.forEach(item -> nodos.add(item));
		return nodos;
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public PageResponse<ObjectNode> getSearch(String codigoSistemaOrigen, String contenido, String fecha, Integer page, Integer pageSize) {
		FilterBuilder filter = new FilterBuilder();
		filter.addFilter("COD_SIS_ORIGEN", codigoSistemaOrigen)
			.addFilter("COD_CONTENIDO", contenido)
			.addFilter("FEC_DATA", fecha);
		
		return this.get(filter.getFilter(), page, pageSize, codigoTabla);
	}

	protected ObjectNode createDTO(ResultSet rs) {
		ObjectNode resultJson = factory.objectNode();
		try {
			resultJson.put("fecha", (BigDecimal) rs.getObject(1));
			resultJson.put("codigoContenido", (String) rs.getObject(2));
			resultJson.put("descripcionContenido", (String) rs.getObject(3));
			resultJson.put("codigoSistemaOrigen", (String) rs.getObject(4));
			resultJson.put("descripcionSistemaOrigen", (String) rs.getObject(5));
			resultJson.put("codigoPais", (BigDecimal) rs.getObject(6));
			resultJson.put("descripcionPais", (String) rs.getObject(7));
			resultJson.put("nombreTarget", (String) rs.getObject(8));
			resultJson.put("registrosAfectados", (BigDecimal) rs.getObject(9));
			resultJson.put("botonDetalle", (String) rs.getObject(10));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultJson;
	}

	public File fileGeneration(String filtro) {
		File generatedFile = null;
		TableDTO table = this.getDataToExcel(filtro, codigoTabla);
		try {
			generatedFile = ExcelFileUtil.generateGeneric(table.getHeader(), table.getRows(), table.getFields());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return generatedFile;
	}

}
