package it.lispa.bdl.server.utils;

import it.lispa.bdl.commons.domain.BdlAutore;
import it.lispa.bdl.commons.domain.BdlCollezione;
import it.lispa.bdl.commons.domain.BdlContestoArch;
import it.lispa.bdl.commons.domain.BdlEditore;
import it.lispa.bdl.commons.domain.BdlEnte;
import it.lispa.bdl.commons.domain.BdlImmagine;
import it.lispa.bdl.commons.domain.BdlLingua;
import it.lispa.bdl.commons.domain.BdlLivelloBiblio;
import it.lispa.bdl.commons.domain.BdlOggettoOriginale;
import it.lispa.bdl.commons.domain.BdlProgetto;
import it.lispa.bdl.commons.domain.BdlQualificaAutore;
import it.lispa.bdl.commons.domain.BdlQualificatoreData;
import it.lispa.bdl.commons.domain.BdlSoggetto;
import it.lispa.bdl.commons.domain.BdlSoggettoProd;
import it.lispa.bdl.commons.domain.BdlSupporto;
import it.lispa.bdl.commons.domain.BdlTecnicaGrafica;
import it.lispa.bdl.commons.domain.BdlTipoArchivio;
import it.lispa.bdl.commons.domain.BdlTipoGrafica;
import it.lispa.bdl.commons.domain.BdlTipoIdentificativo;
import it.lispa.bdl.commons.domain.BdlTipoOggetto;
import it.lispa.bdl.commons.domain.BdlUtente;
import it.lispa.bdl.server.repository.BdlAutoreRepository;
import it.lispa.bdl.server.repository.BdlCollezioneRepository;
import it.lispa.bdl.server.repository.BdlContestoArchRepository;
import it.lispa.bdl.server.repository.BdlEditoreRepository;
import it.lispa.bdl.server.repository.BdlEnteRepository;
import it.lispa.bdl.server.repository.BdlImmagineRepository;
import it.lispa.bdl.server.repository.BdlLinguaRepository;
import it.lispa.bdl.server.repository.BdlLivelloBiblioRepository;
import it.lispa.bdl.server.repository.BdlOggettoOriginaleRepository;
import it.lispa.bdl.server.repository.BdlProgettoRepository;
import it.lispa.bdl.server.repository.BdlQualificaAutoreRepository;
import it.lispa.bdl.server.repository.BdlQualificatoreDataRepository;
import it.lispa.bdl.server.repository.BdlSoggettoProdRepository;
import it.lispa.bdl.server.repository.BdlSoggettoRepository;
import it.lispa.bdl.server.repository.BdlSupportoRepository;
import it.lispa.bdl.server.repository.BdlTecnicaGraficaRepository;
import it.lispa.bdl.server.repository.BdlTipoArchivioRepository;
import it.lispa.bdl.server.repository.BdlTipoGraficaRepository;
import it.lispa.bdl.server.repository.BdlTipoIdentificativoRepository;
import it.lispa.bdl.server.repository.BdlTipoOggettoRepository;
import it.lispa.bdl.server.services.CatalogazioneOggettiServiceImpl;
import it.lispa.bdl.shared.dto.TocSommarioDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class ImportazioneExcelUtils.
 */
@Component
public class ImportazioneExcelUtils {

	/** oggetto repository. */
	@Autowired
	private BdlOggettoOriginaleRepository oggettoRepository;
	
	/** tipo oggetto repository. */
	@Autowired
	private BdlTipoOggettoRepository tipoOggettoRepository;
	
	/** ente repository. */
	@Autowired
	private BdlEnteRepository enteRepository;
	
	/** progetto repository. */
	@Autowired
	private BdlProgettoRepository progettoRepository;
	
	/** collezione repository. */
	@Autowired
	private BdlCollezioneRepository collezioneRepository;

	/** livello biblio repository. */
	@Autowired
	private BdlLivelloBiblioRepository livelloBiblioRepository;
	
	/** qualifica autore repository. */
	@Autowired
	private BdlQualificaAutoreRepository qualificaAutoreRepository;
	
	/** lingua repository. */
	@Autowired
	private BdlLinguaRepository linguaRepository;
	
	/** tipo identificativo repository. */
	@Autowired
	private BdlTipoIdentificativoRepository tipoIdentificativoRepository;
	
	/** qualificatore data repository. */
	@Autowired
	private BdlQualificatoreDataRepository qualificatoreDataRepository;
	
	/** contesto arch repository. */
	@Autowired
	private BdlContestoArchRepository contestoArchRepository;
	
	/** soggetto prod repository. */
	@Autowired
	private BdlSoggettoProdRepository soggettoProdRepository;
	
	/** tipo grafica repository. */
	@Autowired
	private BdlTipoGraficaRepository tipoGraficaRepository;
	
	/** supporto repository. */
	@Autowired
	private BdlSupportoRepository supportoRepository;
	
	/** tecnica grafica repository. */
	@Autowired
	private BdlTecnicaGraficaRepository tecnicaGraficaRepository;
	
	/** tipo archivio repository. */
	@Autowired
	private BdlTipoArchivioRepository tipoArchivioRepository;
	
	/** soggetto repository. */
	@Autowired
	private BdlSoggettoRepository soggettoRepository;
	
	/** autore repository. */
	@Autowired
	private BdlAutoreRepository autoreRepository;

	/** editore repository. */
	@Autowired
	private BdlEditoreRepository editoreRepository;
	
	/** immagine repository. */
	@Autowired
	private BdlImmagineRepository immagineRepository;
	
	/** toc sommario utils. */
	@Autowired
	private TocSommarioUtils tocSommarioUtils;

	/** v oggetti. */
	@Autowired
	private VOggettiUtils vOggetti;
	
	/** immagini utils. */
	@Autowired 
	protected CaricamentoImmaginiUtils immaginiUtils;

	/** start col descrizionecontenutistica. */
	private static Integer startColDescrizionecontenutistica = Integer.valueOf(1);
	
	/** start col descrizionefisica. */
	private static Integer startColDescrizionefisica = Integer.valueOf(2);
	
	/** start col livellobibliografico. */
	private static Integer startColLivellobibliografico = Integer.valueOf(3);
	
	/** start col soggetto. */
	private static Integer startColSoggetto = Integer.valueOf(4);
	
	/** start col qualificatoreautore. */
	private static Integer startColQualificatoreautore = Integer.valueOf(5);
	
	/** start col autore. */
	private static Integer startColAutore = Integer.valueOf(6);
	
	/** start col qualificatoreautore due. */
	private static Integer startColQualificatoreautoreDue = Integer.valueOf(7);
	
	/** start col autore due. */
	private static Integer startColAutoreDue = Integer.valueOf(8);
	
	/** start col editore. */
	private static Integer startColEditore = Integer.valueOf(9);
	
	/** start col lingua. */
	private static Integer startColLingua = Integer.valueOf(10);
	
	/** start col tipoidentificativo. */
	private static Integer startColTipoidentificativo = Integer.valueOf(11);
	
	/** start col identificativo. */
	private static Integer startColIdentificativo = Integer.valueOf(12);
	
