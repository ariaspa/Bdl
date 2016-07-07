package it.lispa.bdl.shared.services;


import it.lispa.bdl.shared.dto.SubMenuItemDTO;
import it.lispa.bdl.shared.dto.VOggettoCountDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

/**
 * Interface VOggettiServiceAsync.
 */
public interface VOggettiServiceAsync {

	/**
	 * Legge tree items for supervisore.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statoOggetto  stato oggetto
	 * @param callback  callback
	 * @return tree items for supervisore
	 */
	void getTreeItemsForSupervisore(String statoProgetto, String statoOggetto, AsyncCallback<List<SubMenuItemDTO>> callback);
	
	/**
	 * Legge tree items for digitalizzatore.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statoOggetto  stato oggetto
	 * @param callback  callback
	 * @return tree items for digitalizzatore
	 */
	void getTreeItemsForDigitalizzatore(String statoProgetto, String statoOggetto, AsyncCallback<List<SubMenuItemDTO>> callback);
	
	/**
	 * Legge tree items for catalogatore.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statoOggetto  stato oggetto
	 * @param callback  callback
	 * @return tree items for catalogatore
	 */
	void getTreeItemsForCatalogatore(String statoProgetto, String statoOggetto, AsyncCallback<List<SubMenuItemDTO>> callback);

	/**
	 * Legge tree items for supervisore.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param callback  callback
	 * @return tree items for supervisore
	 */
	void getTreeItemsForSupervisore(String statoProgetto, List<String> statiOggetto, AsyncCallback<List<SubMenuItemDTO>> callback);
	
	/**
	 * Legge tree items for digitalizzatore.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param callback  callback
	 * @return tree items for digitalizzatore
	 */
	void getTreeItemsForDigitalizzatore(String statoProgetto, List<String> statiOggetto, AsyncCallback<List<SubMenuItemDTO>> callback);
	
	/**
	 * Legge tree items for catalogatore.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param callback  callback
	 * @return tree items for catalogatore
	 */
	void getTreeItemsForCatalogatore(String statoProgetto, List<String> statiOggetto, AsyncCallback<List<SubMenuItemDTO>> callback);

	/**
	 * Legge tree items for supervisore.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @param callback  callback
	 * @return tree items for supervisore
	 */
	void getTreeItemsForSupervisore(String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag, AsyncCallback<List<SubMenuItemDTO>> callback);
	
	/**
	 * Legge tree items for digitalizzatore.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @param callback  callback
	 * @return tree items for digitalizzatore
	 */
	void getTreeItemsForDigitalizzatore(String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag, AsyncCallback<List<SubMenuItemDTO>> callback);
	
	/**
	 * Legge tree items for catalogatore.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @param callback  callback
	 * @return tree items for catalogatore
	 */
	void getTreeItemsForCatalogatore(String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag, AsyncCallback<List<SubMenuItemDTO>> callback);
	
	/**
	 * Legge grid items for supervisore.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statoOggetto  stato oggetto
	 * @param filterIstituto  filter istituto
	 * @param filterProgetto  filter progetto
	 * @param filterCollezione  filter collezione
	 * @param filters  filters
	 * @param sortField  sort field
	 * @param orderDir  order dir
	 * @param myOffset  my offset
	 * @param myLimit  my limit
	 * @param callback  callback
	 * @return grid items for supervisore
	 */
	void getGridItemsForSupervisore(String statoProgetto, String statoOggetto, BigDecimal filterIstituto, BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit, AsyncCallback<PagingLoadResult<VOggettoDTO>> callback);
	
	/**
	 * Legge grid items for digitalizzatore.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statoOggetto  stato oggetto
	 * @param filterIstituto  filter istituto
	 * @param filterProgetto  filter progetto
	 * @param filterCollezione  filter collezione
	 * @param filters  filters
	 * @param sortField  sort field
	 * @param orderDir  order dir
	 * @param myOffset  my offset
	 * @param myLimit  my limit
	 * @param callback  callback
	 * @return grid items for digitalizzatore
	 */
	void getGridItemsForDigitalizzatore(String statoProgetto, String statoOggetto, BigDecimal filterIstituto, BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit, AsyncCallback<PagingLoadResult<VOggettoDTO>> callback);
	
	/**
	 * Legge grid items for catalogatore.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statoOggetto  stato oggetto
	 * @param filterProgetto  filter progetto
	 * @param filterCollezione  filter collezione
	 * @param filters  filters
	 * @param sortField  sort field
	 * @param orderDir  order dir
	 * @param myOffset  my offset
	 * @param myLimit  my limit
	 * @param callback  callback
	 * @return grid items for catalogatore
	 */
	void getGridItemsForCatalogatore(String statoProgetto, String statoOggetto, BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit, AsyncCallback<PagingLoadResult<VOggettoDTO>> callback);

