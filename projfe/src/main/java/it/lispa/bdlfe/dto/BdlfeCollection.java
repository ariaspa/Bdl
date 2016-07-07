package it.lispa.bdlfe.dto;

import java.math.BigDecimal;

public class BdlfeCollection implements Comparable<BdlfeCollection> {
	
	@Override
	public int compareTo(BdlfeCollection o) {
		final int BEFORE = -1;
	    final int EQUAL  =  0;
	    final int AFTER  =  1;
	    if(o.getInstitute()!=null && this.getInstitute()!=null) {
	    	return o.getInstitute().getName().compareTo(this.getInstitute().getName());
	    } else if(o.getInstitute()!=null && this.getInstitute()==null) {
	    	return BEFORE;
	    } else if(o.getInstitute()==null && this.getInstitute()!=null) {
	    	return AFTER;
	    } else {
	    	return EQUAL;
	    }
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((area == null) 			? 0 : area.hashCode());
		result = prime * result + ((descrEN == null) 		? 0 : descrEN.hashCode());
		result = prime * result + ((descrIT == null) 		? 0 : descrIT.hashCode());
		result = prime * result + ((geoCoverage == null) 	? 0 : geoCoverage.hashCode());
		result = prime * result + ((id == null) 			? 0 : id.hashCode());
		result = prime * result + ((institute == null) 		? 0 : institute.hashCode());
		result = prime * result + ((mainItem == null) 		? 0 : mainItem.hashCode());
		result = prime * result + ((numItems == null) 		? 0 : numItems.hashCode());
		result = prime * result + ((period == null) 		? 0 : period.hashCode());
		result = prime * result + ((title == null) 			? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BdlfeCollection other = (BdlfeCollection) obj;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (descrEN == null) {
			if (other.descrEN != null)
				return false;
		} else if (!descrEN.equals(other.descrEN))
			return false;
		if (descrIT == null) {
			if (other.descrIT != null)
				return false;
		} else if (!descrIT.equals(other.descrIT))
			return false;
		if (geoCoverage == null) {
			if (other.geoCoverage != null)
				return false;
		} else if (!geoCoverage.equals(other.geoCoverage))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (institute == null) {
			if (other.institute != null)
				return false;
		} else if (!institute.equals(other.institute))
			return false;
		if (mainItem == null) {
			if (other.mainItem != null)
				return false;
		} else if (!mainItem.equals(other.mainItem))
			return false;
		if (numItems == null) {
			if (other.numItems != null)
				return false;
		} else if (!numItems.equals(other.numItems))
			return false;
		if (period == null) {
			if (other.period != null)
				return false;
		} else if (!period.equals(other.period))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	private BigDecimal id;
	
	private BdlfeRef institute;
	
	private String title;
	
	private BdlfeRef area;
	
	private String geoCoverage;
	
	private String period;
	
	private String descrIT;
	
	private String descrEN;
	
	private Integer numItems;
	
	private BdlfeItem mainItem;

	public BdlfeCollection() {
	}
	
	public BdlfeCollection(BigDecimal id, BdlfeRef institute, String title, BdlfeRef area, String geoCoverage,
			String period, String descrIT, String descrEN, Integer numItems, BdlfeItem mainItem) {
		this.id = id;
		this.institute = institute;
		this.title = title;
		this.area = area;
		this.geoCoverage = geoCoverage;
		this.period = period;
		this.descrIT = descrIT;
		this.descrEN = descrEN;
		this.numItems = numItems;
		this.mainItem = mainItem;
	}
	
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	
	public BdlfeRef getInstitute() {
		return institute;
	}
	public void setInstitute(BdlfeRef institute) {
		this.institute = institute;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public BdlfeRef getArea() {
		return area;
	}
	public void setArea(BdlfeRef area) {
		this.area = area;
	}
	
	public String getGeoCoverage() {
		return geoCoverage;
	}
	public void setGeoCoverage(String geoCoverage) {
		this.geoCoverage = geoCoverage;
	}
	
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	
	public String getDescrIT() {
		return descrIT;
	}
	public void setDescrIT(String descrIT) {
		this.descrIT = descrIT;
	}
	
	public String getDescrEN() {
		return descrEN;
	}
	public void setDescrEN(String descrEN) {
		this.descrEN = descrEN;
	}
	
	public Integer getNumItems() {
		return numItems;
	}
	public void setNumItems(Integer numItems) {
		this.numItems = numItems;
	}
	
	public BdlfeItem getMainItem() {
		return mainItem;
	}
	public void setMainItem(BdlfeItem mainItem) {
		this.mainItem = mainItem;
	}
}