	/** start col luogopubblicazione. */
	private static Integer startColLuogopubblicazione = Integer.valueOf(13);
	
	/** start col qualificatoredata. */
	private static Integer startColQualificatoredata = Integer.valueOf(14);
	
	/** start col data. */
	private static Integer startColData = Integer.valueOf(15);
	
	/** start col note. */
	private static Integer startColNote = Integer.valueOf(16);
	
	/** start col scala. */
	private static Integer startColScala = Integer.valueOf(17);
	
	/** start col proiezione. */
	private static Integer startColProiezione = Integer.valueOf(18);
	
	/** start col coordinate. */
	private static Integer startColCoordinate = Integer.valueOf(19);
	
	/** start col contestoarchivistico. */
	private static Integer startColContestoarchivistico = Integer.valueOf(20);
	
	/** start col soggettiproduttori. */
	private static Integer startColSoggettiproduttori = Integer.valueOf(21);
	
	/** start col linkcatalogo. */
	private static Integer startColLinkcatalogo = Integer.valueOf(22);
	
	/** start col tipografica. */
	private static Integer startColTipografica = Integer.valueOf(23);
	
	/** start col supportoprimario. */
	private static Integer startColSupportoprimario = Integer.valueOf(24);
	
	/** start col tecnicagrafica. */
	private static Integer startColTecnicagrafica = Integer.valueOf(25);
	
	/** start col tipoarchivio. */
	private static Integer startColTipoarchivio = Integer.valueOf(26);
	
	/** start col titoloFe. */
	private static Integer startColTitoloFe = Integer.valueOf(27);
	
	/** start col titoloFe. */
	private static Integer startColSegnatura = Integer.valueOf(28);

	// popolo le liste per il controllo delle ComboBox CHIUSE...
	/** my list livello biblio. */
	private List<BdlLivelloBiblio> myListLivelloBiblio;
	
	/** my list qualifica autore. */
	private List<BdlQualificaAutore> myListQualificaAutore;
	
	/** my list lingua. */
	private List<BdlLingua> myListLingua;
	
	/** my list tipo identificativo. */
	private List<BdlTipoIdentificativo> myListTipoIdentificativo;
	
	/** my list qualificatore data. */
	private List<BdlQualificatoreData> myListQualificatoreData;
	
	/** my list contesto arch. */
	private List<BdlContestoArch> myListContestoArch;
	
	/** my list soggetto prod. */
	private List<BdlSoggettoProd> myListSoggettoProd;
	
	/** my list tipo grafica. */
	private List<BdlTipoGrafica> myListTipoGrafica;
	
	/** my list supporto. */
	private List<BdlSupporto> myListSupporto;
	
	/** my list tecnica grafica. */
	private List<BdlTecnicaGrafica> myListTecnicaGrafica;
	
	/** my list tipo archivio. */
	private List<BdlTipoArchivio> myListTipoArchivio;

	// popolo le liste per il controllo delle ComboBox APERTE...
	/** my list soggetto. */
	private List<BdlSoggetto> myListSoggetto;
	
	/** my list autore. */
	private List<BdlAutore> myListAutore;
	
	/** my list editore. */
	private List<BdlEditore> myListEditore;
	
	/** server messages. */
	ResourceBundle serverMessages = ResourceBundle.getBundle("it.lispa.bdl.server.messages.ImportazioneExcelUtils", new UTF8Control());

	/**
	 * Istanzia un nuovo importazione excel utils.
	 */
	public ImportazioneExcelUtils() {
		super();
	}
	
	/**
	 * Popola liste.
	 */
	private void popolaListe(){

		// popolo le liste per il controllo delle ComboBox CHIUSE...
		myListLivelloBiblio = (List<BdlLivelloBiblio>) livelloBiblioRepository.findAll();
		myListQualificaAutore = (List<BdlQualificaAutore>) qualificaAutoreRepository.findAll();
		myListLingua = (List<BdlLingua>) linguaRepository.findAll();
		myListTipoIdentificativo = (List<BdlTipoIdentificativo>) tipoIdentificativoRepository.findAll();
		myListQualificatoreData = (List<BdlQualificatoreData>) qualificatoreDataRepository.findAll();
		myListContestoArch = (List<BdlContestoArch>) contestoArchRepository.findAll();
		myListSoggettoProd = (List<BdlSoggettoProd>) soggettoProdRepository.findAll();
		myListTipoGrafica = (List<BdlTipoGrafica>) tipoGraficaRepository.findAll();
		myListSupporto = (List<BdlSupporto>) supportoRepository.findAll();
		myListTecnicaGrafica = (List<BdlTecnicaGrafica>) tecnicaGraficaRepository.findAll();
		myListTipoArchivio = (List<BdlTipoArchivio>) tipoArchivioRepository.findAll();

		// popolo le liste per il controllo delle ComboBox APERTE...
		myListSoggetto = (List<BdlSoggetto>) soggettoRepository.findAll();
		myListAutore = (List<BdlAutore>) autoreRepository.findAll();
		myListEditore = (List<BdlEditore>) editoreRepository.findAll();
	}
	
