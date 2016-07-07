package it.lispa.bdlfe.dto;

import it.lispa.bdlfe.dto.BdlfeBookreaderTOC;

import java.util.List;

public class BdlfeBookreaderTOC {
	private Integer id;
	private String label;
	private Integer page;
	private List<BdlfeBookreaderTOC> children;

	public BdlfeBookreaderTOC() {
	}

	public BdlfeBookreaderTOC(Integer id, String label, Integer page,
			List<BdlfeBookreaderTOC> children) {
		this.id = id;
		this.label = label;
		this.page = page;
		this.children = children;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public List<BdlfeBookreaderTOC> getChildren() {
		return children;
	}
	public void setChildren(List<BdlfeBookreaderTOC> children) {
		this.children = children;
	}
}