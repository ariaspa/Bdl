package it.lispa.bdl.client.vc.identificazioneoggetti;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * Interface IOProgettoHandler.
 */
public interface IOProgettoHandler extends UiHandlers {
	void onVAnnulla();
	void onVCollezione();
	void onVModifica();
	void onFAnnulla();
	void onFSalva();
}
