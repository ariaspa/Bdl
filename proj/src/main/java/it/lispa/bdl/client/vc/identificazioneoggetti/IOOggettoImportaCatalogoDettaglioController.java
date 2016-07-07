package it.lispa.bdl.client.vc.identificazioneoggetti;

import it.lispa.bdl.shared.dto.UnimarcDTO;
import it.lispa.bdl.shared.messages.IOOggettoImportaCatalogoMsg;
import it.lispa.bdl.shared.services.IdentOggettiService;
import it.lispa.bdl.shared.services.IdentOggettiServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;

/**
 * Class IOOggettoImportaCatalogoDettaglioController.
 */
public class IOOggettoImportaCatalogoDettaglioController extends PresenterWidget<IOOggettoImportaCatalogoDettaglioController.iIOOggettoImportaCatalogoDettaglioView>
implements IOOggettoImportaCatalogoDettaglioHandler{

	@Inject IOOggettoImportaCatalogoMsg messages;

	IdentOggettiServiceAsync servizioIdentOggetti = (IdentOggettiServiceAsync) GWT.create(IdentOggettiService.class);
	
	/**
	 * Interface iIOOggettoImportaCatalogoDettaglioView.
	 */
	public interface iIOOggettoImportaCatalogoDettaglioView extends PopupView, HasUiHandlers<IOOggettoImportaCatalogoDettaglioHandler> {
		
		/**
		 * Reset form.
		 *
		 * @param item  item
		 */
		public void resetForm(UnimarcDTO item);
	}
	@SuppressWarnings("unused")
	private EventBus eventBus;
	
	IOOggettoImportaCatalogoController listener;

	/**
	 * Istanzia un nuovo IO oggetto importa catalogo dettaglio controller.
	 *
	 * @param eventBus  event bus
	 * @param view  view
	 */
	@Inject
	public IOOggettoImportaCatalogoDettaglioController(final EventBus eventBus, final iIOOggettoImportaCatalogoDettaglioView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.eventBus = eventBus;
	}

	@Override
	protected void onBind() {
		super.onBind();
	}

	/* (non-Javadoc)
	 * @see it.lispa.bdl.client.vc.identificazioneoggetti.IOOggettoImportaCatalogoDettaglioHandler#onChiudi()
	 */
	@Override
	public void onChiudi() {
		getView().resetForm(null);
		getView().hide();		
	}
	

	/**
	 * Imposta listener.
	 *
	 * @param listener nuovo listener
	 */
	public void setListener(IOOggettoImportaCatalogoController listener) {
		this.listener = listener;
	}
}
