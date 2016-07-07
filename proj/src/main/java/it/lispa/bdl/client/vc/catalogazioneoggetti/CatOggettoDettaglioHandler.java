package it.lispa.bdl.client.vc.catalogazioneoggetti;

import it.lispa.bdl.shared.dto.ImmagineDTO;
import it.lispa.bdl.shared.dto.TocSommarioDTO;

import java.util.List;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * Interface CatOggettoDettaglioHandler.
 */
public interface CatOggettoDettaglioHandler extends UiHandlers {

	void onBtnAnnullaCatalogazioneView();
	void onBtnModificaCatalogazioneView();
	void onBtnEsportaMetsCatalogazioneView();
	void onBtnAnnullaCatalogazioneForm();
	void onBtnSalvaCatalogazioneForm();
	
	void onBtnBookreaderAnteprima();	
	void onBtnPdfAnteprima();
	void onBtnInviaCorrezioni();
	
	void onImgAnteprimaSelection(ImmagineDTO img);
	
	void onBtnSalvaSommario(List<TocSommarioDTO> saveTreeSommarioData);
}
