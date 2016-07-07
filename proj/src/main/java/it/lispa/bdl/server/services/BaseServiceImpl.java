package it.lispa.bdl.server.services;

import it.lispa.bdl.commons.domain.BdlAutore;
import it.lispa.bdl.commons.domain.BdlCollezione;
import it.lispa.bdl.commons.domain.BdlCronologia;
import it.lispa.bdl.commons.domain.BdlEditore;
import it.lispa.bdl.commons.domain.BdlImmagine;
import it.lispa.bdl.commons.domain.BdlLingua;
import it.lispa.bdl.commons.domain.BdlOggettoOriginale;
import it.lispa.bdl.commons.domain.BdlProgetto;
import it.lispa.bdl.commons.domain.BdlUtente;
import it.lispa.bdl.server.mail.BdlEmailer;
import it.lispa.bdl.server.repository.BdlAutoreRepository;
import it.lispa.bdl.server.repository.BdlCollezioneRepository;
import it.lispa.bdl.server.repository.BdlCronologiaRepository;
import it.lispa.bdl.server.repository.BdlEditoreRepository;
import it.lispa.bdl.server.repository.BdlImmagineRepository;
import it.lispa.bdl.server.repository.BdlLinguaRepository;
import it.lispa.bdl.server.repository.BdlOggettoOriginaleRepository;
import it.lispa.bdl.server.repository.BdlProgettoRepository;
import it.lispa.bdl.server.repository.BdlTipoIdentificativoRepository;
import it.lispa.bdl.server.repository.BdlTipoOggettoRepository;
import it.lispa.bdl.server.repository.BdlUtenteRepository;
import it.lispa.bdl.server.utils.BdlServerProperties;
import it.lispa.bdl.server.utils.CaricamentoImmaginiUtils;
import it.lispa.bdl.server.utils.MapperUtils;
import it.lispa.bdl.server.utils.RequestFilterUtils;
import it.lispa.bdl.server.utils.SecurityUtils;
import it.lispa.bdl.server.utils.UTF8Control;
import it.lispa.bdl.server.utils.VOggettiUtils;
import it.lispa.bdl.server.utils.Z3950Utils;
import it.lispa.bdl.shared.dto.CollezioneDTO;
import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.ComboStringDTO;
import it.lispa.bdl.shared.dto.CronologiaDTO;
import it.lispa.bdl.shared.dto.ImmagineDTO;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.dto.ProgettoDTO;
import it.lispa.bdl.shared.dto.SubMenuItemDTO;
import it.lispa.bdl.shared.dto.UnimarcDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Class BaseServiceImpl.
 */
public class BaseServiceImpl {

	/** log. */
	private static Logger log = Logger.getLogger(BaseServiceImpl.class);
	
	/** request. */
	@Autowired
	protected HttpServletRequest request;
    
    /** mapper. */
    @Autowired 
    protected MapperUtils mapper;
	
	/** bdl emailer. */
	@Autowired
	protected BdlEmailer bdlEmailer;
	
	/** properties. */
	@Autowired
	protected BdlServerProperties properties;
	
	/** sec utils. */
	@Autowired
	protected SecurityUtils secUtils;
	
	/** immagini utils. */
	@Autowired 
	protected CaricamentoImmaginiUtils immaginiUtils;
	
	/** v oggetti. */
	@Autowired
	protected VOggettiUtils vOggetti;
	
	/** bdl cronologia repository. */
	@Autowired 
	protected BdlCronologiaRepository bdlCronologiaRepository;
	
	/** bdl utente repository. */
	@Autowired 
	protected BdlUtenteRepository bdlUtenteRepository;
	
	/** bdl oggetto originale repository. */
	@Autowired 
	protected BdlOggettoOriginaleRepository bdlOggettoOriginaleRepository;
	
	/** bdl autore repository. */
	@Autowired
	protected BdlAutoreRepository bdlAutoreRepository;
	
	/** bdl editore repository. */
	@Autowired
	protected BdlEditoreRepository bdlEditoreRepository;
	
	/** bdl lingua repository. */
	@Autowired
	protected BdlLinguaRepository bdlLinguaRepository;
	
	/** bdl progetto repository. */
	@Autowired
	protected BdlProgettoRepository bdlProgettoRepository;
	
