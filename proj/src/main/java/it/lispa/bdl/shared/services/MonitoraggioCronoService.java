package it.lispa.bdl.shared.services;

import it.lispa.bdl.shared.dto.CronologiaDTO;
import it.lispa.bdl.shared.dto.OggettoDTO;
import it.lispa.bdl.shared.services.exceptions.AsyncServiceException;
import it.lispa.bdl.shared.utils.BdlSharedConstants;

import java.math.BigDecimal;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Interface MonitoraggioCronoService.
 */
@RemoteServiceRelativePath("../"+BdlSharedConstants.BDL_SERVICE_PATH_PRIVATE+"/services/monitoraggioCronoService")
public interface MonitoraggioCronoService extends RemoteService {
	
	/**
	 * Legge cronologia.
	 *
	 * @param cdOggetto  codice oggetto
	 * @return cronologia
	 * @throws AsyncServiceException async service exception
	 */
	public List<CronologiaDTO> getCronologia(BigDecimal cdOggetto) throws AsyncServiceException;

	/**
	 * Legge oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @return oggetto
	 */
	public OggettoDTO getOggetto(BigDecimal cdOggetto);
	
}
