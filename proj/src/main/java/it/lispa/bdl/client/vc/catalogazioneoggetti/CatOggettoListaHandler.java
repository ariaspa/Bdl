package it.lispa.bdl.client.vc.catalogazioneoggetti;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * Interface CatOggettoListaHandler.
 */
public interface CatOggettoListaHandler extends UiHandlers {
	void onLRichiediValidazione();
	void onLImportaCatalogo();
	void onLVisualizza();
	void onLImportaExcel();
	void onLImportaToc();
}
