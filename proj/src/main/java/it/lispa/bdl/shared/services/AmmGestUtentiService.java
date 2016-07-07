package it.lispa.bdl.shared.services;

import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.UtenteDTO;
import it.lispa.bdl.shared.dto.UtenteLightDTO;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

/**
 * Interface AmmGestUtentiService.
 */
@RemoteServiceRelativePath("../"+BdlSharedConstants.BDL_SERVICE_PATH_PRIVATE+"/services/ammGestUtentiService")
public interface AmmGestUtentiService extends RemoteService {

	/* METODI CRUD STANDARD */
	/**
	 * Legge item by codice.
	 *
	 * @param cdItem  codice item
	 * @return item by codice
	 * @throws AsyncServiceException async service exception
	 */
	UtenteDTO getItemByCodice(BigDecimal cdItem) throws AsyncServiceException;
	
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
	 * @return items
	 */
	PagingLoadResult<UtenteLightDTO> getItems(List<FilterConfig> filters, String orderField, String orderDir, Integer myOffset, Integer myLimit,String orderFieldDefault,
			String orderDirDefault);
	
	/**
	 * Cancella items.
	 *
	 * @param items  items
	 * @throws AsyncServiceException async service exception
	 */
	void cancellaItems(List<UtenteLightDTO> items) throws AsyncServiceException;
	
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
	 * @return utente dto
	 * @throws AsyncServiceException async service exception
	 */
	UtenteDTO salvaItem(UtenteDTO item) throws AsyncServiceException;

	/* METODI CUSTOM */
	/**
	 * Legge ruoli.
	 *
	 * @return ruoli
	 */
	List<ComboDTO> getRuoli();
	
	/**
	 * Legge digitalizzatori.
	 *
	 * @return digitalizzatori
	 */
	List<ComboDTO> getDigitalizzatori();
	
	/**
	 * Legge istituti.
	 *
	 * @return istituti
	 */
	List<ComboDTO> getIstituti();

}
