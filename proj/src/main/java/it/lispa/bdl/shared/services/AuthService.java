package it.lispa.bdl.shared.services;

import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Interface AuthService.
 */
@RemoteServiceRelativePath("../"+BdlSharedConstants.BDL_SERVICE_PATH_PRIVATE+"/services/authService")
public interface AuthService extends RemoteService {
	
	/**
	 * Legge header string.
	 *
	 * @return header string
	 */
	public String getHeaderString();
	
	/**
	 * Legge enti attivabili cmb catalogatore.
	 *
	 * @return enti attivabili cmb catalogatore
	 */
	public List<ComboDTO> getEntiAttivabiliCmbCatalogatore();
	
	/**
	 * Activate ente.
	 *
	 * @param cdEnte  codice ente
	 * @return string
	 */
	public String activateEnte(BigDecimal cdEnte);
}
