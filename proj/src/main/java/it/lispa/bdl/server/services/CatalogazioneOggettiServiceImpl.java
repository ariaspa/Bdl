package it.lispa.bdl.server.services;

import gwtupload.server.UploadServlet;
import it.lispa.bdl.commons.domain.BdlAutore;
import it.lispa.bdl.commons.domain.BdlContestoArch;
import it.lispa.bdl.commons.domain.BdlEditore;
import it.lispa.bdl.commons.domain.BdlImmagine;
import it.lispa.bdl.commons.domain.BdlLingua;
import it.lispa.bdl.commons.domain.BdlLivelloBiblio;
import it.lispa.bdl.commons.domain.BdlOggettoOriginale;
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
import it.lispa.bdl.server.mail.BdlEmailer;
import it.lispa.bdl.server.repository.BdlContestoArchRepository;
import it.lispa.bdl.server.repository.BdlImmagineRepository;
import it.lispa.bdl.server.repository.BdlLivelloBiblioRepository;
import it.lispa.bdl.server.repository.BdlQualificaAutoreRepository;
import it.lispa.bdl.server.repository.BdlQualificatoreDataRepository;
import it.lispa.bdl.server.repository.BdlSoggettoProdRepository;
import it.lispa.bdl.server.repository.BdlSoggettoRepository;
import it.lispa.bdl.server.repository.BdlSupportoRepository;
import it.lispa.bdl.server.repository.BdlTecnicaGraficaRepository;
import it.lispa.bdl.server.repository.BdlTipoArchivioRepository;
import it.lispa.bdl.server.repository.BdlTipoGraficaRepository;
import it.lispa.bdl.server.utils.BdlServerConstants;
import it.lispa.bdl.server.utils.ExcelParser;
import it.lispa.bdl.server.utils.ImportazioneExcelUtils;
import it.lispa.bdl.server.utils.TocSommarioUtils;
import it.lispa.bdl.server.utils.UTF8Control;
import it.lispa.bdl.shared.dto.CollezioneDTO;
import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.ComboStringDTO;
import it.lispa.bdl.shared.dto.ImmagineDTO;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.dto.ProgettoDTO;
import it.lispa.bdl.shared.dto.SubMenuItemDTO;
import it.lispa.bdl.shared.dto.TocSommarioDTO;
import it.lispa.bdl.shared.dto.UnimarcDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.services.CatalogazioneOggettiService;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class CatalogazioneOggettiServiceImpl.
 */
@Service("catalogazioneOggettiService")
@Repository
@Transactional
public class CatalogazioneOggettiServiceImpl extends BaseServiceImpl implements CatalogazioneOggettiService {
	
	/** log. */
	private static Logger log = Logger.getLogger(CatalogazioneOggettiServiceImpl.class);
	
	/** bdl contesto arch repository. */
	@Autowired 
	private BdlContestoArchRepository bdlContestoArchRepository;
	
	/** bdl immagine repository. */
	@Autowired 
	private BdlImmagineRepository bdlImmagineRepository;
	
	/** bdl qualifica autore repository. */
	@Autowired 
	private BdlQualificaAutoreRepository bdlQualificaAutoreRepository;
	
	/** bdl qualificatore data repository. */
	@Autowired
	private BdlQualificatoreDataRepository bdlQualificatoreDataRepository;
	
	/** bdl soggetto prod repository. */
	@Autowired 
	private BdlSoggettoProdRepository bdlSoggettoProdRepository;
	
	/** bdl supporto repository. */
	@Autowired 
	private BdlSupportoRepository bdlSupportoRepository;
	
	/** bdl livello biblio repository. */
	@Autowired 
	private BdlLivelloBiblioRepository bdlLivelloBiblioRepository;
	
	/** bdl soggetto repository. */
	@Autowired 
	private BdlSoggettoRepository bdlSoggettoRepository;
	
	/** bdl tecnica grafica repository. */
	@Autowired 
	private BdlTecnicaGraficaRepository bdlTecnicaGraficaRepository;
	
	/** bdl tipo archivio repository. */
	@Autowired 
	private BdlTipoArchivioRepository bdlTipoArchivioRepository;
	
	/** bdl tipo grafica repository. */
	@Autowired 
	private BdlTipoGraficaRepository bdlTipoGraficaRepository;
	
	/** toc sommario utils. */
	@Autowired
	private TocSommarioUtils tocSommarioUtils;
	
	/** importazione excel utils. */
	@Autowired
	private ImportazioneExcelUtils importazioneExcelUtils;
	
//	/** entity manager. */
//	@PersistenceContext
//	private EntityManager entityManager;

	/** server messages. */
	ResourceBundle serverMessages = ResourceBundle.getBundle("it.lispa.bdl.server.messages.CatalogazioneOggettiMsg", new UTF8Control());
	

