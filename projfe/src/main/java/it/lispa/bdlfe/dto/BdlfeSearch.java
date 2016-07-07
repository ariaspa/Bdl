package it.lispa.bdlfe.dto;

import java.util.List;

public class BdlfeSearch {
	
	protected List<BdlfeSearchFilter> filters;
	protected List<BdlfeItemLight> results;
	protected List<BdlfeSearchFilter> otherfilters;

	/**
	 * 
	 */
	public BdlfeSearch() {
	}

	/**
	 * @param filters
	 * @param results
	 * @param otherfilters
	 * @param resultsCount
	 */
	public BdlfeSearch(List<BdlfeSearchFilter> filters,
			List<BdlfeItemLight> results, List<BdlfeSearchFilter> otherfilters) {
		this.filters = filters;
		this.results = results;
		this.otherfilters = otherfilters;
	}


	/**
	 * @return the filters
	 */
	public List<BdlfeSearchFilter> getFilters() {
		return filters;
	}

	/**
	 * @param filters the filters to set
	 */
	public void setFilters(List<BdlfeSearchFilter> filters) {
		this.filters = filters;
	}

	/**
	 * @return the results
	 */
	public List<BdlfeItemLight> getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(List<BdlfeItemLight> results) {
		this.results = results;
	}

	/**
	 * @return the otherfilters
	 */
	public List<BdlfeSearchFilter> getOtherfilters() {
		return otherfilters;
	}

	/**
	 * @param otherfilters the otherfilters to set
	 */
	public void setOtherfilters(List<BdlfeSearchFilter> otherfilters) {
		this.otherfilters = otherfilters;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BdlfeSearch [filters=");
		builder.append(filters);
		builder.append(", results=");
		builder.append(results);
		builder.append(", otherfilters=");
		builder.append(otherfilters);
		builder.append("]");
		return builder.toString();
	}
}