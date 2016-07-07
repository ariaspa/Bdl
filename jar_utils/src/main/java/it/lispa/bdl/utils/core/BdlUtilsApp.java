package it.lispa.bdl.utils.core;

import it.lispa.bdl.commons.domain.BdlFormato;
import it.lispa.bdl.utils.core.immagini.BaseImmaginiUtils;
import it.lispa.bdl.utils.core.immagini.CreazioneImmaginiUtils;
import it.lispa.bdl.utils.enums.LogLevelsEnum;
import it.lispa.bdl.utils.enums.MainArgsEnum;
import it.lispa.bdl.utils.persistence.PersistenceManager;
import it.lispa.bdl.utils.services.BdlFormatoService;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;

import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class BdlUtilsApp {

	private static Logger log = Logger.getLogger(BdlUtilsApp.class);
	
	private static BdlFormatoService bdlFormatoService = null;
	
	public static final String prependLogStrInizio = "______";
	public static final String logStrDivisore = "________________________________________________________________________";
	
	public static List<String> handleBackEnd(String pathImmaginiDigitalizzate, BigDecimal nroImmaginiDigitalizzate, List<BdlFormato> formatiImage, String imagemagickBin) {
		List<String> erroriOccorsi = new ArrayList<String>();
		/* Genero le immagini derivate */
		erroriOccorsi.addAll(CreazioneImmaginiUtils.creaImmaginiDerivate(pathImmaginiDigitalizzate, nroImmaginiDigitalizzate, formatiImage, imagemagickBin));
		return erroriOccorsi;
	}
	
	@SuppressWarnings("rawtypes")
	private static void changeLogLevel(Level logLevel) {
		Logger root = Logger.getRootLogger();
		Enumeration allLoggers = root.getLoggerRepository().getCurrentCategories();
		// set logging level of root and all logging instances in the system
	    root.setLevel(logLevel);
	    while (allLoggers.hasMoreElements()){
	        Category tmpLogger = (Category) allLoggers.nextElement();
	        tmpLogger.setLevel(logLevel);
	    }
		return;
	}
	
	public static void main(String[] args) {
			
		List<String> erroriOccorsi = new ArrayList<String>();
		if(args.length == MainArgsEnum.values().length) {
			Map<String, String> mapArgs = new HashMap<String, String>();
			
			/* 0. Creo argNamePattern */
			StringBuilder argNamePattern = new StringBuilder();
			for(MainArgsEnum argEnum : MainArgsEnum.values()) {
				argNamePattern.append(argEnum.getArgName());
				argNamePattern.append("|");
			}
			argNamePattern.deleteCharAt(argNamePattern.lastIndexOf("|"));
			/* 1. Verifico gli argomenti dati in pasto al main */
			for(int i=0; i<args.length; i++) {
			    Pattern argPattern = Pattern.compile("--(" + argNamePattern.toString() + ")=(.*)");
			    Matcher argMatcher = argPattern.matcher(args[i]);
			    if(argMatcher.find()) {
			    	final String argName  = argMatcher.group(1);
			    	final String argValue = argMatcher.group(2).trim();
			    	/* Popolo mapArgs, la mappa chiave-valore degli argomenti dati in pasto al main */
			    	mapArgs.put(argName, argValue);
			    }
			}
			/* 3. Verifico che i valori di mapArgs non siano nulli */
			for (Map.Entry<String, String> arg : mapArgs.entrySet()) {
				if(arg.getValue()==null || arg.getValue().equals("")) {
					erroriOccorsi.add(BaseImmaginiUtils.prependSys + 
							"Il valore del parametro \"" + arg.getKey() + "\" non e' stato specificato ");
				}
			}
			/* 2. Verifico che esistano tutti e soli gli argomenti registrati in MainArgsEnum */
			for(MainArgsEnum argEnum : MainArgsEnum.values()) {
				if(!mapArgs.containsKey(argEnum.getArgName())) {
					erroriOccorsi.add(BaseImmaginiUtils.prependSys + 
							"Il parametro \"" + argEnum.getArgName() + "\" non e' stato specificato ");
				}
			}
			if(erroriOccorsi.isEmpty()) {
				/* 4. Verifico l'effettiva validità dei valori degli argomenti forniti dall'utente */
				String valueToCheck = null;
				File fileTmp = null;
				for(MainArgsEnum argEnum : MainArgsEnum.values()) {
					valueToCheck = mapArgs.get(argEnum.getArgName());
					if(argEnum.getArgPattern().equals("")) {
						/* Lo tratto come PATH */
						try{
	    					fileTmp = new File(valueToCheck);
	    					if(!fileTmp.exists()) {
	    						erroriOccorsi.add(BaseImmaginiUtils.prependSys + 
	    								"Il valore del parametro \"" + argEnum.getArgName() + "\" non e' valido. "
	    										+ "La cartella specificata non esiste ");
	    					}
						} catch(Exception e) {
							erroriOccorsi.add(BaseImmaginiUtils.prependSys + 
									"Il valore del parametro \"" + argEnum.getArgName() + "\" non e' valido. ");
						}
					} else if(!valueToCheck.matches(argEnum.getArgPattern())) {
						erroriOccorsi.add(BaseImmaginiUtils.prependSys + 
								"Il valore del parametro \"" + argEnum.getArgName() + "\" non e' valido ");
					}
				}
				///////////////////////////////////////////////////////////////////////////////// Blocco principale 
	    		if(erroriOccorsi.isEmpty()) {
	    			
	    			/* 0. Imposto il LogLevel */
	    			String myLogLevel = mapArgs.get(MainArgsEnum.ARG_LOGLEVEL.getArgName());
	    			try {
	    				LogLevelsEnum logLevelEnum = LogLevelsEnum.valueOf(myLogLevel.toUpperCase());
	    				/* Passo dalla LogLevelsEnum per verificare che il valore abbia senso */
	    				myLogLevel = logLevelEnum.getLogLevelName();
	    			} catch(Exception e) {
	    				mapArgs.remove(MainArgsEnum.ARG_LOGLEVEL.getArgName());
	    				mapArgs.put(MainArgsEnum.ARG_LOGLEVEL.getArgName(), LogLevelsEnum.INFO.getLogLevelName());
	    				/* In caso contrario lo imposto a INFO */
	    				myLogLevel = LogLevelsEnum.INFO.getLogLevelName();
					}
	    			/* Cambio il logLevel */
	    			changeLogLevel(Level.toLevel(myLogLevel));
	    			
	    			/* 0. Aspetto qualche secondo in modo da presentare gli argomenti dati in input dall'utente */
	    			final Integer msToWait = Integer.valueOf(2000);
	    			logInputArgsMsg(mapArgs);
	    			// Pause for msToWait=2000=2s...
	                try {
						Thread.sleep(msToWait);
					} catch (InterruptedException e) {
						// nothing to do...
					}
	    			
	                ////////////////////// Procedo /////////////////////////////////////////////////////////////////////
	                
	    			final String 		imagemagickBin 				= mapArgs.get(MainArgsEnum.ARG_IMGBIN.getArgName());
	    			final String 		pathImmaginiDigitalizzate 	= mapArgs.get(MainArgsEnum.ARG_IMGPATH.getArgName());
	    			final BigDecimal 	nroImmaginiDigitalizzate 	= new BigDecimal(mapArgs.get(MainArgsEnum.ARG_IMGNRO.getArgName()));
	    			final BigDecimal 	cdTipoOggetto 				= new BigDecimal(mapArgs.get(MainArgsEnum.ARG_OGGTIPO.getArgName()));
	    			
	    			final String ARG_ORCLURL = "jdbc:oracle:thin:@" + 	mapArgs.get(MainArgsEnum.ARG_ORCLSRV.getArgName()) 	+ ":" + 
	    																mapArgs.get(MainArgsEnum.ARG_ORCLPORT.getArgName()) + ":" + 
	    																mapArgs.get(MainArgsEnum.ARG_ORCLSID.getArgName());
	    			try {
	        			/* Imposto PersistenceManager e recupero EntityManager */
	        			PersistenceManager pm = new PersistenceManager(
	        					ARG_ORCLURL, 
	        					mapArgs.get(MainArgsEnum.ARG_ORCLUSR.getArgName()), 
	        					mapArgs.get(MainArgsEnum.ARG_ORCLPWD.getArgName())
	        			);
	        			EntityManager em = pm.getEntityManager();
	        			/* Imposto BdlFormatoService */
	        			bdlFormatoService = new BdlFormatoService(em);
	        			
	    			} catch(Exception e) {
						erroriOccorsi.add(BaseImmaginiUtils.prependSys + 
								"Si e' verificato un errore di sistema: [" + e.getClass().getName() + "] " + e.getMessage());
					}
	    			
	    			File pathFileImmaginiDigitalizzate = new File(pathImmaginiDigitalizzate);
	    			if (pathFileImmaginiDigitalizzate.exists()) {
	    				try {
	        				/* 1. Faccio un check sulla natura del cdTipoOggetto che sto per trattare */
	        				Boolean hasNaturaAudio 	= hasNaturaAudio(cdTipoOggetto);
	        				Boolean hasNaturaMap 	= hasNaturaMap(cdTipoOggetto);
	        				if(!hasNaturaMap && !hasNaturaAudio) {
	        					/* 2. Recupero i formati di natura IMAGE da trattare */
	        					List<BdlFormato> formatiImage = bdlFormatoService.findByDsNaturaOrDsNaturaAndCdTipoOggetto(BdlUtilsAppConstants.BDL_FORMATO_NATURA_MULTIIMAGE,BdlUtilsAppConstants.BDL_FORMATO_NATURA_SINGLEIMAGE, cdTipoOggetto);
	        					if(formatiImage.isEmpty()) {
	        						erroriOccorsi.add(BaseImmaginiUtils.prependSys + 
	            							"Il cdTipoOggetto=" + cdTipoOggetto + " inserito non esiste ");
	        					} else {
	        						/* 3. Creo le immagini derivate */
	            					log.info(BdlUtilsApp.logStrDivisore);
	            	    			erroriOccorsi.addAll(CreazioneImmaginiUtils.creaImmaginiDerivate(pathImmaginiDigitalizzate, nroImmaginiDigitalizzate, formatiImage, imagemagickBin));
	        					}
	        				} else {
	        					erroriOccorsi.add(BaseImmaginiUtils.prependSys + 
	        							"Non e' possibile procedere alla generazione delle immagini derivate per il cdTipoOggetto=" + cdTipoOggetto);
	        				}
	    				} catch(Exception e) {
							erroriOccorsi.add(BaseImmaginiUtils.prependSys + 
									"Si e' verificato un errore di sistema: [" + e.getClass().getName() + "] " + e.getMessage());
	    				}
	    			} else {
	    				erroriOccorsi.add(BaseImmaginiUtils.prependSys + 
	    						"La cartella "+ pathImmaginiDigitalizzate + " non esiste ");
	    			}
	    		}
			}
		} else {
			if(args.length == Integer.valueOf(1) && args[0].equals(BdlUtilsAppConstants.HELP_COMMAND)) {
				/* Presento le modalità d'uso del comando all'utente */
				logHelpMsg();
				return;
			} else {
				erroriOccorsi.add(BaseImmaginiUtils.prependSys + 
						"Mi aspetto " + MainArgsEnum.values().length + " parametri, ma ne ho ricevuti " + args.length);
			}
		}

		if(!erroriOccorsi.isEmpty()) {
			logHelpMsg();
			logErrors(erroriOccorsi);
		} else {
			logOkMsg();
		}
		return;
	}
	
	private static void logErrors(List<String> erroriOccorsi) {
		log.info(BdlUtilsApp.logStrDivisore);
		log.info(BdlUtilsApp.prependLogStrInizio + "SI SONO VERIFICATI I SEGUENTI ERRORI: ");
		for(int i=0; i<erroriOccorsi.size(); i++) {
			log.info(i+": "+erroriOccorsi.get(i));
		}
		log.info(BdlUtilsApp.logStrDivisore);
	}
	
	private static void logOkMsg() {
		log.info(BdlUtilsApp.logStrDivisore);
		log.info(BdlUtilsApp.prependLogStrInizio + "LE IMMAGINI DERIVATE SONO STATE GENERATE CORRETTAMENTE. ");
		log.info(BdlUtilsApp.logStrDivisore);
	}
	
	private static void logHelpMsg() {
		log.info(BdlUtilsApp.logStrDivisore);
		log.info(BdlUtilsApp.prependLogStrInizio + "PARAMETRI IN INGRESSO: ");
		log.info("\t" + " --" + MainArgsEnum.ARG_ORCLSRV.getArgName()		
				+ "\t" + "Server Oracle di LIspa ");
		log.info("\t" + " --" + MainArgsEnum.ARG_ORCLPORT.getArgName()	
				+ "\t" + "Porta da cui e' accessibile il server Oracle di LIspa ");
		log.info("\t" + " --" + MainArgsEnum.ARG_ORCLSID.getArgName()		
				+ "\t" + "SID (\"Oracle System ID\") del DB LIspa ");
		log.info("\t" + " --" + MainArgsEnum.ARG_ORCLUSR.getArgName()		
				+ "\t" + "Username di accesso al DB LIspa ");
		log.info("\t" + " --" + MainArgsEnum.ARG_ORCLPWD.getArgName()		
				+ "\t" + "Password di accesso al DB LIspa ");
		log.info("\t" + " --" + MainArgsEnum.ARG_IMGBIN.getArgName()		
				+ "\t" + "Path dove sono locati gli eseguibili del programma Imagemagick ");
		log.info("\t" + " --" + MainArgsEnum.ARG_IMGPATH.getArgName()		
				+ "\t" + "Path dove e' stata inserita la dir \"master\" contenente le immagini digitalizzate ");
		log.info("\t" + " --" + MainArgsEnum.ARG_IMGNRO.getArgName()		
				+ "\t" + "Nro. delle immagini digitalizzate ");
		log.info("\t" + " --" + MainArgsEnum.ARG_OGGTIPO.getArgName()		
				+ "\t" + "Tipologia dell'oggetto digitalizzato ");
		log.info("ESEMPIO: java -jar bdl-utils-<VERSIONE_BDL>.jar "
				+ "--orclSrv=127.0.0.0 "
				+ "--orclPort=8000 "
				+ "--orclSid=SIDLispaDB "
				+ "--orclUsr=UsernameLispaDB "
				+ "--orclPwd=PasswordLispaDB "
				+ "--imgBin=C:\\ImageMagick-6.8.9-Q8 "
				+ "--imgPath=C:\\lispa\\root\\netapp\\tmp\\2\\26\\32\\600 "
				+ "--imgNro=20 "
				+ "--oggTipo=1 "
				+ "--logLevel=INFO ");
		log.info(BdlUtilsApp.logStrDivisore);
	}
	
	private static void logInputArgsMsg(Map<String, String> mapArgs) {
		log.info(BdlUtilsApp.logStrDivisore);
		log.info(BdlUtilsApp.prependLogStrInizio + "PARAMETRI DATI IN INGRESSO: ");
		log.info(	"\t" + " --" + MainArgsEnum.ARG_ORCLSRV.getArgName() 	+ "=" + mapArgs.get(MainArgsEnum.ARG_ORCLSRV.getArgName())	);
		log.info(	"\t" + " --" + MainArgsEnum.ARG_ORCLPORT.getArgName() 	+ "=" + mapArgs.get(MainArgsEnum.ARG_ORCLPORT.getArgName())	);
		log.info(	"\t" + " --" + MainArgsEnum.ARG_ORCLSID.getArgName() 	+ "=" + mapArgs.get(MainArgsEnum.ARG_ORCLSID.getArgName())	);
		log.info(	"\t" + " --" + MainArgsEnum.ARG_ORCLUSR.getArgName() 	+ "=" + mapArgs.get(MainArgsEnum.ARG_ORCLUSR.getArgName())	);
		log.info(	"\t" + " --" + MainArgsEnum.ARG_ORCLPWD.getArgName() 	+ "=" + mapArgs.get(MainArgsEnum.ARG_ORCLPWD.getArgName())	);
		log.info(	"\t" + " --" + MainArgsEnum.ARG_IMGBIN.getArgName() 	+ "=" + mapArgs.get(MainArgsEnum.ARG_IMGBIN.getArgName())	);
		log.info(	"\t" + " --" + MainArgsEnum.ARG_IMGPATH.getArgName() 	+ "=" + mapArgs.get(MainArgsEnum.ARG_IMGPATH.getArgName())	);
		log.info(	"\t" + " --" + MainArgsEnum.ARG_IMGNRO.getArgName() 	+ "=" + mapArgs.get(MainArgsEnum.ARG_IMGNRO.getArgName())	);
		log.info(	"\t" + " --" + MainArgsEnum.ARG_OGGTIPO.getArgName() 	+ "=" + mapArgs.get(MainArgsEnum.ARG_OGGTIPO.getArgName())	);
		log.info(	"\t" + " --" + MainArgsEnum.ARG_LOGLEVEL.getArgName() 	+ "=" + mapArgs.get(MainArgsEnum.ARG_LOGLEVEL.getArgName())	);
		log.info(BdlUtilsApp.logStrDivisore);
	}
	
	////////////////////////////////
	
	public static List<BdlFormato> getFormatiNaturaMap(BigDecimal cdTipoOggetto) throws SQLException {
		return bdlFormatoService.findByDsNaturaAndCdTipoOggetto(BdlUtilsAppConstants.BDL_FORMATO_NATURA_MAP, cdTipoOggetto);
	}
	public static List<BdlFormato> getFormatiNaturaAudio(BigDecimal cdTipoOggetto) throws SQLException {
		return bdlFormatoService.findByDsNaturaAndCdTipoOggetto(BdlUtilsAppConstants.BDL_FORMATO_NATURA_AUDIO, cdTipoOggetto);
	}
	public static List<BdlFormato> getFormatiNaturaMultiImage(BigDecimal cdTipoOggetto) throws SQLException {
		return bdlFormatoService.findByDsNaturaAndCdTipoOggetto(BdlUtilsAppConstants.BDL_FORMATO_NATURA_MULTIIMAGE, cdTipoOggetto);
	}
	public static List<BdlFormato> getFormatiNaturaSingleImage(BigDecimal cdTipoOggetto) throws SQLException {
		return bdlFormatoService.findByDsNaturaAndCdTipoOggetto(BdlUtilsAppConstants.BDL_FORMATO_NATURA_SINGLEIMAGE, cdTipoOggetto);
	}
	
	/**
	 * Controlla natura map.
	 *
	 * @param cdTipoOggetto  codice tipo oggetto
	 * @return boolean
	 * @throws SQLException 
	 */
	public static Boolean hasNaturaMap(BigDecimal cdTipoOggetto) throws SQLException {
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
	 * @throws SQLException 
	 */
	public static Boolean hasNaturaAudio(BigDecimal cdTipoOggetto) throws SQLException {
		Boolean hasNaturaAudio = false;
		List<BdlFormato> formatiAudio = getFormatiNaturaAudio(cdTipoOggetto);
		if(formatiAudio!=null && !formatiAudio.isEmpty()) {
			hasNaturaAudio = true;
		}
		return hasNaturaAudio;
	}
	
	/**
	 * Controlla natura imagesingle.
	 *
	 * @param cdTipoOggetto  codice tipo oggetto
	 * @return boolean
	 * @throws SQLException 
	 */
	public static Boolean hasNaturaSingleImage(BigDecimal cdTipoOggetto) throws SQLException {
		Boolean hasNaturaSingleImage = false, hasNaturaMultiImage = false, hasNaturaAudio = false, hasNaturaMap = false;
		List<BdlFormato> formatiImage = getFormatiNaturaSingleImage(cdTipoOggetto);
		if(formatiImage!=null && !formatiImage.isEmpty()) {
			hasNaturaAudio = hasNaturaAudio(cdTipoOggetto);
			hasNaturaMap = hasNaturaMap(cdTipoOggetto);
			hasNaturaMultiImage = hasNaturaMultiImage(cdTipoOggetto);
			if(!hasNaturaAudio && !hasNaturaMap && !hasNaturaMultiImage) {
				hasNaturaSingleImage = true;
			}
		}
		return hasNaturaSingleImage;
	}
	
	/**
	 * Controlla natura imagemulti.
	 *
	 * @param cdTipoOggetto  codice tipo oggetto
	 * @return boolean
	 * @throws SQLException 
	 */
	public static Boolean hasNaturaMultiImage(BigDecimal cdTipoOggetto) throws SQLException {
		Boolean hasNaturaSingleImage = false, hasNaturaMultiImage = false, hasNaturaAudio = false, hasNaturaMap = false;
		List<BdlFormato> formatiImage = getFormatiNaturaMultiImage(cdTipoOggetto);
		if(formatiImage!=null && !formatiImage.isEmpty()) {
			hasNaturaAudio = hasNaturaAudio(cdTipoOggetto);
			hasNaturaMap = hasNaturaMap(cdTipoOggetto);
			hasNaturaSingleImage = hasNaturaSingleImage(cdTipoOggetto);
			if(!hasNaturaAudio && !hasNaturaMap && !hasNaturaSingleImage) {
				hasNaturaMultiImage = true;
			}
		}
		return hasNaturaMultiImage;
	}
}
