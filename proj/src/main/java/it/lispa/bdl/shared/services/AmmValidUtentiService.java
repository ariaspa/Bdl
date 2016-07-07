package it.lispa.bdl.shared.services;

import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.UtenteDTO;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

/**
 * Interface AmmValidUtentiService.
 */
@RemoteServiceRelativePath("../"+BdlSharedConstants.BDL_SERVICE_PATH_PRIVATE+"/services/ammValidUtentiService")
public interface AmmValidUtentiService extends RemoteService {
	
	/**
	 * Legge utenti da validare.
	 *
	 * @param filters  filters
	 * @param sortField  sort field
	 * @param orderDir  order dir
	 * @param myOffset  my offset
	 * @param myLimit  my limit
	 * @return utenti da validare
	 */
	public PagingLoadResult<UtenteDTO> getUtentiDaValidare(List<FilterConfig> filters, String sortField, String orderDir, Integer myOffset, Integer myLimit);
	
	/**
	 * Valida utente.
	 *
	 * @param cdUtente  codice utente
	 * @throws AsyncServiceException async service exception
	 */
	public void validaUtente(BigDecimal cdUtente) throws AsyncServiceException;
	
	/**
	 * Valida utenti.
	 *
	 * @param utenti  utenti
	 * @throws AsyncServiceException async service exception
	 */
	public void validaUtenti(List<UtenteDTO> utenti) throws AsyncServiceException;
	
	/**
	 * Legge ruoli.
	 *
	 * @return ruoli
	 */
	public List<ComboDTO> getRuoli();
}