	/**
	 * Check righe.
	 *
	 * @param contenutoFile  contenuto file
	 * @param cdCatalogatore  codice catalogatore
	 * @return list
	 */
	public List<String> checkRighe(List<Map<Integer,String>>contenutoFile, BigDecimal cdCatalogatore) {
		
		
		List<String> errori = new ArrayList<String>();
		List<String> titoliCache = new ArrayList<String>();
		List<String> titoliSupCache = new ArrayList<String>();
		List<String> tipoOggettoCache = new ArrayList<String>();

		List<VOggettoDTO> oggetti = vOggetti.findOggettiByIstituto(cdCatalogatore,null,null,null,null,null);
		for(VOggettoDTO oggetto: oggetti){
			titoliCache.add(oggetto.getO_DnTitolo());
		}

		List<VOggettoDTO> titoliSup = vOggetti.findTitoliSupByIstituto(cdCatalogatore,null,null,null,null,null);
		for(VOggettoDTO titoloSup : titoliSup){
			titoliSupCache.add(titoloSup.getO_DnTitolo());
		}

		List<BdlTipoOggetto> tipi = (List<BdlTipoOggetto>) tipoOggettoRepository.findAll();
		for(BdlTipoOggetto tipo : tipi){
			tipoOggettoCache.add(tipo.getDnNome().toUpperCase());
		}

		try {
			int numRiga = 1;
			for(Map<Integer,String> riga:contenutoFile){
				numRiga++;
				String titolo = getStringAtIdx(riga,0);
				String titoloSuperiore = getStringAtIdx(riga,1);
				String immaginiPrevisteStr = getStringAtIdx(riga,2);
				String tipoOggetto = getStringAtIdx(riga,3);
				String isTitoloSupStr = getStringAtIdx(riga,4);

				if("".equals(titolo)){
					errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoTitolo"));
				}else if("".equals(tipoOggetto)){
					errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoTipoOggetto"));
				}else if("".equals(isTitoloSupStr)){
					errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoTitoloSup"));
				}else if(!"S".equalsIgnoreCase(isTitoloSupStr) && !"N".equalsIgnoreCase(isTitoloSupStr) ){
					errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoTitoloSupValid")+" ("+isTitoloSupStr+")");
				}else{
					BigDecimal immaginiPreviste = !"".equals(immaginiPrevisteStr) ? new BigDecimal(immaginiPrevisteStr) : null;
					Boolean isTitoloSup = ("S".equalsIgnoreCase(isTitoloSupStr) || "SI".equalsIgnoreCase(isTitoloSupStr)) ? true : false;

					if(!isTitoloSup && "".equals(immaginiPrevisteStr)){
						errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoImmaginiPreviste"));
					}else if(isTitoloSup && !"".equals(immaginiPrevisteStr)){
						errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoImmaginiPrevisteTitSup"));
					}else{
						// controllare che tipoOggetto esista
						if(!"".equals(tipoOggetto) && !tipoOggettoCache.contains(tipoOggetto.toUpperCase())){
							errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoTipoOggettoValid")+" ("+tipoOggetto+")");
						}
						// controllare che immaginiPreviste sia >=1
						if(!"".equals(immaginiPrevisteStr) && immaginiPreviste!=null && immaginiPreviste.compareTo(BigDecimal.ONE)<0){
							errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoImmaginiPrevisteValid")+" ("+immaginiPreviste+")");
						}
						// se isTitoloSup allora titoloSuperiore deve essere nullo
						if(!"".equals(isTitoloSupStr) && isTitoloSup && !titoloSuperiore.equals("")){
							errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoTitoloSupNoTit"));
						}
						// check che non esista già il titolo nella cache
						if(!"".equals(titolo) && titoliCache.contains(titolo)){
							errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoTitoloExists"));
						}
						// se titoloSuperiore e' compilato controllare che sia presente nella cache
						if(!titoloSuperiore.equals("") && !titoliSupCache.contains(titoloSuperiore)){
							errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoTitoloNoExists"));
						}

						titoliCache.add(titolo);
						if(isTitoloSup){
							titoliSupCache.add(titolo);
						}
					}					
				}
			}

		} catch (Exception e) {
			errori.add("[SYS] : "+serverMessages.getString("erroreException")+"" + e.getMessage());
		}

		return errori;
	}

	/**
	 * Inserisci righe.
	 *
	 * @param contenutoFile  contenuto file
	 * @param cdCollezione  codice collezione
	 * @param cdCatalogatore  codice catalogatore
	 * @param operatore  operatore
	 * @return integer
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Integer inserisciRighe(List<Map<Integer,String>>contenutoFile, BigDecimal cdCollezione, BigDecimal cdCatalogatore, BdlUtente operatore) {

		int righeInserite = 0;
		for(Map<Integer,String> riga:contenutoFile){
			String titolo = getStringAtIdx(riga,0);
			String titoloSuperiore = getStringAtIdx(riga,1);
			String immaginiPrevisteStr = getStringAtIdx(riga,2);
			String tipoOggetto = getStringAtIdx(riga,3);
			String isTitoloSupStr = getStringAtIdx(riga,4);
			BigDecimal immaginiPreviste = !"".equals(immaginiPrevisteStr) ? new BigDecimal(immaginiPrevisteStr) : null;
			Boolean isTitoloSup = "S".equalsIgnoreCase(isTitoloSupStr) ? true : false;

			List<BdlTipoOggetto> tipoOggettoJpaLista = tipoOggettoRepository.findByDnNomeCI(tipoOggetto);
			BdlTipoOggetto tipoOggettoJpa = tipoOggettoJpaLista.get(0);

			BigDecimal cdOggettoOriginaleSup = null;
			if(!"".equals(titoloSuperiore)){
				List<BdlOggettoOriginale> titoloSuperioreJpas = oggettoRepository.findByTitoloAndCatalogatore(titoloSuperiore, cdCatalogatore);
				cdOggettoOriginaleSup = titoloSuperioreJpas.get(0).getCdOggettoOriginale();
			}

			BdlOggettoOriginale oggetto = new BdlOggettoOriginale(
					cdCollezione,
					tipoOggettoJpa.getCdTipoOggetto(), 
					cdOggettoOriginaleSup, 
					titolo, 
					isTitoloSup, 
					immaginiPreviste
					);
			oggetto.setDsStato(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INSERITO);
			oggetto.setDsCreatoda(operatore.getCdCodiceFiscale());
			oggetto.setDtCreatoil(new Date());
			oggetto.setFlOggettoDigitale(false);
			oggetto.setFlCorrezione(false);
			oggetto.setFlAnomaliaImmagini(false);
			oggetto.setFlPdfMultipagina(false);
			oggetto.setFlRicercaOcr(false);
			oggettoRepository.save(oggetto);

			righeInserite++;
		}


		return righeInserite;
	}

	/**
	 * Legge string at idx.
	 *
	 * @param riga  riga
	 * @param idx  idx
	 * @return string at idx
	 */
	private String getStringAtIdx(Map<Integer,String> riga,Integer idx){
		String val = "";
		if(riga.get(idx)!=null){
			val = riga.get(idx).trim();
		}
		return val;
	}
	
	private String checkEnteProgettoCollezioneOggettoFromID(Integer numRiga, BigDecimal cdEnteBDec, BigDecimal cdProgettoBDec, BigDecimal cdCollezioneBDec, BigDecimal cdOggettoBDec) {
		String errorToRet = null;
		
		BdlEnte myObjEnte = enteRepository.findByCdEnte(cdEnteBDec);
		if(myObjEnte==null) { 
			//controllo ISTITUTO...
			errorToRet = "[R"+numRiga+"] "+serverMessages.getString("erroreNoEnteValid");
		} else {
			BdlProgetto myObjProgetto = progettoRepository.findByCdProgetto(cdProgettoBDec);
			if(myObjProgetto==null) { 
				//controllo PROGETTO...
				errorToRet = "[R"+numRiga+"] "+serverMessages.getString("erroreNoProgettoValid");
			} else {
				BdlCollezione myObjCollezione = collezioneRepository.findByCdCollezione(cdCollezioneBDec);
				if(myObjCollezione==null) { 
					//controllo COLLEZIONE...
					errorToRet = "[R"+numRiga+"] "+serverMessages.getString("erroreNoCollezioneValid");
				} else {
					BdlOggettoOriginale myObjOggetto = oggettoRepository.findByCdOggettoOriginale(cdOggettoBDec);
					if(myObjOggetto==null) { 
						//controllo OGGETTO...
						errorToRet = "[R"+numRiga+"] "+serverMessages.getString("erroreNoOggettoValid");
					}
				}
			}
		}
		
		return errorToRet;
	}
	
