package it.lispa.bdl.shared.services;

import it.lispa.bdl.shared.dto.VOggettoDTO;

import java.math.BigDecimal;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Interface CaricamentoImmaginiServiceAsync.
 */
public interface CaricamentoImmaginiServiceAsync {
	
	/**
	 * Carica immagini.
	 * 
	 * @param id
	 * @param nrImmaginiDigitalizzate
	 * @param isCreaImmaginiDerivate
	 * @param callback
	 */
	void caricaImmagini(BigDecimal id, BigDecimal nrImmaginiDigitalizzate, Boolean isCreaImmaginiDerivate, AsyncCallback<Void> callback);
	
	/**
	 * Crea immagini qrtz.
	 *
	 * @param id  id
	 * @param nrImmaginiDigitalizzate  nr immagini digitalizzate
	 * @param cdUtente  codice utente
	 * @param cdDigitalizzatore  codice digitalizzatore
	 * @param callback  callback
	 */
	void creaImmaginiQrtz(BigDecimal id, BigDecimal nrImmaginiDigitalizzate, BigDecimal cdUtente, BigDecimal cdDigitalizzatore, AsyncCallback<String> callback);
	
	void hasNaturaMapOrAudio(BigDecimal cdOggetto, AsyncCallback<Boolean> callback);
	
	/**
	 * Carica immagini qrtz.
	 *
	 * @param id  id
	 * @param nrImmaginiDigitalizzate  nr immagini digitalizzate
	 * @param cdUtente  codice utente
	 * @param cdDigitalizzatore  codice digitalizzatore
	 * @param callback  callback
	 */
	void caricaImmaginiQrtz(BigDecimal id, BigDecimal nrImmaginiDigitalizzate,  BigDecimal cdUtente,  BigDecimal cdDigitalizzatore, AsyncCallback<Void> callback);
	
	/**
	 * Legge oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param callback  callback
	 * @return oggetto
	 */
	void getOggetto(BigDecimal cdOggetto, AsyncCallback<VOggettoDTO> callback);
	
	/**
	 * Controlla se nr immagini visibile.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param callback  callback
	 */
	void isNrImmaginiVisibile(BigDecimal cdOggetto, AsyncCallback<Boolean> callback);
}
