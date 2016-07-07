package it.lispa.bdl.utils.enums;

import java.util.ArrayList;
import java.util.List;

public enum LogLevelsEnum {

	ALL 	("ALL"),
	DEBUG 	("DEBUG"),
	ERROR 	("ERROR"),
	FATAL 	("FATAL"),
	INFO 	("INFO"),
	OFF 	("OFF"),
	TRACE 	("TRACE"),
	WARN 	("WARN");

    private final String logLevelName;
    
    LogLevelsEnum(String logLevelName) {
        this.logLevelName = logLevelName;
    }
    
    public String getLogLevelName() { 
    	return this.logLevelName; 
    }
    
    public static List<String> getValues() {
    	List<String> logLevelEnumName = new ArrayList<String>();
    	for(LogLevelsEnum obj : LogLevelsEnum.values()) {
    		logLevelEnumName.add(obj.getLogLevelName());
    	}
    	return logLevelEnumName;
    }
}