	private String checkEnteProgettoCollezioneOggettoFromVOggetto(Integer numRiga, VOggettoDTO itemCheck, BigDecimal cdEnteBDec, BigDecimal cdProgettoBDec, BigDecimal cdCollezioneBDec, BigDecimal cdOggettoBDec) {
		String errorToRet = null;
		
		if(!itemCheck.getI_CdIstituto().equals(cdEnteBDec)) { 
			//controllo ISTITUTO...
			errorToRet = "[R"+numRiga+"] "+serverMessages.getString("erroreNoEnteValido");
		} else if(!itemCheck.getP_CdProgetto().equals(cdProgettoBDec)) { 
			//controllo PROGETTO...
			errorToRet = "[R"+numRiga+"] "+serverMessages.getString("erroreNoProgettoValido");
		} else if(!itemCheck.getC_CdCollezione().equals(cdCollezioneBDec)) { 
			//controllo COLLEZIONE...
			errorToRet = "[R"+numRiga+"] "+serverMessages.getString("erroreNoCollezioneValido");
		} else if(!itemCheck.getO_CdOggettoOriginale().equals(cdOggettoBDec)) { 
			//controllo OGGETTO...
			errorToRet = "[R"+numRiga+"] "+serverMessages.getString("erroreNoOggettoValido");
		}
		
		return errorToRet;
	}
	
	/**
	 * Check righe file TOC.
	 *
	 * @param contenutoFile  contenuto file
	 * @param cdCatalogatore  codice catalogatore
	 * @return list
	 */
	public List<String> checkRigheTOC(List<Map<Integer,String>>contenutoFile, BigDecimal cdCatalogatore) {

		List<String> errori = new ArrayList<String>();
		Map<String,List<TocSommarioDTO>> cacheTocs = new HashMap<String,List<TocSommarioDTO>>();
		Integer chiaveTocs = Integer.valueOf(1);
		try {
			int numRiga = 1;
			for(Map<Integer,String> riga:contenutoFile){

				numRiga++;
				String idOggetto  = getStringAtIdx(riga,0).trim();
				String TOClv1     = getStringAtIdx(riga,1).trim();
				String TOClv2     = getStringAtIdx(riga,2).trim();
				String TOClv3     = getStringAtIdx(riga,3).trim();
				String TOClv4     = getStringAtIdx(riga,4).trim();
				String nrImmagini = getStringAtIdx(riga,5).trim();

				String patternForId = "\\d*/\\d*/\\d*/\\d*";
				String patternForImageNr = "\\d*";


				if("".equals(idOggetto)){
					errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreIdOggetto"));
				}else if(!idOggetto.matches(patternForId)){
					errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoIdValid"));
				}else if(!"".equals(idOggetto) && !nrImmagini.matches(patternForImageNr)){
					errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoImageNr"));
				} else{
					
					//recupero i codici numerici che compongono gli ID
					String[] idParts = idOggetto.split("/");
					
					String cdEnte 			= idParts[0];
					String cdProgetto 		= idParts[1];
					String cdCollezione 	= idParts[2];
					String cdOggetto 		= idParts[3];
					
					BigDecimal cdEnteBDec 		= new BigDecimal(cdEnte);
					BigDecimal cdProgettoBDec 	= new BigDecimal(cdProgetto);
					BigDecimal cdCollezioneBDec = new BigDecimal(cdCollezione);
					BigDecimal cdOggettoBDec 	= new BigDecimal(cdOggetto);
					
					//controllo Ente-Progetto-Collezione-Oggetto specificati nell'ID
					String myError = checkEnteProgettoCollezioneOggettoFromID(numRiga, cdEnteBDec, cdProgettoBDec, cdCollezioneBDec, cdOggettoBDec);
					if(myError!=null) { 
						errori.add(myError);
					} else {
						//controllo che l'utente possa accedere all'oggetto...
						VOggettoDTO itemCheck = vOggetti.findOggettoByIstituto(cdOggettoBDec, cdEnteBDec, BdlSharedConstants.BDL_PROGETTO_STATO_INCORSO, CatalogazioneOggettiServiceImpl.statiOggettoOriginale, null);
						
						if(itemCheck==null){
							errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoUtenteValido"));
						} else {
							
							//controllo Ente-Progetto-Collezione-Oggetto specificati in itemCheck
							myError = checkEnteProgettoCollezioneOggettoFromVOggetto(numRiga, itemCheck, cdEnteBDec, cdProgettoBDec, cdCollezioneBDec, cdOggettoBDec);
							if(myError!=null) { 
								errori.add(myError);
							} else {
								
								//controllo la natura dell'oggetto... 
								//se AUDIO o MAPPA non devo consentire l'inserimento del TOC!
								if(immaginiUtils.hasNaturaAudio(itemCheck.getT_CdTipoOggetto()) || immaginiUtils.hasNaturaMap(itemCheck.getT_CdTipoOggetto())) {
									errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNaturaOggetto"));
								} else {
									
									boolean rigaOk = true;
									BigDecimal cdImmagine = null;
									if(!"".equals(nrImmagini)){
										String nomeImmagine = String.format("%04d", Integer.parseInt(nrImmagini));
										List<BdlImmagine> immagini = immagineRepository.findByCdOggettoOriginaleAndDnNomeImmagine(itemCheck.getO_CdOggettoOriginale(),nomeImmagine);
										if(immagini.size()==0){
											errori.add("[R"+numRiga+"] L'oggetto specificato con contiene una immagine "+nomeImmagine);
											rigaOk = false;
										}else{
											cdImmagine = immagini.get(0).getCdImmagine();
										}
									}

									if("".equals(TOClv1)){
										errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreTOClv1"));
										rigaOk = false;
									}else if("".equals(TOClv2) && !"".equals(TOClv3)){
										errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreTOClv2ToLv3"));
										rigaOk = false;
									}else if(("".equals(TOClv2) || "".equals(TOClv3)) && !"".equals(TOClv4)){	
										errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreTOCllv4"));
										rigaOk = false;
									}

									if(rigaOk){
										/**
										 * Se e' valorizzato il 4 devo controllare di avere già in canna il 3 il 2 e 1 per oggetto
										 *   se si passo il check
										 * Se e' valorizzato il 3 devo controllare di avere già in canna il 2 e 1 per oggetto
										 *   se si passo il check e aggiungo il 3 alla cache
										 * Se e' valorizzato il 2 devo controllare di avere già in canna il 1 per oggetto
										 *   se si passo il check e aggiungo il 2 alla cache
										 * ALTRIMENTI 
										 * deve essere valorizzato il 1... quindi lo aggiungo alla cache.
										 * 
										 */

										List<TocSommarioDTO> currItemToc = cacheTocs.get(idOggetto);
										if(currItemToc==null){
											currItemToc = new ArrayList<TocSommarioDTO>();
										}
										if(!"".equals(TOClv4)){
											// devo controllare di avere già in canna il 3 il 2 e 1 per oggetto
											TocSommarioDTO toc1 = findTocLv1(currItemToc, TOClv1);
											if(toc1==null){
												errori.add("[R"+numRiga+"] Il toc di livello 1 specificato non esiste");
												rigaOk = false;
											}else{
												TocSommarioDTO toc2 = toc1.findChildByName(TOClv2);
												if(toc2==null){
													errori.add("[R"+numRiga+"] Il toc di livello 2 specificato non esiste");
													rigaOk = false;
												}else{
													TocSommarioDTO toc3 = toc2.findChildByName(TOClv3);
													if(toc3==null){
														errori.add("[R"+numRiga+"] Il toc di livello 3 specificato non esiste");
														rigaOk = false;
													}else{
														TocSommarioDTO toc4 = new TocSommarioDTO(chiaveTocs++,TOClv4,cdImmagine);
														toc3.addChild(toc4);
													}
												}	
											}
										}else if(!"".equals(TOClv3)){
											// devo controllare di avere già in canna il 3 il 2 e 1 per oggetto
											TocSommarioDTO toc1 = findTocLv1(currItemToc, TOClv1);
											if(toc1==null){
												errori.add("[R"+numRiga+"] Il toc di livello 1 specificato non esiste");
												rigaOk = false;
											}else{
												TocSommarioDTO toc2 = toc1.findChildByName(TOClv2);
												if(toc2==null){
													errori.add("[R"+numRiga+"] Il toc di livello 2 specificato non esiste");
													rigaOk = false;
												}else{
													TocSommarioDTO toc3 = new TocSommarioDTO(chiaveTocs++,TOClv3,cdImmagine);
													toc2.addChild(toc3);
												}
											}
										}else if(!"".equals(TOClv2)){
											// devo controllare di avere già in canna il 3 il 2 e 1 per oggetto
											TocSommarioDTO toc1 = findTocLv1(currItemToc, TOClv1);
											if(toc1==null){
												errori.add("[R"+numRiga+"] Il toc di livello 1 specificato non esiste");
												rigaOk = false;
											}else{
												TocSommarioDTO toc2 = new TocSommarioDTO(chiaveTocs++,TOClv2,cdImmagine);
												toc1.addChild(toc2);
											}
										}else{
											TocSommarioDTO toc1 = new TocSommarioDTO(chiaveTocs++,TOClv1,cdImmagine);
											currItemToc.add(toc1);
										}
										if(rigaOk){
											cacheTocs.put(idOggetto,currItemToc);
										}
									}
								}
							}
						}
					}
				}
			}

		} catch (Exception e) {
			errori.add("[SYS] : "+serverMessages.getString("erroreException")+"" + e.getMessage());
		}

		return errori;
	}
	
