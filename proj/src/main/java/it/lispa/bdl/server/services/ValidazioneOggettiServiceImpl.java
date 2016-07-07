package it.lispa.bdl.server.services;

import it.lispa.bdl.commons.domain.BdlOggettoOriginale;
import it.lispa.bdl.commons.domain.BdlUtente;
import it.lispa.bdl.server.mail.BdlEmailer;
import it.lispa.bdl.server.utils.TocSommarioUtils;
import it.lispa.bdl.server.utils.VOggettiUtils;
import it.lispa.bdl.shared.dto.ImmagineDTO;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.dto.TocSommarioDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.services.ValidazioneOggettiService;
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
 * Class ValidazioneOggettiServiceImpl.
 */
@Service("validazioneOggettiService")
@Repository
@Transactional
public class ValidazioneOggettiServiceImpl extends BaseServiceImpl implements ValidazioneOggettiService {

	/** v oggetti. */
	@Autowired 
	private VOggettiUtils vOggetti;
	
	/** toc sommario utils. */
	@Autowired
	private TocSommarioUtils tocSommarioUtils;
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.ValidazioneOggettiService#validaOggetto(java.math.BigDecimal)
	 */
	@Transactional
	public void validaOggetto(BigDecimal cdOggetto) throws AsyncServiceException {
		
		BdlUtente  operatore = this.getActiveUtente();
		
		BdlOggettoOriginale oggettoJpa = bdlOggettoOriginaleRepository.findByCdOggettoOriginale(cdOggetto);
		oggettoJpa.setDsStato(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_VALIDATO);
		oggettoJpa.setDsModificatoda(operatore.getCdCodiceFiscale());
		oggettoJpa.setDtModificatoil(new Date());
		oggettoJpa = bdlOggettoOriginaleRepository.save(oggettoJpa);

		VOggettoDTO vogg = vOggetti.findOggettoByCodice(oggettoJpa.getCdOggettoOriginale());
		Map<String,String> map = new HashMap<String,String>();
		map.put(BdlEmailer.OGGETTO_TITOLO, vogg.getO_DnTitolo());
		map.put(BdlEmailer.OGGETTO_ID, vogg.getO_DigitalizzatoreId());
		bdlEmailer.sendMailToCatalogatore(getActiveCdUtente(),oggettoJpa.getCdOggettoOriginale(),"OGGETTOVALIDATO", map);
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.ValidazioneOggettiService#validaOggetti(java.util.List)
	 */
	@Transactional
	public void validaOggetti(List<VOggettoDTO> oggetti) throws AsyncServiceException {
		for(VOggettoDTO oggetto : oggetti){
			validaOggetto(oggetto.getO_CdOggettoOriginale());
		}
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.ValidazioneOggettiService#rifiutaOggetto(java.math.BigDecimal, java.lang.String)
	 */
	@Transactional
	public void rifiutaOggetto(BigDecimal cdOggetto, String motivo) throws AsyncServiceException {
		
		BdlUtente  operatore = this.getActiveUtente();
		
		BdlOggettoOriginale oggettoJpa = bdlOggettoOriginaleRepository.findByCdOggettoOriginale(cdOggetto);
		oggettoJpa.setDsStato(BdlSharedConstants.BDL_OGGETTO_ORIGINALE_STATO_NONVALIDATO);
		oggettoJpa.setDsNoteNonValidazione(motivo);
		oggettoJpa.setDsModificatoda(operatore.getCdCodiceFiscale());
		oggettoJpa.setDtModificatoil(new Date());
		oggettoJpa = bdlOggettoOriginaleRepository.save(oggettoJpa);

		VOggettoDTO vogg = vOggetti.findOggettoByCodice(oggettoJpa.getCdOggettoOriginale());
		Map<String,String> map = new HashMap<String,String>();
		map.put(BdlEmailer.OGGETTO_TITOLO, vogg.getO_DnTitolo());
		map.put(BdlEmailer.OGGETTO_ID, vogg.getO_DigitalizzatoreId());
		bdlEmailer.sendMailToCatalogatore(getActiveCdUtente(),oggettoJpa.getCdOggettoOriginale(),"OGGETTODINIEGATO", map);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.ValidazioneOggettiService#getOggetto(java.math.BigDecimal)
	 */
	@Override
	public OggettoDTO getOggetto(BigDecimal cdOggetto) {
		return getOggettoForValidazioneAndPubblicazioneOggetti(cdOggetto);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.ValidazioneOggettiService#getImmaginiOggetto(java.math.BigDecimal)
	 */
	public List<ImmagineDTO> getImmaginiOggetto(BigDecimal cdOggetto) throws AsyncServiceException{
		return getImmaginiOggettoForValidazioneAndPubblicazioneOggetti(cdOggetto);
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.ValidazioneOggettiService#getImmagineReader(java.math.BigDecimal, java.lang.String)
	 */
	public ImmagineDTO getImmagineReader(BigDecimal cdOggetto, String nomeImmagine) throws AsyncServiceException{
		return getImmagineReaderForValidazioneAndPubblicazioneOggetti(cdOggetto, nomeImmagine);
	}
	
	/* (non-Javadoc)
	 * @see it.lispa.bdl.shared.services.ValidazioneOggettiService#getTocSommarioOggetto(java.math.BigDecimal)
	 */
	@Override
	public List<TocSommarioDTO> getTocSommarioOggetto(BigDecimal cdOggettoOriginale){
		return tocSommarioUtils.getTocSommarioOggetto(cdOggettoOriginale);
	}
}
