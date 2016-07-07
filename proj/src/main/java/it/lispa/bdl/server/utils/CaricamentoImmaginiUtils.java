package it.lispa.bdl.server.utils;

import it.lispa.bdl.commons.domain.BdlCollezione;
import it.lispa.bdl.commons.domain.BdlEnte;
import it.lispa.bdl.commons.domain.BdlFormato;
import it.lispa.bdl.commons.domain.BdlOggettoOriginale;
import it.lispa.bdl.commons.domain.BdlProgetto;
import it.lispa.bdl.commons.utils.IdentifyWrapper;
import it.lispa.bdl.server.repository.BdlCollezioneRepository;
import it.lispa.bdl.server.repository.BdlEnteRepository;
import it.lispa.bdl.server.repository.BdlOggettoOriginaleRepository;
import it.lispa.bdl.server.repository.BdlProgettoRepository;
import it.lispa.bdl.shared.utils.BdlSharedConstants;
import it.lispa.bdl.utils.core.BdlUtilsAppConstants;

import java.io.File;
import java.io.FilenameFilter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.util.PDFTextStripper;
import org.im4java.core.InfoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class CaricamentoImmaginiUtils.
 */
@Component
public class CaricamentoImmaginiUtils extends BaseImmaginiUtils {

	/** log. */
	private static Logger log = Logger.getLogger(CaricamentoImmaginiUtils.class);
	
	/** ente repository. */
	@Autowired
	private BdlEnteRepository enteRepository;
	
	/** progetto repository. */
	@Autowired
	private BdlProgettoRepository progettoRepository;
	
	/** collezione repository. */
	@Autowired
	private BdlCollezioneRepository collezioneRepository;
	
	/** oggetto repository. */
	@Autowired
	private BdlOggettoOriginaleRepository oggettoRepository;
	
	/** prepend collezione. */
	private static String prependCollezione 			= "[CC] ";
	/** prepend progetto. */
	private static String prependProgetto 				= "[PP] ";
	/** prepend ente istituto. */
	private static String prependEnteIstituto 			= "[EE] Istituto ";
	/** prepend ente digitalizzatore. */
	private static String prependEnteDigitalizzatore 	= "[EE] Digitalizzatore ";
	
	/** server messages. */
	ResourceBundle serverMessages = ResourceBundle.getBundle("it.lispa.bdl.server.messages.CaricamentoImmaginiMsg", new UTF8Control());

