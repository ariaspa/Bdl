package it.lispa.bdlfe.dto;


public class BdlfeBreadcrumb {
	private String url;
	private String label;

	public BdlfeBreadcrumb() {
	}

	public BdlfeBreadcrumb(String url, String label) {
		this.url = url;
		this.label = label;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}