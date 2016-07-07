package it.lispa.bdl.server.rest.dto;

/**
 * Class JSONBdlSearchComparator.
 */
public class JSONBdlSearchComparator {
	private String comparisonValue;
	private String comparisonLabel;
	
	/**
	 * Istanzia un nuovo JSON bdl search comparator.
	 */
	public JSONBdlSearchComparator() {
		// do nothing...
	}
	
	/**
	 * Istanzia un nuovo JSON bdl search comparator.
	 *
	 * @param comparisonValue  comparison value
	 * @param comparisonLabel  comparison label
	 */
	public JSONBdlSearchComparator(String comparisonValue,
			String comparisonLabel) {
		this.comparisonValue = comparisonValue;
		this.comparisonLabel = comparisonLabel;
	}

	/**
	 * Legge comparison value.
	 *
	 * @return comparison value
	 */
	public String getComparisonValue() {
		return comparisonValue;
	}
	
	/**
	 * Imposta comparison value.
	 *
	 * @param comparisonValue nuovo comparison value
	 */
	public void setComparisonValue(String comparisonValue) {
		this.comparisonValue = comparisonValue;
	}
	
	/**
	 * Legge comparison label.
	 *
	 * @return comparison label
	 */
	public String getComparisonLabel() {
		return comparisonLabel;
	}
	
	/**
	 * Imposta comparison label.
	 *
	 * @param comparisonLabel nuovo comparison label
	 */
	public void setComparisonLabel(String comparisonLabel) {
		this.comparisonLabel = comparisonLabel;
	}
}