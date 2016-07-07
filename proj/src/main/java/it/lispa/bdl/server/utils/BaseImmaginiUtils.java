package it.lispa.bdl.server.utils;

import it.lispa.bdl.commons.domain.BdlFormato;
import it.lispa.bdl.commons.domain.BdlWorkFilesystem;
import it.lispa.bdl.server.repository.BdlFormatoRepository;
import it.lispa.bdl.server.repository.BdlWorkFilesystemRepository;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class BaseImmaginiUtils {

	/** bdl formato repository. */
	@Autowired
	protected BdlFormatoRepository bdlFormatoRepository;
	
	/** bdl formato repository. */
	@Autowired
	protected BdlWorkFilesystemRepository bdlWorkFilesystemRepository;
	
	/** properties. */
	@Autowired
	protected BdlServerProperties properties;
	
	/** prepend oggetto originale. */
	protected static final String prependOggettoOriginale = "[OO] ";
	/** prepend directory. */
	protected static final String prependDirectory 		  = "[DIR] ";
	/** prepend pdf. */
	protected static final String prependPdf 			  = "[PDF] ";
	/** prepend img. */
	protected static final String prependImg 		      = "[IMG] ";
	/** prepend audio. */
	protected static final String prependAudio 			  = "[AUDIO] ";
	/** prepend str. */
	protected static String prependStr = CaricamentoImmaginiUtils.prependImg;
	
	/**
	 * Legge formati natura map.
	 *
	 * @param cdTipoOggetto  codice tipo oggetto
	 * @return formati natura map
	 */
	public List<BdlFormato> getFormatiNaturaMap(BigDecimal cdTipoOggetto) {
		return bdlFormatoRepository.findByDsNaturaAndCdTipoOggetto(BdlSharedConstants.BDL_FORMATO_NATURA_MAP, cdTipoOggetto);
	}
	
	/**
	 * Legge formati natura audio.
	 *
	 * @param cdTipoOggetto  codice tipo oggetto
	 * @return formati natura audio
	 */
	public List<BdlFormato> getFormatiNaturaAudio(BigDecimal cdTipoOggetto) {
		return bdlFormatoRepository.findByDsNaturaAndCdTipoOggetto(BdlSharedConstants.BDL_FORMATO_NATURA_AUDIO, cdTipoOggetto);
	}
	
	/**
	 * Legge formati natura image single.
	 *
	 * @param cdTipoOggetto  codice tipo oggetto
	 * @return formati natura image
	 */
	public List<BdlFormato> getFormatiNaturaSingleImage(BigDecimal cdTipoOggetto) {
		return bdlFormatoRepository.findByDsNaturaAndCdTipoOggetto(BdlSharedConstants.BDL_FORMATO_NATURA_SINGLEIMAGE, cdTipoOggetto);
	}
	
	/**
	 * Legge formati natura image multi.
	 *
	 * @param cdTipoOggetto  codice tipo oggetto
	 * @return formati natura image
	 */
	public List<BdlFormato> getFormatiNaturaMultiImage(BigDecimal cdTipoOggetto) {
		return bdlFormatoRepository.findByDsNaturaAndCdTipoOggetto(BdlSharedConstants.BDL_FORMATO_NATURA_MULTIIMAGE, cdTipoOggetto);
	}
	
	/**
	 * Controlla natura map.
	 *
	 * @param cdTipoOggetto  codice tipo oggetto
	 * @return boolean
	 */
	public Boolean hasNaturaMap(BigDecimal cdTipoOggetto) {
		Boolean hasNaturaMap = false;
		List<BdlFormato> formatiMap = getFormatiNaturaMap(cdTipoOggetto);
		if(formatiMap!=null && !formatiMap.isEmpty()) {
			hasNaturaMap = true;
		}
		return hasNaturaMap;
	}
	
	/**
	 * Controlla natura audio.
	 *
	 * @param cdTipoOggetto  codice tipo oggetto
	 * @return boolean
	 */
	public Boolean hasNaturaAudio(BigDecimal cdTipoOggetto) {
		Boolean hasNaturaAudio = false;
		List<BdlFormato> formatiAudio = getFormatiNaturaAudio(cdTipoOggetto);
		if(formatiAudio!=null && !formatiAudio.isEmpty()) {
			hasNaturaAudio = true;
		}
		return hasNaturaAudio;
	}
	
	/**
	 * Controlla natura image single.
	 *
	 * @param cdTipoOggetto  codice tipo oggetto
	 * @return boolean
	 */
	public Boolean hasNaturaSingleImage(BigDecimal cdTipoOggetto) {
		Boolean hasNaturaImage = false, hasNaturaAudio = false, hasNaturaMap = false;
		List<BdlFormato> formatiImage = getFormatiNaturaSingleImage(cdTipoOggetto);
		if(formatiImage!=null && !formatiImage.isEmpty()) {
			hasNaturaAudio = hasNaturaAudio(cdTipoOggetto);
			hasNaturaMap = hasNaturaMap(cdTipoOggetto);
			if(!hasNaturaAudio && !hasNaturaMap) {
				hasNaturaImage = true;
			}
		}
		return hasNaturaImage;
	}
	
	/**
	 * Controlla natura image multi.
	 *
	 * @param cdTipoOggetto  codice tipo oggetto
	 * @return boolean
	 */
	public Boolean hasNaturaMultiImage(BigDecimal cdTipoOggetto) {
		Boolean hasNaturaImage = false, hasNaturaAudio = false, hasNaturaMap = false;
		List<BdlFormato> formatiImage = getFormatiNaturaMultiImage(cdTipoOggetto);
		if(formatiImage!=null && !formatiImage.isEmpty()) {
			hasNaturaAudio = hasNaturaAudio(cdTipoOggetto);
			hasNaturaMap = hasNaturaMap(cdTipoOggetto);
			if(!hasNaturaAudio && !hasNaturaMap) {
				hasNaturaImage = true;
			}
		}
		return hasNaturaImage;
	}
	
	/**
	 * Make file path.
	 *
	 * @param pathOggettoOriginale  path oggetto originale
	 * @param defaultName  default name
	 * @param pathType  path type
	 * @return string
	 * @throws Exception 
	 */
	private String makeFilePath(String pathOggettoOriginale, String defaultName, String pathType) throws Exception{
		return makeFilePath(pathOggettoOriginale, defaultName, pathType, null);
	}
	/**
	 * Make file path.
	 *
	 * @param pathOggettoOriginale  path oggetto originale
	 * @param defaultName  default name
	 * @param pathType  path type
	 * @param cdWorkFilesystem  codice work filesystem
	 * @return string
	 * @throws Exception 
	 */
	private String makeFilePath(String pathOggettoOriginale, String defaultName, String pathType, BigDecimal cdWorkFilesystem) throws Exception{
		StringBuilder strPath = new StringBuilder();		
		String pp = properties.getDirNetappTmp();
		if(pathType.equals("WRK")){
			pp = properties.getDirNetappWrk();
		}
		strPath.append(pp);		
		strPath.append(File.separator);
		
		if(cdWorkFilesystem!=null){
			BdlWorkFilesystem mountPoint = bdlWorkFilesystemRepository.findByCdWorkFilesystem(cdWorkFilesystem);
			String location = mountPoint.getDsLocation();
			location = location.replaceAll("^\\/+", "");
			location = location.replaceAll("\\/+$", "");
			strPath.append(location);
			strPath.append(File.separator);
		}
		strPath.append(pathOggettoOriginale);
		strPath.append(File.separator);
		if(defaultName!=null){
			strPath.append(defaultName);
		}
		return strPath.toString();
	}
	
	/**
	 * Make file path tmp.
	 *
	 * @param pathOggettoOriginale  path oggetto originale
	 * @param defaultName  default name
	 * @return string
	 * @throws Exception 
	 */
	public String makeFilePathTmp(String pathOggettoOriginale, String defaultName) throws Exception{
		return makeFilePath(pathOggettoOriginale,  defaultName, "TMP");
	}
	
	/**
	 * Make file path tmp.
	 *
	 * @param pathOggettoOriginale  path oggetto originale
	 * @return string
	 * @throws Exception 
	 */
	public String makeFilePathTmp(String pathOggettoOriginale) throws Exception{
		return makeFilePath(pathOggettoOriginale,  null, "TMP");
	}

	/**
	 * Make file path wrk.
	 *
	 * @param pathOggettoOriginale  path oggetto originale
	 * @return string
	 * @throws Exception 
	 */
	public String makeFilePathWrk(BigDecimal cdWorkFilesystem, String pathOggettoOriginale) throws Exception{
		return makeFilePath(pathOggettoOriginale,  null, "WRK", cdWorkFilesystem);
	}
	
	/**
	 * Make file path wrk.
	 *
	 * @param pathOggettoOriginale  path oggetto originale
	 * @param defaultName  default name
	 * @return string
	 * @throws Exception 
	 */
	public String makeFilePathWrk(BigDecimal cdWorkFilesystem, String pathOggettoOriginale, String defaultName) throws Exception{
		return makeFilePath(pathOggettoOriginale,  defaultName, "WRK", cdWorkFilesystem);
	}
    
    /**
     * Make file path wrk.
     *
     * @param cdWorkFilesystem  codice identificativo work filesystem
     * @param pathOggettoOriginale  path oggetto originale
     * @param defaultName  default name
     * @param imgName  img name
     * @param imgExt  img ext
     * @return string
     * @throws Exception 
     */
    public String makeFilePathWrk(BigDecimal cdWorkFilesystem, String pathOggettoOriginale, String defaultName, String imgName, String imgExt) throws Exception {
		String strPath = makeFilePathWrk(cdWorkFilesystem, pathOggettoOriginale, defaultName);
		strPath = strPath+File.separator+imgName+"."+imgExt;
		return strPath;
	}
    
	public BdlServerProperties getProperties() {
		return properties;
	}
	public BdlFormatoRepository getBdlFormatoRepository() {
		return bdlFormatoRepository;
	}
}
