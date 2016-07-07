package it.lispa.bdl.client.vc.identificazioneoggetti;

import it.lispa.bdl.shared.dto.UnimarcDTO;

import java.util.List;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * Interface IOOggettoImportaCatalogoHandler.
 */
public interface IOOggettoImportaCatalogoHandler extends UiHandlers {
	void onChiudi();
	void onAvanti();
	void onOpacSbnImporta(List<UnimarcDTO> gridItems);
	void onOpacSbnVisualizza(List<UnimarcDTO> gridItems);
}
