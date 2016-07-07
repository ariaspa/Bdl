package it.lispa.bdl.shared.services;

import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Interface CaricamentoImmaginiService.
 */
@RemoteServiceRelativePath("../"+BdlSharedConstants.BDL_SERVICE_PATH_PRIVATE+"/services/caricamentoImmaginiService")
public interface CaricamentoImmaginiService extends RemoteService {
	
	/**
	 * Carica immagini.
	 * 
	 * @param id
	 * @param nrImmaginiDigitalizzate
	 * @param isCreaImmaginiDerivate
	 * @throws AsyncServiceException
	 * @throws Exception
	 */
	public void caricaImmagini(BigDecimal id, BigDecimal nrImmaginiDigitalizzate, Boolean isCreaImmaginiDerivate) throws AsyncServiceException, Exception;
	
	/**
	 * Crea immagini.
	 * 
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @param nrImmaginiDigitalizzate  nr immagini digitalizzate
	 * @param cdUtente  codice utente
	 * @param cdDigitalizzatore  codice digitalizzatore
	 * @throws AsyncServiceException async service exception
	 * @throws Exception exception
	 */
	public String creaImmaginiQrtz(BigDecimal cdOggettoOriginale, BigDecimal nrImmaginiDigitalizzate, BigDecimal cdUtente, BigDecimal cdDigitalizzatore) throws AsyncServiceException, Exception;
	
	public Boolean hasNaturaMapOrAudio(BigDecimal cdOggetto) throws AsyncServiceException;
	
	/**
	 * Carica immagini qrtz.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @param nrImmaginiDigitalizzate  nr immagini digitalizzate
	 * @param cdUtente  codice utente
	 * @param cdDigitalizzatore  codice digitalizzatore
	 * @throws AsyncServiceException async service exception
	 * @throws Exception exception
	 */
	public void caricaImmaginiQrtz(BigDecimal cdOggettoOriginale, BigDecimal nrImmaginiDigitalizzate, BigDecimal cdUtente, BigDecimal cdDigitalizzatore) throws AsyncServiceException, Exception;
	
	/**
	 * Legge oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @return oggetto
	 * @throws AsyncServiceException async service exception
	 */
	VOggettoDTO getOggetto(BigDecimal cdOggetto) throws AsyncServiceException;
	
	/**
	 * Controlla se nr immagini visibile.
	 *
	 * @param cdOggetto  codice oggetto
	 * @return boolean
	 * @throws AsyncServiceException async service exception
	 */
	Boolean isNrImmaginiVisibile(BigDecimal cdOggetto) throws AsyncServiceException;
}
