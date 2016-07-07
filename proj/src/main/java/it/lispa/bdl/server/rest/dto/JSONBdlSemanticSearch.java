package it.lispa.bdl.server.rest.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class JSONBdlSemanticSearch.
 */
@XmlRootElement
public class JSONBdlSemanticSearch {
	private List<JSONBdlRef> titles;
	private List<JSONBdlRef> authors;
	private List<JSONBdlRef> subjects;
	
	/**
	 * Istanzia un nuovo JSON bdl semantic search.
	 *
	 * @param titles  titles
	 * @param authors  authors
	 * @param subjects  subjects
	 */
	public JSONBdlSemanticSearch(List<JSONBdlRef> titles,
			List<JSONBdlRef> authors, List<JSONBdlRef> subjects) {
		this.titles = titles;
		this.authors = authors;
		this.subjects = subjects;
	}
	
	/**
	 * Legge titles.
	 *
	 * @return titles
	 */
	public List<JSONBdlRef> getTitles() {
		return titles;
	}
	
	/**
	 * Imposta titles.
	 *
	 * @param titles nuovo titles
	 */
	public void setTitles(List<JSONBdlRef> titles) {
		this.titles = titles;
	}
	
	/**
	 * Legge authors.
	 *
	 * @return authors
	 */
	public List<JSONBdlRef> getAuthors() {
		return authors;
	}
	
	/**
	 * Imposta authors.
	 *
	 * @param authors nuovo authors
	 */
	public void setAuthors(List<JSONBdlRef> authors) {
		this.authors = authors;
	}
	
	/**
	 * Legge subjects.
	 *
	 * @return subjects
	 */
	public List<JSONBdlRef> getSubjects() {
		return subjects;
	}
	
	/**
	 * Imposta subjects.
	 *
	 * @param subjects nuovo subjects
	 */
	public void setSubjects(List<JSONBdlRef> subjects) {
		this.subjects = subjects;
	}
}