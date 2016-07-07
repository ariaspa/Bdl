package it.lispa.bdlfe.form;

import it.lispa.bdlfe.dto.BdlfeSearchFilter;
import it.lispa.bdlfe.utils.BdlfeSearchOrderOptions;

import java.math.BigDecimal;
import java.util.List;

public class SearchForm {
	
	private BdlfeSearchOrderOptions orderOption;
	private List<BdlfeSearchFilter> compositionFilters;
	private BdlfeSearchFilter fakeFilter;
	private BdlfeSearchFilter dateFilter;
	private BigDecimal[] langFilters;
	private BigDecimal[] typeFilters;
	private BigDecimal[] kindFilters;
	private BigDecimal[] subjectFilters;
	private BigDecimal graphicmaterialFilter;
	private BigDecimal supportFilter;
	private BigDecimal tecniqueFilter;
	private BigDecimal[] collectionFilters;

	public static final String mainFormAction = "/search/";
	public static String getMainFormAction() {
		return mainFormAction;
	}

	public SearchForm() {
		subjectFilters = new BigDecimal[] {null,null,null,null,null,null,null,null,null};
		collectionFilters = new BigDecimal[] {null,null,null};
	}

	public SearchForm(BdlfeSearchOrderOptions orderOption,
			List<BdlfeSearchFilter> compositionFilters,
			BdlfeSearchFilter fakeFilter,
			BdlfeSearchFilter dateFilter,
			BigDecimal[] langFilters,
			BigDecimal[] typeFilters,
			BigDecimal[] kindFilters,
			BigDecimal[] subjectFilters,
			BigDecimal graphicmaterialFilter,
			BigDecimal supportFilter,
			BigDecimal tecniqueFilter,
			BigDecimal[] collectionFilters) {
		super();
		this.orderOption = orderOption;
		this.compositionFilters = compositionFilters;
		this.fakeFilter = fakeFilter;
		this.dateFilter = dateFilter;
		this.langFilters = langFilters;
		this.typeFilters = typeFilters;
		this.kindFilters = kindFilters;
		this.subjectFilters = subjectFilters;
		this.graphicmaterialFilter = graphicmaterialFilter;
		this.supportFilter = supportFilter;
		this.tecniqueFilter = tecniqueFilter;
		this.collectionFilters = collectionFilters;
	}
	
	public BdlfeSearchOrderOptions getOrderOption() {
		return orderOption;
	}
	public void setOrderOption(BdlfeSearchOrderOptions orderOption) {
		this.orderOption = orderOption;
	}
	public List<BdlfeSearchFilter> getCompositionFilters() {
		return compositionFilters;
	}
	public void setCompositionFilters(List<BdlfeSearchFilter> compositionFilters) {
		this.compositionFilters = compositionFilters;
	}
	public BdlfeSearchFilter getFakeFilter() {
		return fakeFilter;
	}

	public void setFakeFilter(BdlfeSearchFilter fakeFilter) {
		this.fakeFilter = fakeFilter;
	}

	public BdlfeSearchFilter getDateFilter() {
		return dateFilter;
	}
	public void setDateFilter(BdlfeSearchFilter dateFilter) {
		this.dateFilter = dateFilter;
	}
	public BigDecimal[] getLangFilters() {
		return langFilters;
	}
	public void setLangFilters(BigDecimal[] langFilters) {
		this.langFilters = langFilters;
	}
	public BigDecimal[] getTypeFilters() {
		return typeFilters;
	}
	public void setTypeFilters(BigDecimal[] typeFilters) {
		this.typeFilters = typeFilters;
	}
	public BigDecimal[] getKindFilters() {
		return kindFilters;
	}
	public void setKindFilters(BigDecimal[] kindFilters) {
		this.kindFilters = kindFilters;
	}

	public BigDecimal[] getSubjectFilters() {
		return subjectFilters;
	}

	public void setSubjectFilters(BigDecimal[] subjectFilters) {
		this.subjectFilters = subjectFilters;
	}

	public BigDecimal getGraphicmaterialFilter() {
		return graphicmaterialFilter;
	}

	public void setGraphicmaterialFilter(BigDecimal graphicmaterialFilter) {
		this.graphicmaterialFilter = graphicmaterialFilter;
	}

	public BigDecimal getSupportFilter() {
		return supportFilter;
	}

	public void setSupportFilter(BigDecimal supportFilter) {
		this.supportFilter = supportFilter;
	}

	public BigDecimal getTecniqueFilter() {
		return tecniqueFilter;
	}

	public void setTecniqueFilter(BigDecimal tecniqueFilter) {
		this.tecniqueFilter = tecniqueFilter;
	}

	public BigDecimal[] getCollectionFilters() {
		return collectionFilters;
	}

	public void setCollectionFilters(BigDecimal[] collectionFilters) {
		this.collectionFilters = collectionFilters;
	}
	
	public Boolean isNotNull() {
		if(orderOption!=null)
			return Boolean.TRUE;
		if(compositionFilters!=null)
			return Boolean.TRUE;
		if(fakeFilter!=null)
			return Boolean.TRUE;
		if(dateFilter!=null)
			return Boolean.TRUE;
		if(langFilters!=null)
			return Boolean.TRUE;
		if(typeFilters!=null)
			return Boolean.TRUE;
		if(kindFilters!=null)
			return Boolean.TRUE;
		if(subjectFilters!=null) {
			for(BigDecimal f : subjectFilters) {
				if(f!=null)
					return Boolean.TRUE;
			}
		}
		if(graphicmaterialFilter!=null)
			return Boolean.TRUE;
		if(supportFilter!=null)
			return Boolean.TRUE;
		if(tecniqueFilter!=null)
			return Boolean.TRUE;
		if(collectionFilters!=null) {
			for(BigDecimal f : collectionFilters) {
				if(f!=null)
					return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
}