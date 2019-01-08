package com.isban.javaapps.reporting.service;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.isban.javaapps.reporting.dto.TableDTO;
import com.isban.javaapps.reporting.util.ExcelFileUtil;
import com.isban.javaapps.reporting.util.JSONObjectConverter;

@Service
public abstract class ConciliacionService extends BaseService<Object> {

	private static final Logger LOGGER = Logger.getLogger(ConciliacionService.class.getName());

	protected final JsonNodeFactory factory = JsonNodeFactory.instance;

	protected JsonNode getFiltroString(String sql) {
		Query query = entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<String> items = query.getResultList();
		ArrayNode nodos = factory.arrayNode();
		items.forEach(item-> nodos.add(item));
		return nodos;
	}

	public ArrayNode getFiltroContenidos() {
		String sql = "SELECT DISTINCT COD_CONTENIDO,COD_CONTENIDO||'-'||DES_CONTENIDO FROM HE0_DT_AU_CONTENIDO ORDER BY 2 ASC";
		Query query = entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> items = query.getResultList();
		ArrayNode contenidos = factory.arrayNode();
		
		items.forEach(item-> contenidos.add(JSONObjectConverter.makeJsonNode(item)));
		
		return contenidos;
	}
	
	protected ArrayNode getFiltroEntidades() {
		String sql = "select COD_ENTIDAD, DES_ENTIDAD from HE0_DT_AU_ENTIDAD order by 1";
		Query query = entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> items = query.getResultList();
		ArrayNode entidades = factory.arrayNode();
		
		items.forEach(item-> entidades.add(JSONObjectConverter.makeJsonNode(item)));
		return entidades;
	}

	protected ArrayNode getFiltroFecha() {
		String sql = "SELECT FEC_DATA FROM (SELECT DISTINCT FEC_DATA FROM (SELECT TO_CHAR(LAST_DAY(TO_DATE (   SUBSTR (PARTITION_NAME,9,6)|| '01','YYYYMMDD')),'RRRRMMDD') AS FEC_DATA FROM ALL_TAB_PARTITIONS WHERE TABLE_NAME = 'HE0_FT_VALIDACION_CONTABLE_MES'))ORDER BY 1 DESC";
		Query query = entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<String> items = query.getResultList();
		ArrayNode nodos = factory.arrayNode();
		items.forEach(item-> nodos.add(item));
		return nodos;
	}
	
	protected JsonNode getFiltroFechaDiaria() {
		String sql = "SELECT FEC_DATA FROM (SELECT DISTINCT FEC_DATA FROM (SELECT SUBSTR(PARTITION_NAME,9,8) AS FEC_DATA FROM all_tab_partitions WHERE table_name='HE0_FT_VALIDACION_CONTABLE_DIA' )WHERE  FEC_DATA IS NOT NULL)ORDER BY 1 desc";
		return this.getFiltroString(sql);
	}

	public File fileGeneration(String filtro, String codigoTabla) {
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
