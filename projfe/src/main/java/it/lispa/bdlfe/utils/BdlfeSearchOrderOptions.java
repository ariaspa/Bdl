package it.lispa.bdlfe.utils;

import java.util.EnumSet;

public enum BdlfeSearchOrderOptions {
	RELEVANCE("Rilevanza"),
	AO_TITLE("Ordine alfabetico (Titolo)"),
	AO_AUTHOR("Ordine alfabetico (Autore)"),
	DATE("Data di pubblicazione");
	
	private String label;
	
	private BdlfeSearchOrderOptions(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
    public static BdlfeSearchOrderOptions parse(String id) {
    	BdlfeSearchOrderOptions myObj = null;
        for (BdlfeSearchOrderOptions opt : BdlfeSearchOrderOptions.values()) {
            if (opt.getLabel().equalsIgnoreCase(id)) {
            	myObj = opt;
                break;
            }
        }
        return myObj;
    }

    public static final EnumSet<BdlfeSearchOrderOptions> BDLFE_SEARCH_ORDER_OPTIONS = EnumSet.of(RELEVANCE, AO_TITLE, AO_AUTHOR, DATE);
}