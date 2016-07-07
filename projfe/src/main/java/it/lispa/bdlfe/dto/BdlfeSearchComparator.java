package it.lispa.bdlfe.dto;

public class BdlfeSearchComparator {
	private String comparisonValue;
	private String comparisonLabel;

	public BdlfeSearchComparator() {
	}
	public BdlfeSearchComparator(String comparisonValue,
			String comparisonLabel) {
		this.comparisonValue = comparisonValue;
		this.comparisonLabel = comparisonLabel;
	}

	public String getComparisonValue() {
		return comparisonValue;
	}
	public void setComparisonValue(String comparisonValue) {
		this.comparisonValue = comparisonValue;
	}
	public String getComparisonLabel() {
		return comparisonLabel;
	}
	public void setComparisonLabel(String comparisonLabel) {
		this.comparisonLabel = comparisonLabel;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BdlfeSearchComparator [comparisonValue=");
		builder.append(comparisonValue);
		builder.append(", comparisonLabel=");
		builder.append(comparisonLabel);
		builder.append("]");
		return builder.toString();
	}
}