	/**
	 * Find toc lv1.
	 *
	 * @param tocs  tocs
	 * @param tocName  toc name
	 * @return toc sommario dto
	 */
	private TocSommarioDTO findTocLv1(List<TocSommarioDTO> tocs, String tocName){
		for(TocSommarioDTO toc:tocs){
			if(toc.getNomeToc().equals(tocName)){
				return toc;
			}
		}
		return null;
	}

	/**
	 * createExcelErrorLogRecord.
	 *
	 * @param errors  errors
	 * @return string builder
	 */
	public StringBuilder createExcelErrorLogRecord (List<String> errors) {
		StringBuilder strErrorLog = new StringBuilder();

		for(String error : errors) {
			strErrorLog.append(error);
			strErrorLog.append(BdlServerConstants.LOGIMPORTAZIONEEXCELSEPARATOR);
		}

		return strErrorLog;
	}	
	
	/**
	 * Inserisci righe provenienti da file excel dei TOC.
	 *
	 * @param contenutoFile  contenuto file
	 * @param cdIstituto  codice istituto
	 * @param operatore  operatore
	 * @return integer
	 * @throws AsyncServiceException async service exception
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void inserisciRigheTOC(List<Map<Integer,String>>contenutoFile, BigDecimal cdIstituto, BdlUtente operatore) throws AsyncServiceException {


		Map<BigDecimal,List<TocSommarioDTO>> cacheTocs = new HashMap<BigDecimal,List<TocSommarioDTO>>();
		Integer chiaveTocs = Integer.valueOf(1);
		
		for(Map<Integer,String> riga:contenutoFile){
			
			String idOggetto  = getStringAtIdx(riga,0).trim();
			String TOClv1     = getStringAtIdx(riga,1).trim();
			String TOClv2     = getStringAtIdx(riga,2).trim();
			String TOClv3     = getStringAtIdx(riga,3).trim();
			String TOClv4     = getStringAtIdx(riga,4).trim();
			String nrImmagini = getStringAtIdx(riga,5).trim();
			
			String[] idParts = idOggetto.split("/");

			
			BigDecimal cdOggetto 	= new BigDecimal(idParts[3]);

			BigDecimal cdImmagine = null;
			if(!"".equals(nrImmagini)){
				String nomeImmagine = String.format("%04d", Integer.parseInt(nrImmagini));
				List<BdlImmagine> immagini = immagineRepository.findByCdOggettoOriginaleAndDnNomeImmagine(cdOggetto,nomeImmagine);
				cdImmagine = immagini.get(0).getCdImmagine();
			}
			
			List<TocSommarioDTO> currItemToc = cacheTocs.get(cdOggetto);
			if(currItemToc==null){
				currItemToc = new ArrayList<TocSommarioDTO>();
			}
			if(!"".equals(TOClv4)){
				TocSommarioDTO toc1 = findTocLv1(currItemToc, TOClv1);
				TocSommarioDTO toc2 = toc1.findChildByName(TOClv2);
				TocSommarioDTO toc3 = toc2.findChildByName(TOClv3);
				TocSommarioDTO toc4 = new TocSommarioDTO(chiaveTocs++,TOClv4,cdImmagine);
				toc3.addChild(toc4);
			}else if(!"".equals(TOClv3)){
				TocSommarioDTO toc1 = findTocLv1(currItemToc, TOClv1);
				TocSommarioDTO toc2 = toc1.findChildByName(TOClv2);
				TocSommarioDTO toc3 = new TocSommarioDTO(chiaveTocs++,TOClv3,cdImmagine);
				toc2.addChild(toc3);
			}else if(!"".equals(TOClv2)){
				TocSommarioDTO toc1 = findTocLv1(currItemToc, TOClv1);
				TocSommarioDTO toc2 = new TocSommarioDTO(chiaveTocs++,TOClv2,cdImmagine);
				toc1.addChild(toc2);
			}else{
				TocSommarioDTO toc1 = new TocSommarioDTO(chiaveTocs++,TOClv1,cdImmagine);
				currItemToc.add(toc1);
			}
			cacheTocs.put(cdOggetto,currItemToc);
		}
		for(Map.Entry<BigDecimal, List<TocSommarioDTO>> toc : cacheTocs.entrySet()) {
			BigDecimal cdOggettoOriginale 				= toc.getKey();
			List<TocSommarioDTO> saveTreeSommarioData  	= toc.getValue();
			tocSommarioUtils.salvaToc(cdOggettoOriginale, saveTreeSommarioData, operatore);
		}
	}
	

	/**
	 * Check righe for catalogazione tpl.
	 *
	 * @param contenutoFile  contenuto file
	 * @param cdTipoOggetto  codice tipo oggetto
	 * @param cdIstituto  codice istituto
	 * @return list
	 */
	public List<String> checkRigheForCatalogazioneTpl(List<Map<Integer,String>> contenutoFile, 
			BigDecimal cdTipoOggetto, BigDecimal cdIstituto) {
		this.popolaListe();

		List<String> errori = new ArrayList<String>();
		try {
			int numRiga = 1;
			for(Map<Integer,String> riga : contenutoFile) {
				numRiga++;

				/* verifico che l'ID abbia i riferimenti corretti a Istituto-Progetto-Collezione-Oggetto */

				String idExcel = getStringAtIdx(riga,0);

				String patternForId = "\\d*/\\d*/\\d*/\\d*";
				if(!idExcel.matches(patternForId)) {
					errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoIdValid"));
				} else {

					//recupero i codici numerici che compongono gli ID
					String[] idParts = idExcel.split("/");

					String cdEnte 			= idParts[0];
					String cdProgetto 		= idParts[1];
					String cdCollezione 	= idParts[2];
					String cdOggetto 		= idParts[3];

					BigDecimal cdEnteBDec 		= new BigDecimal(cdEnte);
					BigDecimal cdProgettoBDec 	= new BigDecimal(cdProgetto);
					BigDecimal cdCollezioneBDec = new BigDecimal(cdCollezione);
					BigDecimal cdOggettoBDec 	= new BigDecimal(cdOggetto);

					//controllo Ente-Progetto-Collezione-Oggetto specificati nell'ID
					String myError = checkEnteProgettoCollezioneOggettoFromID(numRiga, cdEnteBDec, cdProgettoBDec, cdCollezioneBDec, cdOggettoBDec);
					if(myError!=null) { 
						errori.add(myError);
					} else {
						//controllo che l'utente possa accedere all'oggetto...
						VOggettoDTO itemCheck = vOggetti.findOggettoByIstituto(cdOggettoBDec, cdEnteBDec, BdlSharedConstants.BDL_PROGETTO_STATO_INCORSO, CatalogazioneOggettiServiceImpl.statiOggettoOriginale, null);
						if(itemCheck==null){
							errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoUtenteValido"));
						} else {

							//controllo che itemCheck sia del tipo indicato
							if(!itemCheck.getT_CdTipoOggetto().equals(cdTipoOggetto)) {
								errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoTipoOggettoValid"));
							} else {

								//controllo Ente-Progetto-Collezione-Oggetto specificati in itemCheck
								myError = checkEnteProgettoCollezioneOggettoFromVOggetto(numRiga, itemCheck, cdEnteBDec, cdProgettoBDec, cdCollezioneBDec, cdOggettoBDec);
								if(myError!=null) { 
									errori.add(myError);
								} else {

									/* verifico che la struttura di ogni riga abbia consistenza su DB */

									String livelloBibliografico = getStringAtIdx(riga,startColLivellobibliografico);
									if(!livelloBibliografico.equals("") && checkLivelloBiblio(livelloBibliografico)==null) {
										errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoLivelloBiblioValido"));
									} 
									String qualificatoreAutore = getStringAtIdx(riga,startColQualificatoreautore);
									if(!qualificatoreAutore.equals("") && checkQualificaAutore(qualificatoreAutore)==null) {
										errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoQualificaAutoreValido"));
									}
									String qualificatoreAutoreDue = getStringAtIdx(riga,startColQualificatoreautoreDue);
									if(!qualificatoreAutoreDue.equals("") && checkQualificaAutore(qualificatoreAutoreDue)==null) {
										errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoQualificaAutoreDueValido"));
									}
									String lingua = getStringAtIdx(riga,startColLingua);
									if(!lingua.equals("") && checkLingua(lingua)==null) {
										errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoLinguaValido"));
									}
									String tipoIdentificativo = getStringAtIdx(riga,startColTipoidentificativo);
									if(!tipoIdentificativo.equals("") && checkTipoIdentificativo(tipoIdentificativo)==null) {
										errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoTipoIdentificativoValido"));
									}
									String qualificatoreData = getStringAtIdx(riga,startColQualificatoredata);
									if(!qualificatoreData.equals("") && checkQualificatoreData(qualificatoreData)==null) {
										errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoQualificatoreDataValido"));
									}
									String contestoArchivistico = getStringAtIdx(riga,startColContestoarchivistico);
									if(!contestoArchivistico.equals("") && checkContestoArch(contestoArchivistico)==null) {
										errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoContestoArchValido"));
									}
									String soggettiProduttori = getStringAtIdx(riga,startColSoggettiproduttori);
									if(!soggettiProduttori.equals("") && checkSoggettoProd(soggettiProduttori)==null) {
										errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoSoggettoProdValido"));
									}
									String tipoGrafica = getStringAtIdx(riga,startColTipografica);
									if(!tipoGrafica.equals("") && checkTipoGrafica(tipoGrafica)==null) {
										errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoTipoGraficaValido"));
									}
									String supportoPrimario = getStringAtIdx(riga,startColSupportoprimario);
									if(!supportoPrimario.equals("") && checkSupporto(supportoPrimario)==null) {
										errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoSupportoValido"));
									}
									String tecnicaGrafica = getStringAtIdx(riga,startColTecnicagrafica);
									if(!tecnicaGrafica.equals("") && checkTecnicaGrafica(tecnicaGrafica)==null) {
										errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoTecnicaGraficaValido"));
									}
									String tipoArchivio = getStringAtIdx(riga,startColTipoarchivio);
									if(!tipoArchivio.equals("") && checkTipoArchivio(tipoArchivio)==null) {
										errori.add("[R"+numRiga+"] "+serverMessages.getString("erroreNoTipoArchivioValido"));
									}
								}
							}
						}
					}
				}
			}

		} catch (Exception e) {
			errori.add("[SYS] : "+serverMessages.getString("erroreException")+"" + e.getMessage());
		}

		return errori;
	}

