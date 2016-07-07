package it.lispa.bdlfe.dto;

import java.util.List;

public class BdlfeSemanticSearch {

	private List<BdlfeRef> titles;
	private List<BdlfeRef> authors;
	private List<BdlfeRef> subjects;

	public BdlfeSemanticSearch() {
	}
	public BdlfeSemanticSearch(List<BdlfeRef> titles,
			List<BdlfeRef> authors, List<BdlfeRef> subjects) {
		this.titles = titles;
		this.authors = authors;
		this.subjects = subjects;
	}
	
	public List<BdlfeRef> getTitles() {
		return titles;
	}
	public void setTitles(List<BdlfeRef> titles) {
		this.titles = titles;
	}
	public List<BdlfeRef> getAuthors() {
		return authors;
	}
	public void setAuthors(List<BdlfeRef> authors) {
		this.authors = authors;
	}
	public List<BdlfeRef> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<BdlfeRef> subjects) {
		this.subjects = subjects;
	}
}
