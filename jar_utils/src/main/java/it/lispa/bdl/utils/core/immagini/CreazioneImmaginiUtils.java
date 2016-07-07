package it.lispa.bdl.utils.core.immagini;

import it.lispa.bdl.commons.domain.BdlFormato;
import it.lispa.bdl.utils.core.BdlUtilsApp;
import it.lispa.bdl.utils.core.BdlUtilsAppConstants;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.im4java.core.CommandException;

public class CreazioneImmaginiUtils extends BaseImmaginiUtils {

	private static Logger log = Logger.getLogger(CreazioneImmaginiUtils.class);

	private static final BaseImmaginiUtils immaginiUtils = new BaseImmaginiUtils();
	
	/**
	 * Creo le immagini derivate. E' il metodo principale.
	 */
	public static List<String> creaImmaginiDerivate(String pathImmaginiDigitalizzate, BigDecimal nroImmaginiDigitalizzate, List<BdlFormato> formatiImage, String imagemagickBin) {
		log.debug("[creaImmaginiDerivate] Entro nel metodo ");
		
		List<String> erroriOccorsi = new ArrayList<String>();
		
		/* Imposto le properties */
		immaginiUtils.setProperties(imagemagickBin);
		
		/* 1. Mi riporto ad una situazione pulita */
		log.info(BdlUtilsApp.prependLogStrInizio + "Fase 1: Inizializzazione... ");
		erroriOccorsi.addAll(initCreaImmaginiDerivate(pathImmaginiDigitalizzate, formatiImage));
		if(erroriOccorsi.isEmpty()) {
			log.info(BdlUtilsApp.prependLogStrInizio + "OK ");
			/* 2. Verifico che il materiale sia OK */
			log.info(BdlUtilsApp.prependLogStrInizio + "Fase 2: Verifica delle immagini digitalizzate... ");
			erroriOccorsi.addAll(checkImmaginiDigitalizzate(pathImmaginiDigitalizzate, nroImmaginiDigitalizzate));
			if(erroriOccorsi.isEmpty()) {
				log.info(BdlUtilsApp.prependLogStrInizio + "OK ");
				
				log.debug("[creaImmaginiDerivate] Le verifiche sono andate a buon fine! "
						+ "Posso procedere con la generazione delle immagini derivate... ");
				/* 3. Genero le immagini derivate */
				log.info(BdlUtilsApp.prependLogStrInizio + "Fase 3: Generazione delle immagini derivate... ");
				erroriOccorsi.addAll(generaImmaginiDerivate(pathImmaginiDigitalizzate, formatiImage));
				if(erroriOccorsi.isEmpty()) {
					log.info(BdlUtilsApp.prependLogStrInizio + "OK ");
					/* 4. Segnalo all'utente che tutto è andato a buon fine */
					erroriOccorsi.addAll(segnalaGenerazioneImmagini(pathImmaginiDigitalizzate));
				}
			}
		}
		
		return erroriOccorsi;
	}
	
	/**
	 * Segnalo la generazione delle immagini derivate creando un file
	 * ".creato" all'interno del pathImmaginiDigitalizzate
	 */
	private static List<String> segnalaGenerazioneImmagini(String pathImmaginiDigitalizzate) {
		log.debug("[segnalaGenerazioneImmagini] Entro nel metodo ");
		List<String> erroriOccorsi = new ArrayList<String>();
		try {
			/* Creo il file .creato */
			File fileCreato = new File(immaginiUtils.makeFilePath(pathImmaginiDigitalizzate, BdlUtilsAppConstants.FILE_CREATO));
			FileUtils.touch(fileCreato);
		} catch (Exception e) {
			erroriOccorsi.add(BaseImmaginiUtils.prependSys + 
					"Si e' verificato un errore di sistema: [" + e.getClass().getName() + "] " + e.getMessage());
		}
		log.debug("[segnalaGenerazioneImmagini] Ritorno " + erroriOccorsi.size() + " errori ");
		return erroriOccorsi;
	}
	
