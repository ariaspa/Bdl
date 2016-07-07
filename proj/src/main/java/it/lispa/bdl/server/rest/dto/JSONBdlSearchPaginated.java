package it.lispa.bdl.server.rest.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class JSONBdlSearch.
 */
@XmlRootElement
public class JSONBdlSearchPaginated extends JSONBdlSearch {
	
	private Integer resCount;
	
	public JSONBdlSearchPaginated(List<JSONBdlSearchFilter> filters,
			List<JSONBdlItemLight> results,
			List<JSONBdlSearchFilter> otherfilters,
			Integer resCount) {
		this.filters = filters;
		this.results = results;
		this.otherfilters = otherfilters;
		this.resCount = resCount;
	}
	
	public JSONBdlSearchPaginated() {
	}

	public Integer getResCount() {
		return resCount;
	}
	public void setResCount(Integer resCount) {
		this.resCount = resCount;
	}
}