	/** bdl collezione repository. */
	@Autowired
	protected BdlCollezioneRepository bdlCollezioneRepository;
	
	/** bdl immagine repository. */
	@Autowired 
	protected BdlImmagineRepository bdlImmagineRepository;
	
	/** bdl tipo identificativo repository. */
	@Autowired
	protected BdlTipoIdentificativoRepository bdlTipoIdentificativoRepository;
	
	/** bdl tipo oggetto repository. */
	@Autowired
	protected BdlTipoOggettoRepository bdlTipoOggettoRepository;
	
	/** server base messages. */
	protected ResourceBundle serverBaseMessages = ResourceBundle.getBundle("it.lispa.bdl.server.messages.BaseServiceMsg", new UTF8Control());

	/**
	 * Legge user is logged in.
	 *
	 * @return user is logged in
	 */
	protected Boolean getUserIsLoggedIn(){
		return (Boolean) request.getSession().getAttribute(RequestFilterUtils.SESSION_USER_IS_LOGGED_IN);
	}
	
	/**
	 * Legge user is validated.
	 *
	 * @return user is validated
	 */
	protected Boolean getUserIsValidated(){
		return (Boolean) request.getSession().getAttribute(RequestFilterUtils.SESSION_USER_IS_VALIDATED);
	}
	
	/**
	 * Legge active codice ente.
	 *
	 * @return active codice ente
	 */
	protected BigDecimal getActiveCdEnte(){
		BigDecimal  cdEnte = (BigDecimal) request.getSession().getAttribute(RequestFilterUtils.SESSION_ACTIVE_ENTE_CD);
		return cdEnte;
	}
	
	/**
	 * Legge active codice utente.
	 *
	 * @return active codice utente
	 */
	protected BigDecimal getActiveCdUtente(){
		BigDecimal  cdUtente = (BigDecimal) request.getSession().getAttribute(RequestFilterUtils.SESSION_ACTIVE_USER_CD);
		return cdUtente;
	}
	
	/**
	 * Legge active utente.
	 *
	 * @return active utente
	 */
	public BdlUtente getActiveUtente(){
		BdlUtente operatore = (BdlUtente) request.getSession().getAttribute(RequestFilterUtils.SESSION_ACTIVE_USER);
		return operatore;
	}
	
	/**
	 * Legge codice enti for active utente.
	 *
	 * @return codice enti for active utente
	 */
	public List<BigDecimal> getCdEntiForActiveUtente(){
		return secUtils.getCdEntiForUtente(getActiveCdUtente());
	}
	
	/**
	 * Legge natura.
	 *
	 * @param cdOggetto  codice oggetto
	 * @return natura
	 */
	public String getNatura(BigDecimal cdOggetto) {
		/* In prima battuta verifico che l'item sia leggibile dall'utente... */
		VOggettoDTO itemCheck = vOggetti.findOggettoByCodice(cdOggetto);
		String strToRet = null;
		if(immaginiUtils.hasNaturaMap(itemCheck.getT_CdTipoOggetto())) {
			strToRet = BdlSharedConstants.BDL_FORMATO_NATURA_MAP;
		} else if(immaginiUtils.hasNaturaAudio(itemCheck.getT_CdTipoOggetto())) {
			strToRet = BdlSharedConstants.BDL_FORMATO_NATURA_AUDIO;
		} else if(immaginiUtils.hasNaturaSingleImage(itemCheck.getT_CdTipoOggetto())) {
			strToRet = BdlSharedConstants.BDL_FORMATO_NATURA_SINGLEIMAGE;
		} else if(immaginiUtils.hasNaturaMultiImage(itemCheck.getT_CdTipoOggetto())) {
			strToRet = BdlSharedConstants.BDL_FORMATO_NATURA_MULTIIMAGE;
		}
		return strToRet;
	}
	
