package it.lispa.bdl.shared.services;

import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Interface AmmGestIndexService.
 */
@RemoteServiceRelativePath("../"+BdlSharedConstants.BDL_SERVICE_PATH_PRIVATE+"/services/ammGestIndexService")
public interface AmmGestIndexService extends RemoteService {

	/* METODI CRUD STANDARD */
	/**
	 * Legge item by codice.
	 *
	 * @param cdItem  codice item
	 * @return item by codice
	 * @throws AsyncServiceException async service exception
	 */
	OggettoDTO getItemByCodice(BigDecimal cdItem) throws AsyncServiceException;
	
	/**
	 * Cancella items.
	 *
	 * @param items  items
	 * @throws AsyncServiceException async service exception
	 */
	void cancellaItems(List<VOggettoDTO> items) throws AsyncServiceException;
	
	/**
	 * Cancella item by codice.
	 *
	 * @param cdItem  codice item
	 * @throws AsyncServiceException async service exception
	 */
	void cancellaItemByCodice(BigDecimal cdItem) throws AsyncServiceException;
	
	/**
	 * Salva item.
	 *
	 * @param item  item
	 * @return oggetto dto
	 * @throws AsyncServiceException async service exception
	 */
	OggettoDTO salvaItem(OggettoDTO item) throws AsyncServiceException;

	/* METODI CUSTOM */
	/**
	 * Legge tipologia oggetti.
	 *
	 * @return tipologia oggetti
	 */
	List<ComboDTO> getTipologiaOggetti();
	
	/**
	 * Legge oggetti superiori.
	 *
	 * @return oggetti superiori
	 * @throws AsyncServiceException async service exception
	 */
	List<ComboDTO> getOggettiSuperiori() throws AsyncServiceException;
}
