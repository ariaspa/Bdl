package it.lispa.bdlfe.dto;

import java.math.BigDecimal;

public class BdlfeRef {
	
	protected BigDecimal id;
	protected String name;

	public BdlfeRef() {
	}
	
	public BdlfeRef(BigDecimal id, String name) {
		this.id = id;
		this.name = name;
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
}