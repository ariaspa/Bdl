package it.lispa.bdl.client.vc.validazioneoggetti;

import it.lispa.bdl.shared.dto.ImmagineDTO;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * Interface ValOggettoDettaglioHandler.
 */
public interface ValOggettoDettaglioHandler extends UiHandlers {

	void onBtnAnnulla();
	void onBtnValida();
	void onBtnDiniega(String val);
	void onBtnEsportaMetsCatalogazioneView();
	void onBtnBookreaderAnteprima();	
	void onBtnPdfAnteprima();
	void onImgAnteprimaSelection(ImmagineDTO img);
}
