package it.lispa.bdlfe.dto;

public class BdlfeBookreaderPage {
	private String id;
	private Integer thumbWidth;
	private Integer thumbHeight;
	private Integer readerWidth;
	private Integer readerHeight;
	private Integer zoomWidth;
	private Integer zoomHeight;
	private Boolean doublePage;

	public BdlfeBookreaderPage() {
	}
	public BdlfeBookreaderPage(String id, Integer thumbWidth, Integer thumbHeight,
			Integer readerWidth, Integer readerHeight, Integer zoomWidth, Integer zoomHeight) {
		this.id = id;
		this.thumbWidth = thumbWidth;
		this.thumbHeight = thumbHeight;
		this.readerWidth = readerWidth;
		this.readerHeight = readerHeight;
		this.zoomWidth = zoomWidth;
		this.zoomHeight = zoomHeight;
	}
	public BdlfeBookreaderPage(String id, Integer thumbWidth, Integer thumbHeight,
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getThumbWidth() {
		return thumbWidth;
	}

	public void setThumbWidth(Integer thumbWidth) {
		this.thumbWidth = thumbWidth;
	}

	public Integer getThumbHeight() {
		return thumbHeight;
	}

	public void setThumbHeight(Integer thumbHeight) {
		this.thumbHeight = thumbHeight;
	}

	public Integer getReaderWidth() {
		return readerWidth;
	}

	public void setReaderWidth(Integer readerWidth) {
		this.readerWidth = readerWidth;
	}

	public Integer getReaderHeight() {
		return readerHeight;
	}

	public void setReaderHeight(Integer readerHeight) {
		this.readerHeight = readerHeight;
	}

	public Integer getZoomWidth() {
		return zoomWidth;
	}

	public void setZoomWidth(Integer zoomWidth) {
		this.zoomWidth = zoomWidth;
	}

	public Integer getZoomHeight() {
		return zoomHeight;
	}

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