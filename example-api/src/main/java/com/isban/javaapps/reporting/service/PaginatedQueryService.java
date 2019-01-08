package com.isban.javaapps.reporting.service;

import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.isban.javaapps.reporting.pagination.PageResponse;
import com.isban.javaapps.reporting.util.ExcelFileUtil;
import com.isban.javaapps.reporting.util.Object2ObjectNodeMapping;

public class PaginatedQueryService {
	
	protected final JsonNodeFactory factory = JsonNodeFactory.instance;

    @SuppressWarnings("unchecked")
	protected PageResponse<ObjectNode> getPaginatedResult(StoredProcedureQuery query, Integer pagePosition, Integer pageSizePosition, Integer pagesPosition, Object2ObjectNodeMapping mapper, EntityManager entityManager) {
        boolean isResult = query.execute();
        List<Object[]> resultList = null;
        if(isResult) {
            resultList = query.getResultList();
        }
        entityManager.close();

        List<ObjectNode> jsonObjects = new LinkedList<ObjectNode>();
        for (Object[] resultItem : resultList) {
        	ObjectNode jsonObject = mapper.map(resultItem);
        	jsonObjects.add(jsonObject);
		}

        Integer page = new Integer((String) query.getParameterValue(pagePosition));
        Integer pageSize = new Integer((String) query.getParameterValue(pageSizePosition));
        Integer pages = new Integer((String) query.getOutputParameterValue(pagesPosition));
        Long totalSize = pages.longValue() * pageSize.longValue();

        return new PageResponse<ObjectNode>(jsonObjects, page, pageSize, totalSize);
    }

	public File getFile(StoredProcedureQuery query, EntityManager entityManager) {
		boolean isResult = query.execute();
        File generatedFile = null;
        if(isResult) {
            try {
            	List<String> header = new LinkedList<String>();
            	List<Object[]> elements = new LinkedList<Object[]>();
            	List<String> fields = new LinkedList<String>();
            	ResultSet rs = (ResultSet) query.getOutputParameterValue(2);
            	ResultSetMetaData rsMetaData = rs.getMetaData();
            	int numberOfColumns = rsMetaData.getColumnCount();
            	for (int i = 1; i < numberOfColumns + 1; i++) {
            		String columnName = rsMetaData.getColumnName(i);
            		header.add(columnName);
            		fields.add(String.valueOf(i - 1));
            	}
            	while (rs.next()) {
            		Object[] result = new Object[numberOfColumns];
            		for (int i = 0; i < numberOfColumns; i++) {
            			String columnLabel = header.get(i);
            			Object objectFieldValue = rs.getObject(columnLabel);
            			result[i] = objectFieldValue;
            		}
            		elements.add(result);
            	}
            	generatedFile = ExcelFileUtil.generateGeneric(header, elements, fields);
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
        entityManager.close();
        return generatedFile;
	}

}
