package it.lispa.bdlfe.utils;

import java.util.EnumSet;

public enum BdlfeSearchFilterTypes {
	TITLE("Titolo"),
	AUTHOR("Autore"),
	SUBJECT("Soggetto"),
	TYPE("Tipo documento"),
	LANG("Lingua"),
	DATE("Data"),
	PUBLISHER("Editore"),
	KIND("Natura"),
	INSTITUTE("Istituto"),
	SEGNATURA("Segnatura");
	
	private String label;
	
	private BdlfeSearchFilterTypes(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
    public static BdlfeSearchFilterTypes parse(String id) {
    	BdlfeSearchFilterTypes myObj = null;
        for (BdlfeSearchFilterTypes opt : BdlfeSearchFilterTypes.values()) {
            if (opt.getLabel().equalsIgnoreCase(id)) {
            	myObj = opt;
                break;
            }
        }
        return myObj;
    }

    public static final EnumSet<BdlfeSearchFilterTypes> BDLFE_SEARCH_FILTER_TYPES = EnumSet.of(
    		TITLE, AUTHOR, SUBJECT, TYPE, LANG, DATE, PUBLISHER, KIND, INSTITUTE, SEGNATURA);
}