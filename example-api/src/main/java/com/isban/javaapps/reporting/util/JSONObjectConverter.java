package com.isban.javaapps.reporting.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JSONObjectConverter {
    
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static <T> T convertToObject(String json, Class<T> clazz) {

        ObjectMapper mapper = createMapper();

        try {
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Ocurrio un ERROR en la des-serializacion de "
                            + clazz.getSimpleName(), e);
        }
    }

    public static String convertToJSON(Object obj) {

        ObjectMapper mapper = createMapper();

        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Ocurrio un ERROR en la serializacion de "
                            + obj.getClass().getSimpleName(), e);
        }
    }
    
    private static ObjectMapper createMapper() {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(df);
        return mapper;
    }
    
    public static JsonNode makeJsonNode(Object[] item) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put("codigo", (String) item[0]);
		node.put("descripcion", (String) item[1]);
		return node;
	}
}
