package it.lispa.bdl.client.vc.identificazioneoggetti;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * Interface IOOggettoDettaglioHandler.
 */
public interface IOOggettoDettaglioHandler extends UiHandlers {
	void onVAnnulla();
	void onVCancella();
	void onVModifica();
	void onFAnnulla();
	void onFCancella();
	void onFSalva();
}
