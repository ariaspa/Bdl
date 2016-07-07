package it.lispa.bdl.server.rest.dto;

import it.lispa.bdl.server.utils.BdlServerConstants;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class JSONBdlSearchFilter.
 */
public class JSONBdlSearchFilter implements Comparable<JSONBdlSearchFilter>{
	
	private Integer order;
	private String logicalOperator;
	private String filterType;
	private String comparisonOperator;
	private List<JSONBdlSearchComparator> comparisonValues;
	private Integer itemNumber;

	/**
	 * Istanzia un nuovo JSON bdl search filter.
	 */
	public JSONBdlSearchFilter(){
		// do nothing...
	}
	
	/**
	 * Istanzia un nuovo JSON bdl search filter.
	 *
	 * @param order  order
	 * @param logicalOperator  logical operator
	 * @param filterType  filter type
	 * @param comparisonOperator  comparison operator
	 * @param comparisonValues  comparison values
	 * @param itemNumber  item number
	 */
	public JSONBdlSearchFilter(Integer order, String logicalOperator,
			String filterType, String comparisonOperator,
			List<JSONBdlSearchComparator> comparisonValues, Integer itemNumber) {
		this.order = order;
		this.logicalOperator = logicalOperator;
		this.filterType = filterType;
		this.comparisonOperator = comparisonOperator;
		this.comparisonValues = comparisonValues;
		this.itemNumber = itemNumber;
	}

	/**
	 * Legge order.
	 *
	 * @return order
	 */
	public Integer getOrder() {
		return order;
	}
	
	/**
	 * Imposta order.
	 *
	 * @param order nuovo order
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}
	
	/**
	 * Legge logical operator.
	 *
	 * @return logical operator
	 */
	public String getLogicalOperator() {
		return logicalOperator;
	}
	
	/**
	 * Imposta logical operator.
	 *
	 * @param logicalOperator nuovo logical operator
	 */
	public void setLogicalOperator(String logicalOperator) {
		this.logicalOperator = logicalOperator;
	}
	
	/**
	 * Legge filter type.
	 *
	 * @return filter type
	 */
	public String getFilterType() {
		return filterType;
	}
	
