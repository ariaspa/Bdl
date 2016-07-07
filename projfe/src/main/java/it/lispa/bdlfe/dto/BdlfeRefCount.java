package it.lispa.bdlfe.dto;

import java.math.BigDecimal;

public class BdlfeRefCount extends BdlfeRef {

	private BigDecimal countValue;
	
	public BdlfeRefCount() {
	}
	
	public BdlfeRefCount(BigDecimal id, String name, BigDecimal countValue) {
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
