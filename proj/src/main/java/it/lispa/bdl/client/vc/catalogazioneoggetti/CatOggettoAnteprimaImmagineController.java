package it.lispa.bdl.client.vc.catalogazioneoggetti;

import it.lispa.bdl.shared.dto.ImmagineDTO;
import it.lispa.bdl.shared.messages.IOOggettoImportaCatalogoMsg;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

/**
 * Class CatOggettoAnteprimaImmagineController.
 */
public class CatOggettoAnteprimaImmagineController extends PresenterWidget<CatOggettoAnteprimaImmagineController.iCatOggettoAnteprimaImmagineView>
implements CatOggettoAnteprimaImmagineHandler{

	@Inject IOOggettoImportaCatalogoMsg messages;

	/**
	 * Interface iCatOggettoAnteprimaImmagineView.
	 */
	public interface iCatOggettoAnteprimaImmagineView extends PopupView, HasUiHandlers<CatOggettoAnteprimaImmagineHandler> {
		
		/**
		 * Reset image.
		 *
		 * @param img  img
		 */
		public void resetImage(ImmagineDTO img);
	}
	@SuppressWarnings("unused")
	private EventBus eventBus;
	
	CatalogazioneOggettiController listener;

	/**
	 * Istanzia un nuovo cat oggetto anteprima immagine controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public CatOggettoAnteprimaImmagineController(final EventBus eventBus, final iCatOggettoAnteprimaImmagineView view) {
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
	public void setListener(CatalogazioneOggettiController listener) {
		this.listener = listener;
	}
}
