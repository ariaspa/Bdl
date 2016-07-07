package it.lispa.bdl.shared.services;

import it.lispa.bdl.shared.dto.CronologiaDTO;
import it.lispa.bdl.shared.dto.OggettoDTO;

import java.math.BigDecimal;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Interface MonitoraggioCronoServiceAsync.
 */
public interface MonitoraggioCronoServiceAsync {

	/**
	 * Legge cronologia.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param asyncCallback  async callback
	 * @return cronologia
	 */
	void getCronologia(BigDecimal cdOggetto,AsyncCallback<List<CronologiaDTO>> asyncCallback);
	
	/**
	 * Legge oggetto.
	 *
	 * @param cdOggetto  codice oggetto
	 * @param asyncCallback  async callback
	 * @return oggetto
	 */
	void getOggetto(BigDecimal cdOggetto,AsyncCallback<OggettoDTO> asyncCallback);
	
}
