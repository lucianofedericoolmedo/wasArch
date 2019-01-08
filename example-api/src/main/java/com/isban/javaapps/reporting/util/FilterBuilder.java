package com.isban.javaapps.reporting.util;

public class FilterBuilder {

	private String filter = "";
	
	public FilterBuilder addFilter(String field, String value) {
		if(value != null && !value.isEmpty()) {
			String comparator = value.contains("%") ? "LIKE " : "= ";
			if(this.filter.isEmpty()) {
				this.filter += field + comparator + "'" + value + "'"; 
			}else {
				this.filter += " and " + field + comparator + "'" + value + "'";
			}
		}
		return this;
	}
	
	public String getFilter() {
		return this.filter;
	}
	
}
