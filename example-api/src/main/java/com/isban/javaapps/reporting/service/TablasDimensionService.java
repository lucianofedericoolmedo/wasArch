package com.isban.javaapps.reporting.service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.isban.javaapps.reporting.pagination.PageResponse;
import com.isban.javaapps.reporting.util.FilterBuilder;

@Service
public class TablasDimensionService extends BaseService<Object> {

	private static final String COD_TABLA_DIMENSION_CENTRO = "181";
	
	private static final Logger LOGGER = Logger.getLogger(ProductoService.class.getName());

	final JsonNodeFactory factory = JsonNodeFactory.instance;

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public PageResponse<ObjectNode> getTablaDimensionCentro(
			Integer page,
			Integer rowPage,
			String codigoCentro 
			) {
		FilterBuilder filter = new FilterBuilder();
		filter.addFilter("COD_CENTRO", codigoCentro);
			
		return super.get(filter.getFilter(), page, rowPage, COD_TABLA_DIMENSION_CENTRO);
	}

	@Override
	protected ObjectNode createDTO(ResultSet rs) {
		ObjectNode objectNode = factory.objectNode();
		try {
			objectNode.put("codigoCentro", (String) rs.getObject(1));
			objectNode.put("descripcionCentro", (String) rs.getObject(2));
			objectNode.put("codigoZona", (String) rs.getObject(3));
			objectNode.put("codigoPlaza", (BigDecimal) rs.getObject(4));
			objectNode.put("codigoLocalidad", (String) rs.getObject(5));
			objectNode.put("codigoRegion", (String) rs.getObject(6));
			objectNode.put("codigoCamara", (String) rs.getObject(7));
			objectNode.put("indBaja", (String) rs.getObject(8));
			objectNode.put("tipOficina", (String) rs.getObject(9));
			objectNode.put("infOficinaContable", (String) rs.getObject(10));
			objectNode.put("centroOperante", (String) rs.getObject(11));
			return objectNode;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE,e.getMessage());
			throw new RuntimeException("Ocurrió un error durante la consulta de datos");
		}
	}

}