	public List<CronologiaDTO> getCronologia(BigDecimal cdOggetto){
		List<CronologiaDTO> items = new ArrayList<CronologiaDTO>();
		List<BdlCronologia> main = (List<BdlCronologia>) bdlCronologiaRepository.findByCdOggettoOriginaleOrderByDtOperazioneDesc(cdOggetto);
		for(BdlCronologia sub:main){
			BdlUtente usr = bdlUtenteRepository.findByCdCodiceFiscale(sub.getCdCodiceFiscale());
			String nomeCognome = sub.getCdCodiceFiscale();
			if(usr!=null){
				nomeCognome = usr.getCmCognome()+" "+usr.getNmNome();
			}
			CronologiaDTO item = new CronologiaDTO(sub.getCdCronologia(),sub.getDtOperazione(),sub.getDsRuolo(),sub.getCdCodiceFiscale(),nomeCognome,sub.getDsDescrizione(),sub.getCdOggettoOriginale());
			items.add(item);
		}
		return items;
	}
	
	protected ProgettoDTO getProgettoForIdentAndCatalogazioneOggetti(BigDecimal cdProgetto) throws AsyncServiceException {
		BigDecimal  cdIstituto = this.getActiveCdEnte();
		BdlProgetto progetto = bdlProgettoRepository.findByCdProgetto(cdProgetto);
		if(!progetto.getDsStato().equals(BdlSharedConstants.BDL_PROGETTO_STATO_INCORSO) || !progetto.getCdEnte().equals(cdIstituto)){
			throw new AsyncServiceException(serverBaseMessages.getString("accessoNonConsentito"));
		}
		return mapper.mapBdlProgettoToProgettoDTO(progetto);
	}
	
	protected OggettoDTO getOggettoForIdentAndCatalogazioneOggetti(BigDecimal cdOggetto, List<String> statiOggettoOriginale) throws AsyncServiceException {
		/* In prima battuta verifico che l'item sia leggibile dall'utente....*/
		BigDecimal  cdIstituto = this.getActiveCdEnte();
		VOggettoDTO itemCheck = vOggetti.findOggettoByIstituto(cdOggetto, cdIstituto, BdlSharedConstants.BDL_PROGETTO_STATO_INCORSO, statiOggettoOriginale, null);
		if(itemCheck==null){
			throw new AsyncServiceException(serverBaseMessages.getString("accessoNonConsentito"));
		}
		BdlOggettoOriginale oggetto = bdlOggettoOriginaleRepository.findByCdOggettoOriginale(cdOggetto);
		OggettoDTO mapBdlOggettoOriginaleToOggettoDTO = mapper.mapBdlOggettoOriginaleToOggettoDTO(oggetto);
		return mapBdlOggettoOriginaleToOggettoDTO;
	}
	
	protected List<ComboDTO> getTitoliInferiori(BigDecimal cdOggettoSup, List<String> statiOggettoOriginale) throws AsyncServiceException {
		/* In prima battuta verifico che l'item sia leggibile dall'utente....*/
		BigDecimal cdIstituto = this.getActiveCdEnte();
		VOggettoDTO itemCheck = vOggetti.findOggettoByIstituto(cdOggettoSup, cdIstituto, BdlSharedConstants.BDL_PROGETTO_STATO_INCORSO, statiOggettoOriginale, null);
		if(itemCheck==null){
			throw new AsyncServiceException(serverBaseMessages.getString("accessoNonConsentito"));
		}
		List<BdlOggettoOriginale> oggettiInf = bdlOggettoOriginaleRepository.findByCdOggettoOriginaleSupOrderByDnTitoloAsc(cdOggettoSup);
		ComboDTO objApp = null;
		List<ComboDTO> objToRet = new ArrayList<ComboDTO>();
		if(oggettiInf!=null) {
			for(int i=0; i<oggettiInf.size(); i++) {
				objApp = new ComboDTO(oggettiInf.get(i).getCdOggettoOriginale(), oggettiInf.get(i).getDnTitolo());
				objToRet.add(objApp);
			}
		}
		return objToRet;
	}
	
	protected CollezioneDTO getCollezioneForIdentAndCatalogazioneOggetti(BigDecimal cdCollezione) throws AsyncServiceException {
		BdlCollezione collezione = bdlCollezioneRepository.findByCdCollezione(cdCollezione);
		ProgettoDTO progettoCheck = getProgettoForIdentAndCatalogazioneOggetti(collezione.getCdProgetto()); 
		if(progettoCheck==null){
			throw new AsyncServiceException(serverBaseMessages.getString("accessoNonConsentito"));
		}
		return mapper.mapBdlCollezioneToCollezioneDTO(collezione);
	}

