package it.lispa.bdl.shared.services;

import it.lispa.bdl.shared.dto.VOggettoDTO;

import java.math.BigDecimal;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Interface VerificaOggettiServiceAsync.
 */
public interface VerificaOggettiServiceAsync {

	/**
	 * Verifica oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param callback  callback
	 */
	void verificaOggetto(BigDecimal cdOggetto, AsyncCallback<Void> callback);
	
	/**
	 * Verifica oggetti.
	 *
	 * @param oggetti  oggetti
	 * @param callback  callback
	 */
	void verificaOggetti(List<VOggettoDTO> oggetti, AsyncCallback<Void> callback);
	
	/**
	 * Rifiuta oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param callback  callback
	 */
	void rifiutaOggetto(BigDecimal cdOggetto, AsyncCallback<Void> callback);
	
}
