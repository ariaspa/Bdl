package it.lispa.bdl.shared.services;

import it.lispa.bdl.shared.dto.VOggettoDTO;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Interface VerificaOggettiService.
 */
@RemoteServiceRelativePath("../"+BdlSharedConstants.BDL_SERVICE_PATH_PRIVATE+"/services/verificaOggettiService")
public interface VerificaOggettiService extends RemoteService {
	
	/**
	 * Verifica oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @throws AsyncServiceException async service exception
	 */
	public void verificaOggetto(BigDecimal cdOggetto) throws AsyncServiceException;
	
	/**
	 * Verifica oggetti.
	 *
	 * @param oggetti  oggetti
	 * @throws AsyncServiceException async service exception
	 */
	public void verificaOggetti(List<VOggettoDTO> oggetti) throws AsyncServiceException;
	
	/**
	 * Rifiuta oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @throws AsyncServiceException async service exception
	 */
	public void rifiutaOggetto(BigDecimal cdOggetto) throws AsyncServiceException;
}
