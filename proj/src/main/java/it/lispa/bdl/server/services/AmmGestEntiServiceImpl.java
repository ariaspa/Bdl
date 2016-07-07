package it.lispa.bdl.server.services;

import it.lispa.bdl.commons.domain.BdlEnte;
import it.lispa.bdl.commons.domain.BdlProvincia;
import it.lispa.bdl.commons.domain.BdlUtente;
import it.lispa.bdl.server.repository.BdlEnteRepository;
import it.lispa.bdl.server.repository.BdlProvinciaRepository;
import it.lispa.bdl.server.utils.GridDataUtils;
import it.lispa.bdl.server.utils.UTF8Control;
import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.ComboStringDTO;
import it.lispa.bdl.shared.dto.EnteDTO;
import it.lispa.bdl.shared.services.AmmGestEntiService;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

/**
 * Class AmmGestEntiServiceImpl.
 */
@Service("ammGestEntiService")
@Repository
@Transactional
public class AmmGestEntiServiceImpl extends BaseServiceImpl implements AmmGestEntiService {

	/** item repository. */
	@Autowired
	private BdlEnteRepository itemRepository;

	/** provincia repository. */
	@Autowired
	private BdlProvinciaRepository provinciaRepository;

//	/** entity manager. */
//	@PersistenceContext
//	private EntityManager entityManager;

	/** server messages. */
	ResourceBundle serverMessages = ResourceBundle.getBundle("it.lispa.bdl.server.messages.AmmGestEntiMsg", new UTF8Control());

	/* METODI CRUD STANDARD */

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmGestEntiService#getItemByCodice(java.math.BigDecimal)
	 */
	public EnteDTO getItemByCodice(BigDecimal cdItem) throws AsyncServiceException {
		BdlEnte item = itemRepository.findByCdEnte(cdItem);

		BdlProvincia prov = null;
		if (item.getDnProvincia() != null) {
			prov = provinciaRepository.findByCdSigla(item.getDnProvincia());
		}

		BdlEnte digitalizzatore = null;
		if (item.getCdEnteDigit() != null) {
			digitalizzatore = itemRepository.findByCdEnte(item.getCdEnteDigit());
		}

		return mapper.mapBdlEnteToEnteDTO(item, prov, digitalizzatore);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmGestEntiService#getItems(java.util.List, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Transactional
	public PagingLoadResult<EnteDTO> getItems(List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit,
			String orderFieldDefault, String orderDirDefault) {
		List<EnteDTO> items = new ArrayList<EnteDTO>();
		for (BdlEnte item : itemRepository.findAll()) {
			BdlProvincia prov = null;
			if (item.getDnProvincia() != null) {
				prov = provinciaRepository.findByCdSigla(item.getDnProvincia());
			}
			BdlEnte digitalizzatore = null;
			if (item.getCdEnteDigit() != null) {
				digitalizzatore = itemRepository.findByCdEnte(item.getCdEnteDigit());
			}
			items.add(mapper.mapBdlEnteToEnteDTO(item, prov, digitalizzatore));
		}
		return GridDataUtils.gridApplyFiltersSortingPagination(items, filters, sortField, orderDir, myOffset, myLimit, orderFieldDefault, orderDirDefault);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmGestEntiService#cancellaItems(java.util.List)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void cancellaItems(List<EnteDTO> items) throws AsyncServiceException {
		for (EnteDTO item : items) {
			cancellaItemByCodice(item.getCdEnte());
		}

	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmGestEntiService#cancellaItemByCodice(java.math.BigDecimal)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void cancellaItemByCodice(BigDecimal cdItem) throws AsyncServiceException {
		BdlEnte item = itemRepository.findByCdEnte(cdItem);
		itemRepository.delete(item);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmGestEntiService#salvaItem(it.lispa.bdl.shared.dto.EnteDTO)
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public EnteDTO salvaItem(EnteDTO item) throws AsyncServiceException {

		BdlUtente operatore = this.getActiveUtente();

		BdlEnte bdlItem = new BdlEnte();

		if (item.getCdEnte() == null) {
			bdlItem.setDsCreatoda(operatore.getCdCodiceFiscale());
			bdlItem.setDtCreatoil(new Date());
		} else {
			bdlItem = itemRepository.findByCdEnte(item.getCdEnte());
			bdlItem.setDsModificatoda(operatore.getCdCodiceFiscale());
			bdlItem.setDtModificatoil(new Date());
		}

		BdlEnte digitalizzatore = null;
		if(item.getClasse()==BdlSharedConstants.BDL_ENTE_CLASSE_ISTITUTO){
			if(item.getCdEnteDigit()==null){
				throw new AsyncServiceException(serverMessages.getString("salvaItemKOnodigitalizzatore"));
			}
			digitalizzatore = itemRepository.findByCdEnte(item.getCdEnteDigit());
			if (digitalizzatore == null) {
				throw new AsyncServiceException(serverMessages.getString("salvaItemKOdigitalizzatore"));
			}
			bdlItem.setCdEnteDigit(digitalizzatore.getCdEnte());
		}else{
			bdlItem.setCdEnteDigit(null);
		}

		bdlItem.setDnNome(item.getNome());
		bdlItem.setDsIndirizzo(item.getIndirizzo());
		bdlItem.setDnComune(item.getComune());

		BdlProvincia prov = provinciaRepository.findByCdSigla(item.getProvincia());
		if (prov == null) {
			throw new AsyncServiceException(serverMessages.getString("salvaItemKOprovincia"));
		}

		bdlItem.setDnProvincia(prov.getCdSigla());
		bdlItem.setDsCap(item.getCap());
		bdlItem.setNrTelefono(item.getTelefono());
		bdlItem.setNrFax(item.getFax());
		bdlItem.setDnEmail(item.getEmail());
		bdlItem.setDnIndirizzoWww(item.getIndirizzoWww());
		bdlItem.setFlClasse(item.getClasse());

		bdlItem = itemRepository.save(bdlItem);

		return mapper.mapBdlEnteToEnteDTO(bdlItem, prov, digitalizzatore);
	}


	/* METODI CUSTOM */
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmGestEntiService#getProvince()
	 */
	public List<ComboStringDTO> getProvince(){
		List<ComboStringDTO> listProvince = new ArrayList<ComboStringDTO>();
		for(BdlProvincia prov : provinciaRepository.findAll(new Sort(Sort.Direction.ASC, "dsProvincia"))) {
			listProvince.add(new ComboStringDTO(prov.getCdSigla(), prov.getDsProvincia()));
		}
		return listProvince;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmGestEntiService#getDigitalizzatori()
	 */
	public List<ComboDTO> getDigitalizzatori(){
		List<ComboDTO> listEntiDig = new ArrayList<ComboDTO>();
		for (BdlEnte enteDig : itemRepository.findByFlClasseOrderByDnNomeAsc(BdlSharedConstants.BDL_ENTE_CLASSE_DIGITALIZZATORE)){
			listEntiDig.add(new ComboDTO(enteDig.getCdEnte(), enteDig.getDnNome()));
		}
		return listEntiDig;
	}

}
