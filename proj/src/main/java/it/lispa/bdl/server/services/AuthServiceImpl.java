package it.lispa.bdl.server.services;

import it.lispa.bdl.commons.domain.BdlEnte;
import it.lispa.bdl.commons.domain.BdlEnteUtente;
import it.lispa.bdl.commons.domain.BdlRuolo;
import it.lispa.bdl.commons.domain.BdlUtente;
import it.lispa.bdl.server.repository.BdlEnteRepository;
import it.lispa.bdl.server.repository.BdlEnteUtenteRepository;
import it.lispa.bdl.server.repository.BdlRuoloRepository;
import it.lispa.bdl.server.utils.RequestFilterUtils;
import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.services.AuthService;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Class AuthServiceImpl.
 */
@Service("authService")
@Repository
@Transactional
public class AuthServiceImpl extends BaseServiceImpl implements AuthService {

	/*private static final long serialVersionUID = 8835042844574570803L;*/

	/** ente repository. */
	@Autowired
	private BdlEnteRepository enteRepository;
	
	/** ente utente repository. */
	@Autowired
	private BdlEnteUtenteRepository enteUtenteRepository;
	
	/** ruolo repository. */
	@Autowired
	private BdlRuoloRepository ruoloRepository;

	/** log. */
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(AuthServiceImpl.class);
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AuthService#getHeaderString()
	 */
	@Transactional(readOnly=true)
	public String getHeaderString() {
		String header = "";
		
		BigDecimal cdUtente = getActiveCdUtente();
		BigDecimal cdEnte = getActiveCdEnte();
		
		BdlUtente utenteJpa = bdlUtenteRepository.findByCdUtente(cdUtente);
		
		BdlRuolo ruoloJpa = ruoloRepository.findByCdRuolo(utenteJpa.getCdRuolo());
		
		header = "Utente: "+utenteJpa.getNmNome()+" "+utenteJpa.getCmCognome()+" - "+utenteJpa.getCdCodiceFiscale()+" - "+ruoloJpa.getDnNome()+"";
		
		if(cdEnte!=null && !BdlSharedConstants.BDL_RUOLO_SUPERVISORE.equalsIgnoreCase(ruoloJpa.getDnNome())){
			BdlEnte enteJpa = enteRepository.findByCdEnte(cdEnte);
			header += " "+enteJpa.getDnNome();
		}		
		
		return header;
	}


	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AuthService#activateEnte(java.math.BigDecimal)
	 */
	public String activateEnte(BigDecimal cdEnte){
		

		BigDecimal cdUtente = getActiveCdUtente();
		
		List<BdlEnteUtente> entiRel = enteUtenteRepository.findByCdEnteAndCdUtente(cdEnte, cdUtente);
		if(!entiRel.isEmpty()){
			BdlEnte ente = enteRepository.findByCdEnte(entiRel.get(0).getCdEnte());
			request.getSession().setAttribute(RequestFilterUtils.SESSION_ACTIVE_ENTE_CD, ente.getCdEnte());
			request.getSession().setAttribute(RequestFilterUtils.SESSION_ACTIVE_ENTE, ente);
		}
		
		return this.getHeaderString();
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.AuthService#getEntiAttivabiliCmbCatalogatore()
	 */
	public List<ComboDTO> getEntiAttivabiliCmbCatalogatore(){

		BdlUtente utente = getActiveUtente();
		BigDecimal cdUtente = utente.getCdUtente();
		BigDecimal cdEnte = getActiveCdEnte();
		BdlRuolo ruolo = ruoloRepository.findByCdRuolo(utente.getCdRuolo());
		List<ComboDTO> enti = new ArrayList<ComboDTO>();
		
		if(BdlSharedConstants.BDL_RUOLO_CATALOGATORE.equalsIgnoreCase(ruolo.getDnNome())){
			List<BdlEnteUtente> entiRel = enteUtenteRepository.findByCdUtente(cdUtente);
			if(!entiRel.isEmpty()){
				Iterator<BdlEnteUtente> relItr = entiRel.iterator();
				while(relItr.hasNext()){
					BdlEnteUtente itm = relItr.next();
					BdlEnte ente = enteRepository.findByCdEnte(itm.getCdEnte());
					boolean selected = false;
					if(cdEnte.equals(ente.getCdEnte())){
						selected = true;
					}
					enti.add(new ComboDTO(ente.getCdEnte(),ente.getDnNome(),selected));
				}
			}
		}
		return enti;
	}
	
}
