package it.lispa.bdl.client.vc.validazioneoggetti;

import it.lispa.bdl.shared.dto.ImmagineDTO;
import it.lispa.bdl.shared.messages.IOOggettoImportaCatalogoMsg;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

/**
 * Class ValOggettoAnteprimaImmagineController.
 */
public class ValOggettoAnteprimaImmagineController extends PresenterWidget<ValOggettoAnteprimaImmagineController.iValOggettoAnteprimaImmagineView>
implements ValOggettoAnteprimaImmagineHandler{

	@Inject IOOggettoImportaCatalogoMsg messages;

	/**
	 * Interface iValOggettoAnteprimaImmagineView.
	 */
	public interface iValOggettoAnteprimaImmagineView extends PopupView, HasUiHandlers<ValOggettoAnteprimaImmagineHandler> {
		
		/**
		 * Reset image.
		 *
		 * @param img  img
		 */
		public void resetImage(ImmagineDTO img);
	}
	@SuppressWarnings("unused")
	private EventBus eventBus;
	
	ValidazioneOggettiController listener;

	/**
	 * Istanzia un nuovo val oggetto anteprima immagine controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public ValOggettoAnteprimaImmagineController(final EventBus eventBus, final iValOggettoAnteprimaImmagineView view) {
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
	public void setListener(ValidazioneOggettiController listener) {
		this.listener = listener;
	}
}
