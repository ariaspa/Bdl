/**
 * Created on Oct 18, 2011
 */
package it.lispa.bdl.server.services;

import it.lispa.bdl.commons.domain.BdlRuolo;
import it.lispa.bdl.commons.domain.BdlUtente;
import it.lispa.bdl.server.repository.BdlRuoloRepository;
import it.lispa.bdl.server.utils.VOggettiUtils;
import it.lispa.bdl.shared.dto.SubMenuItemDTO;
import it.lispa.bdl.shared.dto.VOggettoCountDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.services.VOggettiService;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

/**
 * Class VOggettiServiceImpl.
 */
@Service("vOggettiService")
@Repository
@Transactional
public class VOggettiServiceImpl extends BaseServiceImpl implements VOggettiService {

	/** v oggetti. */
	@Autowired
	private VOggettiUtils vOggetti;

	/** ruolo repository. */
	@Autowired
	private BdlRuoloRepository ruoloRepository;

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.VOggettiService#getTreeCounters()
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<SubMenuItemDTO> getTreeCounters() throws AsyncServiceException {
		return getTreeItemsForSupervisore(null, null, null);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.VOggettiService#getTreeCritici()
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public List<SubMenuItemDTO> getTreeCritici() throws AsyncServiceException {
		Map<String,Boolean> filtriFlag = new HashMap<String,Boolean>();
		filtriFlag.put(BdlSharedConstants.VOGGETTI_FILTRO_FL_IS_CRITICO, true);
		return getTreeItemsForSupervisore(null, null, filtriFlag);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.VOggettiService#getTreeItemsForSupervisore(java.lang.String, java.lang.String)
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public List<SubMenuItemDTO> getTreeItemsForSupervisore(String statoProgetto, String statoOggetto) throws AsyncServiceException {	
		return getTreeItemsForSupervisore(statoProgetto, vOggetti.stringAsList(statoOggetto));
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.VOggettiService#getTreeItemsForSupervisore(java.lang.String, java.util.List)
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public List<SubMenuItemDTO> getTreeItemsForSupervisore(String statoProgetto, List<String> statiOggetto) throws AsyncServiceException {		
		return getTreeItemsForSupervisore(statoProgetto, statiOggetto, null);
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.VOggettiService#getTreeItemsForSupervisore(java.lang.String, java.util.List, java.util.Map)
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public List<SubMenuItemDTO> getTreeItemsForSupervisore(String statoProgetto, List<String> statiOggetto, Map<String,Boolean>filtriFlag) throws AsyncServiceException {	
		List<BigDecimal> cdEntiAbilitati = new ArrayList<BigDecimal>();

		BdlUtente utente = getActiveUtente();
		BdlRuolo ruoloUtente = ruoloRepository.findByCdRuolo(utente.getCdRuolo());

		boolean flAmministratore = false;

		if(BdlSharedConstants.BDL_RUOLO_SUPERVISORE.equalsIgnoreCase(ruoloUtente.getDnNome())){
			cdEntiAbilitati = getCdEntiForActiveUtente();
			if(cdEntiAbilitati.isEmpty()){
				throw new AsyncServiceException(serverBaseMessages.getString("supervisoreSenzaEnti"));
			}
		}else{
			flAmministratore = true;
		}

		List<SubMenuItemDTO> items = vOggetti.findIstitutiByEntiAbilitati(statoProgetto, statiOggetto, filtriFlag, cdEntiAbilitati, flAmministratore);
		for (SubMenuItemDTO item : items) {
			processTreeChildren(item, statoProgetto, statiOggetto, filtriFlag);
		}
		return items;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.VOggettiService#getTreeItemsForDigitalizzatore(java.lang.String, java.lang.String)
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public List<SubMenuItemDTO> getTreeItemsForDigitalizzatore(String statoProgetto, String statoOggetto) {	
		return getTreeItemsForDigitalizzatore(statoProgetto, vOggetti.stringAsList(statoOggetto));
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.VOggettiService#getTreeItemsForDigitalizzatore(java.lang.String, java.util.List)
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public List<SubMenuItemDTO> getTreeItemsForDigitalizzatore(String statoProgetto, List<String> statiOggetto) {
		return getTreeItemsForDigitalizzatore(statoProgetto, statiOggetto, null);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.VOggettiService#getTreeItemsForDigitalizzatore(java.lang.String, java.util.List, java.util.Map)
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public List<SubMenuItemDTO> getTreeItemsForDigitalizzatore(String statoProgetto, List<String> statiOggetto, Map<String,Boolean>filtriFlag) {

		BigDecimal  cdEnte = getActiveCdEnte();
		List<SubMenuItemDTO> items = vOggetti.findIstitutiByDigitalizzatore(cdEnte, statoProgetto, statiOggetto,filtriFlag);
		for (SubMenuItemDTO item : items) {
			processTreeChildren(item, statoProgetto, statiOggetto,filtriFlag);
		}
		return items;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.VOggettiService#getTreeItemsForCatalogatore(java.lang.String, java.lang.String)
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public List<SubMenuItemDTO> getTreeItemsForCatalogatore(String statoProgetto, String statoOggetto) {
		return getTreeItemsForCatalogatore(statoProgetto, vOggetti.stringAsList(statoOggetto));
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.VOggettiService#getTreeItemsForCatalogatore(java.lang.String, java.util.List)
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public List<SubMenuItemDTO> getTreeItemsForCatalogatore(String statoProgetto, List<String> statiOggetto) {
		return getTreeItemsForCatalogatore(statoProgetto, statiOggetto, null);
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.VOggettiService#getTreeItemsForCatalogatore(java.lang.String, java.util.List, java.util.Map)
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public List<SubMenuItemDTO> getTreeItemsForCatalogatore(String statoProgetto, List<String> statiOggetto, Map<String,Boolean>filtriFlag) {

		BigDecimal  cdEnte = getActiveCdEnte();
		List<SubMenuItemDTO> items = vOggetti.findProgettiByIstituto(cdEnte, statoProgetto, statiOggetto,filtriFlag);
		for (SubMenuItemDTO item : items) {
			processTreeChildren(item, statoProgetto, statiOggetto,filtriFlag);
			for (SubMenuItemDTO collezione : item.getChildren()) {
				List<VOggettoDTO> children = vOggetti.findOggettiByIstituto(cdEnte, statoProgetto, statiOggetto, filtriFlag, null, collezione.getCodice());
				String label = null;
				if(children.size()>1){
					label = children.size()+" oggetti";
				}else if(children.size()==1){
					label = children.size()+" oggetto";
				}
				if(label!=null){
					SubMenuItemDTO collezioneOggettiDto = new SubMenuItemDTO(collezione.getCodice(),children.size()+" oggetti",SubMenuItemDTO.TIPO_COLLOGGETTI);
					collezione.addChild(collezioneOggettiDto);
				}
			}
		}
		return items;
	}

	/**
	 * Process tree children.
	 *
	 * @param rootNode  root node
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 */
	private void processTreeChildren(SubMenuItemDTO rootNode, String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag) {
		List<SubMenuItemDTO> children = null;
		if(rootNode.getTipo().equals(SubMenuItemDTO.TIPO_ISTITUTO)){
			children = vOggetti.findProgettiByIstituto(rootNode.getCodice(), statoProgetto, statiOggetto, filtriFlag);
		}else if(rootNode.getTipo().equals(SubMenuItemDTO.TIPO_PROGETTO)){
			children = vOggetti.findCollezioniByProgetto(rootNode.getCodice(), statoProgetto, statiOggetto, filtriFlag);
		}		
		if(children!=null){
			rootNode.setChildren(children);
			for (SubMenuItemDTO item : children) {
				processTreeChildren(item, statoProgetto, statiOggetto, filtriFlag);
			}
		}
	}



	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.VOggettiService#getGridCounters(java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal)
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public List<VOggettoCountDTO> getGridCounters(BigDecimal filterIstituto, BigDecimal filterProgetto, BigDecimal filterCollezione) throws AsyncServiceException {
		List<BigDecimal> cdEntiAbilitati = new ArrayList<BigDecimal>();

		BdlUtente utente = getActiveUtente();
		BdlRuolo ruoloUtente = ruoloRepository.findByCdRuolo(utente.getCdRuolo());

		boolean flAmministratore = false;

		if(BdlSharedConstants.BDL_RUOLO_SUPERVISORE.equalsIgnoreCase(ruoloUtente.getDnNome())){
			cdEntiAbilitati = getCdEntiForActiveUtente();
			if(cdEntiAbilitati.isEmpty()){
				throw new AsyncServiceException(serverBaseMessages.getString("supervisoreSenzaEnti"));
			}
		}else{
			flAmministratore = true;
		}
		return vOggetti.findConteggioPerStato(filterIstituto, filterProgetto, filterCollezione, cdEntiAbilitati, flAmministratore);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.VOggettiService#getGridCritici(java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal, java.util.List, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public PagingLoadResult<VOggettoDTO> getGridCritici(BigDecimal filterIstituto, BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit) throws AsyncServiceException {
		Map<String,Boolean> filtriFlag = new HashMap<String,Boolean>();
		filtriFlag.put(BdlSharedConstants.VOGGETTI_FILTRO_FL_IS_CRITICO, true);
		return getGridItemsForSupervisore(null, null, filtriFlag, filterIstituto, filterProgetto, filterCollezione, filters, sortField, orderDir, myOffset, myLimit);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.VOggettiService#getGridItemsForSupervisore(java.lang.String, java.lang.String, java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal, java.util.List, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public PagingLoadResult<VOggettoDTO> getGridItemsForSupervisore(String statoProgetto, String statoOggetto,BigDecimal filterIstituto, BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit) throws AsyncServiceException{
		return getGridItemsForSupervisore( statoProgetto, vOggetti.stringAsList(statoOggetto), filterIstituto, filterProgetto, filterCollezione, filters, sortField, orderDir, myOffset, myLimit);
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.VOggettiService#getGridItemsForSupervisore(java.lang.String, java.util.List, java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal, java.util.List, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public PagingLoadResult<VOggettoDTO> getGridItemsForSupervisore(String statoProgetto, List<String> statiOggetto,BigDecimal filterIstituto, BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit) throws AsyncServiceException{
		return getGridItemsForSupervisore(statoProgetto, statiOggetto, null, filterIstituto, filterProgetto, filterCollezione, filters, sortField, orderDir, myOffset, myLimit);
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.VOggettiService#getGridItemsForSupervisore(java.lang.String, java.util.List, java.util.Map, java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal, java.util.List, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public PagingLoadResult<VOggettoDTO> getGridItemsForSupervisore(String statoProgetto, List<String> statiOggetto, Map<String,Boolean>filtriFlag, BigDecimal filterIstituto, BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit) throws AsyncServiceException{
		
		List<BigDecimal> cdEntiAbilitati = new ArrayList<BigDecimal>();

		BdlUtente utente = getActiveUtente();
		BdlRuolo ruoloUtente = ruoloRepository.findByCdRuolo(utente.getCdRuolo());

		boolean flAmministratore = false;

		if(BdlSharedConstants.BDL_RUOLO_SUPERVISORE.equalsIgnoreCase(ruoloUtente.getDnNome())){
			cdEntiAbilitati = getCdEntiForActiveUtente();
			if(cdEntiAbilitati.isEmpty()){
				throw new AsyncServiceException(serverBaseMessages.getString("supervisoreSenzaEnti"));
			}
		}else{
			flAmministratore = true;
		}

		List<VOggettoDTO> items = vOggetti.findOggettiByEntiAbilitati(statoProgetto, statiOggetto, filtriFlag, filterIstituto, filterProgetto, filterCollezione, cdEntiAbilitati, flAmministratore);
		return vOggetti.gridApplyFiltersSortingPagination(items, filters, sortField, orderDir, myOffset, myLimit);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.VOggettiService#getGridItemsForDigitalizzatore(java.lang.String, java.lang.String, java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal, java.util.List, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public PagingLoadResult<VOggettoDTO> getGridItemsForDigitalizzatore(String statoProgetto, String statoOggetto, BigDecimal filterIstituto,BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit){
		return getGridItemsForDigitalizzatore( statoProgetto, vOggetti.stringAsList(statoOggetto), filterIstituto, filterProgetto, filterCollezione, filters, sortField, orderDir, myOffset, myLimit);
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.VOggettiService#getGridItemsForDigitalizzatore(java.lang.String, java.util.List, java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal, java.util.List, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public PagingLoadResult<VOggettoDTO> getGridItemsForDigitalizzatore(String statoProgetto, List<String> statiOggetto, BigDecimal filterIstituto,BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit){
		return getGridItemsForDigitalizzatore(statoProgetto, statiOggetto, null, filterIstituto,filterProgetto, filterCollezione, filters, sortField, orderDir, myOffset, myLimit);
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.VOggettiService#getGridItemsForDigitalizzatore(java.lang.String, java.util.List, java.util.Map, java.math.BigDecimal, java.math.BigDecimal, java.math.BigDecimal, java.util.List, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public PagingLoadResult<VOggettoDTO> getGridItemsForDigitalizzatore(String statoProgetto, List<String> statiOggetto, Map<String,Boolean>filtriFlag, BigDecimal filterIstituto,BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit){
		BigDecimal  cdEnte = getActiveCdEnte();
		List<VOggettoDTO> items = vOggetti.findOggettiByDigitalizzatore(cdEnte, statoProgetto, statiOggetto, filtriFlag, filterIstituto, filterProgetto, filterCollezione);
		return vOggetti.gridApplyFiltersSortingPagination(items, filters, sortField, orderDir, myOffset, myLimit);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.VOggettiService#getGridItemsForCatalogatore(java.lang.String, java.lang.String, java.math.BigDecimal, java.math.BigDecimal, java.util.List, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public PagingLoadResult<VOggettoDTO> getGridItemsForCatalogatore(String statoProgetto, String statoOggetto, BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit){
		return getGridItemsForCatalogatore(statoProgetto, vOggetti.stringAsList(statoOggetto), filterProgetto, filterCollezione, filters, sortField, orderDir, myOffset, myLimit);
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.VOggettiService#getGridItemsForCatalogatore(java.lang.String, java.util.List, java.math.BigDecimal, java.math.BigDecimal, java.util.List, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public PagingLoadResult<VOggettoDTO> getGridItemsForCatalogatore(String statoProgetto, List<String> statiOggetto, BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit){
		return getGridItemsForCatalogatore(statoProgetto, statiOggetto, null, filterProgetto, filterCollezione, filters, sortField, orderDir, myOffset, myLimit);
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.VOggettiService#getGridItemsForCatalogatore(java.lang.String, java.util.List, java.util.Map, java.math.BigDecimal, java.math.BigDecimal, java.util.List, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	@Override
	public PagingLoadResult<VOggettoDTO> getGridItemsForCatalogatore(String statoProgetto, List<String> statiOggetto, Map<String,Boolean>filtriFlag, BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit){
		BigDecimal  cdEnte = getActiveCdEnte();
		List<VOggettoDTO> items = vOggetti.findOggettiByIstituto(cdEnte, statoProgetto, statiOggetto, filtriFlag, filterProgetto, filterCollezione);
		return vOggetti.gridApplyFiltersSortingPagination(items, filters, sortField, orderDir, myOffset, myLimit);
	}

}
