package it.lispa.bdl.shared.services;

import it.lispa.bdl.shared.dto.CronologiaDTO;
import it.lispa.bdl.shared.dto.ImmagineDTO;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.dto.TocSommarioDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Interface ValidazioneOggettiService.
 */
@RemoteServiceRelativePath("../"+BdlSharedConstants.BDL_SERVICE_PATH_PRIVATE+"/services/validazioneOggettiService")
public interface ValidazioneOggettiService extends RemoteService {
	
	/**
	 * Valida oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @throws AsyncServiceException async service exception
	 */
	public void validaOggetto(BigDecimal cdOggetto) throws AsyncServiceException;
	
	/**
	 * Valida oggetti.
	 *
	 * @param oggetti  oggetti
	 * @throws AsyncServiceException async service exception
	 */
	public void validaOggetti(List<VOggettoDTO> oggetti) throws AsyncServiceException;
	
	/**
	 * Rifiuta oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param motivo  motivo
	 * @throws AsyncServiceException async service exception
	 */
	public void rifiutaOggetto(BigDecimal cdOggetto, String motivo) throws AsyncServiceException;
	
	/**
	 * Legge oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @return oggetto
	 */
	public OggettoDTO getOggetto(BigDecimal cdOggetto);
	
	/**
	 * Legge cronologia.
	 *
	 * @param cdOggetto  codice oggetto
	 * @return cronologia
	 * @throws AsyncServiceException async service exception
	 */
	public List<CronologiaDTO> getCronologia(BigDecimal cdOggetto) throws AsyncServiceException;
	
	/**
	 * Legge immagini oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @return immagini oggetto
	 * @throws AsyncServiceException async service exception
	 */
	public List<ImmagineDTO> getImmaginiOggetto(BigDecimal cdOggetto)  throws AsyncServiceException;
	
	/**
	 * Legge toc sommario oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @return toc sommario oggetto
	 * @throws AsyncServiceException async service exception
	 */
	public List<TocSommarioDTO> getTocSommarioOggetto(BigDecimal cdOggetto)  throws AsyncServiceException;
	
	/**
	 * Legge immagine reader.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param nomeImmagine  nome immagine
	 * @return immagine reader
	 * @throws AsyncServiceException async service exception
	 */
	public ImmagineDTO getImmagineReader(BigDecimal cdOggetto, String nomeImmagine)  throws AsyncServiceException;
	
	/**
	 * Legge natura.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @return natura
	 */
	public String getNatura(BigDecimal cdOggettoOriginale);
}
