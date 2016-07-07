package it.lispa.bdl.utils.core.immagini;

import it.lispa.bdl.commons.utils.ConvertWrapper;
import it.lispa.bdl.utils.core.BdlUtilsAppConstants;

import java.io.File;

public class BaseImmaginiUtils {

	public static final String prependOggettoOriginale 	= "[OO] ";
	public static final String prependDirectory 		= "[DIR] ";
	public static final String prependPdf 				= "[PDF] ";
	public static final String prependImg 				= "[IMG] ";
	public static final String prependAudio 			= "[AUDIO] ";
	public static final String prependSys 				= "[SYS] ";
	
	protected static String imagemagickBin;
	protected static ConvertWrapper convertWrapper;
	
	public void setProperties(String myimagemagickBin) {
		imagemagickBin = myimagemagickBin;
		convertWrapper = new ConvertWrapper(imagemagickBin);
	}
	
	protected String makeFilePath(String pathImmaginiDigitalizzate, String dirToAppend) {
		StringBuilder strPath = new StringBuilder();	
		strPath.append(pathImmaginiDigitalizzate);		
		if(dirToAppend!=null) {
			if(!pathImmaginiDigitalizzate.endsWith(File.separator)) {
				strPath.append(File.separator);
			}
			strPath.append(dirToAppend);
		}
		return strPath.toString();
	}
	
	protected String makeFilePath(String pathImmaginiDigitalizzate, String dirToAppend, String imgName, String imgExt) {
		String strPath = makeFilePath(pathImmaginiDigitalizzate, dirToAppend);
		strPath = strPath + File.separator + imgName + BdlUtilsAppConstants.DOT + imgExt;
		return strPath;
	}
}
