package com.isban.javaapps.reporting.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TableDTO implements Serializable{

	private static final long serialVersionUID = -4982548495859041214L;
	
	private List<String> header;
	
	private List<Object[]> rows;
	
	private List<String> fields;
	
	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public List<String> getHeader() {
		return header;
	}

	public void setHeader(List<String> header) {
		this.header = header;
	}

	public List<Object[]> getRows() {
		return rows;
	}

	public void setRows(List<Object[]> rows) {
		this.rows = rows;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TableDTO() {}
	
	public TableDTO(List<String> header, List<Object[]> rows, List<String> fields) {
		this.header = header;
		this.rows = rows;
		this.fields = fields;
	}

}
