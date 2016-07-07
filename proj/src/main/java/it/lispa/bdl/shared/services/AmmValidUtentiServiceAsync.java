package it.lispa.bdl.shared.services;

import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.UtenteDTO;

import java.math.BigDecimal;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

/**
 * Interface AmmValidUtentiServiceAsync.
 */
public interface AmmValidUtentiServiceAsync {
	
	/**
	 * Legge utenti da validare.
	 *
	 * @param filters  filters
	 * @param sortField  sort field
	 * @param orderDir  order dir
	 * @param myOffset  my offset
	 * @param myLimit  my limit
	 * @param callback  callback
	 * @return utenti da validare
	 */
	void getUtentiDaValidare(List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit, AsyncCallback<PagingLoadResult<UtenteDTO>> callback);

	/**
	 * Valida utente.
	 *
	 * @param cdUtente  codice utente
	 * @param callback  callback
	 */
	void validaUtente(BigDecimal cdUtente, AsyncCallback<Void> callback);
	
	/**
	 * Valida utenti.
	 *
	 * @param utenti  utenti
	 * @param callback  callback
	 */
	void validaUtenti(List<UtenteDTO> utenti, AsyncCallback<Void> callback);
	
	/**
	 * Legge ruoli.
	 *
	 * @param callback  callback
	 * @return ruoli
	 */
	void getRuoli(AsyncCallback<List<ComboDTO>> callback);
	
}
