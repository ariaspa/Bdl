package it.lispa.bdl.client.vc.ammgestindex;

import com.gwtplatform.mvp.client.UiHandlers;

/**
 * Interface AmmGestIndexFormHandler.
 */
public interface AmmGestIndexFormHandler extends UiHandlers {
	
	void onVBtnAnnulla();
	void onVBtnCancella();
	void onVBtnModifica();
	
	void onFBtnAnnulla();
	void onFBtnCancella();
	void onFBtnSalva();
}
