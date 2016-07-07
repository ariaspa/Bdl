package it.lispa.bdl.shared.services;

import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.ComboStringDTO;
import it.lispa.bdl.shared.dto.EnteDTO;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

/**
 * Interface AmmGestEntiService.
 */
@RemoteServiceRelativePath("../"+BdlSharedConstants.BDL_SERVICE_PATH_PRIVATE+"/services/ammGestEntiService")
public interface AmmGestEntiService extends RemoteService {

	/* METODI CRUD STANDARD */
	/**
	 * Legge item by codice.
	 *
	 * @param cdItem  codice item
	 * @return item by codice
	 * @throws AsyncServiceException async service exception
	 */
	EnteDTO getItemByCodice(BigDecimal cdItem) throws AsyncServiceException;
	
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
	PagingLoadResult<EnteDTO> getItems(List<FilterConfig> filters, String orderField, String orderDir, Integer myOffset, Integer myLimit,String orderFieldDefault,
			String orderDirDefault);
	
	/**
	 * Cancella items.
	 *
	 * @param items  items
	 * @throws AsyncServiceException async service exception
	 */
	void cancellaItems(List<EnteDTO> items) throws AsyncServiceException;
	
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
	 * @return ente dto
	 * @throws AsyncServiceException async service exception
	 */
	EnteDTO salvaItem(EnteDTO item) throws AsyncServiceException;

	/* METODI CUSTOM */
	/**
	 * Legge province.
	 *
	 * @return province
	 */
	List<ComboStringDTO> getProvince();
	
	/**
	 * Legge digitalizzatori.
	 *
	 * @return digitalizzatori
	 */
	List<ComboDTO> getDigitalizzatori();

}
