package com.isban.javaapps.reporting.service;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Query;

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
import com.isban.javaapps.reporting.util.FilterBuilder;
import com.isban.javaapps.reporting.util.Object2ObjectNodeMapping;

@Service
public class TablasAuxiliaresService extends BaseService<Object> {

	private static final String COD_TABLA_AUXILIAR_TIPIFICACION_CUENTA = "160";

	final JsonNodeFactory factory = JsonNodeFactory.instance;
	
	private static final Logger LOGGER = Logger.getLogger(TablasAuxiliaresService.class.getName());

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public PageResponse<ObjectNode> getTablaAuxiliarTipificacionCuenta(
			Integer page,
			Integer rowPage,
			String valor,
			String entidad
			) {
		FilterBuilder filter = new FilterBuilder();
		filter.addFilter("COD_VALOR", valor);
		filter.addFilter("COD_ENTIDAD", entidad);
		return super.get(filter.getFilter(), page, rowPage, COD_TABLA_AUXILIAR_TIPIFICACION_CUENTA);
	}

	@Override
	protected ObjectNode createDTO(ResultSet rs) {
		ObjectNode objectNode = factory.objectNode();
		try {
			objectNode.put("entidad", (String) rs.getObject(2));
			objectNode.put("valor", (String) rs.getObject(3));
			objectNode.put("tipoValor", (String) rs.getObject(4));
			objectNode.put("tipoSaldo", (String) rs.getObject(5));
			objectNode.put("descripcionTipoSaldo", (String) rs.getObject(6));
			objectNode.put("claseSaldo", (String) rs.getObject(7));
			objectNode.put("descripcionClaseSaldo", (String) rs.getObject(8));
			objectNode.put("estadoSaldo", (String) rs.getObject(9));
			objectNode.put("descripcionEstadoSaldo", (String) rs.getObject(10));
			return objectNode;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE,e.getMessage());
			throw new RuntimeException("Ocurrió un error durante la consulta de datos");
		}
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public Object getSelectorsValues() {
		ObjectNode resultJson = factory.objectNode();
		resultJson.set("entidad", this.getValoresCodigoEntidad());
		resultJson.set("tipoValor", this.getValoresTipoValor());
		resultJson.set("tipoSaldo", this.getValoresSaldo());
		resultJson.set("claseSaldo", this.getValoresClaseSaldo());
		resultJson.set("estadoSaldo", this.getValoresEstadoSaldo());
		return resultJson;
	}

	private JsonNode getValoresCodigoEntidad() {
		String sql = "SELECT cod_entidad FROM ( "
					+ "		SELECT DISTINCT cod_entidad "
					+ "		FROM he0_dt_au_tip_contable "
					+ "		WHERE cod_pais='54' "
					+ "		AND cod_entidad IS NOT null"
					+ ") order by 1 ";
		return this.getFiltroString(sql);
	}

	private JsonNode getValoresTipoValor() {
		String sql = "SELECT tip_valor_grp FROM ( "
					+ "		SELECT DISTINCT tip_valor_grp "
					+ "		FROM he0_dt_au_tip_contable "
					+ "		WHERE cod_pais='54' "
					+ "		AND tip_valor_grp IS NOT null"
					+ ") order by 1 ";
		return this.getFiltroString(sql);
	}

	private JsonNode getValoresSaldo() {
		String sql = "SELECT tip_sdo, tip_sdo ||' - '|| des_tip_sdo FROM ( " +
					"	SELECT DISTINCT tip_sdo, des_tip_sdo " +
					"	FROM he0_dt_au_tip_contable " +
					"	WHERE cod_pais='54' " +
					"	AND tip_sdo IS NOT NULL " +
					"	AND des_tip_sdo IS NOT NULL " +
					") order by 2 ";
		return this.getFiltroObject(sql, descriptionValueMapping());
	}

	private JsonNode getValoresClaseSaldo() {
		String sql = "SELECT DISTINCT CLS_SDO, CLS_SDO ||' - '|| DES_CLS_SDO FROM ( "
					+ "		SELECT DISTINCT CLS_SDO, DES_CLS_SDO "
					+ "		FROM he0_dt_au_tip_contable "
					+ "		WHERE cod_pais='54' "
					+ "		AND CLS_SDO IS NOT null"
					+ "		AND DES_CLS_SDO IS NOT null"
					+ ") order by 2 ";
		return this.getFiltroObject(sql, descriptionCharacterValueMapping());
	}

	private JsonNode getValoresEstadoSaldo() {
		String sql = "SELECT DISTINCT COD_EST_SDO, COD_EST_SDO||' - '|| DES_EST_SDO FROM ( "
					+ "		SELECT DISTINCT COD_EST_SDO, DES_EST_SDO "
					+ "		FROM he0_dt_au_tip_contable "
					+ "		WHERE cod_pais='54' "
					+ "		AND COD_EST_SDO IS NOT null"
					+ "		AND DES_EST_SDO IS NOT null"
					+ ") order by 2 ";
		return this.getFiltroObject(sql, descriptionValueMapping());
	}

	private JsonNode getFiltroString(String sql) {
		Query query = entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<String> items = query.getResultList();
		ArrayNode nodos = factory.arrayNode();
		items.forEach(item -> nodos.add(item));
		return nodos;
	}

	private JsonNode getFiltroObject(String sql, Object2ObjectNodeMapping mapping) {
		Query query = entityManager.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> items = query.getResultList();
		ArrayNode contenidos = factory.arrayNode();
		items.forEach(item-> contenidos.add(mapping.map(item)));
		return contenidos;
	}

	private Object2ObjectNodeMapping descriptionValueMapping() {
		Object2ObjectNodeMapping mapping = new Object2ObjectNodeMapping() {
			@Override
			public ObjectNode map(Object[] object) {
				ObjectNode jsonObject = factory.objectNode();
				jsonObject.put("codigo",      (String) object[0]);
				jsonObject.put("descripcion", (String) object[1]);
				return jsonObject;
			}
		};
		return mapping;
	}

	private Object2ObjectNodeMapping descriptionCharacterValueMapping() {
		Object2ObjectNodeMapping mapping = new Object2ObjectNodeMapping() {
			@Override
			public ObjectNode map(Object[] object) {
				ObjectNode jsonObject = factory.objectNode();
				Character codigo = (Character) object[0];
				jsonObject.put("codigo",      String.valueOf(codigo));
				jsonObject.put("descripcion", (String) object[1]);
				return jsonObject;
			}
		};
		return mapping;
	}

	public File fileGeneration(String valor, String entidad) {
		File generatedFile = null;
		FilterBuilder filter = new FilterBuilder();
		filter.addFilter("COD_VALOR", valor);
		filter.addFilter("COD_ENTIDAD", entidad);
		TableDTO table = this.getDataToExcel(filter.getFilter(), COD_TABLA_AUXILIAR_TIPIFICACION_CUENTA);
		try {
			generatedFile = ExcelFileUtil.generateGeneric(table.getHeader(), table.getRows(), table.getFields());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return generatedFile;
	}

}
