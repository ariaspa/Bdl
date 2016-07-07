package it.lispa.bdlfe.dto;

import java.util.List;


public class BdlfeSearchPaginated extends BdlfeSearch {
	private Integer resCount;

	/**
	 * 
	 */
	public BdlfeSearchPaginated() {
	}

    /**
     * 
     * @param filters
     * @param results
     * @param otherfilters
     * @param resCount
     */
	public BdlfeSearchPaginated(List<BdlfeSearchFilter> filters,
			List<BdlfeItemLight> results, List<BdlfeSearchFilter> otherfilters, 
			Integer resCount) {
		super(filters, results, otherfilters);
		this.filters = filters;
		this.results = results;
		this.otherfilters = otherfilters;
		this.resCount = resCount;
	}

	/**
	 * @return the resCount
	 */
	public Integer getResCount() {
		return resCount;
	}

	/**
	 * @param resCount the resCount to set
	 */
	public void setResCount(Integer resCount) {
		this.resCount = resCount;
	}
}