package it.lispa.bdl.server.services;

import it.lispa.bdl.commons.domain.BdlEnte;
import it.lispa.bdl.commons.domain.BdlEnteUtente;
import it.lispa.bdl.commons.domain.BdlRuolo;
import it.lispa.bdl.commons.domain.BdlUtente;
import it.lispa.bdl.server.repository.BdlEnteRepository;
import it.lispa.bdl.server.repository.BdlEnteUtenteRepository;
import it.lispa.bdl.server.repository.BdlRuoloRepository;
import it.lispa.bdl.server.utils.GridDataUtils;
import it.lispa.bdl.server.utils.UTF8Control;
import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.UtenteDTO;
import it.lispa.bdl.shared.dto.UtenteLightDTO;
import it.lispa.bdl.shared.services.AmmGestUtentiService;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

/**
 * Class AmmGestUtentiServiceImpl.
 */
@Service("ammGestUtentiService")
@Repository
@Transactional
public class AmmGestUtentiServiceImpl extends BaseServiceImpl implements AmmGestUtentiService {

	/** ente utente repository. */
	@Autowired
	private BdlEnteUtenteRepository enteUtenteRepository;
	
	/** ente repository. */
	@Autowired
	private BdlEnteRepository enteRepository;
	
	/** ruolo repository. */
	@Autowired
	private BdlRuoloRepository ruoloRepository;

//	/** entity manager. */
//	@PersistenceContext
//	private EntityManager entityManager;
	
	/** server messages. */
	ResourceBundle serverMessages = ResourceBundle.getBundle("it.lispa.bdl.server.messages.AmmGestUtentiMsg", new UTF8Control());

