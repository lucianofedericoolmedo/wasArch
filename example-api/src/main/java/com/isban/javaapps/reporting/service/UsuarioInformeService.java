package com.isban.javaapps.reporting.service;

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.isban.javaapps.reporting.dto.TableDTO;
import com.isban.javaapps.reporting.util.ExcelFileUtil;
import com.isban.javaapps.reporting.util.FilterBuilder;

@Service
public class UsuarioInformeService extends BaseService<Object>{
    
	private static final Logger LOGGER = Logger.getLogger(UsuarioInformeService.class.getName());

	@Autowired
    private EntityManager entityManager;
	private String codigoTabla = "221";
	
	private String getFilter(String codigoEntidad, String numeroPersona) {
		FilterBuilder filter = new FilterBuilder();
		filter.addFilter("COD_ENTIDAD", codigoEntidad)
			.addFilter("NUM_PERSONA", numeroPersona);
		
		return filter.getFilter();
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Object search(Integer page, Integer pageSize, String codigoEntidad, String numeroPersona) {
		return this.get(this.getFilter(codigoEntidad, numeroPersona), page, pageSize, codigoTabla);
	}

	@Override
	protected ObjectNode createDTO(ResultSet rs) {
		ObjectNode resultJson = factory.objectNode();
		try {
			resultJson.put("identificador", (String) rs.getObject(1));
			resultJson.put("codigoPais", (BigDecimal) rs.getObject(2));
			resultJson.put("codigoEntidad", rs.getObject(3).toString());
			resultJson.put("numeroPersona", (String) rs.getObject(4));
			resultJson.put("tip_DOC_ID", (String) rs.getObject(5));
			resultJson.put("numeroDocumento", (String) rs.getObject(6));
			resultJson.put("sec_NUM_DOC", (String) rs.getObject(7));
			resultJson.put("nombre", (String) rs.getObject(8));
			resultJson.put("apellidoPaterno", (String) rs.getObject(9));
			resultJson.put("apellidoMaterno", (String) rs.getObject(10));
			resultJson.put("pais", (String) rs.getObject(11));
			resultJson.put("tipoPersona", (String) rs.getObject(12));
			resultJson.put("codCondicionesCli", (String) rs.getObject(13));
			resultJson.put("codSituacionCli", (String) rs.getObject(14));
			resultJson.put("estadoCliente", (String) rs.getObject(15));
			resultJson.put("codVinculaCliente", (String) rs.getObject(16));
			resultJson.put("codCentroContable", (String) rs.getObject(17));
			resultJson.put("codOficinaCliente", (String) rs.getObject(18));
			resultJson.put("sexo", (String) rs.getObject(19));
			resultJson.put("codigoSegmento", (String) rs.getObject(20));
			resultJson.put("codigoSegmentoNegocio", (String) rs.getObject(21));
			resultJson.put("codigoSegmentoRiesgo", (String) rs.getObject(22));
			resultJson.put("codSectorContable", (String) rs.getObject(23));
			resultJson.put("codSectorActividad", (String) rs.getObject(24));
			resultJson.put("indEmpleado", (String) rs.getObject(25));
			resultJson.put("indBanca", (String) rs.getObject(26));
			resultJson.put("indConfidencialidad", (String) rs.getObject(27));
			resultJson.put("indCarterizado", (String) rs.getObject(28));
			resultJson.put("fechaAlta", (BigDecimal) rs.getObject(29));
			resultJson.put("fechaBaja", (BigDecimal) rs.getObject(30));
			resultJson.put("fechaData", (BigDecimal) rs.getObject(31));
			resultJson.put("fechaAltaODS", (BigDecimal) rs.getObject(32));
			resultJson.put("codigoProceso", (String) rs.getObject(33));
			resultJson.put("usuario", (String) rs.getObject(34));
			resultJson.put("timest_UMO", (String) rs.getObject(35));
			resultJson.put("codigoCartera", (String) rs.getObject(36));
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE,e.getMessage());
		}

		return resultJson;
	}

	public File fileGeneration(String numeroPersona, String codigoEntidad) {
		File generatedFile = null;
		TableDTO table = this.getDataToExcel(this.getFilter(codigoEntidad, numeroPersona), codigoTabla);
		try {
			generatedFile = ExcelFileUtil.generateGeneric(table.getHeader(), table.getRows(), table.getFields());
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE,e.getMessage());
		}
		return generatedFile;
	}
	
}