	/** stati oggetto originale. */
	public static final List<String> statiOggettoOriginale = new ArrayList<String>() {
		private static final long serialVersionUID = -7626183828843663412L;
		{
			add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INCATALOGAZIONE);
			add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_CATALOGATO);
			add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_NONVALIDATO);
			add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_NONPUBBLICATO);
		}
	};

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#getProgetto(java.math.BigDecimal)
	 */
	@Override
	public ProgettoDTO getProgetto(BigDecimal cdProgetto) throws AsyncServiceException {
		return getProgettoForIdentAndCatalogazioneOggetti(cdProgetto);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#getCollezione(java.math.BigDecimal)
	 */
	@Override
	public CollezioneDTO getCollezione(BigDecimal cdCollezione) throws AsyncServiceException {
		return getCollezioneForIdentAndCatalogazioneOggetti(cdCollezione);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#getOggetto(java.math.BigDecimal)
	 */
	@Override
	public OggettoDTO getOggetto(BigDecimal cdOggetto) throws AsyncServiceException {
		return getOggettoForIdentAndCatalogazioneOggetti(cdOggetto, statiOggettoOriginale);
	}
	
	@Override
	public List<ComboDTO> getTitoliInf(BigDecimal cdOggetto) throws AsyncServiceException {
		return getTitoliInferiori(cdOggetto, statiOggettoOriginale);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#getOggetto(java.math.BigDecimal)
	 */
	@Override
	public String getOggettoId(BigDecimal cdOggetto) throws AsyncServiceException {
		VOggettoDTO itemCheck = vOggetti.findOggettoByCodice(cdOggetto);
		return itemCheck.getO_DigitalizzatoreId();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#salvaProgetto(it.lispa.bdl.shared.dto.ProgettoDTO)
	 */
	@Transactional
	@Override
	public ProgettoDTO salvaProgetto(ProgettoDTO progetto) throws AsyncServiceException{
		return salvaProgettoForIdentAndCatalogazioneOggetti(progetto);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#salvaCollezione(it.lispa.bdl.shared.dto.CollezioneDTO)
	 */
	@Transactional
	@Override
	public CollezioneDTO salvaCollezione(CollezioneDTO collezione) throws AsyncServiceException{
		return salvaCollezioneForIdentAndCatalogazioneOggetti(collezione);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#salvaOggetto(it.lispa.bdl.shared.dto.OggettoDTO)
	 */
	@Transactional
	@Override
	public OggettoDTO salvaOggetto(OggettoDTO myOggetto) throws AsyncServiceException{
		BdlUtente operatore = this.getActiveUtente();

		CollezioneDTO itemCheck = getCollezione(myOggetto.getCdCollezione());
		if(itemCheck == null){
			throw new AsyncServiceException(serverBaseMessages.getString("accessoNonConsentito"));
		}
		
		BdlOggettoOriginale oggetto2save = bdlOggettoOriginaleRepository.findByCdOggettoOriginale(myOggetto.getCdOggettoOriginale());
		oggetto2save.setDsModificatoda(operatore.getCdCodiceFiscale());
		oggetto2save.setDtModificatoil(new Date());
		
		oggetto2save.setSegnatura(myOggetto.getSegnatura());

		oggetto2save.setDsStato(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_CATALOGATO);

		if(myOggetto.getCdAutore()==null  && myOggetto.getDescrizioneAutore()!=null){
			if(!"".equals(myOggetto.getDescrizioneAutore())){
				List<BdlAutore> autori = bdlAutoreRepository.findByDsDescrizione(myOggetto.getDescrizioneAutore());
				BdlAutore autore;
				if(!autori.isEmpty()){
					autore = autori.get(0);
				}else{
					BdlAutore autore2save = new BdlAutore(myOggetto.getDescrizioneAutore(),operatore.getCdCodiceFiscale(),new Date());
					autore = bdlAutoreRepository.save(autore2save);
				}
				oggetto2save.setCdAutore(autore.getCdAutore());				
			}
		}else{
			oggetto2save.setCdAutore(myOggetto.getCdAutore());	
		}

		if(myOggetto.getCdAutoreSec()==null  && myOggetto.getDescrizioneAutoreSec()!=null){
			if(!"".equals(myOggetto.getDescrizioneAutoreSec())){
				List<BdlAutore> autori = bdlAutoreRepository.findByDsDescrizione(myOggetto.getDescrizioneAutoreSec());
				BdlAutore autore;
				if(!autori.isEmpty()){
					autore = autori.get(0);
				}else{
					BdlAutore autore2save = new BdlAutore(myOggetto.getDescrizioneAutoreSec(),operatore.getCdCodiceFiscale(),new Date());
					autore = bdlAutoreRepository.save(autore2save);
				}
				oggetto2save.setCdAutoreSec(autore.getCdAutore());				
			}
		}else{
			oggetto2save.setCdAutoreSec(myOggetto.getCdAutoreSec());	
		}

		if(myOggetto.getCdEditore()==null  && myOggetto.getDescrizioneEditore()!=null){
			if(!"".equals(myOggetto.getDescrizioneEditore())){
				List<BdlEditore> editori = bdlEditoreRepository.findByDsDescrizione(myOggetto.getDescrizioneEditore());
				BdlEditore editore;
				if(!editori.isEmpty()){
					editore = editori.get(0);
				}else{
					BdlEditore editore2save = new BdlEditore(myOggetto.getDescrizioneEditore(),operatore.getCdCodiceFiscale(),new Date());
					editore = bdlEditoreRepository.save(editore2save);
				}
				oggetto2save.setCdEditore(editore.getCdEditore());				
			}
		}else{
			oggetto2save.setCdEditore(myOggetto.getCdEditore());	
		}
		
		oggetto2save.setDsContenutistica(myOggetto.getContenutistica());
		oggetto2save.setDsFisica(myOggetto.getFisica());
		oggetto2save.setCdLivelloBiblio(myOggetto.getCdLivelloBiblio());
		
		if(myOggetto.getCdSoggetto()==null  && myOggetto.getDescrizioneSoggetto()!=null){
			if(!"".equals(myOggetto.getDescrizioneSoggetto())){
				List<BdlSoggetto> soggetti = bdlSoggettoRepository.findByDsDescrizione(myOggetto.getDescrizioneSoggetto());
				BdlSoggetto soggetto;
				if(!soggetti.isEmpty()){
					soggetto = soggetti.get(0);
				}else{
					BdlSoggetto soggetto2save = new BdlSoggetto(myOggetto.getDescrizioneSoggetto(),operatore.getCdCodiceFiscale(),new Date());
					soggetto = bdlSoggettoRepository.save(soggetto2save);
				}
				oggetto2save.setCdSoggetto(soggetto.getCdSoggetto());				
			}
		}else{
			oggetto2save.setCdSoggetto(myOggetto.getCdSoggetto());
		}
		
		oggetto2save.setCdQualificaAutore(myOggetto.getCdQualificaAutore());
		oggetto2save.setCdQualificaAutoreSec(myOggetto.getCdQualificaAutoreSec());
		oggetto2save.setCdLingua(myOggetto.getCdLingua());
		oggetto2save.setCdTipoIdentificativo(myOggetto.getCdTipoIdentificativo());
		oggetto2save.setCdIdentificativo(myOggetto.getIdentificativo());
		//oggetto2save.setCdIdentificativoIsbn(myOggetto.getIdentificativoIsbn());
		//oggetto2save.setCdIdentificativoIssn(myOggetto.getIdentificativoIssn());
		oggetto2save.setDnLuogoPubblicazione(myOggetto.getLuogoPubblicazione());
		oggetto2save.setCdQualificatoreData(myOggetto.getCdQualificatoreData());
		oggetto2save.setDsData(myOggetto.getData());
		oggetto2save.setDsNote(myOggetto.getNote());
		oggetto2save.setCdSupporto(myOggetto.getCdSupporto());
		oggetto2save.setCdTecnicaGrafica(myOggetto.getCdTecnicaGrafica());
		oggetto2save.setCdTipoGrafica(myOggetto.getCdTipoGrafica());
		oggetto2save.setDsScala(myOggetto.getScala());
		oggetto2save.setDsProiezione(myOggetto.getProiezione());
		oggetto2save.setDsCoordinate(myOggetto.getCoordinate());
		oggetto2save.setCdSoggettoProd(myOggetto.getCdSoggettoProd());
		oggetto2save.setCdContestoArch(myOggetto.getCdContestoArch());
		oggetto2save.setCdTipoArchivio(myOggetto.getCdTipoArchivio());
		oggetto2save.setDsLinkCatalogo(myOggetto.getLinkCatalogo());
		oggetto2save.setDsDiritti(myOggetto.getDiritti());
		oggetto2save.setDsAltreinfo(myOggetto.getAltreinfo());
		/* TitoloFE */
		oggetto2save.setDnTitoloFe(myOggetto.getTitoloFe());

		oggetto2save = bdlOggettoOriginaleRepository.save(oggetto2save);
		return mapper.mapBdlOggettoOriginaleToOggettoDTO(oggetto2save);
	}

	@Transactional
	@Override
	public OggettoDTO salvaOggettoForImportaCatalogo(OggettoDTO oggettoDTO) throws AsyncServiceException {
		log.debug("[salvaOggettoForImportaCatalogo] Entro nel metodo...");

		//recupero l'utente attivo
		BdlUtente operatore = this.getActiveUtente();

		//verifico che l'utente abbia accesso alla collezione
		CollezioneDTO itemCheck = getCollezione(oggettoDTO.getCdCollezione());
		if(itemCheck==null){
			throw new AsyncServiceException(serverBaseMessages.getString("accessoNonConsentito"));
		}
		BdlOggettoOriginale oggetto2save = new BdlOggettoOriginale();
		oggetto2save.setCdCollezione(oggettoDTO.getCdCollezione());
		
		if(oggettoDTO.getCdOggettoOriginale()!=null) {
			//controllo che non esista un titolo duplicato a sistema
			BdlOggettoOriginale duplicatoCheck = bdlOggettoOriginaleRepository.findByDnTitolo(oggettoDTO.getTitolo());
			if(duplicatoCheck!=null && !duplicatoCheck.getCdOggettoOriginale().equals(oggettoDTO.getCdOggettoOriginale())){
				throw new AsyncServiceException(serverMessages.getString("eccezioneTitoloDuplicato"));
			}
			
			//recupero l'obj BdlOggettoOriginale
			oggetto2save = bdlOggettoOriginaleRepository.findByCdOggettoOriginale(oggettoDTO.getCdOggettoOriginale());
			oggetto2save.setDsModificatoda(operatore.getCdCodiceFiscale());
			oggetto2save.setDtModificatoil(new Date());
		} else {
			throw new AsyncServiceException(serverMessages.getString("eccezioneSBNImportKO"));
		}
		
		if(oggettoDTO.getTitolo()!=null) {
			oggetto2save.setDnTitolo(oggettoDTO.getTitolo());
		}
//		if(oggettoDTO.getFisica()!=null) {
//			oggetto2save.setDsFisica(oggettoDTO.getFisica());
//		}
		if(oggettoDTO.getCdAutore()!=null) {
			oggetto2save.setCdAutore(oggettoDTO.getCdAutore());
		}
		if(oggettoDTO.getCdAutoreSec()!=null) {
			oggetto2save.setCdAutoreSec(oggettoDTO.getCdAutoreSec());
		}
		if(oggettoDTO.getCdEditore()!=null) {
			oggetto2save.setCdEditore(oggettoDTO.getCdEditore());
		}
		if(oggettoDTO.getCdLingua()!=null) {
			oggetto2save.setCdLingua(oggettoDTO.getCdLingua());
		}
		if(oggettoDTO.getCdTipoIdentificativo()!=null) {
			oggetto2save.setCdTipoIdentificativo(oggettoDTO.getCdTipoIdentificativo());
		}
		if(oggettoDTO.getIdentificativo()!=null) {
			oggetto2save.setCdIdentificativo(oggettoDTO.getIdentificativo());
		}
		if(oggettoDTO.getLuogoPubblicazione()!=null) {
			oggetto2save.setDnLuogoPubblicazione(oggettoDTO.getLuogoPubblicazione());
		}
		if(oggettoDTO.getData()!=null) {
			oggetto2save.setDsData(oggettoDTO.getData());
		}

		//qualora abbia valorizzato la "descrizioneAutore" e non 
		//"cdAutore", provo a recuperare l'autore in questo modo...
		if(oggettoDTO.getCdAutore()==null  && oggettoDTO.getDescrizioneAutore()!=null){
			if(!"".equals(oggettoDTO.getDescrizioneAutore())){
				List<BdlAutore> autori = bdlAutoreRepository.findByDsDescrizione(oggettoDTO.getDescrizioneAutore());
				BdlAutore autore;
				if(!autori.isEmpty()){
					autore = autori.get(0);
				}else{
					BdlAutore autore2save = new BdlAutore(oggettoDTO.getDescrizioneAutore(),operatore.getCdCodiceFiscale(),new Date());
					autore = bdlAutoreRepository.save(autore2save);
				}
				oggetto2save.setCdAutore(autore.getCdAutore());				
			}
		}
		//qualora abbia valorizzato la "descrizioneAutoreSecondario" e non 
		//"cdAutoreSecondario", provo a recuperare l'autore in questo modo...
		if(oggettoDTO.getCdAutoreSec()==null  && oggettoDTO.getDescrizioneAutoreSec()!=null){
			if(!"".equals(oggettoDTO.getDescrizioneAutoreSec())){
				List<BdlAutore> autori = bdlAutoreRepository.findByDsDescrizione(oggettoDTO.getDescrizioneAutoreSec());
				BdlAutore autore;
				if(!autori.isEmpty()){
					autore = autori.get(0);
				}else{
					BdlAutore autore2save = new BdlAutore(oggettoDTO.getDescrizioneAutoreSec(),operatore.getCdCodiceFiscale(),new Date());
					autore = bdlAutoreRepository.save(autore2save);
				}
				oggetto2save.setCdAutoreSec(autore.getCdAutore());				
			}
		}
		//qualora abbia valorizzato la "descrizioneEditore" e non 
		//"cdEditore", provo a recuperare l'editore in questo modo...
		if(oggettoDTO.getCdEditore()==null  && oggettoDTO.getDescrizioneEditore()!=null){
			if(!"".equals(oggettoDTO.getDescrizioneEditore())){
				List<BdlEditore> editori = bdlEditoreRepository.findByDsDescrizione(oggettoDTO.getDescrizioneEditore());
				BdlEditore editore;
				if(!editori.isEmpty()){
					editore = editori.get(0);
				}else{
					BdlEditore editore2save = new BdlEditore(oggettoDTO.getDescrizioneEditore(),operatore.getCdCodiceFiscale(),new Date());
					editore = bdlEditoreRepository.save(editore2save);
				}
				oggetto2save.setCdEditore(editore.getCdEditore());				
			}
		}
		//qualora abbia valorizzato la "descrizioneLingua" e non 
		//"cdLingua", provo a recuperare la lingua in questo modo...
		if(oggettoDTO.getDescrizioneLingua()!=null && !"".equals(oggettoDTO.getDescrizioneLingua())){
			List<BdlLingua> lingue = bdlLinguaRepository.findByDsCodlang(oggettoDTO.getDescrizioneLingua());
			if(!lingue.isEmpty()){
				BdlLingua lingua = lingue.get(0);
				oggetto2save.setCdLingua(lingua.getCdLingua());
			}
		}
		
		oggetto2save = bdlOggettoOriginaleRepository.save(oggetto2save);
		
		return mapper.mapBdlOggettoOriginaleToOggettoDTO(oggetto2save);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#getTreeItems()
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<SubMenuItemDTO> getTreeItems() {
		return getTreeItemsForIdentAndCatalogazioneOggetti(statiOggettoOriginale);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#mandaInValidazione(java.util.List)
	 */
	@Override
	@Transactional
	public void mandaInValidazione(List<VOggettoDTO> gridItems) throws AsyncServiceException {
		for(VOggettoDTO oggetto : gridItems){
			mandaInValidazioneOggetto(oggetto.getO_CdOggettoOriginale());
		}
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#mandaInValidazioneOggetto(java.math.BigDecimal)
	 */
	@Override
	public void mandaInValidazioneOggetto(BigDecimal cdOggetto) throws AsyncServiceException{

		OggettoDTO itemCheck = getOggetto(cdOggetto);
		if(itemCheck == null){
			throw new AsyncServiceException(serverBaseMessages.getString("accessoNonConsentito"));
		}

		BdlUtente  operatore = this.getActiveUtente();

		BdlOggettoOriginale oggettoJpa = bdlOggettoOriginaleRepository.findByCdOggettoOriginale(cdOggetto);
		oggettoJpa.setDsStato(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INVALIDAZIONE);
		oggettoJpa.setDsModificatoda(operatore.getCdCodiceFiscale());
		oggettoJpa.setDtModificatoil(new Date());
		oggettoJpa = bdlOggettoOriginaleRepository.save(oggettoJpa);

		VOggettoDTO vogg = vOggetti.findOggettoByCodice(oggettoJpa.getCdOggettoOriginale());
		Map<String,String> map = new HashMap<String,String>();
		map.put(BdlEmailer.OGGETTO_TITOLO, vogg.getO_DnTitolo());
		map.put(BdlEmailer.OGGETTO_ID, vogg.getO_DigitalizzatoreId());
		bdlEmailer.sendMailToCatalogatore(getActiveCdUtente(),oggettoJpa.getCdOggettoOriginale(),"OGGETTODAVALIDARE", map);
	}
	

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#getAutore()
	 */
	@Override
	public List<ComboDTO> getAutore(){
		List<ComboDTO> items = new ArrayList<ComboDTO>();
		List<BdlAutore> main = (List<BdlAutore>) bdlAutoreRepository.findAll();
		for(BdlAutore sub:main){
			items.add(new ComboDTO(sub.getCdAutore(),sub.getDsDescrizione()));
		}
		return items;
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#getContestoArchivistico()
	 */
	@Override
	public List<ComboDTO> getContestoArchivistico(){
		List<ComboDTO> items = new ArrayList<ComboDTO>();
		List<BdlContestoArch> main = (List<BdlContestoArch>) bdlContestoArchRepository.findAll();
		for(BdlContestoArch sub:main){
			items.add(new ComboDTO(sub.getCdContestoArch(),sub.getDsDescrizione()));
		}
		return items;
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#getEditore()
	 */
	@Override
	public List<ComboDTO> getEditore(){
		List<ComboDTO> items = new ArrayList<ComboDTO>();
		List<BdlEditore> main = (List<BdlEditore>) bdlEditoreRepository.findAll();
		for(BdlEditore sub:main){
			items.add(new ComboDTO(sub.getCdEditore(),sub.getDsDescrizione()));
		}
		return items;
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#getLingua()
	 */
	@Override
	public List<ComboDTO> getLingua(){
		List<ComboDTO> items = new ArrayList<ComboDTO>();
		List<BdlLingua> main = (List<BdlLingua>) bdlLinguaRepository.findAll();
		for(BdlLingua sub:main){
			items.add(new ComboDTO(sub.getCdLingua(),sub.getDsDescrizione()));
		}
		return items;
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#getLivelloBiblio()
	 */
	@Override
	public List<ComboDTO> getLivelloBiblio(){
		List<ComboDTO> items = new ArrayList<ComboDTO>();
		List<BdlLivelloBiblio> main = (List<BdlLivelloBiblio>) bdlLivelloBiblioRepository.findAll();
		for(BdlLivelloBiblio sub:main){
			items.add(new ComboDTO(sub.getCdLivelloBiblio(),sub.getDsDescrizione()));
		}
		return items;
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#getQualificaAutore()
	 */
	@Override
	public List<ComboDTO> getQualificaAutore(){
		List<ComboDTO> items = new ArrayList<ComboDTO>();
		List<BdlQualificaAutore> main = (List<BdlQualificaAutore>) bdlQualificaAutoreRepository.findAll();
		for(BdlQualificaAutore sub:main){
			items.add(new ComboDTO(sub.getCdQualificaAutore(),sub.getDsDescrizione()));
		}
		return items;
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#getQualificatoreData()
	 */
	@Override
	public List<ComboDTO> getQualificatoreData(){
		List<ComboDTO> items = new ArrayList<ComboDTO>();
		List<BdlQualificatoreData> main = (List<BdlQualificatoreData>) bdlQualificatoreDataRepository.findAll();
		for(BdlQualificatoreData sub:main){
			items.add(new ComboDTO(sub.getCdQualificatoreData(),sub.getDsDescrizione()));
		}
		return items;
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#getSoggettoProd()
	 */
	@Override
	public List<ComboDTO> getSoggettoProd(){
		List<ComboDTO> items = new ArrayList<ComboDTO>();
		List<BdlSoggettoProd> main = (List<BdlSoggettoProd>) bdlSoggettoProdRepository.findAll();
		for(BdlSoggettoProd sub:main){
			items.add(new ComboDTO(sub.getCdSoggettoProd(),sub.getDsDescrizione()));
		}
		return items;
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#getSoggetto()
	 */
	@Override
	public List<ComboDTO> getSoggetto(){
		List<ComboDTO> items = new ArrayList<ComboDTO>();
		List<BdlSoggetto> main = (List<BdlSoggetto>) bdlSoggettoRepository.findAll();
		for(BdlSoggetto sub:main){
			items.add(new ComboDTO(sub.getCdSoggetto(),sub.getDsDescrizione()));
		}
		return items;
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#getSupporto()
	 */
	@Override
	public List<ComboDTO> getSupporto(){
		List<ComboDTO> items = new ArrayList<ComboDTO>();
		List<BdlSupporto> main = (List<BdlSupporto>) bdlSupportoRepository.findAll();
		for(BdlSupporto sub:main){
			items.add(new ComboDTO(sub.getCdSupporto(),sub.getDsDescrizione()));
		}
		return items;
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#getTecnicaGrafica()
	 */
	@Override
	public List<ComboDTO> getTecnicaGrafica(){
		List<ComboDTO> items = new ArrayList<ComboDTO>();
		List<BdlTecnicaGrafica> main = (List<BdlTecnicaGrafica>) bdlTecnicaGraficaRepository.findAll();
		for(BdlTecnicaGrafica sub:main){
			items.add(new ComboDTO(sub.getCdTecnicaGrafica(),sub.getDsDescrizione()));
		}
		return items;
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#getTipoArchivio()
	 */
	@Override
	public List<ComboDTO> getTipoArchivio(){
		List<ComboDTO> items = new ArrayList<ComboDTO>();
		List<BdlTipoArchivio> main = (List<BdlTipoArchivio>) bdlTipoArchivioRepository.findAll();
		for(BdlTipoArchivio sub:main){
			items.add(new ComboDTO(sub.getCdTipoArchivio(),sub.getDsDescrizione()));
		}
		return items;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#getTipoGrafica()
	 */
	@Override
	public List<ComboDTO> getTipoGrafica(){
		List<ComboDTO> items = new ArrayList<ComboDTO>();
		List<BdlTipoGrafica> main = (List<BdlTipoGrafica>) bdlTipoGraficaRepository.findAll();
		for(BdlTipoGrafica sub:main){
			items.add(new ComboDTO(sub.getCdTipoGrafica(),sub.getDsDescrizione()));
		}
		return items;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#getTipoIdentificativo()
	 */
	@Override
	public List<ComboDTO> getTipoIdentificativo(){
		List<ComboDTO> items = new ArrayList<ComboDTO>();
		List<BdlTipoIdentificativo> main = (List<BdlTipoIdentificativo>) bdlTipoIdentificativoRepository.findAll();
		for(BdlTipoIdentificativo sub:main){
			items.add(new ComboDTO(sub.getCdTipoIdentificativo(),sub.getDsDescrizione()));
		}
		return items;
	}


	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#inviaCorrezioni(java.math.BigDecimal, java.lang.String)
	 */
	@Override
	@Transactional
	public void inviaCorrezioni(BigDecimal cdOggetto, String correzione) throws AsyncServiceException{

		BdlUtente  operatore = this.getActiveUtente();

		OggettoDTO itemCheck = getOggetto(cdOggetto);
		if(itemCheck == null){
			throw new AsyncServiceException(serverBaseMessages.getString("accessoNonConsentito"));
		}

		BdlOggettoOriginale oggetto2save = bdlOggettoOriginaleRepository.findByCdOggettoOriginale(cdOggetto);
		oggetto2save.setDsModificatoda(operatore.getCdCodiceFiscale());
		oggetto2save.setDtModificatoil(new Date());
		
		// Questo � necessario nel caso in cui l'oggetto arrivi da NON_VALIDATO
		oggetto2save.setDsStato(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_CATALOGATO);
		oggetto2save.setFlCorrezione(true);
		oggetto2save.setDsNotaCorrezione(correzione);

		oggetto2save = bdlOggettoOriginaleRepository.save(oggetto2save);
		

		VOggettoDTO vogg = vOggetti.findOggettoByCodice(oggetto2save.getCdOggettoOriginale());
		Map<String,String> map = new HashMap<String,String>();
		map.put(BdlEmailer.OGGETTO_TITOLO, vogg.getO_DnTitolo());
		map.put(BdlEmailer.OGGETTO_ID, vogg.getO_DigitalizzatoreId());
		bdlEmailer.sendMailToCatalogatore(getActiveCdUtente(),oggetto2save.getCdOggettoOriginale(),"OGGETTODACORREGGERE", map);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#getImmaginiOggetto(java.math.BigDecimal)
	 */
	@Override
	public List<ImmagineDTO> getImmaginiOggetto(BigDecimal cdOggetto) throws AsyncServiceException{

		OggettoDTO itemCheck = getOggetto(cdOggetto);
		if(itemCheck == null){
			throw new AsyncServiceException(serverBaseMessages.getString("accessoNonConsentito"));
		}

		List<BdlImmagine> immagini = bdlImmagineRepository.findByCdOggettoOriginaleOrderByDnNomeImmagineAsc(cdOggetto);
		
		return mapper.mapListBdlImmagineToListImmagineDTO(immagini,request.getContextPath(),BdlSharedConstants.BDL_SERVICE_PATH_PRIVATE,itemCheck.getCdOggettoOriginale());
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#salvaToc(java.math.BigDecimal, java.util.List)
	 */
	@Override
	public void salvaToc(BigDecimal cdOggettoOriginale, List<TocSommarioDTO> saveTreeSommarioData) throws AsyncServiceException{
		BdlUtente  operatore = this.getActiveUtente();
		try{
			tocSommarioUtils.salvaToc(cdOggettoOriginale, saveTreeSommarioData, operatore);
		} catch (AsyncServiceException e){
			AsyncServiceException myAsyncServiceException = new AsyncServiceException(serverMessages.getString("eccezioneToc")+ e.getMessage());
			myAsyncServiceException.setStackTrace(e.getStackTrace());
			throw myAsyncServiceException;
		}
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#getTocSommarioOggetto(java.math.BigDecimal)
	 */
	@Override
	public List<TocSommarioDTO> getTocSommarioOggetto(BigDecimal cdOggettoOriginale){
		return tocSommarioUtils.getTocSommarioOggetto(cdOggettoOriginale);
	}
	
	/**
	 * Import excel toc.
	 *
	 * @throws AsyncServiceException async service exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void importaToc() throws AsyncServiceException, IOException{
	
		ExcelParser parser = new ExcelParser();
		BdlUtente  operatore = this.getActiveUtente();
		BigDecimal  cdIstituto = this.getActiveCdEnte();

		/* Leggo il file uploadato */
		List<FileItem> files = UploadServlet.getSessionFileItems(request);
		if (files != null) {
			FileItem fileItm = files.get(0);
			
			List<Map<Integer, String>> contenuto = parser.parseXlsxFileWithOptionalColumns(fileItm.getInputStream(), 6, ExcelParser.FIRST_ROW_IS_HEADER);
			
			List<String> errors = importazioneExcelUtils.checkRigheTOC(contenuto, cdIstituto);
			if(errors.isEmpty()){
				importazioneExcelUtils.inserisciRigheTOC(contenuto, cdIstituto, operatore);
				UploadServlet.removeSessionFileItems(request);
			}else{
				UploadServlet.removeSessionFileItems(request);
				StringBuilder logError = importazioneExcelUtils.createExcelErrorLogRecord(errors);
				throw new AsyncServiceException(logError.toString());
			}
		}
	}

	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.CatalogazioneOggettiService#importaExcel(java.math.BigDecimal)
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void importaExcel(BigDecimal cdTipoOggetto) throws AsyncServiceException, IOException{

		BdlUtente operatore = this.getActiveUtente();
		BigDecimal cdIstituto = this.getActiveCdEnte();

		ExcelParser parser = new ExcelParser();
		try{
			List<FileItem> files = UploadServlet.getSessionFileItems(request);
			if (files != null) {
				FileItem fileItm = files.get(0);
				List<Map<Integer, String>> contenuti = parser.parseXlsxFileWithOptionalColumns(fileItm.getInputStream(), 29, ExcelParser.FIRST_ROW_IS_HEADER);
				List<String> errors = importazioneExcelUtils.checkRigheForCatalogazioneTpl(contenuti,cdTipoOggetto, cdIstituto);
				if(errors.isEmpty()){
					importazioneExcelUtils.inserisciRigheForCatalogazioneTpl(contenuti, cdTipoOggetto, cdIstituto, operatore);
				}else{
					StringBuilder logError = createExcelErrorLogRecord(errors);
					throw new AsyncServiceException(logError.toString());
				}
			}
		} finally {
			UploadServlet.removeSessionFileItems(request);
		}
	}
	
	/**
	 * Crea excel error log record.
	 *
	 * @param errors  errors
	 * @return string builder
	 */
	private StringBuilder createExcelErrorLogRecord (List<String> errors) {
		StringBuilder strErrorLog = new StringBuilder();

		for(String error : errors) {
			strErrorLog.append(error);
			strErrorLog.append(BdlServerConstants.LOGIMPORTAZIONEEXCELSEPARATOR);
		}

		return strErrorLog;
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.IdentOggettiService#getTipologiaOggetti()
	 */
	@Override
	public List<ComboDTO> getTipologiaOggetti(){
		List<BdlTipoOggetto> items = (List<BdlTipoOggetto>) bdlTipoOggettoRepository.findAll();
		List<ComboDTO> toReturn = new ArrayList<ComboDTO>();
		for(BdlTipoOggetto item:items){
			toReturn.add(new ComboDTO(item.getCdTipoOggetto(),item.getDnNome()));
		}

		return toReturn;
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public void opacSbnImport(BigDecimal cdCollezione, BigDecimal cdOggetto, UnimarcDTO item) throws AsyncServiceException {
		log.debug("[opacSbnImport] Importo l'oggetto \""+item.getTitolo()+"\" [ID="+cdOggetto+"]");

		//verifico che l'utente abbia accesso alla collezione
		getCollezione(cdCollezione);
		
		//recupero i dati del tipoIdentificativo SBN
		List<BdlTipoIdentificativo> tipiIdentificativi = bdlTipoIdentificativoRepository.findByDsDescrizione("SBN");
		BdlTipoIdentificativo tipoIdentificativoSBN = tipiIdentificativi.get(0); 
		BigDecimal cdTipoIdentificativoSBN = tipoIdentificativoSBN.getCdTipoIdentificativo();
		
		/**
		 * Su segnalazione di Malini del 8/10/2015 commentiamo questo check
		 * che sembra inspiegabile e illogico
		 */
		//if(item.getPath()==null || item.getPath().equals("")) {
			//verifico se si tratta di un oggetto superiore
			Boolean isOggettoSuperiore = false;
			if(item.getTitoliInferiori()!=null && !item.getTitoliInferiori().isEmpty()){
				isOggettoSuperiore = true;
			}
			//creo l'obj OggettoDTO partendo dall'obj UnimarcDTO
			OggettoDTO obj2Save = mapper.mapUnimarcDTOToOggettoDTO(item, cdCollezione, isOggettoSuperiore, cdTipoIdentificativoSBN);
			obj2Save.setCdOggettoOriginale(cdOggetto);
			OggettoDTO savedObj = salvaOggettoForImportaCatalogo(obj2Save);
			if(savedObj==null || !savedObj.getTitolo().equals(obj2Save.getTitolo())) {
				throw new AsyncServiceException(serverMessages.getString("eccezioneSBNImportKO"));
			}
		//} else {
		//	throw new AsyncServiceException(serverMessages.getString("eccezioneSBNImportNoPath"));
		//}
		log.debug("[opacSbnImport] ...Importazione terminata ");
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public List<UnimarcDTO> opacSbnSearch(String sbn, String autore, String titolo, 
			String editore, String annoPubblicazione) throws Exception {
		return opacSbnSearchForImportaCatalogo(sbn, autore, titolo, editore, annoPubblicazione);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public List<ComboStringDTO> getTipologiaOggettiConCatalogo() {
		return getTipologiaOggettiConCatalogoForImportaCatalogo();
	}
}