	/**
	 * Verifica caricamento immagini.
	 *
	 * @param oggettoOriginale  oggetto originale
	 * @param digitalizzatore  digitalizzatore
	 * @param nrImmaginiDigitalizzate  nr immagini digitalizzate
	 * @return list
	 */
	public List<String> verificaCaricamentoImmagini(BdlOggettoOriginale oggettoOriginale, BdlEnte digitalizzatore, BigDecimal nrImmaginiDigitalizzate) {
		log.debug("[verificaCaricamentoImmagini] Entro nel metodo... ");
		
		/* Ho un materile AUDIO? */
		final Boolean hasNaturaAudio = hasNaturaAudio(oggettoOriginale.getCdTipoOggetto());
		
		List<String> erroriOccorsi = new ArrayList<String>();
		String pdfFilePath = "";
		try {
			BdlOggettoOriginale oggettoJpa = oggettoRepository.findByCdOggettoOriginale(oggettoOriginale.getCdOggettoOriginale());
			if (oggettoJpa==null) {
				/* Controllo l'OggettoOriginale */
				erroriOccorsi.add(CaricamentoImmaginiUtils.prependOggettoOriginale+ oggettoOriginale.getCdOggettoOriginale() + " non esiste ");
			} else {
				if (!BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_CATALOGATO.equals(oggettoJpa.getDsStato())
						&& !BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INCATALOGAZIONE.equals(oggettoJpa.getDsStato())) {
					/* Controllo che abbia un OggettoOriginale in stato CATALOGATO/INCATALOGAZIONE */
					erroriOccorsi.add(CaricamentoImmaginiUtils.prependOggettoOriginale+ oggettoOriginale.getCdOggettoOriginale() + " non risulta catalogato/in catalogazione ");
				}
				BdlCollezione collezioneJpa = collezioneRepository.findByCdCollezione(oggettoJpa.getCdCollezione());
				if (collezioneJpa==null) {
					/* Controllo la Collezione */
					erroriOccorsi.add(CaricamentoImmaginiUtils.prependCollezione+ oggettoJpa.getCdCollezione() + " non esiste ");
				} else {
					BdlProgetto progettoJpa = progettoRepository.findByCdProgetto(collezioneJpa.getCdProgetto());
					if (progettoJpa==null) {
						/* Controllo il Progetto */
						erroriOccorsi.add(CaricamentoImmaginiUtils.prependProgetto+ collezioneJpa.getCdProgetto() + " non esiste ");
					} else {
						if (BdlSharedConstants.BDL_PROGETTO_STATO_CONCLUSO.equals(progettoJpa.getDsStato())) {
							/* Controllo che il progetto non sia CONCLUSO */
							erroriOccorsi.add(CaricamentoImmaginiUtils.prependProgetto + progettoJpa.getCdProgetto()+ " risulta concluso ");
						}
						BdlEnte enteJpa = enteRepository.findByCdEnte(progettoJpa.getCdEnte());
						if (enteJpa==null) {
							/* Controllo l'Istituto (Ente) */
							erroriOccorsi.add(CaricamentoImmaginiUtils.prependEnteIstituto+ progettoJpa.getCdEnte() + " non esiste ");
						} else {
							BdlEnte digitalizzatoreJpa = enteRepository.findByCdEnte(enteJpa.getCdEnteDigit());
							if (digitalizzatoreJpa==null) {
								/* Controllo il Digitalizzatore */
								erroriOccorsi.add(CaricamentoImmaginiUtils.prependEnteDigitalizzatore+ enteJpa.getCdEnteDigit() + " non esiste ");
							} else {
								if (!digitalizzatoreJpa.getCdEnte().equals(digitalizzatore.getCdEnte())) {
									/* Controllo che l'Ente sia autorizzato ad accedere all'OggettoOriginale */
									erroriOccorsi.add(CaricamentoImmaginiUtils.prependEnteDigitalizzatore+digitalizzatore.getCdEnte()+" non autorizzato per oggetto "+ oggettoOriginale.getCdOggettoOriginale() + "");
								}

								String codiceIdentificativoOO = enteJpa.getCdEnte().toString() + File.separator + progettoJpa.getCdProgetto().toString() + File.separator + collezioneJpa.getCdCollezione().toString() + File.separator + oggettoJpa.getCdOggettoOriginale().toString();
								log.debug("[verificaCaricamentoImmagini] codiceIdentificativoOO="+codiceIdentificativoOO);
								
								String tmpAreaAsString = properties.getDirNetappTmp() + File.separator + codiceIdentificativoOO;
								File tmpAreaFile = new File(tmpAreaAsString);
								if (!tmpAreaFile.exists()) {
									erroriOccorsi.add(CaricamentoImmaginiUtils.prependDirectory+"La cartella dell'oggetto "+codiceIdentificativoOO+" non esiste, e' stato effettuato il caricamento via SFTP?");
								} else {

									/* Effettuo i controlli sui formati dell'oggetto originale */
									List<BdlFormato> formatiToCheck = (List<BdlFormato>) bdlFormatoRepository.findByFlRegolaNamingAndCdTipoOggetto(true, oggettoJpa.getCdTipoOggetto());
									for (BdlFormato formatoToCheck : formatiToCheck) {
										log.debug("[verificaCaricamentoImmagini] Tratto il formato "+formatoToCheck.getDnNomeFormato().toUpperCase()+" di natura "+formatoToCheck.getDsNatura().toUpperCase());
										erroriOccorsi.addAll(checkFormato(tmpAreaAsString, formatoToCheck, nrImmaginiDigitalizzate));
									}
									
									/* Nel caso in cui ho un materiale AUDIO... bypasso i controlli legati al PDF */
									if(!hasNaturaAudio) {
										/* Controllo l'esistenza della dirPDF */
										String dirPDFPath = tmpAreaAsString + File.separator + BdlServerConstants.DIR_PDF;
										log.debug("[verificaCaricamentoImmagini] dirPDFPath="+dirPDFPath);
										File dirPDF = new File(dirPDFPath);
										if (!dirPDF.exists()) {
											erroriOccorsi.add(CaricamentoImmaginiUtils.prependPdf+"La directory <"+BdlServerConstants.DIR_PDF+"> che deve contenere il PDF dell'intera opera digitale non esiste ");
										} else {
											/* Controllo l'esistenza del PDF dell'opera completa */
											String filePDFCompletoPath = dirPDFPath + File.separator + oggettoJpa.getCdOggettoOriginale().toString() + ".pdf";
											log.debug("[verificaCaricamentoImmagini] pdfFilePath="+pdfFilePath);
											File filePDFCompleto = new File(filePDFCompletoPath);
											if (!filePDFCompleto.exists()) {
												erroriOccorsi.add(CaricamentoImmaginiUtils.prependPdf+"Il file PDF dell'opera digitale completa non esiste o "
														+ "non e' stato nominato correttamente. Mi aspetto il file \""+oggettoJpa.getCdOggettoOriginale().toString()+".pdf\"");
											}
										}
										String dirPDFMultipaginaPath = tmpAreaAsString + File.separator + BdlServerConstants.DIR_PDFSINGLE;
										log.debug("[verificaCaricamentoImmagini] dirPDFMultipaginaPath="+dirPDFMultipaginaPath);
										/* Faccio un check iniziale per valutare se ho un PDF Multipagina */
										Boolean isPDFMultipagina = isPDFMultipagina(dirPDFMultipaginaPath);
										if(isPDFMultipagina) {
											/* Faccio tutti i controlli del caso per il PDF Multipagina */
											erroriOccorsi.addAll(checkPDFMultipagina(dirPDFMultipaginaPath, nrImmaginiDigitalizzate));
											if(erroriOccorsi.isEmpty()) {
												/* SSE non si sono verificati errori precedentemente ed i controlli di checkPDFMultipagina 
												 * non hanno prodotto errori... setto il flPdfMultipagina dell'oggetto originale **********/
												oggettoOriginale.setFlPdfMultipagina(Boolean.TRUE);
											}
											
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			erroriOccorsi.add("[SYS] Si verifica un errore di sistema: " + e.getMessage());
		}
		log.debug("[verificaCaricamentoImmagini] ritorno "+erroriOccorsi.size()+" errori");
		log.debug("[verificaCaricamentoImmagini] getDirNetappTmp = "+properties.getDirNetappTmp());
		
		return erroriOccorsi;
	}
	
	private Boolean isPDFMultipagina(String dirPDFMultipaginaPath) {
		/* Esiste dirPDFMultipagina?! */
		File dirPDFMultipagina = new File(dirPDFMultipaginaPath);
		if (!dirPDFMultipagina.exists()) {
			return Boolean.FALSE;
		} else {
			/* Esiste almeno il PDF della prima pagina?! */
			String firstPDFName = String.format("%04d", 1);
			String firstPDFPath = dirPDFMultipaginaPath + File.separator + firstPDFName + BdlServerConstants.DOT + "pdf";
			
			File firstPDF = new File(firstPDFPath);
			if (!firstPDF.exists()) {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}
	
	private List<String> checkPDFMultipagina(String dirPDFMultipaginaPath, BigDecimal nroImgDigitalizzate) {
		List<String> erroriOccorsi = new ArrayList<String>();
		
		log.debug("[checkForPDFMultipagina] Entro nel metodo ");
		
		/* Nel caso in cui sia un PDF Multipagina... */
		File dirPDFMultipagina = new File(dirPDFMultipaginaPath);
		
		String myPDFName 		= null;
		String myPDFPath 		= null;
		String myPDFPathHuman   = null;
		File   myPDFFile 		= null;
		
		final String regexp = "^\\d{4}\\.pdf$";
		
		Integer numPDF = 0;
		String[] filePresentiInDirPDF = dirPDFMultipagina.list();
		
		/* 1. Controllo che si rispettino le regole di naming e il nroImmaginiDigitalizzate */
		for (int i=0; i<filePresentiInDirPDF.length; i++) {

			myPDFName 	   = filePresentiInDirPDF[i];
			myPDFPathHuman = BdlServerConstants.DIR_PDFSINGLE + File.separator + myPDFName;

			if (!myPDFName.equals(BdlUtilsAppConstants.DOT) && !myPDFName.equals(BdlUtilsAppConstants.DOTDOT)) {
				numPDF++;
			}
			if (!myPDFName.matches(regexp)) {
				erroriOccorsi.add(BaseImmaginiUtils.prependPdf + 
						myPDFPathHuman + " nome non rispetta le regole di naming o non ha estensione PDF ");
			}
		}
		if (numPDF!=nroImgDigitalizzate.intValue()) {
			erroriOccorsi.add(BaseImmaginiUtils.prependPdf + 
					"Nella cartella " + BdlUtilsAppConstants.DIR_MASTER + " sono presenti "+ numPDF + " immagini, ma me ne aspetto " + nroImgDigitalizzate.intValue());
		}
		
		
		if(erroriOccorsi.isEmpty()) {
			/* 2. Controllo che siano presenti tutti i PDF */
			for (int i=1; i<=nroImgDigitalizzate.intValue(); i++) {

				myPDFName = String.format("%04d", i) + ".pdf";

				myPDFPath 	   = dirPDFMultipagina + File.separator + myPDFName;
				myPDFPathHuman = BdlServerConstants.DIR_PDFSINGLE + File.separator + myPDFName;

				myPDFFile = new File(myPDFPath);
				Boolean foundFile = Boolean.FALSE;
				if (myPDFFile.exists()) {
					foundFile = Boolean.TRUE;
				}
				if (!foundFile) {
					erroriOccorsi.add(BaseImmaginiUtils.prependPdf + myPDFPathHuman + " non esiste ");
				}
			}
		}
		log.debug("[checkForPDFMultipagina] Ritorno "+erroriOccorsi.size()+" errori ");
		
		return erroriOccorsi;
	}
	
	/**
	 * Check formato.
	 *
	 * @param tmpAreaAsString  tmp area as string
	 * @param formato  formato
	 * @param numeroImmaginiDigitalizzate  numero immagini digitalizzate
	 * @return list
	 */
	private List<String> checkFormato(String tmpAreaAsString, BdlFormato formato, BigDecimal numeroImmaginiDigitalizzate){

		Boolean hasNaturaAudio 	= hasNaturaAudio(formato.getCdTipoOggetto());
		
		Boolean isNaturaAudio = BdlSharedConstants.BDL_FORMATO_NATURA_AUDIO.equals(formato.getDsNatura());
		Boolean isNaturaMultiImage = BdlSharedConstants.BDL_FORMATO_NATURA_MULTIIMAGE.equals(formato.getDsNatura()); 
		Boolean isNaturaSingleImage = BdlSharedConstants.BDL_FORMATO_NATURA_SINGLEIMAGE.equals(formato.getDsNatura());
		Boolean isNaturaMap   = BdlSharedConstants.BDL_FORMATO_NATURA_MAP.equals(formato.getDsNatura());
		
		if(isNaturaAudio) {
			CaricamentoImmaginiUtils.prependStr = CaricamentoImmaginiUtils.prependAudio;
			log.debug("[checkFormato] CaricamentoImmaginiUtils.prependStr = "+CaricamentoImmaginiUtils.prependStr);
		}
			
		String nomeFormato = formato.getDnNomeFormato();
		
		log.debug("[checkFormato] tratto formato="+nomeFormato);
		List<String> errori = new ArrayList<String>();

		String formatoDirPath = tmpAreaAsString + File.separator + nomeFormato;
		File formatoDir = new File(formatoDirPath);
		log.debug("[checkFormato] formatoDirPath="+formatoDirPath);

		if (!formatoDir.exists()) {
			errori.add(CaricamentoImmaginiUtils.prependDirectory+"La cartella per il formato "+nomeFormato+" non esiste");
		} else {
			
			String[] exts = getEstensioni(formato.getDsEstensione());

			if(hasNaturaAudio){
				if(!isNaturaAudio){
					numeroImmaginiDigitalizzate = BigDecimal.ONE;
				}
			}
			
			for (int i = 1; i <= numeroImmaginiDigitalizzate.intValue(); i++) {
				String nomeImmagine = String.format("%04d", i);

				String immaginePath = "";
				String immaginePathHuman = "";
				File immagine;
				
				Boolean foundFile = false;

				BigDecimal risoluzione = formato.getVlRisoluzione();
				BigDecimal pixelBoxSize = formato.getVlPixelBoxSize();
				
				String extHuman;
				if(exts.length>1){
					StringBuffer buf = new StringBuffer();
					for(String ext:exts){
						if(buf.length()!=0) {
							buf.append("|");
						}
						buf.append(ext);
					}
					String subExtHuman = buf.toString();
					
					extHuman = "("+subExtHuman+")";
				}else{
					extHuman = exts[0];
				}
				
				for(String ext:exts){
					String tmp = formatoDirPath + File.separator + nomeImmagine + BdlServerConstants.DOT + ext;
					immagine = new File(tmp);
					if (immagine.exists()) {
						immaginePath = formatoDirPath + File.separator + nomeImmagine + BdlServerConstants.DOT + ext;
						immaginePathHuman = nomeFormato + File.separator + nomeImmagine + BdlServerConstants.DOT + ext;
						foundFile = true;
						break;
					}
				}
				
				if (!foundFile) {
					if(isNaturaSingleImage || isNaturaMultiImage || isNaturaMap){
						errori.add(CaricamentoImmaginiUtils.prependStr+nomeFormato + File.separator + nomeImmagine+BdlServerConstants.DOT+extHuman+" non esiste");
					}
				} else {
					if(isNaturaSingleImage || isNaturaMultiImage || isNaturaMap){
						if (risoluzione != null || pixelBoxSize != null) {
							try {
								IdentifyWrapper imageInfo = new IdentifyWrapper(immaginePath, properties.getImagemagickBin());							
								if (risoluzione != null){
									if (!risoluzione.equals(new BigDecimal(imageInfo.getResolutionw()))) {
										errori.add(CaricamentoImmaginiUtils.prependStr+immaginePathHuman+" ha una risoluzione (H) di "+imageInfo.getResolutionw()+" DPI diversa da "+risoluzione+" DPI");
									}
									if (!risoluzione.equals(new BigDecimal(imageInfo.getResolutionh()))) {
										errori.add(CaricamentoImmaginiUtils.prependStr+immaginePathHuman+" ha una risoluzione (V) di "+imageInfo.getResolutionh()+" DPI diversa da "+risoluzione+" DPI");
									}								
								}
								if (pixelBoxSize != null){
									if (!pixelBoxSize.equals(new BigDecimal(imageInfo.getWidth()))) {
										errori.add(CaricamentoImmaginiUtils.prependStr+immaginePathHuman+" ha una larghezza di "+imageInfo.getWidth()+" pixel diversa da "+pixelBoxSize+" pixel");
									}
									if (!pixelBoxSize.equals(new BigDecimal(imageInfo.getHeight()))) {
										errori.add(CaricamentoImmaginiUtils.prependStr+immaginePathHuman+" ha una altezza di "+imageInfo.getHeight()+" pixel diversa da "+pixelBoxSize+" pixel");
									}
								}
							} catch (InfoException e) {
								errori.add(CaricamentoImmaginiUtils.prependStr+immaginePathHuman+" si e' verificata eccezione in imagemagick:" + e.getMessage());
							}
						}
					}
				}
			}

			int numImmagini = 0;
			String[] filePresenti = formatoDir.list();
			for (int i = 0; i < filePresenti.length; i++) {

				String immagineName = filePresenti[i];
				if (immagineName != BdlServerConstants.DOT && immagineName != BdlServerConstants.DOTDOT ) {
					numImmagini++;
				}
				String immaginePathHuman = nomeFormato + File.separator + immagineName;

				Boolean foundAtLeastAnExt = false;
				for(String ext:exts){
					if (immagineName.endsWith(BdlServerConstants.DOT + ext)) {
						foundAtLeastAnExt = true;
					}
				}
				
				if (!foundAtLeastAnExt) {
					errori.add(CaricamentoImmaginiUtils.prependStr + immaginePathHuman+" estensione non corretta per il formato " + nomeFormato);
				}
				String regexp;
				if(!isNaturaAudio){
					regexp = "^\\d{4}\\..*$";
				}else{
					regexp = "^\\d{4}\\s\\-\\s.*\\..{3}$";
				}
				if (!immagineName.matches(regexp)) {
					errori.add(CaricamentoImmaginiUtils.prependStr + immaginePathHuman + " nome non rispetta le regole di naming");
				}
			}

			if (numImmagini != numeroImmaginiDigitalizzate.intValue()) {
				StringBuilder errorToAdd = new StringBuilder();
				errorToAdd.append(CaricamentoImmaginiUtils.prependDirectory+"Nella cartella " + nomeFormato + " sono presenti "+ numImmagini);
				errorToAdd.append(" file, ma me ne aspetto " + numeroImmaginiDigitalizzate.intValue());
				errori.add(errorToAdd.toString());
			}
		}
		log.debug("[checkFormato] per il formato="+nomeFormato+" ritorno "+errori.size());
		return errori;
	}
	
	/**
	 * Legge estensioni.
	 *
	 * @param estensioneFormato  estensione formato
	 * @return estensioni
	 */
	public String[] getEstensioni(String estensioneFormato){
		String[] exts = estensioneFormato.split("\\|");
		return exts;
	}

	/**
	 * Legge existing file.
	 *
	 * @param dir  dir
	 * @param filename  filename
	 * @param estensioneFormato  estensione formato
	 * @return existing file
	 */
	public File getExistingFile(String dir, String filename, String estensioneFormato) {
		
		String[] exts = getEstensioni(estensioneFormato);
		
		StringBuilder strFilename = new StringBuilder();

		strFilename.append(dir);
		strFilename.append(File.separator);
		strFilename.append(filename);
		strFilename.append(BdlServerConstants.DOT);
		
		File imageFile = null;
		
		for(String ext:exts){
			String tmp = strFilename.toString() + ext;
			File myImageFile = new File(tmp);
			if (myImageFile.exists()) {
				imageFile = new File(tmp);
				break;
			}
		}
		
		return imageFile;
	}
	
	/**
	 * Legge existing file.
	 *
	 * @param dir  dir
	 * @param filename  filename
	 * @return existing file
	 */
	public File getExistingFile(String dir, String filename) {
		
		StringBuilder strFilename = new StringBuilder();

		strFilename.append(dir);
		strFilename.append(File.separator);
		strFilename.append(filename);
		
		File imageFile = null;
		
		String tmp = strFilename.toString();
		File myImageFile = new File(tmp);
		if (myImageFile.exists()) {
			imageFile = new File(tmp);
		}
		
		return imageFile;
	}
	
	/**
	 * Legge existing audio.
	 *
	 * @param dir  dir
	 * @param filename  filename
	 * @param estensioneFormato  estensione formato
	 * @return existing audio
	 */
	public File getExistingAudio(String dir, String filename, String estensioneFormato) {
		
		final String myFilename = filename;
		
		FilenameFilter myFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.startsWith(myFilename)) {
					return true;
				}
				return false;
			}
		};

		File myDir = new File(dir);
		String audioFilename = null;
		
		File[] listOfFiles = myDir.listFiles(myFilter);
		if(listOfFiles.length==1) {
			audioFilename = listOfFiles[0].getName();
		}
		
		return getExistingFile(dir, audioFilename);
	}
	
	/**
	 * Manage pdf.
	 *
	 * @param pathToPdfFile  path to pdf file
	 * @return list
	 * @throws Exception exception
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public List<String> managePdf(String pathToPdfFile) throws Exception{
		log.debug("[managePdf] entering the method");
		List<String> bdlPdfPagine = new ArrayList<String>();
		PDDocument document = PDDocument.load(new File(pathToPdfFile));             
		List<PDPage> pages = (List<PDPage>) document.getDocumentCatalog().getAllPages();
		
		int pagesNum = pages.size();
		
		log.debug("[managePdf] found "+pagesNum+" pages");
		for(int i=0; i<pagesNum; i++){
			log.debug("[managePdf] treating page "+i+"/"+pagesNum);
			PDStream tmpStream = pages.get(i).getContents();
			
			PDDocument doc = new PDDocument();
		
			doc.addPage(pages.get(i));
			PDFTextStripper stripper = new PDFTextStripper();
			bdlPdfPagine.add(stripper.getText(doc));
			doc.close();
		}
		document.close();
		log.debug("[managePdf] exit from the method");
		return bdlPdfPagine;
	}

    /**
     * Cut string utf8.
     *
     * @param s  s
     * @param n  n
     * @return string
     */
    public String cutStringUtf8(String s, int n) {
	    byte[] utf8 = s.getBytes();
	    if (utf8.length < n) {
	    	n = utf8.length;
	    }
	    int n16 = 0;
	    int advance = 1;
	    int i = 0;
	    while (i < n) {
	      advance = 1;
	      if ((utf8[i] & 0x80) == 0) {
	    	  i += 1;
	      } else if ((utf8[i] & 0xE0) == 0xC0) {
	    	  i += 2;
	      } else if ((utf8[i] & 0xF0) == 0xE0){
	    	  i += 3;
	      } else { 
	    	  i += 4; 
	    	  advance = 2; 
	      }
	      if (i <= n) {
	    	  n16 += advance;
	      }
	    }
	    return s.substring(0,n16);
	}
}
