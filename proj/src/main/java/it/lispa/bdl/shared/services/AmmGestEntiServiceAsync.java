package it.lispa.bdl.shared.services;


import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.ComboStringDTO;
import it.lispa.bdl.shared.dto.EnteDTO;

import java.math.BigDecimal;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

/**
 * Interface AmmGestEntiServiceAsync.
 */
public interface AmmGestEntiServiceAsync {

	/* METODI CRUD STANDARD */
	/**
	 * Legge item by codice.
	 *
	 * @param cdItem  codice item
	 * @param callback  callback
	 * @return item by codice
	 */
	void getItemByCodice(BigDecimal cdItem, AsyncCallback<EnteDTO> callback);
	
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
			String orderDirDefault, AsyncCallback<PagingLoadResult<EnteDTO>> callback);
	
	/**
	 * Cancella items.
	 *
	 * @param items  items
	 * @param callback  callback
	 */
	void cancellaItems(List<EnteDTO> items, AsyncCallback<Void> callback);
	
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
	void salvaItem(EnteDTO item, AsyncCallback<EnteDTO> callback);

	/* METODI CUSTOM */
	/**
	 * Legge province.
	 *
	 * @param callback  callback
	 * @return province
	 */
	void getProvince(AsyncCallback<List<ComboStringDTO>> callback);
	
	/**
	 * Legge digitalizzatori.
	 *
	 * @param callback  callback
	 * @return digitalizzatori
	 */
	void getDigitalizzatori(AsyncCallback<List<ComboDTO>> callback);

}