	/**
	 * Check livello biblio.
	 *
	 * @param myDescr  my descr
	 * @return bdl livello biblio
	 */
	private BdlLivelloBiblio checkLivelloBiblio(String myDescr) {
		for(BdlLivelloBiblio myObj : myListLivelloBiblio) {
			if(myObj.getDsDescrizione().equals(myDescr)) {
				return myObj;
			}
		}
		return null;
	}
	
	/**
	 * Check qualifica autore.
	 *
	 * @param myDescr  my descr
	 * @return bdl qualifica autore
	 */
	private BdlQualificaAutore checkQualificaAutore(String myDescr) {
		for(BdlQualificaAutore myObj : myListQualificaAutore) {
			if(myObj.getDsDescrizione().equals(myDescr)) {
				return myObj;
			}
		}
		return null;
	}
	
	/**
	 * Check lingua.
	 *
	 * @param myDescr  my descr
	 * @return bdl lingua
	 */
	private BdlLingua checkLingua(String myDescr) {
		for(BdlLingua myObj : myListLingua) {
			if(myObj.getDsDescrizione().equals(myDescr)) {
				return myObj;
			}
		}
		return null;
	}
	
	/**
	 * Check tipo identificativo.
	 *
	 * @param myDescr  my descr
	 * @return bdl tipo identificativo
	 */
	private BdlTipoIdentificativo checkTipoIdentificativo(String myDescr) {
		for(BdlTipoIdentificativo myObj : myListTipoIdentificativo) {
			if(myObj.getDsDescrizione().equals(myDescr)) {
				return myObj;
			}
		}
		return null;
	}
	
