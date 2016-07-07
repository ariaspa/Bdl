package it.lispa.bdl.client.vc.pubblicazioneoggetti;

import it.lispa.bdl.shared.dto.ImmagineDTO;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * Interface PubOggettoDettaglioHandler.
 */
public interface PubOggettoDettaglioHandler extends UiHandlers {

	void onBtnAnnulla();
	void onBtnPubblica();
	void onBtnDiniega(String val);
	void onBtnEsportaMetsCatalogazioneView();
	void onBtnBookreaderAnteprima();	
	void onBtnPdfAnteprima();
	void onImgAnteprimaSelection(ImmagineDTO img);
}
