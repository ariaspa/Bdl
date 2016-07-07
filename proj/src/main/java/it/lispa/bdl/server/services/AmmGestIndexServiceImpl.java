package it.lispa.bdl.server.services;

import it.lispa.bdl.commons.domain.BdlOggettoOriginale;
import it.lispa.bdl.commons.domain.BdlTipoOggetto;
import it.lispa.bdl.commons.domain.BdlUtente;
import it.lispa.bdl.server.utils.UTF8Control;
import it.lispa.bdl.server.utils.VOggettiUtils;
import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.services.AmmGestIndexService;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class AmmGestIndexServiceImpl.
 */
@Service("ammGestIndexService")
@Repository
@Transactional
public class AmmGestIndexServiceImpl extends BaseServiceImpl implements AmmGestIndexService {

	/** v oggetti. */
	@Autowired
	private VOggettiUtils vOggetti;
	
//	/** entity manager. */
//	@PersistenceContext
//	private EntityManager entityManager;

	/** server messages. */
	ResourceBundle serverMessages = ResourceBundle.getBundle("it.lispa.bdl.server.messages.AmmGestEntiMsg", new UTF8Control());

	/* METODI CRUD STANDARD */

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmGestIndexService#getItemByCodice(java.math.BigDecimal)
	 */
	public OggettoDTO getItemByCodice(BigDecimal cdItem) throws AsyncServiceException {
		BdlOggettoOriginale item = bdlOggettoOriginaleRepository.findByCdOggettoOriginale(cdItem);
		return mapper.mapBdlOggettoOriginaleToOggettoDTO(item);
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmGestIndexService#cancellaItems(java.util.List)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void cancellaItems(List<VOggettoDTO> items) throws AsyncServiceException {
		for (VOggettoDTO item : items) {
			cancellaItemByCodice(item.getO_CdOggettoOriginale());
		}

	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmGestIndexService#cancellaItemByCodice(java.math.BigDecimal)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void cancellaItemByCodice(BigDecimal cdItem) throws AsyncServiceException {
		BdlOggettoOriginale item = bdlOggettoOriginaleRepository.findByCdOggettoOriginale(cdItem);
		bdlOggettoOriginaleRepository.delete(item);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmGestIndexService#salvaItem(it.lispa.bdl.shared.dto.OggettoDTO)
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public OggettoDTO salvaItem(OggettoDTO oggetto) throws AsyncServiceException {

		BdlUtente operatore = this.getActiveUtente();
		
		BdlOggettoOriginale oggetto2save = new BdlOggettoOriginale();
		return salvaOggettoForIOAndAmmGestIndex(serverMessages, oggetto, oggetto2save, operatore);
	}


	/* METODI CUSTOM */
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmGestIndexService#getTipologiaOggetti()
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
	 * @see it.lispa.bdl.shared.services.AmmGestIndexService#getOggettiSuperiori()
	 */
	@Override
	public List<ComboDTO> getOggettiSuperiori(){
		Map<String,Boolean> filtriFlag = new HashMap<String,Boolean>();
		filtriFlag.put(BdlSharedConstants.VOGGETTI_FILTRO_FL_OGGETTO_SUPERIORE,true);

		List<VOggettoDTO> items = vOggetti.findOggetti(BdlSharedConstants.BDL_PROGETTO_STATO_INCORSO, IdentOggettiServiceImpl.statiOggettoSuperiore,filtriFlag,null,null, null);
		List<ComboDTO> toReturn = new ArrayList<ComboDTO>();
		for(VOggettoDTO item:items){
			toReturn.add(new ComboDTO(item.getO_CdOggettoOriginale(),item.getO_DnTitolo()));
		}

		return toReturn;
	}

}
