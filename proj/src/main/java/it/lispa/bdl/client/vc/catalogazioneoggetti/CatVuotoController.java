package it.lispa.bdl.client.vc.catalogazioneoggetti;

import it.lispa.bdl.shared.dto.OggettoDTO;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * Class CatVuotoController.
 */
public class CatVuotoController extends PresenterWidget<CatVuotoController.iCatVuotoView>{

	/**
	 * Interface iCatVuotoView.
	 */
	public interface iCatVuotoView extends View {
		
	}

	CatalogazioneOggettiController listener;

	/**
	 * Istanzia un nuovo cat vuoto controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public CatVuotoController(final EventBus eventBus, final iCatVuotoView view) {
		super(eventBus, view);
	}


	@Override
	protected void onBind() {
		super.onBind();
		
	}

	@Override
	protected void onReveal() {
		
	}
	
	protected void onReset() {
		super.onReset();
		
	}


	/**
	 * Legge listener.
	 *
	 * @return listener
	 */
	public CatalogazioneOggettiController getListener() {
		return listener;
	}


	/**
	 * Imposta listener.
	 *
	 * @param listener nuovo listener
	 */
	public void setListener(CatalogazioneOggettiController listener) {
		this.listener = listener;
	}
	
	/**
	 * Activate view.
	 *
	 * @param item  item
	 */
	public void activateView(OggettoDTO item){
		
	}
}