	/**
	 * Legge grid items for supervisore.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filterIstituto  filter istituto
	 * @param filterProgetto  filter progetto
	 * @param filterCollezione  filter collezione
	 * @param filters  filters
	 * @param sortField  sort field
	 * @param orderDir  order dir
	 * @param myOffset  my offset
	 * @param myLimit  my limit
	 * @param callback  callback
	 * @return grid items for supervisore
	 */
	void getGridItemsForSupervisore(String statoProgetto, List<String> statiOggetto, BigDecimal filterIstituto, BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit, AsyncCallback<PagingLoadResult<VOggettoDTO>> callback);
	
	/**
	 * Legge grid items for digitalizzatore.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filterIstituto  filter istituto
	 * @param filterProgetto  filter progetto
	 * @param filterCollezione  filter collezione
	 * @param filters  filters
	 * @param sortField  sort field
	 * @param orderDir  order dir
	 * @param myOffset  my offset
	 * @param myLimit  my limit
	 * @param callback  callback
	 * @return grid items for digitalizzatore
	 */
	void getGridItemsForDigitalizzatore(String statoProgetto, List<String> statiOggetto, BigDecimal filterIstituto, BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit, AsyncCallback<PagingLoadResult<VOggettoDTO>> callback);
	
	/**
	 * Legge grid items for catalogatore.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filterProgetto  filter progetto
	 * @param filterCollezione  filter collezione
	 * @param filters  filters
	 * @param sortField  sort field
	 * @param orderDir  order dir
	 * @param myOffset  my offset
	 * @param myLimit  my limit
	 * @param callback  callback
	 * @return grid items for catalogatore
	 */
	void getGridItemsForCatalogatore(String statoProgetto, List<String> statiOggetto, BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit, AsyncCallback<PagingLoadResult<VOggettoDTO>> callback);

	/**
	 * Legge grid items for supervisore.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @param filterIstituto  filter istituto
	 * @param filterProgetto  filter progetto
	 * @param filterCollezione  filter collezione
	 * @param filters  filters
	 * @param sortField  sort field
	 * @param orderDir  order dir
	 * @param myOffset  my offset
	 * @param myLimit  my limit
	 * @param callback  callback
	 * @return grid items for supervisore
	 */
	void getGridItemsForSupervisore(String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag, BigDecimal filterIstituto, BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit, AsyncCallback<PagingLoadResult<VOggettoDTO>> callback);
	
	/**
	 * Legge grid items for digitalizzatore.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @param filterIstituto  filter istituto
	 * @param filterProgetto  filter progetto
	 * @param filterCollezione  filter collezione
	 * @param filters  filters
	 * @param sortField  sort field
	 * @param orderDir  order dir
	 * @param myOffset  my offset
	 * @param myLimit  my limit
	 * @param callback  callback
	 * @return grid items for digitalizzatore
	 */
	void getGridItemsForDigitalizzatore(String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag, BigDecimal filterIstituto, BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit, AsyncCallback<PagingLoadResult<VOggettoDTO>> callback);
	
	/**
	 * Legge grid items for catalogatore.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @param filterProgetto  filter progetto
	 * @param filterCollezione  filter collezione
	 * @param filters  filters
	 * @param sortField  sort field
	 * @param orderDir  order dir
	 * @param myOffset  my offset
	 * @param myLimit  my limit
	 * @param callback  callback
	 * @return grid items for catalogatore
	 */
	void getGridItemsForCatalogatore(String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag, BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit, AsyncCallback<PagingLoadResult<VOggettoDTO>> callback);

	/**
	 * Legge grid critici.
	 *
	 * @param filterIstituto  filter istituto
	 * @param filterProgetto  filter progetto
	 * @param filterCollezione  filter collezione
	 * @param filters  filters
	 * @param sortField  sort field
	 * @param orderDir  order dir
	 * @param myOffset  my offset
	 * @param myLimit  my limit
	 * @param callback  callback
	 * @return grid critici
	 */
	void getGridCritici(BigDecimal filterIstituto, BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit, AsyncCallback<PagingLoadResult<VOggettoDTO>> callback);
	
	/**
	 * Legge tree critici.
	 *
	 * @param callback  callback
	 * @return tree critici
	 */
	void getTreeCritici(AsyncCallback<List<SubMenuItemDTO>> callback);
	
	/**
	 * Legge grid counters.
	 *
	 * @param filterIstituto  filter istituto
	 * @param filterProgetto  filter progetto
	 * @param filterCollezione  filter collezione
	 * @param callback  callback
	 * @return grid counters
	 */
	void getGridCounters(BigDecimal filterIstituto, BigDecimal filterProgetto, BigDecimal filterCollezione,AsyncCallback<List<VOggettoCountDTO>> callback);
	
	/**
	 * Legge tree counters.
	 *
	 * @param callback  callback
	 * @return tree counters
	 */
	void getTreeCounters(AsyncCallback<List<SubMenuItemDTO>> callback);
}
