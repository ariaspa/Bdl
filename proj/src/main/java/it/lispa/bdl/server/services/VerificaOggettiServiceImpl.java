package it.lispa.bdl.server.services;

import it.lispa.bdl.commons.domain.BdlOggettoOriginale;
import it.lispa.bdl.commons.domain.BdlUtente;
import it.lispa.bdl.server.mail.BdlEmailer;
import it.lispa.bdl.server.utils.VOggettiUtils;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.services.VerificaOggettiService;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Class VerificaOggettiServiceImpl.
 */
@Service("verificaOggettiService")
@Repository
@Transactional
public class VerificaOggettiServiceImpl extends BaseServiceImpl implements VerificaOggettiService {

	/** v oggetti. */
	@Autowired
	private VOggettiUtils vOggetti;
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.VerificaOggettiService#verificaOggetto(java.math.BigDecimal)
	 */
	@Transactional
	public void verificaOggetto(BigDecimal cdOggetto) throws AsyncServiceException {
		
		BdlUtente  operatore = this.getActiveUtente();
		
		BdlOggettoOriginale oggettoJpa = bdlOggettoOriginaleRepository.findByCdOggettoOriginale(cdOggetto);
		oggettoJpa.setDsStato(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_INCATALOGAZIONE);
		oggettoJpa.setDsModificatoda(operatore.getCdCodiceFiscale());
		oggettoJpa.setDtModificatoil(new Date());
		oggettoJpa = bdlOggettoOriginaleRepository.save(oggettoJpa);
		
		VOggettoDTO vogg = vOggetti.findOggettoByCodice(oggettoJpa.getCdOggettoOriginale());

		Map<String,String> map = new HashMap<String,String>();
		map.put(BdlEmailer.OGGETTO_TITOLO, vogg.getO_DnTitolo());
		map.put(BdlEmailer.OGGETTO_ID, vogg.getO_DigitalizzatoreId());
		bdlEmailer.sendMailToCatalogatore(getActiveCdUtente(),oggettoJpa.getCdOggettoOriginale(),"OGGETTOVERIFICATOCAT", map);
		bdlEmailer.sendMailToDigitalizzatore(getActiveCdUtente(),oggettoJpa.getCdOggettoOriginale(),"OGGETTOVERIFICATODIG", map);
		
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.VerificaOggettiService#verificaOggetti(java.util.List)
	 */
	@Transactional
	public void verificaOggetti(List<VOggettoDTO> oggetti) throws AsyncServiceException {
		for(VOggettoDTO oggetto : oggetti){
			verificaOggetto(oggetto.getO_CdOggettoOriginale());
		}
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.VerificaOggettiService#rifiutaOggetto(java.math.BigDecimal)
	 */
	@Transactional
	public void rifiutaOggetto(BigDecimal cdOggetto) throws AsyncServiceException {
		
		BdlUtente  operatore = this.getActiveUtente();
		
		BdlOggettoOriginale oggettoJpa = bdlOggettoOriginaleRepository.findByCdOggettoOriginale(cdOggetto);
		oggettoJpa.setDsStato(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_RIFIUTATO);
		oggettoJpa.setDsModificatoda(operatore.getCdCodiceFiscale());
		oggettoJpa.setDtModificatoil(new Date());
		oggettoJpa = bdlOggettoOriginaleRepository.save(oggettoJpa);

		VOggettoDTO vogg = vOggetti.findOggettoByCodice(oggettoJpa.getCdOggettoOriginale());
		Map<String,String> map = new HashMap<String,String>();
		map.put(BdlEmailer.OGGETTO_TITOLO, vogg.getO_DnTitolo());
		map.put(BdlEmailer.OGGETTO_ID, vogg.getO_DigitalizzatoreId());
		bdlEmailer.sendMailToCatalogatore(getActiveCdUtente(),oggettoJpa.getCdOggettoOriginale(),"OGGETTORIFIUTATO", map);
	}
	
	
}
