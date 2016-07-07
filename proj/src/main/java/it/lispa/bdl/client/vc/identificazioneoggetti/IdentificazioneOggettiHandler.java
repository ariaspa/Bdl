package it.lispa.bdl.client.vc.identificazioneoggetti;

import it.lispa.bdl.shared.dto.CollezioneDTO;
import it.lispa.bdl.shared.dto.ProgettoDTO;

import java.math.BigDecimal;

import com.gwtplatform.mvp.client.UiHandlers;


/**
 * Interface IdentificazioneOggettiHandler.
 */
public interface IdentificazioneOggettiHandler extends UiHandlers{
	void goToProgettoInsertForm();

	void goToProgettoView(ProgettoDTO item);

	void goToProgettoModifyForm(ProgettoDTO item);

	void goToCollezioneInsertForm(BigDecimal cdProgetto);

	void goToCollezioneModifyForm(CollezioneDTO item);

	void goToCollezioneView(CollezioneDTO item);

}
