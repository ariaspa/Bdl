package it.lispa.bdl.shared.services;

import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.UtenteDTO;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Interface ExternalService.
 */
@RemoteServiceRelativePath("../"+BdlSharedConstants.BDL_SERVICE_PATH_PUBLIC+"/services/externalService")
public interface ExternalService extends RemoteService {
	
	/**
	 * Controlla se user online.
	 *
	 * @return string
	 */
	public String isUserOnline();
	
	/**
	 * Legge ruoli registrabili.
	 *
	 * @return ruoli registrabili
	 */
	public List<ComboDTO> getRuoliRegistrabili();
	
	/**
	 * Legge enti.
	 *
	 * @param ruolo  ruolo
	 * @return enti
	 */
	public List<ComboDTO> getEnti(BigDecimal ruolo);
	
	/**
	 * Registra utente.
	 *
	 * @param user  user
	 * @throws AsyncServiceException async service exception
	 */
	void registraUtente(UtenteDTO user) throws AsyncServiceException;
	
	/**
	 * Legge user email.
	 *
	 * @return user email
	 */
	public String getUserEmail();
	
	/**
	 * Legge idpc url login.
	 *
	 * @return idpc url login
	 */
	public String getIdpcUrlLogin();
	
	/**
	 * Legge idpc url logout.
	 *
	 * @return idpc url logout
	 */
	public String getIdpcUrlLogout();
}
