package com.isban.javaapps.reporting.service;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.isban.javaapps.reporting.dto.TableDTO;
import com.isban.javaapps.reporting.pagination.PageResponse;
import com.isban.javaapps.reporting.util.DateUtil;
import com.isban.javaapps.reporting.util.ExcelFileUtil;

@Service
public class CongelamientoService extends BaseService<Object> {

	@Autowired
    private EntityManager entityManager;
	
	final JsonNodeFactory factory = JsonNodeFactory.instance;
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public PageResponse<JsonNode> getSearch(String fecha, boolean isAltair, String page, String pageSize) {
		String sql = null;
		String sqlTotal = null;
		Integer numberPage = page == null ? 0 : Integer.valueOf(page) - 1;
		Integer rowPage = pageSize == null ? 10 : Integer.valueOf(pageSize);
		Integer offset = numberPage * rowPage;
		String where = fecha == null ? "" : " and FEC_CNG=TO_DATE('" + fecha + "', 'YYYY-MM-DD')";
		if(isAltair) {
			sql = "select * from HE0_DT_CONGELA_CTB a where ind_filiales=0";
			sqlTotal = "SELECT COUNT(*) FROM HE0_DT_CONGELA_CTB where ind_filiales=0";
		}else {
			sql = "select * from HE0_DT_CONGELA_CTB a where ind_filiales=1";
			sqlTotal = "SELECT COUNT(*) FROM HE0_DT_CONGELA_CTB where ind_filiales=1";
		}
		Query query = entityManager.createNativeQuery(sqlTotal + where);
		long total = ((BigDecimal)query.getSingleResult()).longValue();
		sql += where +" ORDER BY FEC_CNG DESC OFFSET " +  offset + " ROWS FETCH NEXT " + rowPage + " ROWS ONLY";
		List<JsonNode> items = this.getFiltroString(sql);
		return new PageResponse<JsonNode>(items, new Integer(1), new Integer(items.size()), new Long(1 * total));
	}
	
	private List<JsonNode> getFiltroString(String sql) {
		Query query = entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> items = query.getResultList();
		
		List<JsonNode> congelamientos = new LinkedList<JsonNode>();
		for (Object[] item : items) { 
			ObjectNode congelamiento = factory.objectNode();
			congelamiento.put("fechaCongelamiento",  DateUtil.toDateStringWithFormat((Date)item[1], DateUtil.SIMPLE_DATE_FORMAT));
			congelamiento.put("estadoEjecucion", (String) item[3]);
			congelamientos.add(congelamiento);
		}
		
		return congelamientos;
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public Object getFechasCongelamientos() {
		String sql = "SELECT to_char(FEC_CNG,'YYYY-MM-DD') AS value, to_char(FEC_CNG,'YYYY-MM-DD') AS description " +
						"FROM (" +
						"	SELECT DISTINCT FEC_CNG " +
						"	FROM HE0_DT_CONGELA_CTB " +
						"	WHERE COD_PAIS='54' AND FEC_CNG IS NOT NULL " + 
						")" +
						"ORDER BY 1 DESC";
		Query query = entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> lista = query.getResultList();

		List<JsonNode> fechasCongelamientosJson = new LinkedList<JsonNode>();
		for (Object[] fechaCongelamientoRaw : lista) { 
			JsonNode fechaCongelamientoJson = this.createFechaCongelamientoJson(fechaCongelamientoRaw);
			fechasCongelamientosJson.add(fechaCongelamientoJson);
		}
		
		return fechasCongelamientosJson;
	}

	private JsonNode createFechaCongelamientoJson(Object[] fechaCongelamiento) {
		ObjectNode fechaCongelamientoJson = factory.objectNode();
		fechaCongelamientoJson.put("value", (String) fechaCongelamiento[0]);
		fechaCongelamientoJson.put("description", (String) fechaCongelamiento[1]);
		return fechaCongelamientoJson;
	}
	
	public File fileGeneration(String filtro, boolean isAltair) {
		File generatedFile = null;
		String codigoTabla = isAltair ? "226" : "225";
		TableDTO table = this.getDataToExcel(filtro, codigoTabla);
		try {
			generatedFile = ExcelFileUtil.generateGeneric(table.getHeader(), table.getRows(), table.getFields());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return generatedFile;
	}

	@Override
	protected ObjectNode createDTO(ResultSet rs) {
		//TODO revisar
		return null;
	}
}
