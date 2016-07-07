package it.lispa.bdlfe.utils;

import java.util.EnumSet;

public enum BdlfeSearchItemsPerPage {
	NUM15("15"),
	NUM30("30"),
	NUM45("45"),
	NUM60("60");
	
	private String label;
	
	private BdlfeSearchItemsPerPage(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
    public static BdlfeSearchItemsPerPage parse(String id) {
    	BdlfeSearchItemsPerPage myObj = null;
        for (BdlfeSearchItemsPerPage opt : BdlfeSearchItemsPerPage.values()) {
            if (opt.getLabel().equalsIgnoreCase(id)) {
            	myObj = opt;
                break;
            }
        }
        return myObj;
    }

    public static final EnumSet<BdlfeSearchItemsPerPage> BDLFE_SEARCH_ITEMS_PER_PAGE = EnumSet.of(NUM15, NUM30, NUM45, NUM60);
}