	protected ProgettoDTO salvaProgettoForIdentAndCatalogazioneOggetti(ProgettoDTO progetto) throws AsyncServiceException {
		
		BdlUtente operatore = this.getActiveUtente();
		BigDecimal cdIstituto = this.getActiveCdEnte();

		if(progetto.getCdProgetto()!=null){
			ProgettoDTO itemCheck = getProgettoForIdentAndCatalogazioneOggetti(progetto.getCdProgetto());
			if(itemCheck==null){
				throw new AsyncServiceException(serverBaseMessages.getString("accessoNonConsentito"));
			}
		}

		BdlProgetto progetto2save = new BdlProgetto();

		if(progetto.getCdProgetto()==null){
			progetto2save.setCdEnte(cdIstituto);
			progetto2save.setDsStato(BdlSharedConstants.BDL_PROGETTO_STATO_INCORSO);
			progetto2save.setDsCreatoda(operatore.getCdCodiceFiscale());
			progetto2save.setDtCreatoil(new Date());
		}else{
			progetto2save = bdlProgettoRepository.findByCdProgetto(progetto.getCdProgetto());
			progetto2save.setDsModificatoda(operatore.getCdCodiceFiscale());
			progetto2save.setDtModificatoil(new Date());
		}	
		
		progetto2save.setDnTitolo(progetto.getTitolo());
		progetto2save.setDnTitoloBreve(progetto.getTitoloBreve());
		progetto2save.setDsDescrizioneIt(progetto.getDescrizioneIt());
		progetto2save.setDsDescrizioneEn(progetto.getDescrizioneEn());
		progetto2save.setDtInizio(progetto.getInizio());
		progetto2save.setDtConclusione(progetto.getConclusione());

		progetto2save = bdlProgettoRepository.save(progetto2save);
		
		return mapper.mapBdlProgettoToProgettoDTO(progetto2save);
	}
	
	public CollezioneDTO salvaCollezioneForIdentAndCatalogazioneOggetti(CollezioneDTO collezione) throws AsyncServiceException {

		BdlUtente  operatore = this.getActiveUtente();

		if(collezione.getCdCollezione()!=null){
			CollezioneDTO itemCheck = getCollezioneForIdentAndCatalogazioneOggetti(collezione.getCdCollezione());
			if(itemCheck == null){
				throw new AsyncServiceException(serverBaseMessages.getString("accessoNonConsentito"));
			}
		}
		ProgettoDTO itemCheck = getProgettoForIdentAndCatalogazioneOggetti(collezione.getCdProgetto());
		if(itemCheck==null){
			throw new AsyncServiceException(serverBaseMessages.getString("accessoNonConsentito"));
		}

		BdlCollezione collezione2save = new BdlCollezione();

		if(collezione.getCdCollezione()==null){
			collezione2save.setDsCreatoda(operatore.getCdCodiceFiscale());
			collezione2save.setDtCreatoil(new Date());
		}else{
			collezione2save = bdlCollezioneRepository.findByCdCollezione(collezione.getCdCollezione());
			collezione2save.setDsModificatoda(operatore.getCdCodiceFiscale());
			collezione2save.setDtModificatoil(new Date());
		}		
		
		collezione2save.setCdProgetto(collezione.getCdProgetto());
		collezione2save.setDnTitolo(collezione.getTitolo());
		collezione2save.setDsDescrizioneIt(collezione.getDescrizioneIt());
		collezione2save.setDsDescrizioneEn(collezione.getDescrizioneEn());
		collezione2save.setDsDiritti(collezione.getDiritti());
		collezione2save.setDsAmbitoDisciplinare(collezione.getAmbitoDisciplinare());
		collezione2save.setDsCoperturaGeografica(collezione.getCoperturaGeografica());
		collezione2save.setDsPeriodo(collezione.getPeriodo());
		collezione2save.setDsAnnoOggAntico(collezione.getAnnoOggAntico());
		collezione2save.setDsAnnoOggRecente(collezione.getAnnoOggRecente());

		collezione2save = bdlCollezioneRepository.save(collezione2save);
		
		return mapper.mapBdlCollezioneToCollezioneDTO(collezione2save);
	}
	