	/**
	 * Verifica che esista la dir pathImmaginiDigitalizzate e ricrea una situazione
	 * pulita per poi potre generare correttamente le immgini derivate. 
	 */
	private static List<String> initCreaImmaginiDerivate(String pathImmaginiDigitalizzate, final List<BdlFormato> formatiImage) {
		List<String> erroriOccorsi = new ArrayList<String>();
		
		log.debug("[initCreaImmaginiDerivate] Entro nel metodo ");
		
		File pathFileImmaginiDigitalizzate = new File(pathImmaginiDigitalizzate);
		if (!pathFileImmaginiDigitalizzate.exists()) {
			erroriOccorsi.add(BaseImmaginiUtils.prependDirectory + 
					"La cartella " + BdlUtilsAppConstants.DIR_MASTER + " non esiste, e' stato effettuato il caricamento via SFTP? ");
		} else {
			FileFilter dirFilter = null;
			/* Creo FileFilter per isolare gli elementi da eliminare */
			dirFilter = new FileFilter() {
				public boolean accept(File file) {
					
					Boolean isDirFormato = Boolean.FALSE;
					for(BdlFormato formatoImage : formatiImage) {
						isDirFormato = isDirFormato || file.getName().equalsIgnoreCase(formatoImage.getDnNomeFormato());
					}
					
					Boolean isDirToDelete  = file.isDirectory() && isDirFormato;
					Boolean isFileToDelete = file.isFile() && file.getName().equals(BdlUtilsAppConstants.FILE_CREATO);
					
					return isDirToDelete || isFileToDelete;
				}
			};
			/* Cancello tutto tranne ciò che e' indicato nel FileFilter */
			File[] arrFile = pathFileImmaginiDigitalizzate.listFiles(dirFilter);
			for (File file : arrFile) {
				if(file.isDirectory()) {
					try {
						FileUtils.deleteDirectory(file);
					} catch (IOException e) {
						erroriOccorsi.add(BaseImmaginiUtils.prependSys + 
								"Si e' verificato un errore di sistema: [" + e.getClass().getName() + "] " + e.getMessage());
					}
				} else if(file.isFile()) {
					try {
						FileUtils.forceDelete(file);
					} catch (IOException e) {
						erroriOccorsi.add(BaseImmaginiUtils.prependSys + 
								"Si e' verificato un errore di sistema: [" + e.getClass().getName() + "] " + e.getMessage());
					}
				}
			}
		}
		log.debug("[initCreaImmaginiDerivate] Ritorno " + erroriOccorsi.size() + " errori ");
		return erroriOccorsi;
	}

	/**
	 * Verifica che le immagini digitalizzate dal Digitalizzatore siano OK.
	 */
	private static List<String> checkImmaginiDigitalizzate(String pathImmaginiDigitalizzate, BigDecimal nroImmaginiDigitalizzate) {
		List<String> erroriOccorsi = new ArrayList<String>();

		log.debug("[checkImmaginiDigitalizzate] Entro nel metodo ");
		
		try {
			File pathFileImmaginiDigitalizzate = new File(pathImmaginiDigitalizzate);
			if (!pathFileImmaginiDigitalizzate.exists()) {
				erroriOccorsi.add(BaseImmaginiUtils.prependDirectory + 
						"La cartella " + BdlUtilsAppConstants.DIR_MASTER + " non esiste, e' stato effettuato il caricamento via SFTP? ");
			} else {
				/* Verifico che il materiale nella dir MASTER sia OK */
				erroriOccorsi.addAll(checkDirMaster(pathImmaginiDigitalizzate, nroImmaginiDigitalizzate));
			}
		} catch (Exception e) {
			erroriOccorsi.add(BaseImmaginiUtils.prependSys + 
					"Si e' verificato un errore di sistema: [" + e.getClass().getName() + "] " + e.getMessage());
		}
		log.debug("[checkImmaginiDigitalizzate] Ritorno " + erroriOccorsi.size() + " errori ");
		return erroriOccorsi;
	}
	
