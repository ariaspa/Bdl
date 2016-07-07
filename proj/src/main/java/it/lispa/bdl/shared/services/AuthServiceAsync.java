package it.lispa.bdl.shared.services;

import it.lispa.bdl.shared.dto.ComboDTO;

import java.math.BigDecimal;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Interface AuthServiceAsync.
 */
public interface AuthServiceAsync {

	/**
	 * Legge header string.
	 *
	 * @param callback  callback
	 * @return header string
	 */
	void getHeaderString(AsyncCallback<String> callback);
	
	/**
	 * Legge enti attivabili cmb catalogatore.
	 *
	 * @param callback  callback
	 * @return enti attivabili cmb catalogatore
	 */
	void getEntiAttivabiliCmbCatalogatore(AsyncCallback<List<ComboDTO>> callback);
	
	/**
	 * Activate ente.
	 *
	 * @param cdEnte  codice ente
	 * @param callback  callback
	 */
	void activateEnte(BigDecimal cdEnte, AsyncCallback<String> callback);
	
}
