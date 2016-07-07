package it.lispa.bdl.client.vc.catalogazioneoggetti;

import it.lispa.bdl.shared.dto.CollezioneDTO;
import it.lispa.bdl.shared.dto.ProgettoDTO;

import com.gwtplatform.mvp.client.UiHandlers;


/**
 * Interface CatalogazioneOggettiHandler.
 */
public interface CatalogazioneOggettiHandler extends UiHandlers{

	void goToProgettoView(ProgettoDTO item);

	void goToProgettoModifyForm(ProgettoDTO item);

	void goToCollezioneModifyForm(CollezioneDTO item);

	void goToCollezioneView(CollezioneDTO item);

}
