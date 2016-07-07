package it.lispa.bdl.shared.services;

import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;

import java.math.BigDecimal;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Interface AmmGestIndexServiceAsync.
 */
public interface AmmGestIndexServiceAsync {

	
	/* METODI CRUD STANDARD */
	/**
	 * Legge item by codice.
	 *
	 * @param cdItem  codice item
	 * @param callback  callback
	 * @return item by codice
	 */
	void getItemByCodice(BigDecimal cdItem, AsyncCallback<OggettoDTO> callback);
	
	/**
	 * Cancella items.
	 *
	 * @param items  items
	 * @param callback  callback
	 */
	void cancellaItems(List<VOggettoDTO> items, AsyncCallback<Void> callback);
	
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
	void salvaItem(OggettoDTO item, AsyncCallback<OggettoDTO> callback);

	/* METODI CUSTOM */
	/**
	 * Legge tipologia oggetti.
	 *
	 * @param callback  callback
	 * @return tipologia oggetti
	 */
	void getTipologiaOggetti(AsyncCallback<List<ComboDTO>> callback);
	
	/**
	 * Legge oggetti superiori.
	 *
	 * @param callback  callback
	 * @return oggetti superiori
	 */
	void getOggettiSuperiori(AsyncCallback<List<ComboDTO>> callback);
	
}
