package it.lispa.bdl.server.rest.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class JSONBdlMapreader.
 */
@XmlRootElement
public class JSONBdlMapreader {
	private int maxZoomLevel;
	private int minZoomLevel;
	private int maxZoomW;
	private int maxZoomH;
	private int borderW;
	private int borderH;
	
	/**
	 * Istanzia un nuovo JSON bdl mapreader.
	 *
	 * @param maxZoomLevel  max zoom level
	 * @param minZoomLevel  min zoom level
	 * @param maxZoomW  max zoom w
	 * @param maxZoomH  max zoom h
	 * @param borderW  border w
	 * @param borderH  border h
	 */
	public JSONBdlMapreader(int maxZoomLevel, int minZoomLevel,
			int maxZoomW, int maxZoomH, int borderW, int borderH) {
		this.maxZoomLevel = maxZoomLevel;
		this.minZoomLevel = minZoomLevel;
		this.maxZoomW = maxZoomW;
		this.maxZoomH = maxZoomH;
		this.borderW = borderW;
		this.borderH = borderH;
	}

	/**
	 * Legge max zoom level.
	 *
	 * @return max zoom level
	 */
	public int getMaxZoomLevel() {
		return maxZoomLevel;
	}

	/**
	 * Imposta max zoom level.
	 *
	 * @param maxZoomLevel nuovo max zoom level
	 */
	public void setMaxZoomLevel(int maxZoomLevel) {
		this.maxZoomLevel = maxZoomLevel;
	}

	/**
	 * Legge min zoom level.
	 *
	 * @return min zoom level
	 */
	public int getMinZoomLevel() {
		return minZoomLevel;
	}

	/**
	 * Imposta min zoom level.
	 *
	 * @param minZoomLevel nuovo min zoom level
	 */
	public void setMinZoomLevel(int minZoomLevel) {
		this.minZoomLevel = minZoomLevel;
	}

	/**
	 * Legge max zoom w.
	 *
	 * @return max zoom w
	 */
	public int getMaxZoomW() {
		return maxZoomW;
	}

	/**
	 * Imposta max zoom w.
	 *
	 * @param maxZoomW nuovo max zoom w
	 */
	public void setMaxZoomW(int maxZoomW) {
		this.maxZoomW = maxZoomW;
	}

	/**
	 * Legge max zoom h.
	 *
	 * @return max zoom h
	 */
	public int getMaxZoomH() {
		return maxZoomH;
	}

	/**
	 * Imposta max zoom h.
	 *
	 * @param maxZoomH nuovo max zoom h
	 */
	public void setMaxZoomH(int maxZoomH) {
		this.maxZoomH = maxZoomH;
	}

	/**
	 * Legge border w.
	 *
	 * @return border w
	 */
	public int getBorderW() {
		return borderW;
	}

	/**
	 * Imposta border w.
	 *
	 * @param borderW nuovo border w
	 */
	public void setBorderW(int borderW) {
		this.borderW = borderW;
	}

	/**
	 * Legge border h.
	 *
	 * @return border h
	 */
	public int getBorderH() {
		return borderH;
	}

	/**
	 * Imposta border h.
	 *
	 * @param borderH nuovo border h
	 */
	public void setBorderH(int borderH) {
		this.borderH = borderH;
	}
}