	/**
	 * Check qualificatore data.
	 *
	 * @param myDescr  my descr
	 * @return bdl qualificatore data
	 */
	private BdlQualificatoreData checkQualificatoreData(String myDescr) {
		for(BdlQualificatoreData myObj : myListQualificatoreData) {
			if(myObj.getDsDescrizione().equals(myDescr)) {
				return myObj;
			}
		}
		return null;
	}
	
	/**
	 * Check contesto arch.
	 *
	 * @param myDescr  my descr
	 * @return bdl contesto arch
	 */
	private BdlContestoArch checkContestoArch(String myDescr) {
		for(BdlContestoArch myObj : myListContestoArch) {
			if(myObj.getDsDescrizione().equals(myDescr)) {
				return myObj;
			}
		}
		return null;
	}
	
	/**
	 * Check soggetto prod.
	 *
	 * @param myDescr  my descr
	 * @return bdl soggetto prod
	 */
	private BdlSoggettoProd checkSoggettoProd(String myDescr) {
		for(BdlSoggettoProd myObj : myListSoggettoProd) {
			if(myObj.getDsDescrizione().equals(myDescr)) {
				return myObj;
			}
		}
		return null;
	}
	
	/**
	 * Check tipo grafica.
	 *
	 * @param myDescr  my descr
	 * @return bdl tipo grafica
	 */
	private BdlTipoGrafica checkTipoGrafica(String myDescr) {
		for(BdlTipoGrafica myObj : myListTipoGrafica) {
			if(myObj.getDsDescrizione().equals(myDescr)) {
				return myObj;
			}
		}
		return null;
	}
	
	/**
	 * Check supporto.
	 *
	 * @param myDescr  my descr
	 * @return bdl supporto
	 */
	private BdlSupporto checkSupporto(String myDescr) {
		for(BdlSupporto myObj : myListSupporto) {
			if(myObj.getDsDescrizione().equals(myDescr)) {
				return myObj;
			}
		}
		return null;
	}
	
	/**
	 * Check tecnica grafica.
	 *
	 * @param myDescr  my descr
	 * @return bdl tecnica grafica
	 */
	private BdlTecnicaGrafica checkTecnicaGrafica(String myDescr) {
		for(BdlTecnicaGrafica myObj : myListTecnicaGrafica) {
			if(myObj.getDsDescrizione().equals(myDescr)) {
				return myObj;
			}
		}
		return null;
	}
	
	/**
	 * Check tipo archivio.
	 *
	 * @param myDescr  my descr
	 * @return bdl tipo archivio
	 */
	private BdlTipoArchivio checkTipoArchivio(String myDescr) {
		for(BdlTipoArchivio myObj : myListTipoArchivio) {
			if(myObj.getDsDescrizione().equals(myDescr)) {
				return myObj;
			}
		}
		return null;
	}
	
	/**
	 * Check soggetto.
	 *
	 * @param myDescr  my descr
	 * @return bdl soggetto
	 */
	private BdlSoggetto checkSoggetto(String myDescr) {
		for(BdlSoggetto myObj : myListSoggetto) {
			if(myObj.getDsDescrizione().equals(myDescr)) {
				return myObj;
			}
		}
		return null;
	}
	
	/**
	 * Check autore.
	 *
	 * @param myDescr  my descr
	 * @return bdl autore
	 */
	private BdlAutore checkAutore(String myDescr) {
		for(BdlAutore myObj : myListAutore) {
			if(myObj.getDsDescrizione().equals(myDescr)) {
				return myObj;
			}
		}
		return null;
	}
	
	/**
	 * Check editore.
	 *
	 * @param myDescr  my descr
	 * @return bdl editore
	 */
	private BdlEditore checkEditore(String myDescr) {
		for(BdlEditore myObj : myListEditore) {
			if(myObj.getDsDescrizione().equals(myDescr)) {
				return myObj;
			}
		}
		return null;
	}
	
	/**
	 * Inserisci righe for catalogazione tpl.
	 *
	 * @param contenutoFile  contenuto file
	 * @param cdTipoOggetto  codice tipo oggetto
	 * @param cdIstituto  codice istituto
	 * @param operatore  operatore
	 * @return integer
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Integer inserisciRigheForCatalogazioneTpl(List<Map<Integer,String>> contenutoFile, 
			BigDecimal cdTipoOggetto, BigDecimal cdIstituto, BdlUtente operatore) {
		this.popolaListe();
		
		int righeInserite = 0;
		for(Map<Integer,String> riga : contenutoFile) {
			
			/* Tratto le ComboBox CHIUSE */
			
			String livelloBibliografico 	= getStringAtIdx(riga,startColLivellobibliografico);
			String qualificatoreAutore 		= getStringAtIdx(riga,startColQualificatoreautore);
			String qualificatoreAutoreDue 	= getStringAtIdx(riga,startColQualificatoreautoreDue);
			String lingua					= getStringAtIdx(riga,startColLingua);
			String tipoIdentificativo 		= getStringAtIdx(riga,startColTipoidentificativo);
			String qualificatoreData	 	= getStringAtIdx(riga,startColQualificatoredata);
			String contestoArchivistico 	= getStringAtIdx(riga,startColContestoarchivistico);
			String soggettiProduttori 		= getStringAtIdx(riga,startColSoggettiproduttori);
			String tipoGrafica 				= getStringAtIdx(riga,startColTipografica);
			String supportoPrimario 		= getStringAtIdx(riga,startColSupportoprimario);
			String tecnicaGrafica 			= getStringAtIdx(riga,startColTecnicagrafica);
			String tipoArchivio 			= getStringAtIdx(riga,startColTipoarchivio);

			String[] idParts = getStringAtIdx(riga,0).split("/");
			String cdOggetto = idParts[3];
					
			BdlOggettoOriginale myOggetto = oggettoRepository.findByCdOggettoOriginale(new BigDecimal(cdOggetto));
			
			BdlLivelloBiblio myLivelloBiblio 			= checkLivelloBiblio(livelloBibliografico);
			BdlQualificaAutore myQualificaAutore 		= checkQualificaAutore(qualificatoreAutore);
			BdlQualificaAutore myQualificaAutoreDue 	= checkQualificaAutore(qualificatoreAutoreDue);
			BdlLingua myLingua 							= checkLingua(lingua);
			BdlTipoIdentificativo myTipoIdentificativo 	= checkTipoIdentificativo(tipoIdentificativo);
			BdlQualificatoreData myQualificatoreData 	= checkQualificatoreData(qualificatoreData);
			BdlContestoArch myContestoArch 				= checkContestoArch(contestoArchivistico);
			BdlSoggettoProd mySoggettoProd 				= checkSoggettoProd(soggettiProduttori);
			BdlTipoGrafica myTipoGrafica 				= checkTipoGrafica(tipoGrafica);
			BdlSupporto mySupporto 						= checkSupporto(supportoPrimario);
			BdlTecnicaGrafica myTecnicaGrafica 			= checkTecnicaGrafica(tecnicaGrafica);
			BdlTipoArchivio myTipoArchivio 				= checkTipoArchivio(tipoArchivio);
			