	protected List<SubMenuItemDTO> getTreeItemsForIdentAndCatalogazioneOggetti(List<String> statiOggettoOriginale) {

		BigDecimal  cdEnte = getActiveCdEnte();

		List<SubMenuItemDTO> items = new ArrayList<SubMenuItemDTO>();

		List<BdlProgetto> progetti = bdlProgettoRepository.findByCdEnteAndDsStatoOrderByDnTitoloAsc(cdEnte, BdlSharedConstants.BDL_PROGETTO_STATO_INCORSO);

		for (BdlProgetto progetto : progetti) {
			SubMenuItemDTO progettoDto = new SubMenuItemDTO(progetto.getCdProgetto(),progetto.getDnTitolo(),SubMenuItemDTO.TIPO_PROGETTO);
			items.add(progettoDto);

			List<BdlCollezione> collezioni = bdlCollezioneRepository.findByCdProgettoOrderByDnTitoloAsc(progetto.getCdProgetto());
			for (BdlCollezione collezione : collezioni) {
				SubMenuItemDTO collezioneDto = new SubMenuItemDTO(collezione.getCdCollezione(),collezione.getDnTitolo(),SubMenuItemDTO.TIPO_COLLEZIONE);
				progettoDto.addChild(collezioneDto);

				List<VOggettoDTO> children = vOggetti.findOggettiByIstituto(cdEnte, BdlSharedConstants.BDL_PROGETTO_STATO_INCORSO, statiOggettoOriginale, null, null, collezione.getCdCollezione());
				String label = null;
				if(children.size()>1){
					label = children.size()+" oggetti";
				}else if(children.size()==1){
					label = children.size()+" oggetto";
				}else if(children.isEmpty()){
					label = "0 oggetti";
				}
				if(label!=null){
					SubMenuItemDTO collezioneOggettiDto = new SubMenuItemDTO(collezioneDto.getCodice(),children.size()+" oggetti",SubMenuItemDTO.TIPO_COLLOGGETTI);
					collezioneDto.addChild(collezioneOggettiDto);
				}
			}
		}
		return items;
	}