	/* METODI CRUD STANDARD */

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmGestUtentiService#getItemByCodice(java.math.BigDecimal)
	 */
	@Override
	public UtenteDTO getItemByCodice(BigDecimal cdItem) throws AsyncServiceException {
		BdlUtente item = bdlUtenteRepository.findByCdUtente(cdItem);
		BdlRuolo ruolo = ruoloRepository.findByCdRuolo(item.getCdRuolo());
		List<BdlEnteUtente> bdlEntiUtente = enteUtenteRepository.findByCdUtente(item.getCdUtente());
		return mapper.mapBdlUtenteToUtenteDTO(item, ruolo, bdlEntiUtente);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmGestUtentiService#getItems(java.util.List, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	public PagingLoadResult<UtenteLightDTO> getItems(List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit,
			String orderFieldDefault, String orderDirDefault) {
		List<UtenteLightDTO> items = new ArrayList<UtenteLightDTO>();
		for (BdlUtente item : bdlUtenteRepository.findAll()) {
			BdlRuolo ruolo = ruoloRepository.findByCdRuolo(item.getCdRuolo());
			items.add(mapper.mapBdlUtenteToUtenteLightDTO(item, ruolo));
		}
		return GridDataUtils.gridApplyFiltersSortingPagination(items, filters, sortField, orderDir, myOffset, myLimit, orderFieldDefault, orderDirDefault);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmGestUtentiService#cancellaItems(java.util.List)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public void cancellaItems(List<UtenteLightDTO> items) throws AsyncServiceException {
		for (UtenteLightDTO item : items) {
			cancellaItemByCodice(item.getCdUtente());
		}

	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmGestUtentiService#cancellaItemByCodice(java.math.BigDecimal)
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public void cancellaItemByCodice(BigDecimal cdItem) throws AsyncServiceException {
		BdlUtente item = bdlUtenteRepository.findByCdUtente(cdItem);
		bdlUtenteRepository.delete(item);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmGestUtentiService#salvaItem(it.lispa.bdl.shared.dto.UtenteDTO)
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	@Override
	public UtenteDTO salvaItem(UtenteDTO item) throws AsyncServiceException {

		BdlUtente operatore = this.getActiveUtente();
		
		BdlUtente bdlItem = new BdlUtente();

		if (item.getCdUtente() == null) {
			bdlItem.setDtRegistrazione(new Date());
			bdlItem.setDsCreatoda(operatore.getCdCodiceFiscale());
			bdlItem.setDtCreatoil(new Date());
		} else {
			bdlItem = bdlUtenteRepository.findByCdUtente(item.getCdUtente());
			bdlItem.setDsModificatoda(operatore.getCdCodiceFiscale());
			bdlItem.setDtModificatoil(new Date());
		}

		BdlRuolo ruolo = ruoloRepository.findByCdRuolo(item.getCdRuolo());
		if (ruolo == null) {
			throw new AsyncServiceException(serverMessages.getString("salvaItemKOruolo"));
		}
		if(ruolo.getDnNome().equalsIgnoreCase(BdlSharedConstants.BDL_RUOLO_CATALOGATORE) && (item.getCdEnti()==null || item.getCdEnti().isEmpty())){
			throw new AsyncServiceException(serverMessages.getString("salvaItemKOruoloCatalogatore"));			
		}
		if(ruolo.getDnNome().equalsIgnoreCase(BdlSharedConstants.BDL_RUOLO_SUPERVISORE) && (item.getCdEnti()==null || item.getCdEnti().isEmpty())){
			throw new AsyncServiceException(serverMessages.getString("salvaItemKOruoloSupervisore"));			
		}
		if(ruolo.getDnNome().equalsIgnoreCase(BdlSharedConstants.BDL_RUOLO_DIGITALIZZATORE) && (item.getCdEnti()==null || item.getCdEnti().size()!=1)){
			throw new AsyncServiceException(serverMessages.getString("salvaItemKOruoloDigitalizzatore"));			
		}

		bdlItem.setCdRuolo(item.getCdRuolo());

		bdlItem.setNmNome(item.getNome());
		bdlItem.setCmCognome(item.getCognome());

		String cf = item.getCf().toUpperCase();
		BdlUtente utenteSameCf = bdlUtenteRepository.findByCdCodiceFiscale(cf);
		Boolean cfError = false;
		if (item.getCdUtente() == null) {
			if (utenteSameCf != null) {
				cfError = true;
			}
		} else {
			if (utenteSameCf != null && !utenteSameCf.getCdUtente().equals(bdlItem.getCdUtente())) {
				cfError = true;
			}
		}

		if (cfError) {
			throw new AsyncServiceException(serverMessages.getString("salvaItemKOcf"));
		}

		bdlItem.setCdCodiceFiscale(cf);

		bdlItem.setDnEmail(item.getEmail());
		bdlItem.setNrTelefono(item.getTelefono());

		if (item.getCdUtente() == null) {
			if (item.getStato().equals(BdlSharedConstants.BDL_UTENTE_STATO_VALIDATO)) {
				bdlItem.setDtValidazione(new Date());
			} else {
				bdlItem.setDtValidazione(null);
			}
		} else {
			// Cerco di capire se l'operatore sta modificando
			// lo stato dell'utente
			// da validare->validato o
			// validato->da validare
			// nel primo caso modifico la data validazione
			if (!item.getStato().equals(bdlItem.getDsStato())) {
				if (bdlItem.getDsStato().equals(BdlSharedConstants.BDL_UTENTE_STATO_DAVALIDARE)
						&& item.getStato().equals(BdlSharedConstants.BDL_UTENTE_STATO_VALIDATO)) {
					bdlItem.setDtValidazione(new Date());
				}
			}
		}
		// Successivamente al check sullo stato in relazione alla data di
		// validazione
		// imposto lo stato sul bdlItem
		bdlItem.setDsStato(item.getStato());

		bdlItem = bdlUtenteRepository.save(bdlItem);

		List<BdlEnteUtente> entiUtentiAttuali = enteUtenteRepository.findByCdUtente(bdlItem.getCdUtente());
		List<BigDecimal> cdEntiUtentiAttuali = new ArrayList<BigDecimal>();
		/*
		 * Cancello gli item esistenti
		 */
		for (BdlEnteUtente enteUtente : entiUtentiAttuali){
			cdEntiUtentiAttuali.add(enteUtente.getCdEnte());
			if (!item.getCdEnti().contains(enteUtente.getCdEnte())) {
				enteUtenteRepository.delete(enteUtente);
			}
		}
		
		/*
		 * Inserisco i nuovi item 
		 */
		for (BigDecimal ente : item.getCdEnti()){
			if (!cdEntiUtentiAttuali.contains(ente)){
				BdlEnteUtente tmpEnteUtente = new BdlEnteUtente();
				tmpEnteUtente.setCdEnte(ente);
				tmpEnteUtente.setCdUtente(bdlItem.getCdUtente());
				enteUtenteRepository.save(tmpEnteUtente);
			}
		}

		List<BdlEnteUtente> listEntiUtenti = enteUtenteRepository.findByCdUtente(bdlItem.getCdUtente());
		return mapper.mapBdlUtenteToUtenteDTO(bdlItem, ruolo, listEntiUtenti);
	}

	/* METODI CUSTOM */

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmGestUtentiService#getRuoli()
	 */
	@Transactional
	@Override
	public List<ComboDTO> getRuoli() {

		List<ComboDTO> listRuoli = new ArrayList<ComboDTO>();
		for (BdlRuolo ruolo : ruoloRepository.findAll()) {
			listRuoli.add(new ComboDTO(ruolo.getCdRuolo(), ruolo.getDnNome()));
		}

		return listRuoli;

	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmGestUtentiService#getDigitalizzatori()
	 */
	@Override
	public List<ComboDTO> getDigitalizzatori() {
		List<ComboDTO> lista = new ArrayList<ComboDTO>();		
		for (BdlEnte ente : enteRepository.findByFlClasseOrderByDnNomeAsc(BdlSharedConstants.BDL_ENTE_CLASSE_DIGITALIZZATORE)) {
			lista.add(new ComboDTO(ente.getCdEnte(), ente.getDnNome()));
		}
		return lista;
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmGestUtentiService#getIstituti()
	 */
	@Transactional
	@Override
	public List<ComboDTO> getIstituti() {
		List<ComboDTO> lista = new ArrayList<ComboDTO>();		
		for (BdlEnte ente : enteRepository.findByFlClasseOrderByDnNomeAsc(BdlSharedConstants.BDL_ENTE_CLASSE_ISTITUTO)) {
			lista.add(new ComboDTO(ente.getCdEnte(), ente.getDnNome()));
		}
		return lista;
	}
}
