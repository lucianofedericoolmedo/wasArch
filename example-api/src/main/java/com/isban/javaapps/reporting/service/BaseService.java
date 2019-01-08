package com.isban.javaapps.reporting.service;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.isban.javaapps.reporting.dto.TableDTO;
import com.isban.javaapps.reporting.pagination.PageResponse;

@Transactional
public abstract class BaseService<T> {
	
	@Autowired
    protected EntityManager entityManager;
	
	final JsonNodeFactory factory = JsonNodeFactory.instance;
	
	private String codigoPais = "54";

	@Transactional(propagation=Propagation.REQUIRES_NEW)
    public PageResponse<ObjectNode> get(String filtro, Integer page, Integer pageSize, String codigoTabla) {
        pageSize = pageSize == null ? 10 : pageSize;
        page = page == null ? 1 : page;
        filtro = filtro == null ? "" : filtro;
        StoredProcedureQuery query = this.getRecuperaTablaMtoPart(filtro, codigoTabla, pageSize, page);
          
        boolean isResult = query.execute();
        Long pages = Long.parseLong(query.getOutputParameterValue(11).toString());
        List<ObjectNode> rowList = new LinkedList<ObjectNode>();
        if(isResult) {
        	ResultSet rs = (ResultSet) query.getOutputParameterValue(2);
			try {
				while (rs.next())
				{
					ObjectNode resultJson = this.createDTO(rs);
					rowList.add(resultJson);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        entityManager.close();
        
        return new PageResponse<ObjectNode>(rowList, new Integer(page), new Integer(pageSize), pages * pageSize);
    }

	protected abstract ObjectNode createDTO(ResultSet rs);
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	protected  TableDTO getDataToExcel(String filtro, String codigoTabla) {
		List<String> header = new LinkedList<String>();
		List<String> fields = new LinkedList<String>();
		List<Object[]> elements = new LinkedList<Object[]>();
	    Integer pageSize = 1048576;
        Integer page = 1;
        filtro = filtro == null ? "" : filtro;
	      StoredProcedureQuery query = this.getRecuperaTablaMtoPart(filtro, codigoTabla, pageSize, page);
          
        boolean isResult = query.execute();
        ResultSet rs = null;
        if(isResult) {
        	try {
	        	rs = (ResultSet) query.getOutputParameterValue(2);
	        	ResultSetMetaData rsMetaData = rs.getMetaData();
	            int numberOfColumns = rsMetaData.getColumnCount();
	
	            for (int i = 1; i < numberOfColumns + 1; i++) {
	              String columnName = rsMetaData.getColumnName(i);
	              header.add(columnName);
	              fields.add(String.valueOf(i - 1));
	            }
				while (rs.next())
				{
					Object[] result = new Object[numberOfColumns];
					for (int i = 1; i < numberOfColumns + 1; i++) {
			              result[i - 1] = rs.getObject(header.get(i - 1));
		            }
		            elements.add(result);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        entityManager.close();
        
	    return new TableDTO(header, elements, fields);
	}

	private StoredProcedureQuery getRecuperaTablaMtoPart(String filtro, String codigoTabla, Integer pageSize, Integer page) {
		StoredProcedureQuery query = entityManager
	      .createStoredProcedureQuery("PKG_ODS_SERVICIOS_MTO_MP.Recupera_TablaMto_Part")
	      .registerStoredProcedureParameter(1, String.class, 
	          ParameterMode.IN)
	      .registerStoredProcedureParameter(2, void.class, ParameterMode.REF_CURSOR)
	      .registerStoredProcedureParameter(3, Long.class, ParameterMode.OUT)
	      .registerStoredProcedureParameter(4, String.class, ParameterMode.OUT)
	      .registerStoredProcedureParameter(5, Long.class, ParameterMode.OUT)
	      .registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
	      .registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
	      .registerStoredProcedureParameter(8, String.class, ParameterMode.IN)
	      .registerStoredProcedureParameter(9, String.class, ParameterMode.IN)
	      .registerStoredProcedureParameter(10, String.class, ParameterMode.IN)
	      .registerStoredProcedureParameter(11, String.class, ParameterMode.OUT)
	      .registerStoredProcedureParameter(12, String.class, ParameterMode.IN)
	      .registerStoredProcedureParameter(13, String.class, ParameterMode.IN)
	      .registerStoredProcedureParameter(14, String.class, ParameterMode.IN)
	      .registerStoredProcedureParameter(15, String.class, ParameterMode.IN)
	      
	      .setParameter(1, "")
	      .setParameter(6, "")
	      .setParameter(7, page.toString())
	      .setParameter(8, pageSize.toString())
	      .setParameter(9, codigoTabla)
	      .setParameter(10, filtro)
	      .setParameter(12, "")
	      .setParameter(13, "")
	      .setParameter(14, codigoPais)
	      .setParameter(15, "");
		return query;
	}

}