			if(myLivelloBiblio!=null) {
				myOggetto.setCdLivelloBiblio(myLivelloBiblio.getCdLivelloBiblio());
			}
			if(myQualificaAutore!=null) {
				myOggetto.setCdQualificaAutore(myQualificaAutore.getCdQualificaAutore());
			}
			if(myQualificaAutoreDue!=null) {
				myOggetto.setCdQualificaAutoreSec(myQualificaAutoreDue.getCdQualificaAutore());
			}
			if(myLingua!=null) {
				myOggetto.setCdLingua(myLingua.getCdLingua());
			}
			if(myTipoIdentificativo!=null) {
				myOggetto.setCdTipoIdentificativo(myTipoIdentificativo.getCdTipoIdentificativo());
			}
			if(myQualificatoreData!=null) {
				myOggetto.setCdQualificatoreData(myQualificatoreData.getCdQualificatoreData());
			}
			if(myContestoArch!=null) {
				myOggetto.setCdContestoArch(myContestoArch.getCdContestoArch());
			}
			if(mySoggettoProd!=null) {
				myOggetto.setCdSoggettoProd(mySoggettoProd.getCdSoggettoProd());
			}
			if(myTipoGrafica!=null) {
				myOggetto.setCdTipoGrafica(myTipoGrafica.getCdTipoGrafica());
			}
			if(mySupporto!=null) {
				myOggetto.setCdSupporto(mySupporto.getCdSupporto());
			}
			if(myTecnicaGrafica!=null) {
				myOggetto.setCdTecnicaGrafica(myTecnicaGrafica.getCdTecnicaGrafica());
			}
			if(myTipoArchivio!=null) {
				myOggetto.setCdTipoArchivio(myTipoArchivio.getCdTipoArchivio());
			}

			/* Tratto le ComboBox APERTE */
			
			String soggetto		= getStringAtIdx(riga,startColSoggetto).replaceAll("\\s+", " ");
			String autore		= getStringAtIdx(riga,startColAutore).replaceAll("\\s+", " ");
			String autoreDue	= getStringAtIdx(riga,startColAutoreDue).replaceAll("\\s+", " ");
			String editore		= getStringAtIdx(riga,startColEditore).replaceAll("\\s+", " ");
			
			if(!soggetto.equals("")) {
				BdlSoggetto mySoggetto = checkSoggetto(soggetto);
				if(mySoggetto==null) {
					mySoggetto = new BdlSoggetto();
					mySoggetto.setDsDescrizione(soggetto);
					mySoggetto.setDsCreatoda(operatore.getCdCodiceFiscale());
					mySoggetto.setDtCreatoil(new Date());
					mySoggetto = soggettoRepository.save(mySoggetto);
				}
				myOggetto.setCdSoggetto(mySoggetto.getCdSoggetto());
			}
			
			BdlAutore myAutore = null;
			if(!autore.equals("")) {
				myAutore = checkAutore(autore);
				if(myAutore==null) {
					//nel caso in cui l'AUTORE non sia su DB...
					myAutore = new BdlAutore();
					myAutore.setDsDescrizione(autore);
					myAutore.setDsCreatoda(operatore.getCdCodiceFiscale());
					myAutore.setDtCreatoil(new Date());
					//...lo inserisco
					myAutore = autoreRepository.save(myAutore);
				}
				myOggetto.setCdAutore(myAutore.getCdAutore());
			}
			if(!autoreDue.equals("")) {
				BdlAutore myAutoreDue = checkAutore(autoreDue);
				if(myAutoreDue==null) {
					//nel caso in cui l'AUTOREsec non sia su DB...
					if(autoreDue.equals(myAutore.getDsDescrizione())) {
						//...mi chiedo se e' stato inserito al passo precedente...
						myAutoreDue = myAutore;
					} else {
						myAutoreDue = new BdlAutore();
						myAutoreDue.setDsDescrizione(autoreDue);
						myAutoreDue.setDsCreatoda(operatore.getCdCodiceFiscale());
						myAutoreDue.setDtCreatoil(new Date());
						//...altrimenti lo inserisco
						myAutoreDue = autoreRepository.save(myAutoreDue);
					}
				}
				myOggetto.setCdAutoreSec(myAutoreDue.getCdAutore());
			}
			
			if(!editore.equals("")) {
				BdlEditore myEditore = checkEditore(editore);
				if(myEditore==null) {
					myEditore = new BdlEditore();
					myEditore.setDsDescrizione(editore);
					myEditore.setDsCreatoda(operatore.getCdCodiceFiscale());
					myEditore.setDtCreatoil(new Date());
					myEditore = editoreRepository.save(myEditore);
				}
				myOggetto.setCdEditore(myEditore.getCdEditore());
			}
			
			/* Tratto i campi TextArea e TextField */
			
			String myDescrContenutistica 	= getStringAtIdx(riga,startColDescrizionecontenutistica);
			String myDescrFisica 			= getStringAtIdx(riga,startColDescrizionefisica);
			String myIdentificativo 		= getStringAtIdx(riga,startColIdentificativo);
			String myLuogoPubblicazione 	= getStringAtIdx(riga,startColLuogopubblicazione);
			String myData 					= getStringAtIdx(riga,startColData);
			String myNote 					= getStringAtIdx(riga,startColNote);
			String myScala 					= getStringAtIdx(riga,startColScala);
			String myProiezione 			= getStringAtIdx(riga,startColProiezione);
			String myCoordinate 			= getStringAtIdx(riga,startColCoordinate);
			String myLinkCatalogo 			= getStringAtIdx(riga,startColLinkcatalogo);
			String myTitoloFe 				= getStringAtIdx(riga,startColTitoloFe);
			String segnatura 				= getStringAtIdx(riga,startColSegnatura);

			if(myDescrContenutistica!=null) {
				myOggetto.setDsContenutistica(myDescrContenutistica);
			}
			if(myDescrFisica!=null) {
				myOggetto.setDsFisica(myDescrFisica);
			}
			if(myLuogoPubblicazione!=null) {
				myOggetto.setDnLuogoPubblicazione(myLuogoPubblicazione);
			}
			if(myData!=null) {
				myOggetto.setDsData(myData);
			}
			if(myNote!=null) {
				myOggetto.setDsNote(myNote);
			}
			if(myScala!=null) {
				myOggetto.setDsScala(myScala);
			}
			if(myProiezione!=null) {
				myOggetto.setDsProiezione(myProiezione);
			}
			if(myCoordinate!=null) {
				myOggetto.setDsCoordinate(myCoordinate);
			}
			if(myLinkCatalogo!=null) {
				myOggetto.setDsLinkCatalogo(myLinkCatalogo);
			}
			if(myIdentificativo!=null) {
				myOggetto.setCdIdentificativo(myIdentificativo);
			}
			if(myTitoloFe!=null) {
				myOggetto.setDnTitoloFe(myTitoloFe);;
			}
			if (segnatura!=null) {
				myOggetto.setSegnatura(segnatura);
			}
			
			myOggetto.setDsModificatoda(operatore.getCdCodiceFiscale());
			myOggetto.setDtModificatoil(new Date());
			
			oggettoRepository.save(myOggetto);

			righeInserite++;
		}
		
		return righeInserite;
	}
}