	/**
	 * Imposta filter type.
	 *
	 * @param filterType nuovo filter type
	 */
	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}
	
	/**
	 * Legge comparison operator.
	 *
	 * @return comparison operator
	 */
	public String getComparisonOperator() {
		return comparisonOperator;
	}
	
	/**
	 * Imposta comparison operator.
	 *
	 * @param comparisonOperator nuovo comparison operator
	 */
	public void setComparisonOperator(String comparisonOperator) {
		this.comparisonOperator = comparisonOperator;
	}
	
	/**
	 * Legge comparison values.
	 *
	 * @return comparison values
	 */
	public List<JSONBdlSearchComparator> getComparisonValues() {
		return comparisonValues;
	}
	
	/**
	 * Imposta comparison values.
	 *
	 * @param comparisonValues nuovo comparison values
	 */
	public void setComparisonValues(List<JSONBdlSearchComparator> comparisonValues) {
		this.comparisonValues = comparisonValues;
	}
	
	/**
	 * Legge item number.
	 *
	 * @return item number
	 */
	public int getItemNumber() {
		return itemNumber;
	}
	
	/**
	 * Imposta item number.
	 *
	 * @param itemNumber nuovo item number
	 */
	public void setItemNumber(Integer itemNumber) {
		this.itemNumber = itemNumber;
	}

	/**
	 * Crea comparison param name.
	 *
	 * @param num  num
	 * @return string
	 */
	public String buildComparisonParamName(int num){
		StringBuilder strBuff = new StringBuilder();
		
		if(BdlServerConstants.JSON_COMPARISONOP_EQUAL.equals(getComparisonOperator())){
			strBuff.append(" = ");
			strBuff.append("UPPER(:"+getFilterType()+num+") ");
		}else if(BdlServerConstants.JSON_COMPARISONOP_EQUALID.equals(getComparisonOperator())){
			strBuff.append(" = ");
			strBuff.append(":"+getFilterType()+num+" ");
		}else if(BdlServerConstants.JSON_COMPARISONOP_NOTEQUAL.equals(getComparisonOperator())){
			strBuff.append(" <> ");
			strBuff.append("UPPER(:"+getFilterType()+num+") ");
		}else if(BdlServerConstants.JSON_COMPARISONOP_LIKE.equals(getComparisonOperator())){
			strBuff.append(" LIKE ");
			strBuff.append("UPPER(:"+getFilterType()+num+") ");
		}else if(BdlServerConstants.JSON_COMPARISONOP_IN.equals(getComparisonOperator())){
			strBuff.append(" IN (");
			
			for(int i=0;i<getComparisonValues().size();i++){
				if(i!=0){
					strBuff.append(",");
				}
				strBuff.append("UPPER(:"+getFilterType()+(i+num)+") ");
			}
			strBuff.append(") ");
		}else if(BdlServerConstants.JSON_COMPARISONOP_INID.equals(getComparisonOperator())){
			strBuff.append(" IN (");
			
			for(int i=0;i<getComparisonValues().size();i++){
				if(i!=0){
					strBuff.append(",");
				}
				strBuff.append(":"+getFilterType()+(i+num)+"");
			}
			strBuff.append(") ");
		}else if(BdlServerConstants.JSON_COMPARISONOP_BETWEEN.equals(getComparisonOperator())){
			strBuff.append(" BETWEEN ");
			strBuff.append("UPPER(:"+getFilterType()+num+") ");
			strBuff.append(" AND ");
			strBuff.append("UPPER(:"+getFilterType()+(num+1)+") ");
		}
		
		return strBuff.toString();
	}
	
	/**
	 * Crea field name.
	 *
	 * @param fieldNameForCode  field name for code
	 * @param fieldNameForDescription  field name for description
	 * @return string
	 */
	public String buildFieldName(String fieldNameForCode, String fieldNameForDescription){
		String toRet = "";
		if(BdlServerConstants.JSON_COMPARISONOP_EQUALID.equals(getComparisonOperator())){
			toRet += "NVL("+fieldNameForCode+",0)";	
		}else if(BdlServerConstants.JSON_COMPARISONOP_INID.equals(getComparisonOperator())){
			toRet += "NVL("+fieldNameForCode+",0)";	
		}else{
			toRet += "UPPER(NVL("+fieldNameForDescription+",' '))";
		}
		return toRet;
	}

	/**
	 * Crea comparison param value.
	 *
	 * @param num  num
	 * @return map
	 */
	public Map<String,Object> buildComparisonParamValue(int num){
		Map<String,Object> toRet = new HashMap<String , Object>();
		
		if(BdlServerConstants.JSON_COMPARISONOP_EQUALID.equals(getComparisonOperator())){
			if(BdlServerConstants.JSON_FILTERTYPE_DATE.equals(getFilterType())) {
				toRet.put(getFilterType()+num, formatComparisonValue(0));
			}else{
				toRet.put(getFilterType()+num,new BigDecimal(formatComparisonValue(0)));
			}
		}else if(BdlServerConstants.JSON_COMPARISONOP_EQUAL.equals(getComparisonOperator())){
			toRet.put(getFilterType()+num,formatComparisonValue(0));
		}else if(BdlServerConstants.JSON_COMPARISONOP_NOTEQUAL.equals(getComparisonOperator())){
			toRet.put(getFilterType()+num,formatComparisonValue(0));
		}else if(BdlServerConstants.JSON_COMPARISONOP_LIKE.equals(getComparisonOperator())){
			toRet.put(getFilterType()+num,formatComparisonValue(0));
		}else if(BdlServerConstants.JSON_COMPARISONOP_IN.equals(getComparisonOperator())){
			for(int i=0;i<getComparisonValues().size();i++){
				toRet.put(getFilterType()+(num+i),formatComparisonValue(i));
			}
		}else if(BdlServerConstants.JSON_COMPARISONOP_INID.equals(getComparisonOperator())){
			for(int i=0;i<getComparisonValues().size();i++){
				toRet.put(getFilterType()+(num+i),new BigDecimal(formatComparisonValue(i)));
			}
		}else if(BdlServerConstants.JSON_COMPARISONOP_BETWEEN.equals(getComparisonOperator())){
			toRet.put(getFilterType()+num,formatComparisonValue(0));
			toRet.put(getFilterType()+(num+1),formatComparisonValue(1));
		}
		return toRet;
	}
	
	private String formatComparisonValue(Integer idx){
		String toRet = "";
		
		if(BdlServerConstants.JSON_COMPARISONOP_LIKE.equals(getComparisonOperator())){
			toRet += "%"+getComparisonValues().get(idx).getComparisonValue()+"%";
		}else{
			toRet += getComparisonValues().get(idx).getComparisonValue();
		}
		
		return toRet;
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(JSONBdlSearchFilter compareObject) {

		if(getOrder()==null && (compareObject==null || compareObject.getOrder()==null)){
			return 0;
		}
		if(getOrder()!=null && (compareObject==null || compareObject.getOrder()==null)){
			return 1;
		}
		if(getOrder()==null && (compareObject!=null && compareObject.getOrder()!=null)){
			return -1;
		}
		
		if (compareObject!=null && getOrder().equals(compareObject.getOrder())) {
			return 0; 
		}else if (compareObject!=null && getOrder() < compareObject.getOrder()){
			return -1; 
		}else {
			return 1;
		}
	}
}