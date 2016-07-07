package it.lispa.bdl.shared.services;

import it.lispa.bdl.shared.dto.SchedulatoreJobsDTO;

import java.math.BigDecimal;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

/**
 * Interface AmmGestUtentiServiceAsync.
 */
public interface SchedulatoreJobsServiceAsync {
	
	/* METODI CRUD STANDARD */
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
			String orderDirDefault, AsyncCallback<PagingLoadResult<SchedulatoreJobsDTO>> callback);
	
	/**
	 * Cancella items.
	 *
	 * @param items  items
	 * @param callback  callback
	 */
	void cancellaItems(List<SchedulatoreJobsDTO> items, AsyncCallback<Void> callback);
	
	/**
	 * Cancella item by codice.
	 *
	 * @param cdItem  codice item
	 * @param callback  callback
	 */
	void cancellaItemByCodice(BigDecimal cdItem, AsyncCallback<Void> callback);
	
	/* METODI CUSTOM */
	
}
