package it.lispa.bdl.server.rest.dto;

import java.math.BigDecimal;

public class JSONBdlRefCount extends JSONBdlRef {

	private BigDecimal countValue;
	
	public JSONBdlRefCount(BigDecimal id, String name, BigDecimal countValue) {
		super(id, name);
		this.countValue = countValue;
	}

	public BigDecimal getCountValue() {
		return countValue;
	}
	public void setCountValue(BigDecimal countValue) {
		this.countValue = countValue;
	}
}
