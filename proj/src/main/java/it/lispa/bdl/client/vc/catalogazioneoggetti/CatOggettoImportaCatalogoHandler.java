package it.lispa.bdl.client.vc.catalogazioneoggetti;

import it.lispa.bdl.shared.dto.UnimarcDTO;

import java.util.List;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * Interface CatOggettoImportaCatalogoHandler.
 */
public interface CatOggettoImportaCatalogoHandler extends UiHandlers {
	void onChiudi();
	void onAvanti();
	void onOpacSbnImporta(UnimarcDTO gridItem);
	void onOpacSbnVisualizza(List<UnimarcDTO> gridItems);
}
