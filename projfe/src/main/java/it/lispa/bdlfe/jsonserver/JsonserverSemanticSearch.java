package it.lispa.bdlfe.jsonserver;

import java.math.BigDecimal;

public class JsonserverSemanticSearch {

	private BigDecimal id;
	private String name;
	private String category;

	public JsonserverSemanticSearch() {
		
	}
	public JsonserverSemanticSearch(BigDecimal id, String name, String category) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
	}
	
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
}