	/**
	 * Verifica che le immagini siano OK.
	 */
	private static List<String> checkDirMaster(String pathImmaginiDigitalizzate, BigDecimal nroImmaginiDigitalizzate) {
		List<String> erroriOccorsi = new ArrayList<String>();

		log.debug("[checkDirMaster] Entro nel metodo ");
		
		String pathImmaginiMaster = immaginiUtils.makeFilePath(pathImmaginiDigitalizzate, BdlUtilsAppConstants.DIR_MASTER);
		
		File pathFileImmaginiMaster = new File(pathImmaginiMaster);
		if (!pathFileImmaginiMaster.exists()) {
			erroriOccorsi.add(BaseImmaginiUtils.prependDirectory + 
					"La cartella " + BdlUtilsAppConstants.DIR_MASTER + " non esiste, e' stato effettuato il caricamento via SFTP? ");
		} else {
			
			String imgNome 		= null;
			String imgPath 		= null;
			String imgPathHuman = null;
			File   imgFile 		= null;
			
			final String regexp = "^\\d{4}\\." + BdlUtilsAppConstants.EXT_MASTER + "$";

			Integer numImmagini = 0;
			String[] listOfFile = pathFileImmaginiMaster.list();

			/* 1. Controllo che si rispettino le regole di naming e il nroImmaginiDigitalizzate */
			for (int i=0; i<listOfFile.length; i++) {

				imgNome 	 = listOfFile[i];
				imgPathHuman = BdlUtilsAppConstants.DIR_MASTER + File.separator + imgNome;

				if (!imgNome.equals(BdlUtilsAppConstants.DOT) && !imgNome.equals(BdlUtilsAppConstants.DOTDOT)) {
					numImmagini++;
				}
				if (!imgNome.matches(regexp)) {
					erroriOccorsi.add(BaseImmaginiUtils.prependImg + imgPathHuman + " non rispetta le regole di naming o "
							+ "non ha estensione \"" + BdlUtilsAppConstants.EXT_MASTER + "\" ");
				}
			}
			if (numImmagini!=nroImmaginiDigitalizzate.intValue()) {
				erroriOccorsi.add(BaseImmaginiUtils.prependImg + 
						"Nella cartella " + BdlUtilsAppConstants.DIR_MASTER + " sono presenti "+ numImmagini + " "
								+ "immagini, ma me ne aspetto " + nroImmaginiDigitalizzate.intValue());
			}
			
			if(erroriOccorsi.isEmpty()) {
				/* 2. Controllo che siano presenti tutte le immagini richieste */
				for (int i=1; i<=nroImmaginiDigitalizzate.intValue(); i++) {

					imgNome = String.format("%04d", i) + BdlUtilsAppConstants.DOT + BdlUtilsAppConstants.EXT_MASTER;

					imgPath = pathImmaginiDigitalizzate + File.separator + BdlUtilsAppConstants.DIR_MASTER + File.separator + imgNome;
					imgPathHuman = BdlUtilsAppConstants.DIR_MASTER + File.separator + imgNome;

					imgFile = new File(imgPath);
					Boolean foundFile = Boolean.FALSE;
					if (imgFile.exists()) {
						foundFile = Boolean.TRUE;
					}
					if (!foundFile) {
						erroriOccorsi.add(BaseImmaginiUtils.prependImg+imgPathHuman+" non esiste ");
					}
				}
			}
		}
		log.debug("[checkDirMaster] Ritorno "+erroriOccorsi.size()+" errori ");
		return erroriOccorsi;
	}
	/**
	 * Tratta il materiale di natura IMAGE.
	 */
	private static List<String> generaImmaginiDerivate(String pathImmaginiDigitalizzate, List<BdlFormato> formatiImage) {
		List<String> erroriOccorsi = new ArrayList<String>();

		log.debug("[generaImmaginiDerivate] Entro nel metodo ");
		
		File pathFileImmaginiMaster = new File(immaginiUtils.makeFilePath(pathImmaginiDigitalizzate, BdlUtilsAppConstants.DIR_MASTER));
		File[] arrFileImmaginiMaster = pathFileImmaginiMaster.listFiles();

		String pathDestinazioneImmagini = null;
		File fileTmp = null;
		for (BdlFormato formatoImage : formatiImage) {
			/* Creo la dir */
			try {
				pathDestinazioneImmagini = createDir(pathImmaginiDigitalizzate, formatoImage.getDnNomeFormato());
			} catch (Exception e) {
				erroriOccorsi.add(BaseImmaginiUtils.prependSys + 
						"Si e' verificato un errore di sistema: [" + e.getClass().getName() + "] " + e.getMessage());
			}

			/* Genero le immagini */
			String[] arrTmp = null;
			for(int i=0; i<arrFileImmaginiMaster.length; i++) {	
				arrTmp = arrFileImmaginiMaster[i].getName().split("\\.");
				fileTmp = new File(pathDestinazioneImmagini + File.separator + arrTmp[0] + BdlUtilsAppConstants.DOT + BdlUtilsAppConstants.EXT_IMAGE);	
				if(formatoImage.getVlPixelBoxSize()==null) {
					try {
						convertWrapper.resampleImage(arrFileImmaginiMaster[i].getPath(), fileTmp.getPath(), formatoImage.getVlRisoluzione().intValue());
					} catch (CommandException e) {
						erroriOccorsi.add(BaseImmaginiUtils.prependSys + 
								"Si e' verificato un errore di sistema: [" + e.getClass().getName() + "] " + e.getMessage());
					}
				} else {
					/* THUMBNAIL */
					try {
						convertWrapper.scaleImage(arrFileImmaginiMaster[i].getPath(), fileTmp.getPath(), formatoImage.getVlPixelBoxSize().intValue(), formatoImage.getVlPixelBoxSize().intValue(), "!");
					} catch (CommandException e) {
						erroriOccorsi.add(BaseImmaginiUtils.prependSys + 
								"Si e' verificato un errore di sistema: [" + e.getClass().getName() + "] " + e.getMessage());
					}
				}
			}
		}
		log.debug("[generaImmaginiDerivate] Ritorno "+erroriOccorsi.size()+" errori ");
		return erroriOccorsi;
	}

	private static String createDir(String pathImmaginiDigitalizzate, String nomeFormato) throws Exception {
		String dirToCreate = immaginiUtils.makeFilePath(pathImmaginiDigitalizzate, nomeFormato);
		log.debug("[createDir] Entro nel metodo ");
		log.debug("[createDir] dirToCreate="+dirToCreate);
		
		File dirFile = new File(dirToCreate);
		if(dirFile.exists()){
			FileUtils.deleteDirectory(dirFile);
		} else if(!dirFile.mkdir()){
			throw new Exception("[createDir] Ci sono stati dei problemi nella creazione della dir "+dirFile.getPath());
		}
		return dirToCreate;
	}
}
