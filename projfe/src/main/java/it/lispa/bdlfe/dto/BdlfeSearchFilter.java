package it.lispa.bdlfe.dto;

import java.util.ArrayList;
import java.util.List;

public class BdlfeSearchFilter {
	private Integer order;
	private String logicalOperator;
	private String filterType;
	private String comparisonOperator;
	private List<BdlfeSearchComparator> comparisonValues;
	private Integer itemNumber;

	public BdlfeSearchFilter() {
	}
	public BdlfeSearchFilter(Integer order, String logicalOperator,
			String filterType, String comparisonOperator,
			List<BdlfeSearchComparator> comparisonValues) {
		this.order = order;
		this.logicalOperator = logicalOperator;
		this.filterType = filterType;
		this.comparisonOperator = comparisonOperator;
		this.comparisonValues = comparisonValues;
		this.itemNumber=0;
	}
	public BdlfeSearchFilter(Integer order, String logicalOperator,
			String filterType, String comparisonOperator,
			List<BdlfeSearchComparator> comparisonValues, Integer itemNumber) {
		this.order = order;
		this.logicalOperator = logicalOperator;
		this.filterType = filterType;
		this.comparisonOperator = comparisonOperator;
		this.comparisonValues = comparisonValues;
		this.itemNumber = itemNumber;
	}
	public BdlfeSearchFilter(Integer order, String logicalOperator,
			String filterType, String comparisonOperator,
			String comparisonValue, String comparisonLabel) {
		
		List<BdlfeSearchComparator> myComparisonValues = new ArrayList<BdlfeSearchComparator>();
		
		BdlfeSearchComparator cmpVal = new BdlfeSearchComparator(comparisonValue, comparisonLabel);
		myComparisonValues.add(cmpVal);
		
		this.order = order;
		this.logicalOperator = logicalOperator;
		this.filterType = filterType;
		this.comparisonOperator = comparisonOperator;
		this.comparisonValues = myComparisonValues;
		this.itemNumber=0;
	}

	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getLogicalOperator() {
		return logicalOperator;
	}
	public void setLogicalOperator(String logicalOperator) {
		this.logicalOperator = logicalOperator;
	}
	public String getFilterType() {
		return filterType;
	}
	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}
	public String getComparisonOperator() {
		return comparisonOperator;
	}
	public void setComparisonOperator(String comparisonOperator) {
		this.comparisonOperator = comparisonOperator;
	}
	public List<BdlfeSearchComparator> getComparisonValues() {
		return comparisonValues;
	}
	public void setComparisonValues(List<BdlfeSearchComparator> comparisonValues) {
		this.comparisonValues = comparisonValues;
	}
	public Integer getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(Integer itemNumber) {
		this.itemNumber = itemNumber;
	}
	
	public String myHash() {
		try {
			
		    String md5 = this.toString();
	        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
	        byte[] array = md.digest(md5.getBytes());
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < array.length; ++i) {
	    	   sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
	        }
	        return sb.toString();
	    } catch (java.security.NoSuchAlgorithmException e) {
	    }
	    return null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BdlfeSearchFilter [order=");
		builder.append(order);
		builder.append(", logicalOperator=");
		builder.append(logicalOperator);
		builder.append(", filterType=");
		builder.append(filterType);
		builder.append(", comparisonOperator=");
		builder.append(comparisonOperator);
		builder.append(", comparisonValues=");
		if(comparisonValues==null){
			builder.append("");
		}else{
			builder.append("{");
			for(BdlfeSearchComparator scmp : comparisonValues){
				builder.append("BdlfeSearchComparator[");
				builder.append("comparisonValue="+scmp.getComparisonValue()+",");
				builder.append("comparisonLabel="+scmp.getComparisonLabel());				
				builder.append("]");
			}
			builder.append("}");
		}
		builder.append(comparisonValues);
		
		builder.append(", itemNumber=");
		builder.append(itemNumber);
		builder.append("]");
				
		return builder.toString();
	}
}
