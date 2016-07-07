package it.lispa.bdl.server.utils;

import it.lispa.bdl.commons.domain.BdlEnte;
import it.lispa.bdl.commons.domain.BdlEnteUtente;
import it.lispa.bdl.server.repository.BdlEnteRepository;
import it.lispa.bdl.server.repository.BdlEnteUtenteRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class SecurityUtils.
 */
@Component
public class SecurityUtils  {

	/** e rep. */
	@Autowired
	private BdlEnteRepository eRep;
	
	/** ee ue rep. */
	@Autowired
	private BdlEnteUtenteRepository eeUeRep;

	/**
	 * Legge codice enti for utente.
	 *
	 * @param cdUtente  codice utente
	 * @return codice enti for utente
	 */
	public List<BigDecimal> getCdEntiForUtente(BigDecimal cdUtente){
		List<BigDecimal> cdEnti = new ArrayList<BigDecimal>();
		List<BdlEnteUtente> entiRel = eeUeRep.findByCdUtente(cdUtente);
		if(!entiRel.isEmpty()){
			Iterator<BdlEnteUtente> relItr = entiRel.iterator();
			while(relItr.hasNext()){
				BdlEnteUtente itm = relItr.next();
				BdlEnte ente = eRep.findByCdEnte(itm.getCdEnte());
				cdEnti.add(ente.getCdEnte());
			}
		}
		return cdEnti;
	}
}
