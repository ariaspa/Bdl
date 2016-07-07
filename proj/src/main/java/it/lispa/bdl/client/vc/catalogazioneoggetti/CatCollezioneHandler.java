package it.lispa.bdl.client.vc.catalogazioneoggetti;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * Interface CatCollezioneHandler.
 */
public interface CatCollezioneHandler extends UiHandlers {
	void onVAnnulla();
	void onVModifica();
	void onFAnnulla();
	void onFSalva();
}