	protected OggettoDTO salvaOggettoForIOAndAmmGestIndex(ResourceBundle serverMessages, OggettoDTO oggetto, 
			BdlOggettoOriginale oggetto2save, BdlUtente operatore) throws AsyncServiceException {
		if(oggetto.getCdOggettoOriginale()==null){

			BdlOggettoOriginale duplicatoCheck = bdlOggettoOriginaleRepository.findByDnTitoloIgnoreCase(oggetto.getTitolo());
			if(duplicatoCheck!=null){
				throw new AsyncServiceException(serverMessages.getString("eccezioneTitoloDuplicato"));
			}

			oggetto2save.setDsStato(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INSERITO);
			oggetto2save.setDsCreatoda(operatore.getCdCodiceFiscale());
			oggetto2save.setDtCreatoil(new Date());
			oggetto2save.setFlOggettoDigitale(false);
			oggetto2save.setFlCorrezione(false);
			oggetto2save.setFlAnomaliaImmagini(false);
			oggetto2save.setFlPdfMultipagina(false);
			oggetto2save.setFlRicercaOcr(false);
			/* Inizialmente Titolo=TitoloFE */
			oggetto2save.setDnTitoloFe(oggetto.getTitoloFe());
		}else{

			BdlOggettoOriginale duplicatoCheck = bdlOggettoOriginaleRepository.findByDnTitolo(oggetto.getTitolo());
			if(duplicatoCheck!=null && !duplicatoCheck.getCdOggettoOriginale().equals(oggetto.getCdOggettoOriginale())){
				throw new AsyncServiceException(serverMessages.getString("eccezioneTitoloDuplicato"));
			}

			oggetto2save = bdlOggettoOriginaleRepository.findByCdOggettoOriginale(oggetto.getCdOggettoOriginale());
			oggetto2save.setDsModificatoda(operatore.getCdCodiceFiscale());
			oggetto2save.setDtModificatoil(new Date());
		}
		oggetto2save.setCdTipoOggetto(oggetto.getCdTipoOggetto());
		oggetto2save.setCdOggettoOriginaleSup(oggetto.getCdOggettoOriginaleSup());
		/////////////////
		oggetto2save.setDnTitolo(oggetto.getTitolo());
		oggetto2save.setFlOggettoSuperiore(oggetto.getOggettoSuperiore());
		oggetto2save.setNrImmaginiPreviste(oggetto.getImmaginiPreviste());

		if(oggetto.getCdAutore()==null  && oggetto.getDescrizioneAutore()!=null){
			if(!"".equals(oggetto.getDescrizioneAutore())){
				List<BdlAutore> autori = bdlAutoreRepository.findByDsDescrizione(oggetto.getDescrizioneAutore());
				BdlAutore autore;
				if(!autori.isEmpty()){
					autore = autori.get(0);
				}else{
					BdlAutore autore2save = new BdlAutore(oggetto.getDescrizioneAutore(),operatore.getCdCodiceFiscale(),new Date());
					autore = bdlAutoreRepository.save(autore2save);
				}
				oggetto2save.setCdAutore(autore.getCdAutore());				
			}
		}

		if(oggetto.getCdAutoreSec()==null  && oggetto.getDescrizioneAutoreSec()!=null){
			if(!"".equals(oggetto.getDescrizioneAutoreSec())){
				List<BdlAutore> autori = bdlAutoreRepository.findByDsDescrizione(oggetto.getDescrizioneAutoreSec());
				BdlAutore autore;
				if(!autori.isEmpty()){
					autore = autori.get(0);
				}else{
					BdlAutore autore2save = new BdlAutore(oggetto.getDescrizioneAutoreSec(),operatore.getCdCodiceFiscale(),new Date());
					autore = bdlAutoreRepository.save(autore2save);
				}
				oggetto2save.setCdAutoreSec(autore.getCdAutore());				
			}
		}

		if(oggetto.getCdEditore()==null  && oggetto.getDescrizioneEditore()!=null){
			if(!"".equals(oggetto.getDescrizioneEditore())){
				List<BdlEditore> editori = bdlEditoreRepository.findByDsDescrizione(oggetto.getDescrizioneEditore());
				BdlEditore editore;
				if(!editori.isEmpty()){
					editore = editori.get(0);
				}else{
					BdlEditore editore2save = new BdlEditore(oggetto.getDescrizioneEditore(),operatore.getCdCodiceFiscale(),new Date());
					editore = bdlEditoreRepository.save(editore2save);
				}
				oggetto2save.setCdEditore(editore.getCdEditore());				
			}
		}

		oggetto2save.setDsFisica(oggetto.getFisica());
		oggetto2save.setCdTipoIdentificativo(oggetto.getCdTipoIdentificativo());
		oggetto2save.setCdIdentificativo(oggetto.getIdentificativo());
		oggetto2save.setDnLuogoPubblicazione(oggetto.getLuogoPubblicazione());
		oggetto2save.setDsData(oggetto.getData());
		
		if(oggetto.getDescrizioneLingua()!=null && !"".equals(oggetto.getDescrizioneLingua())){
			List<BdlLingua> lingue = bdlLinguaRepository.findByDsCodlang(oggetto.getDescrizioneLingua());
			if(!lingue.isEmpty()){
				BdlLingua lingua = lingue.get(0);
				oggetto2save.setCdLingua(lingua.getCdLingua());
			}
		}
		
		oggetto2save = bdlOggettoOriginaleRepository.save(oggetto2save);
		
		return mapper.mapBdlOggettoOriginaleToOggettoDTO(oggetto2save);
	}
	
	protected OggettoDTO getOggettoForValidazioneAndPubblicazioneOggetti(BigDecimal cdOggetto) {
		BdlOggettoOriginale oggetto = bdlOggettoOriginaleRepository.findByCdOggettoOriginale(cdOggetto);
		return mapper.mapBdlOggettoOriginaleToOggettoDTO(oggetto);
	}
	
	protected List<ImmagineDTO> getImmaginiOggettoForValidazioneAndPubblicazioneOggetti(BigDecimal cdOggetto) throws AsyncServiceException{
		
		OggettoDTO itemCheck = getOggettoForValidazioneAndPubblicazioneOggetti(cdOggetto);
		if(itemCheck==null){
			throw new AsyncServiceException(serverBaseMessages.getString("accessoNonConsentito"));
		}
		
		List<BdlImmagine> immagini = bdlImmagineRepository.findByCdOggettoOriginaleOrderByDnNomeImmagineAsc(cdOggetto);
		
		return mapper.mapListBdlImmagineToListImmagineDTO(immagini, request.getContextPath(),BdlSharedConstants.BDL_SERVICE_PATH_PRIVATE,itemCheck.getCdOggettoOriginale());
	}
	
