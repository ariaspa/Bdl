package it.lispa.bdl.server.services;

import gwtupload.server.UploadServlet;
import it.lispa.bdl.commons.domain.BdlOggettoOriginale;
import it.lispa.bdl.commons.domain.BdlTipoIdentificativo;
import it.lispa.bdl.commons.domain.BdlTipoOggetto;
import it.lispa.bdl.commons.domain.BdlUtente;
import it.lispa.bdl.server.mail.BdlEmailer;
import it.lispa.bdl.server.utils.CaricamentoImmaginiUtils;
import it.lispa.bdl.server.utils.ExcelParser;
import it.lispa.bdl.server.utils.ImportazioneExcelUtils;
import it.lispa.bdl.server.utils.UTF8Control;
import it.lispa.bdl.server.utils.VOggettiUtils;
import it.lispa.bdl.shared.dto.CollezioneDTO;
import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.ComboStringDTO;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.dto.ProgettoDTO;
import it.lispa.bdl.shared.dto.SubMenuItemDTO;
import it.lispa.bdl.shared.dto.UnimarcDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.services.IdentOggettiService;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
 * Class IdentOggettiServiceImpl.
 */
@Service("identOggettiService")
@Repository
@Transactional
public class IdentOggettiServiceImpl extends BaseServiceImpl implements IdentOggettiService {

	/** v oggetti. */
	@Autowired
	private VOggettiUtils vOggetti;
	
	/** importazione excel utils. */
	@Autowired
	private ImportazioneExcelUtils importazioneExcelUtils;
	
	/** immagini utils. */
	@Autowired
	private CaricamentoImmaginiUtils immaginiUtils;
	
//	/** entity manager. */
//	@PersistenceContext
//	private EntityManager entityManager;

	/** server messages. */
	ResourceBundle serverMessages = ResourceBundle.getBundle("it.lispa.bdl.server.messages.IdentOggettiMsg", new UTF8Control());
	
