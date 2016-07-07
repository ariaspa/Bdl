package it.lispa.bdl.server.rest.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class JSONBdlBookreaderPage.
 */
@XmlRootElement
public class JSONBdlBookreaderPage {
	private String id;
	private Integer thumbWidth;
	private Integer thumbHeight;
	private Integer readerWidth;
	private Integer readerHeight;
	private Integer zoomWidth;
	private Integer zoomHeight;
	
	private Boolean doublePage;

	/**
	 * Istanzia un nuovo JSON bdl bookreader page.
	 *
	 * @param id  id
	 * @param thumbWidth  thumb width
	 * @param thumbHeight  thumb height
	 * @param readerWidth  reader width
	 * @param readerHeight  reader height
	 * @param zoomWidth  zoom width
	 * @param zoomHeight  zoom height
	 */
	public JSONBdlBookreaderPage(String id, Integer thumbWidth, Integer thumbHeight,
			Integer readerWidth, Integer readerHeight, Integer zoomWidth, Integer zoomHeight) {
		this.id = id;
		this.thumbWidth = thumbWidth;
		this.thumbHeight = thumbHeight;
		this.readerWidth = readerWidth;
		this.readerHeight = readerHeight;
		this.zoomWidth = zoomWidth;
		this.zoomHeight = zoomHeight;
	}
	/**
	 * Istanzia un nuovo JSON bdl bookreader page.
	 *
	 * @param id  id
	 * @param thumbWidth  thumb width
	 * @param thumbHeight  thumb height
	 * @param readerWidth  reader width
	 * @param readerHeight  reader height
	 * @param zoomWidth  zoom width
	 * @param zoomHeight  zoom height
	 * @param doublePage  doublePage
	 */
	public JSONBdlBookreaderPage(String id, Integer thumbWidth, Integer thumbHeight,
			Integer readerWidth, Integer readerHeight, Integer zoomWidth, Integer zoomHeight, Boolean doublePage) {
		this.id = id;
		this.thumbWidth = thumbWidth;
		this.thumbHeight = thumbHeight;
		this.readerWidth = readerWidth;
		this.readerHeight = readerHeight;
		this.zoomWidth = zoomWidth;
		this.zoomHeight = zoomHeight;
		this.doublePage = doublePage;
	}

	/**
	 * Legge id.
	 *
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Imposta id.
	 *
	 * @param id nuovo id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Legge thumb width.
	 *
	 * @return thumb width
	 */
	public Integer getThumbWidth() {
		return thumbWidth;
	}

	/**
	 * Imposta thumb width.
	 *
	 * @param thumbWidth nuovo thumb width
	 */
	public void setThumbWidth(Integer thumbWidth) {
		this.thumbWidth = thumbWidth;
	}

	/**
	 * Legge thumb height.
	 *
	 * @return thumb height
	 */
	public Integer getThumbHeight() {
		return thumbHeight;
	}

	/**
	 * Imposta thumb height.
	 *
	 * @param thumbHeight nuovo thumb height
	 */
	public void setThumbHeight(Integer thumbHeight) {
		this.thumbHeight = thumbHeight;
	}

	/**
	 * Legge reader width.
	 *
	 * @return reader width
	 */
	public Integer getReaderWidth() {
		return readerWidth;
	}

	/**
	 * Imposta reader width.
	 *
	 * @param readerWidth nuovo reader width
	 */
	public void setReaderWidth(Integer readerWidth) {
		this.readerWidth = readerWidth;
	}

	/**
	 * Legge reader height.
	 *
	 * @return reader height
	 */
	public Integer getReaderHeight() {
		return readerHeight;
	}

	/**
	 * Imposta reader height.
	 *
	 * @param readerHeight nuovo reader height
	 */
	public void setReaderHeight(Integer readerHeight) {
		this.readerHeight = readerHeight;
	}

	/**
	 * Legge zoom width.
	 *
	 * @return zoom width
	 */
	public Integer getZoomWidth() {
		return zoomWidth;
	}

	/**
	 * Imposta zoom width.
	 *
	 * @param zoomWidth nuovo zoom width
	 */
	public void setZoomWidth(Integer zoomWidth) {
		this.zoomWidth = zoomWidth;
	}

	/**
	 * Legge zoom height.
	 *
	 * @return zoom height
	 */
	public Integer getZoomHeight() {
		return zoomHeight;
	}

	/**
	 * Imposta zoom height.
	 *
	 * @param zoomHeight nuovo zoom height
	 */
	public void setZoomHeight(Integer zoomHeight) {
		this.zoomHeight = zoomHeight;
	}
	public Boolean getDoublePage() {
		return doublePage;
	}
	public void setDoublePage(Boolean doublePage) {
		this.doublePage = doublePage;
	}

}