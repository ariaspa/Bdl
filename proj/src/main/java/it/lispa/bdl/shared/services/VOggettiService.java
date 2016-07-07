package it.lispa.bdl.shared.services;

import it.lispa.bdl.shared.dto.SubMenuItemDTO;
import it.lispa.bdl.shared.dto.VOggettoCountDTO;
import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

/**
 * Interface VOggettiService.
 */
@RemoteServiceRelativePath("../"+BdlSharedConstants.BDL_SERVICE_PATH_PRIVATE+"/services/vOggettiService")
public interface VOggettiService extends RemoteService {
	
	/**
	 * Legge tree items for supervisore.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statoOggetto  stato oggetto
	 * @return tree items for supervisore
	 * @throws AsyncServiceException async service exception
	 */
	public List<SubMenuItemDTO> getTreeItemsForSupervisore(String statoProgetto, String statoOggetto) throws AsyncServiceException;
	
	/**
	 * Legge tree items for digitalizzatore.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statoOggetto  stato oggetto
	 * @return tree items for digitalizzatore
	 */
	public List<SubMenuItemDTO> getTreeItemsForDigitalizzatore(String statoProgetto, String statoOggetto);
	
	/**
	 * Legge tree items for catalogatore.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statoOggetto  stato oggetto
	 * @return tree items for catalogatore
	 */
	public List<SubMenuItemDTO> getTreeItemsForCatalogatore(String statoProgetto, String statoOggetto);

	/**
	 * Legge tree items for supervisore.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @return tree items for supervisore
	 * @throws AsyncServiceException async service exception
	 */
	public List<SubMenuItemDTO> getTreeItemsForSupervisore(String statoProgetto, List<String> statiOggetto) throws AsyncServiceException;
	
	/**
	 * Legge tree items for digitalizzatore.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @return tree items for digitalizzatore
	 */
	public List<SubMenuItemDTO> getTreeItemsForDigitalizzatore(String statoProgetto, List<String> statiOggetto);
	
	/**
	 * Legge tree items for catalogatore.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @return tree items for catalogatore
	 */
	public List<SubMenuItemDTO> getTreeItemsForCatalogatore(String statoProgetto, List<String> statiOggetto);

	/**
	 * Legge tree items for supervisore.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @return tree items for supervisore
	 * @throws AsyncServiceException async service exception
	 */
	public List<SubMenuItemDTO> getTreeItemsForSupervisore(String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag) throws AsyncServiceException;
	
	/**
	 * Legge tree items for digitalizzatore.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @return tree items for digitalizzatore
	 */
	public List<SubMenuItemDTO> getTreeItemsForDigitalizzatore(String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag);
	
	/**
	 * Legge tree items for catalogatore.
	 *
	 * @param statoProgetto  stato progetto
	 * @param statiOggetto  stati oggetto
	 * @param filtriFlag  filtri flag
	 * @return tree items for catalogatore
	 */
	public List<SubMenuItemDTO> getTreeItemsForCatalogatore(String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag);

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
	 * @return grid items for supervisore
	 * @throws AsyncServiceException async service exception
	 */
	public PagingLoadResult<VOggettoDTO> getGridItemsForSupervisore(String statoProgetto, String statoOggetto, BigDecimal filterIstituto, BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit) throws AsyncServiceException;
	
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
	 * @return grid items for digitalizzatore
	 */
	public PagingLoadResult<VOggettoDTO> getGridItemsForDigitalizzatore(String statoProgetto, String statoOggetto, BigDecimal filterIstituto, BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit);
	
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
	 * @return grid items for catalogatore
	 */
	public PagingLoadResult<VOggettoDTO> getGridItemsForCatalogatore(String statoProgetto, String statoOggetto, BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit);

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
	 * @return grid items for supervisore
	 * @throws AsyncServiceException async service exception
	 */
	public PagingLoadResult<VOggettoDTO> getGridItemsForSupervisore(String statoProgetto, List<String> statiOggetto, BigDecimal filterIstituto, BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit) throws AsyncServiceException;
	
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
	 * @return grid items for digitalizzatore
	 */
	public PagingLoadResult<VOggettoDTO> getGridItemsForDigitalizzatore(String statoProgetto, List<String> statiOggetto, BigDecimal filterIstituto, BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit);
	
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
	 * @return grid items for catalogatore
	 */
	public PagingLoadResult<VOggettoDTO> getGridItemsForCatalogatore(String statoProgetto, List<String> statiOggetto, BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit);

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
	 * @return grid items for supervisore
	 * @throws AsyncServiceException async service exception
	 */
	public PagingLoadResult<VOggettoDTO> getGridItemsForSupervisore(String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag, BigDecimal filterIstituto, BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit) throws AsyncServiceException;
	
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
	 * @return grid items for digitalizzatore
	 */
	public PagingLoadResult<VOggettoDTO> getGridItemsForDigitalizzatore(String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag, BigDecimal filterIstituto, BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit);
	
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
	 * @return grid items for catalogatore
	 */
	public PagingLoadResult<VOggettoDTO> getGridItemsForCatalogatore(String statoProgetto, List<String> statiOggetto, Map<String,Boolean> filtriFlag, BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit);


	/**
	 * Legge tree critici.
	 *
	 * @return tree critici
	 * @throws AsyncServiceException async service exception
	 */
	public List<SubMenuItemDTO> getTreeCritici() throws AsyncServiceException;
	
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
	 * @return grid critici
	 * @throws AsyncServiceException async service exception
	 */
	public PagingLoadResult<VOggettoDTO> getGridCritici(BigDecimal filterIstituto, BigDecimal filterProgetto, BigDecimal filterCollezione, List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit) throws AsyncServiceException;

	/**
	 * Legge tree counters.
	 *
	 * @return tree counters
	 * @throws AsyncServiceException async service exception
	 */
	public List<SubMenuItemDTO> getTreeCounters() throws AsyncServiceException;
	
	/**
	 * Legge grid counters.
	 *
	 * @param filterIstituto  filter istituto
	 * @param filterProgetto  filter progetto
	 * @param filterCollezione  filter collezione
	 * @return grid counters
	 * @throws AsyncServiceException async service exception
	 */
	public List<VOggettoCountDTO> getGridCounters(BigDecimal filterIstituto, BigDecimal filterProgetto, BigDecimal filterCollezione) throws AsyncServiceException;
	
}
