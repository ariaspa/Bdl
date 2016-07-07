package it.lispa.bdl.client.vc.catalogazioneoggetti;

import com.gwtplatform.mvp.client.UiHandlers;

public interface CatOggettoImportaTocHandler extends UiHandlers {
	void onScaricaTemplate();
	void onUploadComplete();
	void onChiudi();
}
