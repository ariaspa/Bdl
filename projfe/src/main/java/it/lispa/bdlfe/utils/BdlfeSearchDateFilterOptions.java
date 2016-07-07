package it.lispa.bdlfe.utils;

import java.util.EnumSet;

public enum BdlfeSearchDateFilterOptions {
	RANGE("Range"),
	SECOLO("Secolo"),
	DATACERTA("Data certa"),
	CIRCA("Circa");
	
	private String label;
	
	private BdlfeSearchDateFilterOptions(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
    public static BdlfeSearchDateFilterOptions parse(String id) {
    	BdlfeSearchDateFilterOptions myObj = null;
        for (BdlfeSearchDateFilterOptions opt : BdlfeSearchDateFilterOptions.values()) {
            if (opt.getLabel().equalsIgnoreCase(id)) {
            	myObj = opt;
                break;
            }
        }
        return myObj;
    }

    public static final EnumSet<BdlfeSearchDateFilterOptions> BDLFE_SEARCH_DATEFILTER_OPTIONS= EnumSet.of(RANGE, SECOLO, DATACERTA, CIRCA);
}