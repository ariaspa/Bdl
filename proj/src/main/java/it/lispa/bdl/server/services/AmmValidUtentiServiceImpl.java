package it.lispa.bdl.server.services;

import it.lispa.bdl.commons.domain.BdlEnte;
import it.lispa.bdl.commons.domain.BdlEnteUtente;
import it.lispa.bdl.commons.domain.BdlRuolo;
import it.lispa.bdl.commons.domain.BdlUtente;
import it.lispa.bdl.server.mail.BdlEmailer;
import it.lispa.bdl.server.repository.BdlEnteRepository;
import it.lispa.bdl.server.repository.BdlEnteUtenteRepository;
import it.lispa.bdl.server.repository.BdlRuoloRepository;
import it.lispa.bdl.server.repository.BdlUtenteRepository;
import it.lispa.bdl.server.utils.BdlServerConstants;
import it.lispa.bdl.server.utils.GridDataUtils;
import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.UtenteDTO;
import it.lispa.bdl.shared.dto.UtenteLightDTO;
import it.lispa.bdl.shared.services.AmmValidUtentiService;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

/**
 * Class AmmValidUtentiServiceImpl.
 */
@Service("ammValidUtentiService")
@Repository
@Transactional
public class AmmValidUtentiServiceImpl extends BaseServiceImpl implements AmmValidUtentiService {

	/** utente repository. */
	@Autowired
	private BdlUtenteRepository bdlUtenteRepository;
	
	/** ente repository. */
	@Autowired
	private BdlEnteRepository enteRepository;
	
	/** ente utente repository. */
	@Autowired
	private BdlEnteUtenteRepository enteUtenteRepository;
	
	/** ruolo repository. */
	@Autowired
	private BdlRuoloRepository ruoloRepository;

//	/** entity manager. */
//	@PersistenceContext
//	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmValidUtentiService#getUtentiDaValidare(java.util.List, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Transactional(readOnly = true)
	public PagingLoadResult<UtenteDTO> getUtentiDaValidare(List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit) {

		List<UtenteDTO> items = new ArrayList<UtenteDTO>();

		List<BdlUtente> utentiJpa = bdlUtenteRepository.findByDsStato(BdlSharedConstants.BDL_UTENTE_STATO_DAVALIDARE);

		for (BdlUtente utenteJpa : utentiJpa) {

			List<BigDecimal> cdEnti = new ArrayList<BigDecimal>();
			List<String> entiRelazionati = new ArrayList<String>();
			List<BdlEnteUtente> entiRel = enteUtenteRepository.findByCdUtente(utenteJpa.getCdUtente());
			if (!entiRel.isEmpty()) {
				Iterator<BdlEnteUtente> relItr = entiRel.iterator();
				while (relItr.hasNext()) {
					BdlEnteUtente itm = relItr.next();
					BdlEnte ente = enteRepository.findByCdEnte(itm.getCdEnte());
					entiRelazionati.add(ente.getDnNome());
					cdEnti.add(ente.getCdEnte());
				}
			}
			String ruoloRelazionato = "";
			BdlRuolo ruoloJpa = ruoloRepository.findByCdRuolo(utenteJpa.getCdRuolo());
			ruoloRelazionato = ruoloJpa.getDnNome();

			items.add(new UtenteDTO(utenteJpa.getCdUtente(), utenteJpa.getNmNome(), utenteJpa.getCmCognome(), utenteJpa.getCdCodiceFiscale(), utenteJpa
					.getDnEmail(), utenteJpa.getNrTelefono(), utenteJpa.getCdRuolo(), ruoloRelazionato, utenteJpa.getDsStato(), utenteJpa.getDtRegistrazione(),
					utenteJpa.getDtValidazione(), entiRelazionati, cdEnti));
		}

		String orderFieldDefault = "cognome";
		String orderDirDefault = "asc";
		return GridDataUtils.gridApplyFiltersSortingPagination(items, filters, sortField, orderDir, myOffset, myLimit, orderFieldDefault, orderDirDefault);
		
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmValidUtentiService#validaUtente(java.math.BigDecimal)
	 */
	@Transactional
	public void validaUtente(BigDecimal cdUtente) throws AsyncServiceException {

		BdlUtente operatore = this.getActiveUtente();
		
		BdlUtente utenteJpa = bdlUtenteRepository.findByCdUtente(cdUtente);
		utenteJpa.setDsStato(BdlSharedConstants.BDL_UTENTE_STATO_VALIDATO);
		utenteJpa.setDsModificatoda(operatore.getCdCodiceFiscale());
		utenteJpa.setDtModificatoil(new Date());
		utenteJpa.setDtValidazione(new Date());
		utenteJpa = bdlUtenteRepository.save(utenteJpa);

		Map<String,String> map = new HashMap<String,String>();
		String comunicazione = null;

		StringBuilder enteRelazionato = new StringBuilder();
		List<BdlEnteUtente> entiRel = enteUtenteRepository.findByCdUtente(utenteJpa.getCdUtente());
		if (!entiRel.isEmpty()) {
			Iterator<BdlEnteUtente> relItr = entiRel.iterator();
			while (relItr.hasNext()) {
				BdlEnteUtente itm = relItr.next();
				BdlEnte ente = enteRepository.findByCdEnte(itm.getCdEnte());
				enteRelazionato.append(" "+ente.getDnNome());
			}
		}

		String ruoloRelazionato = "";
		BdlRuolo ruoloJpa = ruoloRepository.findByCdRuolo(utenteJpa.getCdRuolo());
		ruoloRelazionato = ruoloJpa.getDnNome();
		
		map.put(BdlEmailer.RUOLO_NOME, ruoloRelazionato);
		map.put(BdlEmailer.ENTE_NOME, enteRelazionato.toString());
		
		if(utenteJpa.getCdRuolo().equals(BdlServerConstants.BDL_RUOLO_CD_CATALOGATORE)){
			comunicazione = "UTENTEVALIDATOCAT";
		}else if(utenteJpa.getCdRuolo().equals(BdlServerConstants.BDL_RUOLO_CD_DIGITALIZZATORE)){
			comunicazione = "UTENTEVALIDATODIG";
		}else{
			comunicazione = "UTENTEVALIDATO";
		}
		
		bdlEmailer.sendMailToSupervisors(getActiveCdUtente(),comunicazione, map);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmValidUtentiService#validaUtenti(java.util.List)
	 */
	@Transactional
	public void validaUtenti(List<UtenteDTO> utenti) throws AsyncServiceException {
		for (UtenteLightDTO utente : utenti) {
			validaUtente(utente.getCdUtente());
		}
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AmmValidUtentiService#getRuoli()
	 */
	@Transactional
	public List<ComboDTO> getRuoli() {

		List<ComboDTO> listRuoli = new ArrayList<ComboDTO>();
		for (BdlRuolo ruolo : ruoloRepository.findAll()) {
			listRuoli.add(new ComboDTO(ruolo.getCdRuolo(), ruolo.getDnNome()));
		}

		return listRuoli;

	}
}
