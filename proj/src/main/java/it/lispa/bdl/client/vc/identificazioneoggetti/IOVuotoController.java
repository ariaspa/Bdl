package it.lispa.bdl.client.vc.identificazioneoggetti;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * Class IOVuotoController.
 */
public class IOVuotoController extends PresenterWidget<IOVuotoController.iIOVuotoView>{

	/**
	 * Interface iIOVuotoView.
	 */
	public interface iIOVuotoView extends View {
		
	}

	IdentificazioneOggettiController listener;

	/**
	 * Istanzia un nuovo IO vuoto controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public IOVuotoController(final EventBus eventBus, final iIOVuotoView view) {
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
	public IdentificazioneOggettiController getListener() {
		return listener;
	}


	/**
	 * Imposta listener.
	 *
	 * @param listener nuovo listener
	 */
	public void setListener(IdentificazioneOggettiController listener) {
		this.listener = listener;
	}

}