package it.lispa.bdl.shared.services;

import it.lispa.bdl.shared.dto.MenuItemDTO;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Interface MenuServiceAsync.
 */
public interface MenuServiceAsync {
	 
	/**
	 * Find funzioni accessibili.
	 *
	 * @param callback  callback
	 */
	void findFunzioniAccessibili(AsyncCallback<List<MenuItemDTO>> callback);
}
