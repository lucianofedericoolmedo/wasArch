package com.isban.javaapps.reporting.util;

import java.util.List;

public class PaginatedResult {

	// Instance Variables
	private List<Object[]> result;
	private String pages;
	
	// Constructors
	public PaginatedResult() {

	}

	public PaginatedResult(List<Object[]> resultList, String pages) {
		this.result = resultList;
		this.pages = pages;
	}
	
	// Accessors
	public List<Object[]> getResult() {
		return result;
	}
	
	public void setResult(List<Object[]> result) {
		this.result = result;
	}
	
	public String getPages() {
		return pages;
	}
	
	public void setPages(String pages) {
		this.pages = pages;
	}
	
}
