package it.lispa.bdl.client.vc.identificazioneoggetti;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * Interface IOOggettoListaHandler.
 */
public interface IOOggettoListaHandler extends UiHandlers {
	void onLInserisciOggetto();
	void onLImportaCatalogo();
	void onLImportaExcel();
	void onLVerifica();
	void onLVisualizza();
	void onLScaricaTemplate();
}
