package it.lispa.bdl.server.rest.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class JSONBdlSearch.
 */
@XmlRootElement
public class JSONBdlSearch {
	
	protected List<JSONBdlSearchFilter> filters;
	protected List<JSONBdlItemLight> results;
	protected List<JSONBdlSearchFilter> otherfilters;

	/**
	 * Istanzia un nuovo JSON bdl search.
	 */
	public JSONBdlSearch(){
		// do nothing...
	}
	
	/**
	 * Istanzia un nuovo JSON bdl search.
	 *
	 * @param filters  filters
	 * @param results  results
	 * @param otherfilters  otherfilters
	 */
	public JSONBdlSearch(List<JSONBdlSearchFilter> filters,
			List<JSONBdlItemLight> results,
			List<JSONBdlSearchFilter> otherfilters) {
		this.filters = filters;
		this.results = results;
		this.otherfilters = otherfilters;
	}

	/**
	 * Legge filters.
	 *
	 * @return filters
	 */
	public List<JSONBdlSearchFilter> getFilters() {
		return filters;
	}
	
	/**
	 * Imposta filters.
	 *
	 * @param filters nuovo filters
	 */
	public void setFilters(List<JSONBdlSearchFilter> filters) {
		this.filters = filters;
	}
	
	/**
	 * Legge results.
	 *
	 * @return results
	 */
	public List<JSONBdlItemLight> getResults() {
		return results;
	}
	
	/**
	 * Imposta results.
	 *
	 * @param results nuovo results
	 */
	public void setResults(List<JSONBdlItemLight> results) {
		this.results = results;
	}
	
	/**
	 * Legge otherfilters.
	 *
	 * @return otherfilters
	 */
	public List<JSONBdlSearchFilter> getOtherfilters() {
		return otherfilters;
	}
	
	/**
	 * Imposta otherfilters.
	 *
	 * @param otherfilters nuovo otherfilters
	 */
	public void setOtherfilters(List<JSONBdlSearchFilter> otherfilters) {
		this.otherfilters = otherfilters;
	}
}