	/** La Costante statiOggettoOriginale. */
	public final static List<String> statiOggettoOriginale = new ArrayList<String>() {
		private static final long serialVersionUID = -7626183828843663412L;
		{
			add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INSERITO);
			add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_RIFIUTATO);
		}
	};
	
	/** La Costante statiOggettoSuperiore. */
	public final static List<String> statiOggettoSuperiore = new ArrayList<String>() {
		private static final long serialVersionUID = -7626183828843663412L;
		{
			add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INSERITO);
			add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_RIFIUTATO);
			add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INCATALOGAZIONE);
			add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_CATALOGATO);
			add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INVERIFICA);
			add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_NONVALIDATO);
			add(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_NONPUBBLICATO);
		}
	};


	/** log. */
	private static Logger log = Logger.getLogger(IdentOggettiServiceImpl.class);
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.IdentOggettiService#getProgetto(java.math.BigDecimal)
	 */
	@Override
	public ProgettoDTO getProgetto(BigDecimal cdProgetto) throws AsyncServiceException {
		return getProgettoForIdentAndCatalogazioneOggetti(cdProgetto);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.IdentOggettiService#getCollezione(java.math.BigDecimal)
	 */
	@Override
	public CollezioneDTO getCollezione(BigDecimal cdCollezione) throws AsyncServiceException {
		return getCollezioneForIdentAndCatalogazioneOggetti(cdCollezione);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.IdentOggettiService#getOggetto(java.math.BigDecimal)
	 */
	@Override
	public OggettoDTO getOggetto(BigDecimal cdOggetto) throws AsyncServiceException {
		return getOggettoForIdentAndCatalogazioneOggetti(cdOggetto, statiOggettoOriginale);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.IdentOggettiService#getOggettiSuperiori()
	 */
	@Override
	public List<ComboDTO> getOggettiSuperiori() throws AsyncServiceException {
		
		BigDecimal  cdIstituto = this.getActiveCdEnte();

		Map<String,Boolean> filtriFlag = new HashMap<String,Boolean>();
		filtriFlag.put(BdlSharedConstants.VOGGETTI_FILTRO_FL_OGGETTO_SUPERIORE,true);

		List<VOggettoDTO> items = vOggetti.findOggettiByIstituto(cdIstituto, BdlSharedConstants.BDL_PROGETTO_STATO_INCORSO, statiOggettoSuperiore,filtriFlag,null,null);
		List<ComboDTO> toReturn = new ArrayList<ComboDTO>();
		for(VOggettoDTO item:items){
			toReturn.add(new ComboDTO(item.getO_CdOggettoOriginale(),item.getO_DnTitolo()));
		}

		return toReturn;
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

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.IdentOggettiService#hasCartografia(java.math.BigDecimal)
	 */
	@Override
	public Boolean hasCartografia(BigDecimal cdTipoOgg){
		return immaginiUtils.hasNaturaMap(cdTipoOgg);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.IdentOggettiService#salvaProgetto(it.lispa.bdl.shared.dto.ProgettoDTO)
	 */
	@Transactional
	@Override
	public ProgettoDTO salvaProgetto(ProgettoDTO progetto) throws AsyncServiceException{
		return salvaProgettoForIdentAndCatalogazioneOggetti(progetto);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.IdentOggettiService#salvaCollezione(it.lispa.bdl.shared.dto.CollezioneDTO)
	 */
	@Transactional
	@Override
	public CollezioneDTO salvaCollezione(CollezioneDTO collezione) throws AsyncServiceException{
		return salvaCollezioneForIdentAndCatalogazioneOggetti(collezione);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.IdentOggettiService#salvaOggetto(it.lispa.bdl.shared.dto.OggettoDTO)
	 */
	@Transactional
	@Override
	public OggettoDTO salvaOggetto(OggettoDTO oggetto) throws AsyncServiceException {
		log.debug("[salvaOggetto] Entro nel metodo");

		BdlUtente operatore = this.getActiveUtente();

		CollezioneDTO itemCheck = getCollezione(oggetto.getCdCollezione());
		if(itemCheck == null){
			throw new AsyncServiceException(serverBaseMessages.getString("accessoNonConsentito"));
		}

		BdlOggettoOriginale oggetto2save = new BdlOggettoOriginale();
		oggetto2save.setCdCollezione(oggetto.getCdCollezione());
		
		return salvaOggettoForIOAndAmmGestIndex(serverMessages, oggetto, oggetto2save, operatore);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.IdentOggettiService#getTreeItems()
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public List<SubMenuItemDTO> getTreeItems() {
		return getTreeItemsForIdentAndCatalogazioneOggetti(statiOggettoOriginale);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.IdentOggettiService#cancellaOggetto(java.math.BigDecimal)
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public void cancellaOggetto(BigDecimal cdOggetto) throws AsyncServiceException {

		OggettoDTO itemCheck = getOggetto(cdOggetto);
		if(itemCheck == null){
			throw new AsyncServiceException(serverBaseMessages.getString("accessoNonConsentito"));
		}

		BdlOggettoOriginale oggetto2save = new BdlOggettoOriginale();

		oggetto2save.setCdOggettoOriginale(cdOggetto);

		bdlOggettoOriginaleRepository.delete(oggetto2save);	

	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.IdentOggettiService#mandaInVerifica(java.util.List)
	 */
	@Transactional
	@Override
	public void mandaInVerifica(List<VOggettoDTO> gridItems) throws AsyncServiceException {
		for(VOggettoDTO oggetto : gridItems){
			mandaInVerificaOggetto(oggetto.getO_CdOggettoOriginale());
		}

	}
	
	/**
	 * Manda in verifica oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @throws AsyncServiceException async service exception
	 */
	public void mandaInVerificaOggetto(BigDecimal cdOggetto) throws AsyncServiceException {

		OggettoDTO itemCheck = getOggetto(cdOggetto);
		if(itemCheck == null){
			throw new AsyncServiceException(serverBaseMessages.getString("accessoNonConsentito"));
		}

		BdlUtente  operatore = this.getActiveUtente();

		BdlOggettoOriginale oggettoJpa = bdlOggettoOriginaleRepository.findByCdOggettoOriginale(cdOggetto);
		oggettoJpa.setDsStato(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INVERIFICA);
		oggettoJpa.setDsModificatoda(operatore.getCdCodiceFiscale());
		oggettoJpa.setDtModificatoil(new Date());
		oggettoJpa = bdlOggettoOriginaleRepository.save(oggettoJpa);

		Map<String,String> map = new HashMap<String,String>();
		map.put(BdlEmailer.OGGETTO_TITOLO, oggettoJpa.getDnTitolo());
		bdlEmailer.sendMailToSupervisors(getActiveCdUtente(),"OGGETTODAVERIFICARE", map);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.IdentOggettiService#importaExcel(java.math.BigDecimal)
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public void importaExcel(BigDecimal cdCollezione, String filename, Long filesize) throws AsyncServiceException, IOException{
		log.debug("[importaExcel] Entro ");
		
		BdlUtente  operatore = this.getActiveUtente();
		BigDecimal  cdIstituto = this.getActiveCdEnte();

		ExcelParser parser = new ExcelParser();
		
		List<FileItem> files = UploadServlet.getSessionFileItems(request);
		if (files != null) {
			List<Map<Integer, String>> contenuti = null;
			
			Iterator<FileItem> fileItmItr = files.iterator();
			while(fileItmItr.hasNext()) {
				FileItem myFileItm = fileItmItr.next();
				log.debug("[importaExcel] File in sessione: name="+myFileItm.getName()+" size="+myFileItm.getSize());
				Long myFileItmFilesize = new Long(myFileItm.getSize());
				if(myFileItm.getName().equalsIgnoreCase(filename) && myFileItmFilesize.equals(filesize)) {
					if(myFileItm.isInMemory()) {
						log.debug("[importaExcel] Il file è in memoria! ");
						byte[] arrFileItm = myFileItm.get();
						InputStream bisFileItm = new ByteArrayInputStream(arrFileItm);
						contenuti = parser.parseXlsxFileWithMandatoryColumns(bisFileItm, 5, ExcelParser.FIRST_ROW_IS_HEADER);
					} else {
						log.debug("[importaExcel] Il file NON è in memoria! Procedo come prima... ");
						contenuti = parser.parseXlsxFileWithMandatoryColumns((FileInputStream) myFileItm.getInputStream(), 5, ExcelParser.FIRST_ROW_IS_HEADER);
					}
				}
			}
			
			List<String> errors = importazioneExcelUtils.checkRighe(contenuti, cdIstituto);
			if(errors.isEmpty()){
				importazioneExcelUtils.inserisciRighe(contenuti, cdCollezione, cdIstituto, operatore);
				UploadServlet.removeSessionFileItems(request);
			}else{
				UploadServlet.removeSessionFileItems(request);
				StringBuilder logError = importazioneExcelUtils.createExcelErrorLogRecord(errors);
				throw new AsyncServiceException(logError.toString());
			}
		}
		log.debug("[importaExcel] Esco ");
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.IdentOggettiService#getTipologiaOggettiConCatalogo()
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public List<ComboStringDTO> getTipologiaOggettiConCatalogo() {
		return getTipologiaOggettiConCatalogoForImportaCatalogo();
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public void opacSbnImport(BigDecimal cdCollezione, List<UnimarcDTO> items) throws AsyncServiceException {
		log.debug("[opacSbnImport] Importo "+items.size()+" oggetti");

		// Con questo verifico anche che l'utente abbia accesso alla collezione!
		getCollezione(cdCollezione);
		
		List<UnimarcDTO> items2remove = new ArrayList<UnimarcDTO>(); 
		
		List<BdlTipoIdentificativo> tipiIdentificativi = bdlTipoIdentificativoRepository.findByDsDescrizione("SBN");
		
		BdlTipoIdentificativo tipoSbn = tipiIdentificativi.get(0); 
		BigDecimal tipoSbnCd = tipoSbn.getCdTipoIdentificativo();
		
		for(UnimarcDTO item:items){
			// Incomincio salvando gli oggetti che non hanno un padre
			if(item.getPath()==null || item.getPath().equals("")){
				log.debug("[opacSbnImport] Tratto item "+item.getTitolo());
				Boolean isOggettoSuperiore = false;
				if(item.getTitoliInferiori()!=null && !item.getTitoliInferiori().isEmpty()){
					isOggettoSuperiore = true;
				}else{
					isOggettoSuperiore = false;
				}
				OggettoDTO obj2Save = mapper.mapUnimarcDTOToOggettoDTO(item, cdCollezione, isOggettoSuperiore, tipoSbnCd);
				OggettoDTO oggettoSalvato = salvaOggetto(obj2Save);
				items2remove.add(item);
				// Procedo salvando gli oggetti che hanno come padre l'oggetto specifico
				for(UnimarcDTO subitem:items){
					if(!subitem.equals(item) && subitem.getPath()!=null && !subitem.getPath().equals("")){
						log.debug("[opacSbnImport] Tratto item "+subitem.getTitolo());
						if(subitem.getTitoliInferiori()!=null && !subitem.getTitoliInferiori().isEmpty()){
							isOggettoSuperiore = true;
						}else{
							isOggettoSuperiore = false;
						}
						OggettoDTO subobj2Save = mapper.mapUnimarcDTOToOggettoDTO(subitem, oggettoSalvato.getCdOggettoOriginale(), cdCollezione, isOggettoSuperiore, tipoSbnCd);
						salvaOggetto(subobj2Save);
						items2remove.add(subitem);
					}
				}
			}
		}
		for(UnimarcDTO item2rem:items2remove){
			items.remove(item2rem);
		}
		for(UnimarcDTO item:items){
			// Procedo salvando gli oggetti che hanno un padre ma non era nel set importato
			log.debug("[opacSbnImport] Tratto item "+item.getTitolo());
			Boolean isOggettoSuperiore = false;
			if(item.getTitoliInferiori()!=null && !item.getTitoliInferiori().isEmpty()){
				isOggettoSuperiore = true;
			}else{
				isOggettoSuperiore = false;
			}
			OggettoDTO obj2Save = mapper.mapUnimarcDTOToOggettoDTO(item, cdCollezione, isOggettoSuperiore, tipoSbnCd);
			salvaOggetto(obj2Save);
		}
		log.debug("[opacSbnImport] Importazione terminata (mi rimangono da importare "+items.size()+")");
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public List<UnimarcDTO> opacSbnSearch(String sbn, String autore, String titolo, 
			String editore, String annoPubblicazione) throws Exception {
		return opacSbnSearchForImportaCatalogo(sbn, autore, titolo, editore, annoPubblicazione);
	}
}