	protected ImmagineDTO getImmagineReaderForValidazioneAndPubblicazioneOggetti(BigDecimal cdOggetto, String nomeImmagine) throws AsyncServiceException{

		OggettoDTO itemCheck = getOggettoForValidazioneAndPubblicazioneOggetti(cdOggetto);
		if(itemCheck==null){
			throw new AsyncServiceException(serverBaseMessages.getString("accessoNonConsentito"));
		}

		String basePathThumb = request.getContextPath()+"/"+BdlSharedConstants.BDL_SERVICE_PATH_PRIVATE+"/rest/srv/item/"+itemCheck.getCdOggettoOriginale()+"/images/thumb/";
		String basePathReader = request.getContextPath()+"/"+BdlSharedConstants.BDL_SERVICE_PATH_PRIVATE+"/rest/srv/item/"+itemCheck.getCdOggettoOriginale()+"/images/reader/";
		String basePathZoom = request.getContextPath()+"/"+BdlSharedConstants.BDL_SERVICE_PATH_PRIVATE+"/rest/srv/item/"+itemCheck.getCdOggettoOriginale()+"/images/zoom/";
		
		List<BdlImmagine> immagini = bdlImmagineRepository.findByCdOggettoOriginaleAndDnNomeImmagine(cdOggetto, nomeImmagine);

		String progressivoImmagine = immagini.get(0).getDnNomeImmagine().replaceFirst("[0]{0,3}", "");
		String pathThumb = basePathThumb+progressivoImmagine;
		String pathReader = basePathReader+progressivoImmagine;
		String pathZoom = basePathZoom+progressivoImmagine;
		
		return mapper.mapBdlImmagineToImmagineDTO(immagini.get(0), basePathThumb, basePathReader, basePathZoom, pathThumb, pathReader, pathZoom);
	}
	
	protected List<UnimarcDTO> opacSbnSearchForImportaCatalogo(String sbn, String autore, String titolo, 
			String editore, String annoPubblicazione) throws Exception {

		log.debug("[opacSbnSearch] Lancio la ricerca:");
		log.debug("[opacSbnSearch]     sbn: "+sbn);
		log.debug("[opacSbnSearch]     autore: "+autore);
		log.debug("[opacSbnSearch]     titolo: "+titolo);
		log.debug("[opacSbnSearch]     editore: "+editore);
		log.debug("[opacSbnSearch]     annoPubblicazione: "+annoPubblicazione);
		
		String zurl = "opac.sbn.it"; 
		Integer port = Integer.valueOf(3950);
		String dbname = "nopac";
		String syntax = "UNIMARC";
		
		if(properties.getOpacSbnAddr()!=null) {
			zurl = properties.getOpacSbnAddr();
		}
		
		if(properties.getOpacSbnPort()!=null) {
			port = properties.getOpacSbnPort();
		}
		
		if(properties.getOpacSbnDbname()!=null) {
			dbname = properties.getOpacSbnDbname();
		}
		
		if(properties.getOpacSbnSyntax()!=null) {
			syntax = properties.getOpacSbnSyntax();
		}
		
		List<UnimarcDTO> res = Z3950Utils.search(zurl, port, dbname, syntax, sbn, autore, titolo, editore, annoPubblicazione);
		if(res.isEmpty()){
			log.debug("Non trovando risultati lancio un errore");
			throw new AsyncServiceException("Nessun risultato trovato");
		}
		log.debug("[opacSbnSearch] Ritorno "+res.size()+" risultati");
		
		return res;
	}
	
	protected List<ComboStringDTO> getTipologiaOggettiConCatalogoForImportaCatalogo() {
		List<ComboStringDTO> items = new ArrayList<ComboStringDTO>();
		List<String> tipi = bdlTipoOggettoRepository.findByDistinctCatalogo();
		for (String tipo : tipi) {
			items.add(new ComboStringDTO(tipo,tipo));
		}
		return items;
	}
}
