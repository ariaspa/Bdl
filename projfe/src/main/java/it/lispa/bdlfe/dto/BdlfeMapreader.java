package it.lispa.bdlfe.dto;

public class BdlfeMapreader {
	private Integer maxZoomLevel;
	private Integer minZoomLevel;
	private Integer maxZoomW;
	private Integer maxZoomH;
	private Integer borderW;
	private Integer borderH;

	public BdlfeMapreader() {
	}
	public BdlfeMapreader(Integer maxZoomLevel, Integer minZoomLevel,
			Integer maxZoomW, Integer maxZoomH, Integer borderW, Integer borderH) {
		this.maxZoomLevel = maxZoomLevel;
		this.minZoomLevel = minZoomLevel;
		this.maxZoomW = maxZoomW;
		this.maxZoomH = maxZoomH;
		this.borderW = borderW;
		this.borderH = borderH;
	}

	public Integer getMaxZoomLevel() {
		return maxZoomLevel;
	}

	public void setMaxZoomLevel(Integer maxZoomLevel) {
		this.maxZoomLevel = maxZoomLevel;
	}

	public Integer getMinZoomLevel() {
		return minZoomLevel;
	}

	public void setMinZoomLevel(Integer minZoomLevel) {
		this.minZoomLevel = minZoomLevel;
	}

	public Integer getMaxZoomW() {
		return maxZoomW;
	}

	public void setMaxZoomW(Integer maxZoomW) {
		this.maxZoomW = maxZoomW;
	}

	public Integer getMaxZoomH() {
		return maxZoomH;
	}

	public void setMaxZoomH(Integer maxZoomH) {
		this.maxZoomH = maxZoomH;
	}

	public Integer getBorderW() {
		return borderW;
	}

	public void setBorderW(Integer borderW) {
		this.borderW = borderW;
	}

	public Integer getBorderH() {
		return borderH;
	}

	public void setBorderH(Integer borderH) {
		this.borderH = borderH;
	}
}