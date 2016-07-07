package it.lispa.bdl.client.vc.pubblicazioneoggetti;

import it.lispa.bdl.shared.dto.ImmagineDTO;
import it.lispa.bdl.shared.messages.IOOggettoImportaCatalogoMsg;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

/**
 * Class PubOggettoAnteprimaImmagineController.
 */
public class PubOggettoAnteprimaImmagineController extends PresenterWidget<PubOggettoAnteprimaImmagineController.iPubOggettoAnteprimaImmagineView>
implements PubOggettoAnteprimaImmagineHandler{

	@Inject IOOggettoImportaCatalogoMsg messages;

	/**
	 * Interface iPubOggettoAnteprimaImmagineView.
	 */
	public interface iPubOggettoAnteprimaImmagineView extends PopupView, HasUiHandlers<PubOggettoAnteprimaImmagineHandler> {
		
		/**
		 * Reset image.
		 *
		 * @param img  img
		 */
		public void resetImage(ImmagineDTO img);
	}
	@SuppressWarnings("unused")
	private EventBus eventBus;
	
	PubblicazioneOggettiController listener;

	/**
	 * Istanzia un nuovo pub oggetto anteprima immagine controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public PubOggettoAnteprimaImmagineController(final EventBus eventBus, final iPubOggettoAnteprimaImmagineView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.eventBus = eventBus;
	}

	@Override
	protected void onBind() {
		super.onBind();
	}
	
	/**
	 * Imposta immagine.
	 *
	 * @param img nuovo immagine
	 */
	public void setImmagine(ImmagineDTO img){
		getView().resetImage(img);
	}

	/**
	 * Imposta listener.
	 *
	 * @param listener nuovo listener
	 */
	public void setListener(PubblicazioneOggettiController listener) {
		this.listener = listener;
	}
}
