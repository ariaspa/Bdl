package it.lispa.bdl.shared.services;

import it.lispa.bdl.shared.dto.SchedulatoreJobsDTO;
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
@RemoteServiceRelativePath("../"+BdlSharedConstants.BDL_SERVICE_PATH_PRIVATE+"/services/schedulatoreJobsService")
public interface SchedulatoreJobsService extends RemoteService {

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
	 * @return items
	 */
	PagingLoadResult<SchedulatoreJobsDTO> getItems(List<FilterConfig> filters, String orderField, String orderDir, Integer myOffset, Integer myLimit,String orderFieldDefault,
			String orderDirDefault);
	
	/**
	 * Cancella items.
	 *
	 * @param items  items
	 * @throws AsyncServiceException async service exception
	 */
	void cancellaItems(List<SchedulatoreJobsDTO> items) throws AsyncServiceException;
	
	/**
	 * Cancella item by codice.
	 *
	 * @param cdItem  codice item
	 * @throws AsyncServiceException async service exception
	 */
	void cancellaItemByCodice(BigDecimal cdItem) throws AsyncServiceException;

	/* METODI CUSTOM */
}
