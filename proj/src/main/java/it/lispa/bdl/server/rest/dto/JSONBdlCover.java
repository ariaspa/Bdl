package it.lispa.bdl.server.rest.dto;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class JSONBdlCover.
 */
@XmlRootElement
public class JSONBdlCover extends JSONBdlBookreaderPage{
	
	private BigDecimal codiceOO;
	
	/**
	 * Istanzia un nuovo JSON bdl cover.
	 *
	 * @param id  id
	 * @param thumbWidth  thumb width
	 * @param thumbHeight  thumb height
	 * @param readerWidth  reader width
	 * @param readerHeight  reader height
	 * @param zoomWidth  zoom width
	 * @param zoomHeight  zoom height
	 */
	public JSONBdlCover(String id, Integer thumbWidth, Integer thumbHeight,
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