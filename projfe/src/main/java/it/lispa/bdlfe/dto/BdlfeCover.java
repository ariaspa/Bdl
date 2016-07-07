package it.lispa.bdlfe.dto;

import java.math.BigDecimal;

public class BdlfeCover extends BdlfeBookreaderPage{
	
	private BigDecimal codiceOO;
	
	/**
	 * 
	 */
	public BdlfeCover() {
	}
	
	/**
	 * 
	 * @param id
	 * @param thumbWidth
	 * @param thumbHeight
	 * @param readerWidth
	 * @param readerHeight
	 * @param zoomWidth
	 * @param zoomHeight
	 * @param codiceOO
	 */
	public BdlfeCover(String id, Integer thumbWidth, Integer thumbHeight,
			Integer readerWidth, Integer readerHeight, Integer zoomWidth, Integer zoomHeight, BigDecimal codiceOO) {
		super(id, thumbWidth, thumbHeight, readerWidth, readerHeight, zoomWidth, zoomHeight);
		this.codiceOO = codiceOO;
	}

	/**
	 * @return the codiceOO
	 */
	public BigDecimal getCodiceOO() {
		return codiceOO;
	}

	/**
	 * @param codiceOO the codiceOO to set
	 */
	public void setCodiceOO(BigDecimal codiceOO) {
		this.codiceOO = codiceOO;
	}
}