package it.lispa.bdl.client.vc.ammgestutenti;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * Interface AmmGestUtentiFormHandler.
 */
public interface AmmGestUtentiFormHandler extends UiHandlers {
	
	void onVBtnAnnulla();
	void onVBtnCancella();
	void onVBtnModifica();
	
	void onFBtnAnnulla();
	void onFBtnCancella();
	void onFBtnSalva();
}
