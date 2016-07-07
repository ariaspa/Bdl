package it.lispa.bdl.shared.services;

import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.UtenteDTO;
import it.lispa.bdl.shared.dto.UtenteLightDTO;

import java.math.BigDecimal;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

/**
 * Interface AmmGestUtentiServiceAsync.
 */
public interface AmmGestUtentiServiceAsync {
	
	/* METODI CRUD STANDARD */
	/**
	 * Legge item by codice.
	 *
	 * @param cdItem  codice item
	 * @param callback  callback
	 * @return item by codice
	 */
	void getItemByCodice(BigDecimal cdItem, AsyncCallback<UtenteDTO> callback);
	
	/**
	 * Legge items.
	 *
	 * @param filters  filters
	 * @param orderField  order field
	 * @param orderDir  order dir
	 * @param myOffset  my offset
	 * @param myLimit  my limit
	 * @param orderFieldDefault  order field default
	 * @param orderDirDefault  order dir default
	 * @param callback  callback
	 * @return items
	 */
	void getItems(List<FilterConfig> filters, String orderField, String orderDir, Integer myOffset, Integer myLimit,String orderFieldDefault,
			String orderDirDefault, AsyncCallback<PagingLoadResult<UtenteLightDTO>> callback);
	
	/**
	 * Cancella items.
	 *
	 * @param items  items
	 * @param callback  callback
	 */
	void cancellaItems(List<UtenteLightDTO> items, AsyncCallback<Void> callback);
	
	/**
	 * Cancella item by codice.
	 *
	 * @param cdItem  codice item
	 * @param callback  callback
	 */
	void cancellaItemByCodice(BigDecimal cdItem, AsyncCallback<Void> callback);
	
	/**
	 * Salva item.
	 *
	 * @param item  item
	 * @param callback  callback
	 */
	void salvaItem(UtenteDTO item, AsyncCallback<UtenteDTO> callback);

	/* METODI CUSTOM */
	/**
	 * Legge ruoli.
	 *
	 * @param callback  callback
	 * @return ruoli
	 */
	void getRuoli(AsyncCallback<List<ComboDTO>> callback);
	
	/**
	 * Legge digitalizzatori.
	 *
	 * @param callback  callback
	 * @return digitalizzatori
	 */
	void getDigitalizzatori(AsyncCallback<List<ComboDTO>> callback);
	
	/**
	 * Legge istituti.
	 *
	 * @param callback  callback
	 * @return istituti
	 */
	void getIstituti(AsyncCallback<List<ComboDTO>> callback);

}
