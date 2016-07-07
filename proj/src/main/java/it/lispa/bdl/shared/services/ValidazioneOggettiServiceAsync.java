package it.lispa.bdl.shared.services;

import it.lispa.bdl.shared.dto.CronologiaDTO;
import it.lispa.bdl.shared.dto.ImmagineDTO;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.dto.TocSommarioDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;

import java.math.BigDecimal;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Interface ValidazioneOggettiServiceAsync.
 */
public interface ValidazioneOggettiServiceAsync {

	/**
	 * Valida oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param callback  callback
	 */
	void validaOggetto(BigDecimal cdOggetto, AsyncCallback<Void> callback);
	
	/**
	 * Valida oggetti.
	 *
	 * @param oggetti  oggetti
	 * @param callback  callback
	 */
	void validaOggetti(List<VOggettoDTO> oggetti, AsyncCallback<Void> callback);
	
	/**
	 * Rifiuta oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param motivo  motivo
	 * @param callback  callback
	 */
	void rifiutaOggetto(BigDecimal cdOggetto, String motivo, AsyncCallback<Void> callback);
	
	/**
	 * Legge oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param callback  callback
	 * @return oggetto
	 */
	void getOggetto(BigDecimal cdOggetto, AsyncCallback<OggettoDTO> callback);
	
	/**
	 * Legge cronologia.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param asyncCallback  async callback
	 * @return cronologia
	 */
	void getCronologia(BigDecimal cdOggetto,AsyncCallback<List<CronologiaDTO>> asyncCallback);
	
	/**
	 * Legge immagini oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param asyncCallback  async callback
	 * @return immagini oggetto
	 */
	void getImmaginiOggetto(BigDecimal cdOggetto, AsyncCallback<List<ImmagineDTO>> asyncCallback);
	
	/**
	 * Legge toc sommario oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param asyncCallback  async callback
	 * @return toc sommario oggetto
	 */
	void getTocSommarioOggetto(BigDecimal cdOggetto, AsyncCallback<List<TocSommarioDTO>> asyncCallback);
	
	/**
	 * Legge immagine reader.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param nomeImmagine  nome immagine
	 * @param asyncCallback  async callback
	 * @return immagine reader
	 */
	void getImmagineReader(BigDecimal cdOggetto, String nomeImmagine, AsyncCallback<ImmagineDTO> asyncCallback);
	
	/**
	 * Legge natura.
	 *
	 * @param cdOggettoOriginale  codice oggetto originale
	 * @param callback  callback
	 * @return natura
	 */
	void getNatura(BigDecimal cdOggettoOriginale, AsyncCallback<String> callback);
}
