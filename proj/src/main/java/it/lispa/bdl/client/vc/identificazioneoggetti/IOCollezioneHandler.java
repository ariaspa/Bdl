package it.lispa.bdl.client.vc.identificazioneoggetti;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * Interface IOCollezioneHandler.
 */
public interface IOCollezioneHandler extends UiHandlers {
	void onVAnnulla();
	void onVOggetto();
	void onVModifica();
	void onFAnnulla();
	void onFSalva();
}
