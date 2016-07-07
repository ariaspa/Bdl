package it.lispa.bdl.server.rest.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class JSONBdlBookreaderTOC.
 */
@XmlRootElement
public class JSONBdlBookreaderTOC {
	private int id;
	private String label;
	private Integer page;
	private List<JSONBdlBookreaderTOC> children;
	
	/**
	 * Istanzia un nuovo JSON bdl bookreader toc.
	 *
	 * @param id  id
	 * @param label  label
	 * @param page  page
	 * @param children  children
	 */
	public JSONBdlBookreaderTOC(int id, String label, Integer page,
			List<JSONBdlBookreaderTOC> children) {
		this.id = id;
		this.label = label;
		this.page = page;
		this.children = children;
	}
	
	/**
	 * Legge id.
	 *
	 * @return id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Imposta id.
	 *
	 * @param id nuovo id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Legge label.
	 *
	 * @return label
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Imposta label.
	 *
	 * @param label nuovo label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * Legge page.
	 *
	 * @return page
	 */
	public Integer getPage() {
		return page;
	}
	
	/**
	 * Imposta page.
	 *
	 * @param page nuovo page
	 */
	public void setPage(Integer page) {
		this.page = page;
	}
	
	/**
	 * Legge children.
	 *
	 * @return children
	 */
	public List<JSONBdlBookreaderTOC> getChildren() {
		return children;
	}
	
	/**
	 * Imposta children.
	 *
	 * @param children nuovo children
	 */
	public void setChildren(List<JSONBdlBookreaderTOC> children) {
		this.children = children;
	}
}