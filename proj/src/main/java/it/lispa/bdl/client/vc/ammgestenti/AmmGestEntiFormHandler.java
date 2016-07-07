package it.lispa.bdl.client.vc.ammgestenti;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * Interface AmmGestEntiFormHandler.
 */
public interface AmmGestEntiFormHandler extends UiHandlers {
	
	void onVBtnAnnulla();
	void onVBtnCancella();
	void onVBtnModifica();
	
	void onFBtnAnnulla();
	void onFBtnCancella();
	void onFBtnSalva();
}
