package it.lispa.bdl.shared.services;

import it.lispa.bdl.shared.dto.ComboDTO;
import it.lispa.bdl.shared.dto.UtenteDTO;

import java.math.BigDecimal;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Interface ExternalServiceAsync.
 */
public interface ExternalServiceAsync {

	/**
	 * Controlla se user online.
	 *
	 * @param callback  callback
	 */
	void isUserOnline( AsyncCallback<String> callback);

	/**
	 * Legge ruoli registrabili.
	 *
	 * @param callback  callback
	 * @return ruoli registrabili
	 */
	void getRuoliRegistrabili(AsyncCallback<List<ComboDTO>> callback);

	/**
	 * Legge enti.
	 *
	 * @param ruolo  ruolo
	 * @param callback  callback
	 * @return enti
	 */
	void getEnti(BigDecimal ruolo, AsyncCallback<List<ComboDTO>> callback);

	/**
	 * Registra utente.
	 *
	 * @param user  user
	 * @param callback  callback
	 */
	void registraUtente(UtenteDTO user, AsyncCallback<Void> callback);

	/**
	 * Legge user email.
	 *
	 * @param callback  callback
	 * @return user email
	 */
	void getUserEmail(AsyncCallback<String> callback);
	
	/**
	 * Legge idpc url login.
	 *
	 * @param callback  callback
	 * @return idpc url login
	 */
	void getIdpcUrlLogin(AsyncCallback<String> callback);
	
	/**
	 * Legge idpc url logout.
	 *
	 * @param callback  callback
	 * @return idpc url logout
	 */
	void getIdpcUrlLogout(AsyncCallback<String> callback);
}
