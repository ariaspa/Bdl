package it.lispa.bdlfe.utils;

import java.util.EnumSet;

public enum BdlfeSearchLogicalOperators {
	AND("E"),
	OR("OPPURE"),
	ANDNOT("E NON");
	
	private String label;
	
	private BdlfeSearchLogicalOperators(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
    public static BdlfeSearchLogicalOperators parse(String id) {
    	BdlfeSearchLogicalOperators myObj = null;
        for (BdlfeSearchLogicalOperators opt : BdlfeSearchLogicalOperators.values()) {
            if (opt.getLabel().equalsIgnoreCase(id)) {
            	myObj = opt;
                break;
            }
        }
        return myObj;
    }

    public static final EnumSet<BdlfeSearchLogicalOperators> BDLFE_SEARCH_LOGICAL_OPERATORS = EnumSet.of(AND, OR, ANDNOT);
}