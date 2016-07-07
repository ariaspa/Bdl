package it.lispa.bdl.utils.enums;

import java.util.ArrayList;
import java.util.List;

public enum MainArgsEnum {
	
	ARG_ORCLSRV 	("orclSrv"	, "(^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$)|(^\\w+$)"),
	ARG_ORCLPORT 	("orclPort"	, "^\\d+$"),
	ARG_ORCLSID 	("orclSid"	, "^\\w+$"),
	ARG_ORCLUSR 	("orclUsr"	, "^\\w+$"),
	ARG_ORCLPWD 	("orclPwd"	, "^\\w+$"),
	ARG_IMGBIN 		("imgBin"	, ""),
	ARG_IMGPATH 	("imgPath"	, ""),
	ARG_IMGNRO 		("imgNro"	, "^\\d+$"),
	ARG_OGGTIPO 	("oggTipo"	, "^\\d+$"),
	ARG_LOGLEVEL 	("logLevel"	, "^[A-Za-z]+$");

    private final String argName;
    private final String argPattern;
    
    MainArgsEnum(String argName, String argPattern) {
        this.argName = argName;
        this.argPattern = argPattern;
    }
    
    public String getArgName() { 
    	return this.argName; 
    }
    public String getArgPattern() { 
    	return this.argPattern; 
    }
    
    public static List<String> getValues() {
    	List<String> mainArgsEnumName = new ArrayList<String>();
    	for(MainArgsEnum obj : MainArgsEnum.values()) {
    		mainArgsEnumName.add(obj.getArgName());
    	}
    	return mainArgsEnumName;
    }
}
