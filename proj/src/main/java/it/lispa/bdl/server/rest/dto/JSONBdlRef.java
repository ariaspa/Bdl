package it.lispa.bdl.server.rest.dto;

import java.math.BigDecimal;

/**
 * Class JSONBdlRef.
 */
public class JSONBdlRef {
	private BigDecimal id;
	private String name;
	
	/**
	 * Istanzia un nuovo JSON bdl ref.
	 *
	 * @param id  id
	 * @param name  name
	 */
	public JSONBdlRef(BigDecimal id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public JSONBdlRef() {
	}

	/**
	 * Legge id.
	 *
	 * @return id
	 */
	public BigDecimal getId() {
		return id;
	}
	
	/**
	 * Imposta id.
	 *
	 * @param id nuovo id
	 */
	public void setId(BigDecimal id) {
		this.id = id;
	}
	
	/**
	 * Legge name.
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Imposta name.
	 *
	 * @param name nuovo name
	 */
	public void setName(String name) {
		this.name = name;
	}
}